package com.example.mvvmdemo.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mvvmdemo.R;
import com.example.mvvmdemo.databinding.ActivityMainBinding;
import com.example.mvvmdemo.model.CountriesService;
import com.example.mvvmdemo.model.CountryModel;
import com.example.mvvmdemo.viewmodel.ListViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MainActivity extends AppCompatActivity {

//    @BindView(R.id.countriesList)
//    RecyclerView countryrecycler;
//    @BindView(R.id.list_error)
//    TextView error_text;
//    @BindView(R.id.loading_view)
//    ProgressBar loader;
//    @BindView(R.id.swipeRefreshLayout)

    ActivityMainBinding binding;
    RecyclerView.LayoutManager linearlayoutmanager;


    private ListViewModel mListViewModel;
    private CountryListAdapter mCountryListAdapter= new CountryListAdapter(new ArrayList<>());

    /**
     *  This class is an observer. It receives data from observable and reflects it here in view.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        View view = binding.getRoot();
//        setContentView(R.layout.activity_main);
            setContentView(binding.getRoot());
//        ButterKnife.bind(this);

        mListViewModel = ViewModelProviders.of(this).get(ListViewModel.class);
        mListViewModel.refresh();

        linearlayoutmanager = new LinearLayoutManager(this);
        binding.countriesList.setLayoutManager(linearlayoutmanager);
//        binding.countriesList.setLayoutManager(new LinearLayoutManager(this));
        binding.countriesList.setAdapter(mCountryListAdapter);

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mListViewModel.refresh();
                binding.swipeRefreshLayout.setRefreshing(false);
            }
        });
        observeViewModel();
    }

    // we are going to attach some live data variables from ListViewModel
    private void observeViewModel() {
        mListViewModel.countries.observe(this, new Observer<List<CountryModel>>() {
            @Override
            public void onChanged(List<CountryModel> countryModels) {
                if(countryModels!=null)
                {
                    binding.countriesList.setVisibility(View.VISIBLE);
                    mCountryListAdapter.updateCountries(countryModels);
                }
            }
        });


        mListViewModel.countryLoadError.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean!=null) {
                    binding.listError.setVisibility(aBoolean ? View.VISIBLE : View.GONE);
                }
                }
        });

        mListViewModel.loading.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean!=null)
                {
                    binding.loadingView.setVisibility(aBoolean ? View.VISIBLE : View.GONE);
                    if(aBoolean){
                        binding.listError.setVisibility(View.GONE);
                        binding.countriesList.setVisibility(View.GONE);
                    }
                }
            }
        });
    }
}
