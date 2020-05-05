package com.example.mvvmdemo.model;

import com.example.mvvmdemo.di.DaggerApiComponent;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CountriesService {

    private static CountriesService instance;

    /**
     * This inject helps in creation of the object and use of the object by mocking it or making a copy
     */
    @Inject
    public CountriesApi api;

    /**
     * This generated class will perform the actual injection
     */
    private  CountriesService() {
        DaggerApiComponent.create().inject(this);
    }

    public static CountriesService getInstance()
    {
        if(instance==null)
        {
            instance = new CountriesService();
        }
        return instance;
    }

    public Single<List<CountryModel>>getCountries()
    {
        return api.getCountries();
    }
}
