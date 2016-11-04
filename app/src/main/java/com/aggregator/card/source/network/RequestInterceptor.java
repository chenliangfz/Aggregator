package com.aggregator.card.source.network;


import android.content.Context;


import com.aggregator.card.util.NetworkUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class RequestInterceptor implements Interceptor {

    Context mContext;

    public RequestInterceptor(Context context) {
        mContext = context;
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request request = original.newBuilder()
                //                .header("pixel", Constant.PIXEL)
                .header("network-state", NetworkUtil.isWifi(mContext) == true ? "1" : "0")
                .method(original.method(), original.body())

                .build();

        return chain.proceed(request);
    }


}
