package com.example.azbowtest.network;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CustomeInterceptor implements Interceptor {

    private static final String TAG="CustomeInterceptor ";
    private String clientId;
    public CustomeInterceptor(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Log.i(TAG,"CALL_INTERCEPTOR");
        Request request = chain.request();
        request = request.newBuilder()
                .addHeader("Authorization", "Client-ID " + clientId)
                .build();
        Log.i(TAG,"RESPONSE_CODE_ "+chain.proceed(request).code());
        return chain.proceed(request);
    }
}
