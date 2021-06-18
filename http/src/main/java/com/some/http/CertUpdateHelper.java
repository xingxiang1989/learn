package com.some.http;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 证书更新处理
 */
public class CertUpdateHelper {

    private final static String TAG = CertUpdateHelper.class.getSimpleName();

    private final static String CERT_UPDATE_URL = "https://download.hikops.com/cert/cer.json";
    private final static String PRIVATE_CERT_NAME = "server.v3.pem.der";
    private final static int TIMEOUT = 15000;

    private File mCertLocalPath;
    private Context mContext;
    private OkHttpClient mHttpClient;
    private ExecutorService mExecutorService;

    /** 单例 */
    private static CertUpdateHelper sInstance;

    public static CertUpdateHelper getInstance() {
        if (sInstance == null) {
            synchronized (CertUpdateHelper.class) {
                if (sInstance == null) {
                    sInstance = new CertUpdateHelper();
                }
            }
        }
        return sInstance;
    }

    private CertUpdateHelper() {
        mExecutorService = Executors.newFixedThreadPool(1);
    }

    /**
     * 检查证书更新情况
     */
    public void checkCertUpdate() {
        Log.d(TAG, "checkCertUpdate");
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                initHttpClient();
                try {
                    Response response =
                        mHttpClient.newCall(new Request.Builder().url(CERT_UPDATE_URL).build()).execute();
                    String json = response.body().string();
                    Log.d(TAG, json);

                    JSONArray jsonArray = new JSONArray(json);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        downloadCert(jsonArray.getString(i));
                    }
                } catch (Exception e) {
                    Log.w(TAG, e);
                }
            }
        });
    }

    /**
     * 获取证书
     */
    public File[] getLocalCert() {
        initCertLocalPath();
        return mCertLocalPath.listFiles();
    }

    /**
     * 下载证书 ["https://download.ezvizops.com/cert/Entrust_CA.pem.cer"]
     */
    private void downloadCert(final String certUrl) {
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.isEmpty(certUrl)) {
                    return;
                }
                Log.d(TAG, "download " + certUrl);

                initCertLocalPath();

                String fileName = "";
//                String fileName = FileUtil.getFileNameByUrl(certUrl);
                File file = new File(mCertLocalPath, fileName);
                if (file.exists()) {
                    Log.d(TAG, "download " + certUrl + " exists");
                    return;
                }

                File tempFile = new File(mCertLocalPath, fileName + ".tmp");

                initHttpClient();

                InputStream inputStream = null;
                OutputStream outputStream = null;
                try {
                    Response response = mHttpClient.newCall(new Request.Builder().url(certUrl).build()).execute();
                    Log.d(TAG, "download " + certUrl + " code:" + response.code());
                    if (response.isSuccessful()) {
                        if (!tempFile.exists()) {
                            tempFile.createNewFile();
                        }

                        inputStream = response.body().byteStream();
                        outputStream = new FileOutputStream(tempFile);
//                        IOUtil.copyLarge(inputStream, outputStream);

//                        IOUtil.closeQuietly(inputStream);
                        inputStream = null;
//                        IOUtil.closeQuietly(outputStream);
                        outputStream = null;

                        tempFile.renameTo(file);
                        RetrofitFactory.clearCache();
                        Log.d(TAG, "download " + certUrl + " done");
                    }
                } catch (Exception e) {
                    Log.w(TAG, e);
                } finally {
//                    IOUtil.closeQuietly(inputStream);
//                    IOUtil.closeQuietly(outputStream);
                }
            }
        });
    }

    private void initHttpClient() {
        if (mHttpClient == null) {
            HttpsUtils.SSLParams sslParams = createSSLSocketFactory(mContext);
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(TIMEOUT, TimeUnit.MILLISECONDS);

            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override public void log(String message) {
                    //Platform.get().log(INFO, message, null);
                    Log.i("okhttp", message);
                }
            });
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(httpLoggingInterceptor);

            mHttpClient = builder.build();
        }
    }

    private void initCertLocalPath() {
        if (mCertLocalPath == null) {
            mCertLocalPath = new File(mContext.getFilesDir(), "cert");
            if (!mCertLocalPath.exists()) {
                mCertLocalPath.mkdirs();
            }
        }
    }

    private HttpsUtils.SSLParams createSSLSocketFactory(Context context) {
        InputStream certificate = null;
        try {
            certificate = context.getAssets().open(PRIVATE_CERT_NAME);
            return HttpsUtils.getSslSocketFactory(certificate);
        } catch (Exception e) {
            Log.w(TAG, e);
        } finally {
//            IOUtil.closeQuietly(certificate);
        }
        return HttpsUtils.getSslSocketFactory();
    }
}
