package com.example.groupbuy.group;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.groupbuy.R;

public class ProductsFragment extends Fragment {

    class Product {
        String name;
        boolean bought = false;

        Product(String name) {
            this.name = name;
        }

        void changeStatus() {
            bought = !bought;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public ProductsFragment() {
    }

    public static ProductsFragment newInstance(String groupName) {
        ProductsFragment productsFragment = new ProductsFragment();

        Bundle args = new Bundle();
        args.putString("groupName", groupName);
        productsFragment.setArguments(args);

        return productsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_with_fab, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String[] title = {"soki - 2l", "wódka - 3l", "chipsy - 2 paczki"};
        String[] subtitle = {"(0 propozycji)", "(1 propozycja)", "Ola, cheetosy"};
        Boolean[] bought = {false, false, true};
        Boolean[] clickable = {true, true, false};

        ListAdapter productListAdapter = new ProductListAdapter(getActivity(), title, subtitle, bought, clickable);
//                new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_multiple_choice, products);
//        TODO: Lista customizowana, checkbox z 3 stanami
//        https://stackoverflow.com/questions/3965484/custom-checkbox-image-android
//        https://developer.android.com/guide/topics/resources/drawable resource.html#StateList
//        https://www.codeproject.com/Articles/1006843/An-Efficient-Way-to-Make-Button-Color-Change-on-An
//        https://stackoverflow.com/questions/18395075/change-the-state-of-button-to-pressed
//        https://stackoverflow.com/questions/12702045/disable-checkbox-after-checked-android

        ListView productListView = getView().findViewById(R.id.list);
        productListView.setAdapter(productListAdapter);

        productListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Product product = (Product) parent.getItemAtPosition(position);
                        product.changeStatus();
                        if(product.bought)
                            Toast.makeText(getActivity(), "you marked " + product + " as bought", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        FloatingActionButton fab = getView().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddProductActivity();
            }
        });
    }

    public void openAddProductActivity() {
        Intent intent = new Intent(getActivity(), AddProductActivity.class);
        intent.putExtra("groupName", getArguments().getString("groupName", ""));
        startActivity(intent);
    }
}
