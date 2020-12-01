package com.some.demo_compiler;

import com.google.auto.service.AutoService;
import com.some.demo_annotation.DeepLink;
import com.some.demo_annotation.DeepLinkEntry;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.sun.tools.javac.util.Pair;

import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * @author xiangxing
 */
@AutoService(Processor.class)
public class DeepLinkProcessor extends BaseProcessor{

    private static final String DEEPLINK_PACKAGE = "com.wenkiwu.deeplink";
    private static final String DEEPLINK_LOADER = "DeepLinkLoader";
    private static final String DEEPLINK_DISPATCH = "DeepLinkDispatch";

    private static final ClassName ANDROID_INTENT = ClassName.get("android.content", "Intent");
    private static final ClassName ANDROID_ACTIVITY = ClassName.get("android.app", "Activity");
    private static final ClassName ANDROID_URI = ClassName.get("android.net", "Uri");

    private static final ClassName CLASS_DEEPLINKENTRY = ClassName.get(DeepLinkEntry.class);
    private static final ClassName CLASS_DEEPLINKLOADER = ClassName.get(DEEPLINK_PACKAGE, DEEPLINK_LOADER);

    @Override
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnvironment) {
        logger.info(" DeepLinkProcessor process start ");
        if(CollectionUtils.isNotEmpty(annotations)){
            Set<? extends Element> deepLinkElements =
                    roundEnvironment.getElementsAnnotatedWith(DeepLink.class);

            logger.info(" DeepLinkProcessor process deepLinkElements size = " + deepLinkElements.size());

            //1. record key-value data in arrayList
            List<Pair<String, TypeElement>> entryList = new ArrayList<>();
            for (Element element : deepLinkElements) {
                TypeElement typeElement = (TypeElement) element;

                DeepLink deepLink = element.getAnnotation(DeepLink.class);
                String path = deepLink.value();
                Pair<String, TypeElement> pair = new Pair<>(path, typeElement);
                entryList.add(pair);
            }

            try {
                generateLoaderClass(entryList);
                generateDispatchClass();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;
        }
        return false;

    }

    /**
     * search annotation and construct DeepLinkLoader, save data in class
     * @param entryList
     * @throws IOException
     */
    private void generateLoaderClass(List<Pair<String, TypeElement>> entryList) throws IOException {
        //define list and add data to list
        CodeBlock.Builder initializer = CodeBlock.builder()
                .add("$T.unmodifiableList($T.asList(\n", Collections.class, Arrays.class)
                .indent();
        int totalEntrys = entryList.size();
        for (int i = 0; i < totalEntrys; i++) {
            Pair<String, TypeElement> pair = entryList.get(i);
            ClassName activity = ClassName.get(pair.snd);
            String path = pair.fst;
            initializer.add("new com.some.demo_annotation.DeepLinkEntry($S, $T.class)$L\n",
                    path, activity, (i < totalEntrys - 1) ? "," : "");
        }
        //define Field: name public
        FieldSpec registry = FieldSpec.builder(
                ParameterizedTypeName.get(List.class, DeepLinkEntry.class),
                "REGISTRY", Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
                .initializer(initializer.unindent().add("))").build())
                .build();

        MethodSpec parseMethod = MethodSpec.methodBuilder("parseUri")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(String.class, "uri")
                .returns(DeepLinkEntry.class)
                .beginControlFlow("for (com.some.demo_annotation.DeepLinkEntry entry : REGISTRY)")
                .beginControlFlow("if (uri.startsWith(entry.getPath()))")
                .addStatement("return entry")
                .endControlFlow()
                .endControlFlow()
                .addStatement("return null")
                .build();

        TypeSpec deepLinkLoader = TypeSpec.classBuilder("DeepLinkLoader")
                .addModifiers(Modifier.PUBLIC)
                .addField(registry)
                .addMethod(parseMethod)
                .build();

        JavaFile.builder(DEEPLINK_PACKAGE, deepLinkLoader)
                .build()
                .writeTo(mFiler);
    }


    /**
     * method
     * @throws IOException
     */
    private void generateDispatchClass() throws IOException {
        // create constructor
        MethodSpec constructor = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addStatement("this.loader = new DeepLinkLoader()")
                .build();

        MethodSpec dispatch = MethodSpec.methodBuilder("dispatchFrom")
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addParameter(ANDROID_ACTIVITY, "activity")
                .beginControlFlow("if(activity == null)")
                .addStatement("throw new NullPointerException($S)", "activity is null")
                .endControlFlow()
                .addStatement("$T sourceIntent = activity.getIntent()", ANDROID_INTENT)
                .addStatement("$T uri = sourceIntent.getData()", ANDROID_URI)
                .beginControlFlow("if(uri != null)")
                .addStatement("$T uriString = uri.toString()", String.class)
                .addStatement("$T deepLinkEntry = loader.parseUri(uriString)", CLASS_DEEPLINKENTRY)
                .beginControlFlow("if (deepLinkEntry != null)")
                .addStatement("Intent newIntent = new Intent(activity, deepLinkEntry.getActivityClass())")
                .beginControlFlow("if(sourceIntent.getExtras() != null)")
                .addStatement("newIntent.putExtras(sourceIntent.getExtras())")
                .endControlFlow()
                .addStatement("newIntent.setData(sourceIntent.getData())")
                .addComment("add some custom data")
                .addStatement("activity.startActivity(newIntent)")
                .endControlFlow()
                .endControlFlow()
                .build();

        FieldSpec loader = FieldSpec.builder(CLASS_DEEPLINKLOADER, "loader", Modifier.PRIVATE)
                .build();

        TypeSpec deepLinkDispatch = TypeSpec.classBuilder(DEEPLINK_DISPATCH)
                .addModifiers(Modifier.PUBLIC)
                .addField(loader)
                .addMethod(constructor)
                .addMethod(dispatch)
                .build();

        JavaFile.builder(DEEPLINK_PACKAGE, deepLinkDispatch).build().writeTo(mFiler);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> mSet = new HashSet<>();
        mSet.add(DeepLink.class.getCanonicalName());
        logger.info(" getCanonicalName = " + DeepLink.class.getCanonicalName());
        return mSet;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}

