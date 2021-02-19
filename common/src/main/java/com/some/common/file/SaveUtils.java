package com.some.common.file;

import android.graphics.Bitmap;

import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.PathUtils;

/**
 * @author xiangxing
 */
public class SaveUtils {
    /**
     * 练习将bitmap存储到本地sd卡
     * 都是使用blankj提供的方法，生成文件路径，创建文件（PathUtils，FileUtils ）
     * 将图片转换成byte[]数据，（ImageUtils）
     * 存储到本地（FileIOUtils）
     *
     * @param bitmap
     * @return
     */
    public static boolean saveBitmapToFile(Bitmap bitmap, String fileName) {

        String dir = PathUtils.getExternalAppFilesPath() + "/picture/";
        String fullPath = dir + fileName;
        FileUtils.createOrExistsFile(fullPath);
        byte[] bytes = ImageUtils.bitmap2Bytes(bitmap, Bitmap.CompressFormat.JPEG);
        if (bytes == null) {
            return false;
        }
        return FileIOUtils.writeFileFromBytesByStream(fullPath, bytes);
    }

    /**
     * 直接使用imageUtils，里面包含上面方法的所有功能
     * @param bitmap
     * @param fileName
     * @return
     */
    public static boolean saveBitmapToFile2(Bitmap bitmap, String fileName) {

        String dir = PathUtils.getExternalAppFilesPath() + "/picture/";
        String fullPath = dir + fileName;
        return ImageUtils.save(bitmap,fullPath,Bitmap.CompressFormat.JPEG);
    }
}
