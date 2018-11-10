package lodzka.politechnika.qrcode.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lodzka.politechnika.qrcode.R;
import lodzka.politechnika.qrcode.Utils;
import lodzka.politechnika.qrcode.api.ApiUtils;
import lodzka.politechnika.qrcode.model.Form;
import lodzka.politechnika.qrcode.model.Root;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyListsFragment extends Fragment {

    private List<Form> formList;
    private ListView listView;
    private ArrayAdapter<Root> listAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.my_lists_fragment, viewGroup, false);
        listView = view.findViewById(R.id.recyclerView);
        getLists();
        return view;
    }

    private void getLists() {
        final String formCode = "RMAEYGBBPZPBZ77UYAFISMCQ2L3WF61V";
        ApiUtils.getFormApi().getFormsInGroup(formCode).enqueue(new Callback<Form[]>() {
            @Override
            public void onResponse(Call<Form[]> call, Response<Form[]> response) {
                formList = new ArrayList<>(Arrays.asList(response.body()));
                final List<Root> forms = new ArrayList<>();
                for (Form form : formList) {
                    forms.add(form.getRoot());
                }
                listAdapter = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, forms);
                listView.setAdapter(listAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                            long arg3) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(Utils.FORM, (Serializable) listAdapter.getItem(arg2));
                        bundle.putString(Utils.FORM_CODE, formCode);
                        Fragment fragment = new AnswerFragment();
                        fragment.setArguments(bundle);
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.miscFragment, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                });
            }

            @Override
            public void onFailure(Call<Form[]> call, Throwable t) {
                System.out.println("FAIL");
            }

        });
    }
}
