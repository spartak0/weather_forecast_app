package com.example.weather.data.network.api;

import androidx.annotation.NonNull;

import com.example.weather.data.db.entity.WeatherEntity;
import com.example.weather.utils.Constant;

import java.io.IOException;

import io.reactivex.Observable;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ForecastApi {

    @GET("data/2.5/onecall")
    Observable<WeatherEntity> getWeatherDataByCoord(@Query("lat") String lat, @Query("lon") String lon, @Query("units")String units);

    class Instance{
        private static Retrofit getRetrofit(){
            //HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            //httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @NonNull
                        @Override
                        public Response intercept(@NonNull Chain chain) throws IOException {
                            Request.Builder request = chain.request().newBuilder();
                            HttpUrl originalHttpUrl = chain.request().url();
                            HttpUrl newUrl = originalHttpUrl.newBuilder().addQueryParameter("appid", Constant.apiKey)
                                    .addQueryParameter("exclude", "minutely,hourly,alerts").build();
                            request.url(newUrl);
                            Response response = chain.proceed(request.build());
                            return response;
                        }
                    });

            Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
            retrofitBuilder.baseUrl(Constant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClientBuilder.build());
            return retrofitBuilder.build();

        }
        public static ForecastApi getForecastApi(){
            return getRetrofit().create(ForecastApi.class);
        }
    }

}
