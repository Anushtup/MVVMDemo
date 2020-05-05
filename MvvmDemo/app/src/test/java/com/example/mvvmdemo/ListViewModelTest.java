package com.example.mvvmdemo;

import com.example.mvvmdemo.model.CountriesService;
import com.example.mvvmdemo.model.CountryModel;
import com.example.mvvmdemo.viewmodel.ListViewModel;
/*import androidx.arch.core.executor.testing.InstantTaskExecutorRule;*/

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

public class ListViewModelTest
{
    /**
     * This simply is a rule that any task execution will be instant
     * It will also test whether the background and the foreground thread has been hitted or not. If yes then it will just return a value after a hit.
     */
    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();


    @Mock
    CountriesService countriesService;

    /**
     * Here we will test the mock objects rather than the real objects
     */
    @InjectMocks
    ListViewModel listViewModel = new ListViewModel();

    private Single<List<CountryModel>> testSingle;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * This will test the success method and in testSingle it will check whether the single list data is created or not by CountriesService.getCountries method
     */
    @Test
    public void getCountriesSuccess()
    {
        CountryModel country = new CountryModel("countryName","capital","flag");
        ArrayList<CountryModel>countriesList = new ArrayList<>();
        countriesList.add(country);
        testSingle = Single.just(countriesList);
        Mockito.when(countriesService.getCountries()).thenReturn(testSingle);

        listViewModel.refresh();

        Assert.assertEquals(1,listViewModel.countries.getValue().size());
        Assert.assertEquals(false,listViewModel.countryLoadError.getValue());
        Assert.assertEquals(false,listViewModel.loading.getValue());
    }

    @Test
    public void getCountryFail()
    {
        testSingle = Single.error(new Throwable());

        Mockito.when(countriesService.getCountries()).thenReturn(testSingle);

        listViewModel.refresh();

        Assert.assertEquals(true,listViewModel.countryLoadError.getValue());
        Assert.assertEquals(false,listViewModel.loading.getValue());
    }



    /**
     * @Before annotation means it will run before every single test
     */
    @Before
    public void setupRxSchedulers()
    {
        Scheduler immediate = new Scheduler() {
            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(new Executor() {
                    @Override
                    public void execute(Runnable runnable) {
                        runnable.run();
                    }
                });
            }
        };
        RxJavaPlugins.setInitNewThreadSchedulerHandler(schedulerCallable -> immediate);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable -> immediate);
    }

}
