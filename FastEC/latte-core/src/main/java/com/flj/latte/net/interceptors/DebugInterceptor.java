package com.flj.latte.net.interceptors;

import android.support.annotation.NonNull;
import android.support.annotation.RawRes;

import com.flj.latte.util.file.FileUtil;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by 傅令杰 on 2017/4/11
 */

public class DebugInterceptor extends BaseInterceptor {

    private final String DEBUG_URL;
    private final int DEBUG_RAW_ID;

    public DebugInterceptor(String debugUrl, int rawId) {
        this.DEBUG_URL = debugUrl;
        this.DEBUG_RAW_ID = rawId;
    }

    private Response getResponse(Chain chain, String json) {
        return new Response.Builder()
                .code(200)
                .addHeader("Content-Type", "application/json")
                .body(ResponseBody.create(MediaType.parse("application/json"), json))
                .message("OK")
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .build();
    }
    //debug的封装，根据rawId查询获取json；
    private Response debugResponse(Chain chain, @RawRes int rawId) {
        final String json = FileUtil.getRawFile(rawId); //根据rawId取出原始文件；
        return getResponse(chain, json); //返回Response请求的响应；
    }

    /**
     * 说明：此时存在的json文件是存在在单个应用程序的res/raw文件夹下的json；
     * @param chain
     * @return
     * @throws IOException
     */
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        final String url = chain.request().url().toString();//得到拦截的url；
        if (url.contains(DEBUG_URL)) {//拦截的url包含了DEBUG_URL，返回存在的json文件；
            return debugResponse(chain, DEBUG_RAW_ID);
        }
        return chain.proceed(chain.request());//否则原样返回数据；
    }
}
