package com.some.custom_plugin;


import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author xiangxing
 *
 * ASM操作class的类
 */
public class PageClassVisitor extends ClassVisitor {

    /**
     * like this :com/znh/gradle/plugin/demo/MainActivity
     */
    private String className;

    /**
     * className super parent
     * like this androidx/appcompat/app/AppCompatActivity
     */
    private String superName;

    public PageClassVisitor(ClassVisitor classVisitor) {
        super(Opcodes.ASM7, classVisitor);
    }

    @Override
    public void visit(int version, int access, String className, String signature, String superName, String[] interfaces) {
        super.visit(version, access, className, signature, superName, interfaces);
        this.className = className;
        this.superName = superName;
    }

    @Override
    public MethodVisitor visitMethod(int access, String methodName, String descriptor, String signature, String[] exceptions) {

        MethodVisitor methodVisitor = cv.visitMethod(access, methodName, descriptor, signature, exceptions);

        //判断是否自己要匹配的方法(这里匹配onCreate和onDestroy方法)
        if ("android/support/v7/app/AppCompatActivity".equals(superName)
                || "androidx/appcompat/app/AppCompatActivity".equals(superName)) {
            if (methodName.startsWith("onCreate")) {
                return new PageMethodVisitor(methodVisitor, className, methodName);
            }
            if (methodName.startsWith("onDestroy")) {
                return new PageMethodVisitor(methodVisitor, className, methodName);
            }
        }
        return methodVisitor;
    }


}
