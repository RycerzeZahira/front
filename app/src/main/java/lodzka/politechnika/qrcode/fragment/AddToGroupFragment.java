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
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import lodzka.politechnika.qrcode.R;
import lodzka.politechnika.qrcode.adapter.GroupsAdapter;
import lodzka.politechnika.qrcode.api.ApiUtils;
import lodzka.politechnika.qrcode.model.Group;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddToGroupFragment extends Fragment {

    private GroupsAdapter groupsAdapter;
    private RecyclerView recyclerView;
    private Button addMe;
    private EditText groupCode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.add_me_to_group, viewGroup, false);

        addMe = view.findViewById(R.id.add_me_button);
        groupCode = view.findViewById(R.id.insert_group_code);

        final ProgressDialog progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setCancelable(true);
        progressDialog.setMessage(view.getResources().getString(R.string.loading));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        addMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiUtils.getGroupApi().addMeToGroup(groupCode.getText().toString()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(getContext(), "Added to group", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getContext(), "ERROR", Toast.LENGTH_LONG).show();
                    }
                });
                Fragment fragment = new MyGroupsFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.miscFragment, fragment).addToBackStack(null).commit();

            }
        });

        ApiUtils.getGroupApi().getPublicGroups().enqueue(new Callback<ArrayList<Group>>() {
            @Override
            public void onResponse(Call<ArrayList<Group>> call, Response<ArrayList<Group>> response) {
                generateGroupList(response.body(), view);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ArrayList<Group>> call, Throwable t) {
                Toast.makeText(getContext(), "ERROR", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });

        return view;
    }

    private void generateGroupList(ArrayList<Group> groupList, final View view) {
        recyclerView = view.findViewById(R.id.public_groups);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        groupsAdapter = new GroupsAdapter(groupList,view.getContext());
        recyclerView.setAdapter(groupsAdapter);
    }
}
