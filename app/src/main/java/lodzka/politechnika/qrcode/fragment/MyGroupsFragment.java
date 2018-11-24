package lodzka.politechnika.qrcode.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import lodzka.politechnika.qrcode.R;
import lodzka.politechnika.qrcode.adapter.GroupsAdapter;
import lodzka.politechnika.qrcode.api.ApiUtils;
import lodzka.politechnika.qrcode.model.Group;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyGroupsFragment extends Fragment {

    private GroupsAdapter groupsAdapter;
    private RecyclerView recyclerView;
    private Button createGroupButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.my_groups_fragment, viewGroup, false);

        createGroupButton = view.findViewById(R.id.create_group_button);



        final ProgressDialog progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setCancelable(true);
        progressDialog.setMessage(view.getResources().getString(R.string.loading));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        createGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new CreateGroupFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.miscFragment, fragment).addToBackStack(null).commit();

            }
        });


        ApiUtils.getGroupApi().getMyGroupsList().enqueue(new Callback<ArrayList<Group>>() {
            @Override
            public void onResponse(Call<ArrayList<Group>> call, Response<ArrayList<Group>> response) {
                generateGroupList(response.body(), view);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ArrayList<Group>> call, Throwable t) {
            }
        });


        return view;
    }

    private void generateGroupList(ArrayList<Group> groupList, final View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        groupsAdapter = new GroupsAdapter(groupList,view.getContext());
        recyclerView.setAdapter(groupsAdapter);
    }
}
