package com.some.mvvmdemo;

import android.os.Bundle;
import android.util.Base64;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.LogUtils;
import com.some.algorithm.BASE64Decoder;
import com.some.algorithm.BASE64Encoder;
import com.some.algorithm.DesUtil;
import com.some.algorithm.RSAEncrypt;
import com.some.mvvmdemo.base.BaseActiviy;
import com.some.mvvmdemo.databinding.ActivityAlgorithmBinding;

import java.io.IOException;

/**
 * @author xiangxing
 * 经过使用android.util.base64, blankj,自定义的加解密，结果是一致的，
 * 优先采用blanj的方式。
 *
 * rsa 使用blankj的有问题
 *
 * des 两者皆可，并且可以
 */
public class AlgorithmActivity extends BaseActiviy {

    private String text = "df723820";
    private ActivityAlgorithmBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_algorithm);

        try{
            /**
             * 用Base64算法加密，当字符串过长（一般超过76）时会自动在中间加一个换行符，
             * 字符串最后也会加一个换行符。导致和其他模块对接时结果不一致。
             * 要使用 Base64.NO_WRAP
             */
            String encoder0 = Base64.encodeToString(text.getBytes(),Base64.NO_WRAP);
            LogUtils.d("使用android.util.base64 加密= " + encoder0);


            String encoder1 = EncodeUtils.base64Encode2String(text.getBytes());
            LogUtils.d("使用blankj base64 加密= " + encoder1);

            String encoder2 = new BASE64Encoder().encode(text.getBytes());
            LogUtils.d("使用其他方式 base64 加密= " + encoder2);


            String decode1 = new String(EncodeUtils.base64Decode(encoder1));
            LogUtils.d("使用blankj base64 解密= " + decode1);

            String decode2 = new String(new BASE64Decoder().decodeBuffer(encoder2));
            LogUtils.d("使用其他方式 base64 解密= " + decode2);

            //---------RSA-----------//

            RSAEncrypt.main();

            byte[] rsaEncode1 = EncryptUtils.encryptRSA(text.getBytes(),
                    EncodeUtils.base64Encode(RSAEncrypt.keyMap.get(0)),true,"RSA");

            if(rsaEncode1 != null){
                LogUtils.d("使用blankj rsa 加密= " + new String(rsaEncode1));
            }else{
                LogUtils.d("使用blankj rsa 加密 == null");
            }

            //---------对称加密 DesUtil-------//
            String desEncode1 = DesUtil.encrypt(text);
            LogUtils.d("使用DesUtil des 加密= " + desEncode1);

            String desEncode2 =
                    new String(EncryptUtils.encryptDES2Base64(text.getBytes(), "37ad9f8e".getBytes(), "DES", null));
            LogUtils.d("使用blankj des 加密= " + desEncode2);


        }catch (Exception e){

            LogUtils.d(" e=" + e.getMessage());
        }
    }
}
