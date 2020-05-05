package com.example.mvvmdemo.viewmodel;

import com.example.mvvmdemo.di.DaggerApiComponent;
import com.example.mvvmdemo.model.CountriesService;
import com.example.mvvmdemo.model.CountryModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * This class is an observable. It will collect data and send it to a receiver
 */
public class ListViewModel extends ViewModel
{
    public MutableLiveData<List<CountryModel>>countries = new MutableLiveData<List<CountryModel>>();
    public MutableLiveData<Boolean> countryLoadError = new MutableLiveData<Boolean>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<Boolean>();

//    private CountriesService countriesService = CountriesService.getInstance();
    @Inject
    public CountriesService countriesService;

    public ListViewModel()
    {
        /**
         * We are calling super here because we extend from the view model and it's constructor needs to be called as well
         */
        super();
        DaggerApiComponent.create().inject(this);
    }

    private CompositeDisposable disposable = new CompositeDisposable();

    public void refresh()
    {
        fetchCountries();
    }

    private void fetchCountries()
    {

       loading.setValue(true);
/*
observeOn enables us to manage the information on main thread when we receive data from backend api.
 */
        disposable.add(
                countriesService.getCountries()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<CountryModel>>() {
                    @Override
                    public void onSuccess(List<CountryModel> countryModels) {
                        countries.setValue(countryModels);
                        loading.setValue(false);
                        countryLoadError.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        countryLoadError.setValue(true);
                        loading.setValue(false);
                        e.printStackTrace();
                    }
                })
        );
    }

    /**
     * In order to kill the task running in the background thread when user closes the application or when the application is closed we use CompositeDisposable variable
     *This prevents memory loss
     */
    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
