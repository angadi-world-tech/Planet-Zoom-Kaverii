package com.angadi.tripmanagementa.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.angadi.tripmanagementa.Activities.HomeActivity;
import com.angadi.tripmanagementa.Adapters.PlacesAutoCompleteAdapter;
import com.angadi.tripmanagementa.R;
import com.crashlytics.android.Crashlytics;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

import static com.angadi.tripmanagementa.Fragments.LocationSearchFragment.hideSoftKeyboard;

public class LocationFragment extends Fragment
{

    @BindView(R.id.LinLayoutBack)LinearLayout LinLayoutBack;
    AutoCompleteTextView autocomplete;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.layout_location_fragment, container, false);

        ButterKnife.bind(this,view);
        Fabric.with(getActivity(), new Crashlytics());

        autocomplete = (AutoCompleteTextView)view.findViewById(R.id.autocomplete);
        autocomplete.requestFocus();

        autocomplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get data associated with the specified position
                // in the list (AdapterView)
                String description = (String) parent.getItemAtPosition(position);
               // mMap.clear();
               // SourceMovecamera(view);
                hideSoftKeyboard(getActivity());
                Toast.makeText(getActivity(), description, Toast.LENGTH_SHORT).show();
            }
        });
        autocomplete.setAdapter(new PlacesAutoCompleteAdapter(getActivity(), R.layout.autocomplete_list_item));

        return view;
    }

    @OnClick(R.id.LinLayoutBack)
    public void setLinLayoutBack()
    {
        startActivity(new Intent(getActivity(),HomeActivity.class));
    }
}
