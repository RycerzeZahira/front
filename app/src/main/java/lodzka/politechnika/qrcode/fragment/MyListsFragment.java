package lodzka.politechnika.qrcode.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lodzka.politechnika.qrcode.R;
import lodzka.politechnika.qrcode.adapter.ListsAdapter;
import lodzka.politechnika.qrcode.api.ApiUtils;
import lodzka.politechnika.qrcode.model.Form;
import lodzka.politechnika.qrcode.model.Root;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyListsFragment extends Fragment {

    private List<Form> formList;
    private RecyclerView recyclerView;
    private ListsAdapter listAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.my_lists_fragment, viewGroup, false);
        final ProgressDialog progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setCancelable(true);
        progressDialog.setMessage(view.getResources().getString(R.string.loading));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        ApiUtils.getFormApi().getAllForms().enqueue(new Callback<Form[]>() {
            @Override
            public void onResponse(Call<Form[]> call, Response<Form[]> response) {
                formList = new ArrayList<>(Arrays.asList(response.body()));
                final List<Root> forms = new ArrayList<>();
                for (Form form : formList) {
                    form.getRoot().setFormCode(form.getCode());
                    forms.add(form.getRoot());
                }
                generateList(forms, view);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Form[]> call, Throwable t) {
                System.out.println("FAIL");
            }

        });


        return view;
    }

    private void generateList(List<Root> formList, final View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        listAdapter = new ListsAdapter(formList,view.getContext());
        recyclerView.setAdapter(listAdapter);
    }

}
