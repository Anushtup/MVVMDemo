package com.example.mvvmdemo.view;

import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mvvmdemo.R;
import com.example.mvvmdemo.databinding.CountryModelBinding;
import com.example.mvvmdemo.model.CountryModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class CountryListAdapter extends RecyclerView.Adapter<CountryListAdapter.CountryViewHolder> {

    private List<CountryModel> mCountryList;


    public CountryListAdapter (List<CountryModel> mCountryList)
    {
        this.mCountryList=mCountryList;
    }

    //so that the view can update the country names
    public void updateCountries(List<CountryModel> newCountryList)
    {
        mCountryList.clear();
        mCountryList.addAll(newCountryList);
        //deletes everything and recreates the list whenever data set is changed
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li= LayoutInflater.from(parent.getContext());
        return new CountryViewHolder(CountryModelBinding.inflate(li));
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
            holder.binding.name.setText(mCountryList.get(position).getCountryName());
            holder.binding.capital.setText(mCountryList.get(position).getCapital());
        /**
         * holder.binding.imageView.getContext() kbse context milne laga?
         */
        Util.loadImage(holder.binding.imageView,mCountryList.get(position).getFlag(),Util.getCircularProgressDrawable(holder.binding.imageView.getContext()));
    }

    @Override
    public int getItemCount() {
        return mCountryList.size();
    }


    // Here we make the transition between view and countrymodel
    public class CountryViewHolder extends RecyclerView.ViewHolder {

        CountryModelBinding binding;

        public CountryViewHolder(CountryModelBinding b) {
            super(b.getRoot());
            binding = b;

        }
    }
}
