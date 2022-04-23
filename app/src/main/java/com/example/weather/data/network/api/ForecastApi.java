package com.example.weather.data.network.api;

import com.example.weather.data.db.entity.WeatherEntity;
import com.example.weather.utils.Constant;

import io.reactivex.Observable;
import okhttp3.HttpUrl;
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
    Observable<WeatherEntity> getWeatherDataByCoord(@Query("lat") String lat, @Query("lon") String lon);


    class Instance{
        private static Retrofit getRetrofit(){

            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                    .addInterceptor(chain -> {
                        Request.Builder request = chain.request().newBuilder();
                        HttpUrl originalHttpUrl = chain.request().url();
                        HttpUrl newUrl = originalHttpUrl.newBuilder().addQueryParameter("appid", Constant.API_KEY)
                                .addQueryParameter("units","metric")
                                .addQueryParameter("exclude", "minutely,alerts").build();
                        request.url(newUrl);
                        Response response = chain.proceed(request.build());
                        return response;
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
