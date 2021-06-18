package com.some.common.util

import android.os.Environment
import com.blankj.utilcode.util.Utils

/**
 * Created by xiangxing5 on 2021/6/17.
 * Describe:
 */
object FilePathUtils {

    /**
     * 不需要存储权限可获取存储地址
     */
    fun getPath(): String {
        return if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() ||
                !Environment.isExternalStorageRemovable()) {
            //外部存储可用 /storage/emulated/0/Android/data/package/files
            Utils.getApp().getExternalFilesDir("")?.path ?: ""
        } else {
            //外部存储不可用 /data/data/package
            Utils.getApp().filesDir.path
        }
    }
}