package com.some.demo_compiler;

import com.some.demo_compiler.utils.Logger;

import org.apache.commons.lang3.reflect.TypeUtils;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * @author xiangxing
 */
public abstract class BaseProcessor extends AbstractProcessor {

    Filer mFiler;
    Logger logger;
    Types types;
    Elements elementUtils;
    TypeUtils typeUtils;
    // Module name, maybe its 'app' or others
    String moduleName = null;
    // If need generate router doc
    boolean generateDoc;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnv.getFiler();
        types = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
        logger = new Logger(processingEnv.getMessager());
    }
}
