package com.example.mvvmdemo.di;

import com.example.mvvmdemo.model.CountriesApi;
import com.example.mvvmdemo.model.CountriesService;
import com.example.mvvmdemo.viewmodel.ListViewModel;

import dagger.Component;

/**
 * This interface makes connection between module and where we need to inject the object. Component needs to be told which module class it's going to handle
 */

//** How would we handle different modules for different data?

@Component(modules = {ApiModule.class})
public interface ApiComponent {
    /**
     * The component knows that it has a CountriesApi and it needs to inject this object in CountriesService class
     * The system will know where to inject the api
     * @param service
     */
    void inject(CountriesService service);
    void inject(ListViewModel viewModel);

}
