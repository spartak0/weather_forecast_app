package com.example.weather.api;

import com.example.weather.model.WeatherData;

import java.util.Queue;

import io.reactivex.Observable;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ForecastApi {
    static String DOMAIN= "http://api.openweathermap.org/";

    @GET("data/2.5/weather")
    Observable<WeatherData> getWeatherDataByCoord(@Query("lat") String lat, @Query("lon") String lon, @Query("appid") String apiKey, @Query("units")String units);

    class Instance{
        private static Retrofit getRetrofit(){
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                    .addInterceptor(httpLoggingInterceptor);

            Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
            retrofitBuilder.baseUrl(DOMAIN)
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
