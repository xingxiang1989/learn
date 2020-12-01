package com.some.demo_compiler;

import com.google.auto.service.AutoService;
import com.some.demo_annotation.HelloAnnotation;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

@AutoService(Processor.class)
public class HelloAnnotationProcessor extends BaseProcessor {


    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        for(TypeElement element: set){
            if(element.getQualifiedName().toString().equals(HelloAnnotation.class.getCanonicalName())){
                //main method
                MethodSpec main = MethodSpec
                        .methodBuilder("main")
                        .addModifiers(Modifier.PUBLIC,Modifier.STATIC)
                        .returns(void.class)
                        .addParameter(String[].class, "args")
                        .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
                        .build();

                //HelloWorld class
                TypeSpec helloWorld = TypeSpec
                        .classBuilder("HelloWorld")
                        .addModifiers(Modifier.PUBLIC,Modifier.FINAL)
                        .addMethod(main)
                        .build();


                try {
                    JavaFile javaFile = JavaFile.builder("com.example",helloWorld)
                            .addFileComment(" This codes are generated automatically. Do not modify!")
                            .build();
                    javaFile.writeTo(mFiler);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(HelloAnnotation.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }


}
