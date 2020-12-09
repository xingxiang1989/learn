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

    /**
     * visit all the class ,object entity,interface ,activity and more
     * @param version
     * @param access
     * @param className
     * @param signature
     * @param superName
     * @param interfaces
     */
    @Override
    public void visit(int version, int access, String className, String signature, String superName, String[] interfaces) {
        super.visit(version, access, className, signature, superName, interfaces);
        this.className = className;
        this.superName = superName;
    }

    /**
     * look at super class , and learn about readings
     * @param access
     * @param methodName
     * @param descriptor
     * @param signature
     * @param exceptions
     * @return
     */
    @Override
    public MethodVisitor visitMethod(int access, String methodName, String descriptor, String signature, String[] exceptions) {

        MethodVisitor methodVisitor = cv.visitMethod(access, methodName, descriptor, signature, exceptions);

        System.out.println("superName = " + superName + " className = " + className);

        //判断是否自己要匹配的方法(这里匹配onCreate和onDestroy方法)
        if ("android/app/Activity".equals(superName)
                || "com/some/mvvmdemo/base/BaseActiviy".equals(superName)) {

            System.out.println("superName 匹配 ");


            if (methodName.startsWith("onCreate")) {
                return new PageMethodVisitor(methodVisitor, className, methodName);
            }
            /**
             * if the class don't hava onDestroy，it will not insert code
             */
            if (methodName.startsWith("onDestroy")) {
                return new PageMethodVisitor(methodVisitor, className, methodName);
            }
        }else{
            System.out.println("superName  不匹配 ");
        }
        return methodVisitor;
    }


}
