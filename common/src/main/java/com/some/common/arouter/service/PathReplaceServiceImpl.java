package com.some.common.arouter.service;

import android.content.Context;
import android.net.Uri;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.PathReplaceService;
import com.some.common.arouter.RouterUrl;

@Route(path = RouterUrl.SERVICE_PATH_REPLACE)
public class PathReplaceServiceImpl implements PathReplaceService {
    @Override
    public String forString(String path) {
//        if (path.startsWith(RouterUrl.SCHEME) || path.startsWith(RouterUrl.SYSTEM)) {
//            return path;
//        } else {
//            if (path.startsWith("/")) {
//                return RouterUrl.SCHEME + path.substring(1);
//            } else {
//                return RouterUrl.SCHEME + path;
//            }
//        }
        return path;
    }

    @Override
    public Uri forUri(Uri uri) {
        return uri;
    }

    @Override
    public void init(Context context) {

    }
}