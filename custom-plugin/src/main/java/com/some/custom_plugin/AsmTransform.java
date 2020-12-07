package com.some.custom_plugin;

import com.android.build.api.transform.Context;
import com.android.build.api.transform.DirectoryInput;
import com.android.build.api.transform.Format;
import com.android.build.api.transform.JarInput;
import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.api.transform.TransformOutputProvider;
import com.android.build.gradle.internal.TaskManager;
import com.android.build.gradle.internal.pipeline.TransformManager;
import com.android.utils.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.ClassWriter;

/**
 * @author xiangxing
 */
public class AsmTransform extends Transform {

    Pattern pattern = Pattern.compile("/*.class");

    @Override
    public String getName() {
        return "ASMLearning";
    }

    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS;
    }

    @Override
    public Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT;
    }

    @Override
    public boolean isIncremental() {
        return false;
    }

    @Override
    public void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation);
        System.out.println("CustomPlugin AsmTransform start transform");

        Collection<TransformInput> inputs = transformInvocation.getInputs();
        TransformOutputProvider outputProvider = transformInvocation.getOutputProvider();

        for(TransformInput transformInput: inputs){
            //deal jarinputs
            Collection<JarInput> jarInputs = transformInput.getJarInputs();
            for(JarInput jarInput : jarInputs){
                File jarFile = jarInput.getFile();
                File destJar = outputProvider.getContentLocation(jarInput.getName(),
                        jarInput.getContentTypes(),
                        jarInput.getScopes(), Format.JAR);
                FileUtils.copyFile(jarFile, destJar);
            }

            //deal class
            Collection<DirectoryInput> directoryInputs =
                    transformInput.getDirectoryInputs();

            for(DirectoryInput directoryInput: directoryInputs){
                File dir = directoryInput.getFile();
                if(dir.isDirectory()){
                   List<File> fileList =  FileUtils.find(dir,pattern);
                   if(fileList != null && fileList.size() > 0){
                       System.out.println("CustomPlugin AsmTransform fileList.size =" + fileList.size());

                       for(File file : fileList){
                           ClassReader classReader = new ClassReader(file.getAbsolutePath());
                           ClassWriter classWriter = new ClassWriter(classReader,
                                   ClassWriter.COMPUTE_MAXS);
                           //访问class文件的内容
                           ClassVisitor classVisitor = new PageClassVisitor(classWriter);
                           //调用classVisitor的各个方法
                           classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES);

                           //将修改的字节码以byte数组返回
                           byte[] bytes = classWriter.toByteArray();
                           //通过文件流写入方式覆盖原先的内容，完成class文件的修改
                           FileOutputStream outputStream =
                                   new FileOutputStream(file.getAbsolutePath());
                           outputStream.write(bytes);
                           outputStream.close();
                       }
                   }else{
                       System.out.println("CustomPlugin AsmTransform fileList == null");

                   }
                }
                File destDir =
                        outputProvider.getContentLocation(directoryInput.getName(),
                                directoryInput.getContentTypes(),
                                directoryInput.getScopes(),
                                Format.DIRECTORY);
                FileUtils.copyDirectory(dir,destDir);
            }
        }
    }
}
