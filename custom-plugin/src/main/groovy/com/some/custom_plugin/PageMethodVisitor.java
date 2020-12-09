package com.some.custom_plugin;


import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author xiangxing
 */
public class PageMethodVisitor extends MethodVisitor {

    //当前的类名
    private String className;

    //当前的方法名
    private String methodName;

    PageMethodVisitor(MethodVisitor methodVisitor, String className, String methodName) {
        super(Opcodes.ASM7, methodVisitor);
        this.className = className;
        this.methodName = methodName;
    }

    /**
     * beacause this methodName is regular，is OnCreate or onDestroy，
     * so it doesnt do much
     */
    @Override
    public void visitCode() {
        super.visitCode();

        //在这里插入自己的字节码指令
        //可以插入页面打开关闭的打点上报代码，这里用log打印代替了
        mv.visitLdcInsn("Page_TAG");//可以用来过滤log日志的tag
        mv.visitLdcInsn(className + "--->" + methodName);//插入要打印的内容
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "android/util/Log", "i",
                "(Ljava/lang/String;Ljava/lang/String;)I", false);
        mv.visitInsn(Opcodes.POP);
    }
}
