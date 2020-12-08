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

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;


/**
 * @author xiangxing
 *
 * java写的方法有问题，正则匹配不对
 */
public class AsmTransform2 extends Transform {

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
                           if(file.isDirectory()){
                               System.out.println("CustomPlugin AsmTransform interor " +
                                       "file isDirectory continue ");
                               continue;
                           }
                           //step1： init classReader
                           ClassReader classReader =
                                   new ClassReader(new FileInputStream(file));
                           //step2：define a classWriter
                           ClassWriter classWriter = new ClassWriter(classReader,
                                   ClassWriter.COMPUTE_MAXS);
                           ClassVisitor classVisitor = new PageClassVisitor(classWriter);
                           //step3： accept method
                           classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES);

                           //write
                           byte[] bytes = classWriter.toByteArray();
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
