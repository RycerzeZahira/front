package lodzka.politechnika.qrcode.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import lodzka.politechnika.qrcode.R;
import lodzka.politechnika.qrcode.api.ApiUtils;
import lodzka.politechnika.qrcode.model.Group;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyGroupsFragment extends Fragment {

    private ArrayList<Group> groupArrayList = new ArrayList<>();
    private ArrayAdapter<Group> listAdapter;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.my_groups_fragment, viewGroup, false);
        listView = view.findViewById(R.id.listView);
        getGroups();
        return view;
    }


    private void getGroups() {
        ApiUtils.getGroupApi().getMyGroupsList().enqueue(new Callback<ArrayList<Group>>() {
            @Override
            public void onResponse(Call<ArrayList<Group>> call, Response<ArrayList<Group>> response) {
                groupArrayList.addAll(response.body());
                listAdapter = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, groupArrayList);
                listView.setAdapter(listAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Group>> call, Throwable t) {
            }
        });
    }
}
