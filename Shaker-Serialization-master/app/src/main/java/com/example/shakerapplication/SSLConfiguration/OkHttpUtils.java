package com.example.shakerapplication.SSLConfiguration;

import android.content.Context;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OkHttpUtils {

    public static Retrofit createRetrofit(Context context, String baseUrl, String certificateFilename, String password) {
        try {
            // Load SSL certificate from assets folder
            InputStream inputStream = context.getAssets().open(certificateFilename);
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(inputStream, password.toCharArray());
            inputStream.close();

            // Set up SSL socket factory with certificate and password
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("X509");
            keyManagerFactory.init(keyStore, password.toCharArray());
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            // Option 2: Create a custom TrustManager that trusts all SSL certificates
            X509TrustManager trustAllManager = new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[]{};
                }
            };
            sslContext.init(keyManagerFactory.getKeyManagers(), new TrustManager[]{trustAllManager}, new SecureRandom());
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            // Build OkHttpClient instance with SSL socket factory and timeout duration
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, trustAllManager); // Pass in custom TrustManager
            builder.readTimeout(60, TimeUnit.SECONDS);
            builder.writeTimeout(60, TimeUnit.SECONDS);
            OkHttpClient okHttpClient = builder.build();

            // Build Retrofit instance with OkHttpClient and SSL socket factory
            return new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}