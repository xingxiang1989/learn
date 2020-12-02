package com.some.demo_compiler;

import com.google.auto.service.AutoService;
import com.some.demo_annotation.BindView;
import com.some.demo_annotation.DeepLink;
import com.some.demo_compiler.utils.ClassCreatorFactory;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.JavaFileObject;

/**
 * @author xiangxing
 *
 *
 */
@AutoService(Processor.class)
public class BindViewProcessor extends BaseProcessor {

    private Map<String, ClassCreatorFactory> mClassCreatorFactoryMap = new HashMap<>();

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        mClassCreatorFactoryMap.clear();
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(BindView.class);

        for (Element element : elements) {
            // instanceof variable field
            VariableElement variableElement = (VariableElement) element;

            TypeElement classElement = (TypeElement) variableElement.getEnclosingElement();
            // get fullname
            String fullClassName = classElement.getQualifiedName().toString();


            ClassCreatorFactory factory = mClassCreatorFactoryMap.get(fullClassName);
            if (factory == null) {
                factory = new ClassCreatorFactory(elementUtils, classElement);
                mClassCreatorFactoryMap.put(fullClassName, factory);
            }
            BindView bindViewAnnotation = variableElement.getAnnotation(BindView.class);
            int id = bindViewAnnotation.value();
            factory.putElement(id, variableElement);
        }

        //get all fullname set
        for (String key : mClassCreatorFactoryMap.keySet()) {
            ClassCreatorFactory factory = mClassCreatorFactoryMap.get(key);
            try {
                JavaFileObject fileObject = processingEnv.getFiler().createSourceFile(
                        factory.getClassFullName(), factory.getTypeElement());
                Writer writer = fileObject.openWriter();

                writer.write(factory.generateJavaCode());
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> mSet = new HashSet<>();
        mSet.add(BindView.class.getCanonicalName());
        logger.info(" getCanonicalName = " + BindView.class.getCanonicalName());
        return mSet;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
