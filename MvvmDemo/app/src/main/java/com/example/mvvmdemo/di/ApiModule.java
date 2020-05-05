package com.example.mvvmdemo.di;


import com.example.mvvmdemo.model.CountriesApi;
import com.example.mvvmdemo.model.CountriesService;


import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Module is going to create the retrofit object
 */

@Module
public class ApiModule {

    private static final String BASE_URL= "https://raw.githubusercontent.com";

    /**
     * GsonConverterFactory converts json from the backend api to object which we get in our code.\
     * RxJava2CallAdapterFactory takes this list and converts it into a single observable in CountryApi's Single<List<CountryModel>>.
     * We are creating the object which will provide network connection and data
     * The system  will create the api from here
     */

    @Provides
    public CountriesApi provideCountriesApi() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(CountriesApi.class);
    }

    @Provides
    public CountriesService provideCountriesService()
    {
        return CountriesService.getInstance();
    }
}
