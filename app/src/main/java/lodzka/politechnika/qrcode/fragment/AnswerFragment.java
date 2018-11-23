package lodzka.politechnika.qrcode.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import lodzka.politechnika.qrcode.R;
import lodzka.politechnika.qrcode.Utils;
import lodzka.politechnika.qrcode.adapter.AnswerAdapter;
import lodzka.politechnika.qrcode.api.ApiUtils;
import lodzka.politechnika.qrcode.model.Root;
import lodzka.politechnika.qrcode.model.SaveAnswersRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnswerFragment extends Fragment {

    private String formCode;
    private Root root;
    private ListView listView;
    private Button saveButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.anwer_fragment, viewGroup, false);
        initialize(view);
        initBundles();
        setAdapter();
        initButtonListener();
        return view;
    }

    private void initialize(View view) {
        listView = view.findViewById(R.id.recyclerView);
        saveButton = view.findViewById(R.id.save_button);
    }

    private void initBundles() {
        root = (Root) getArguments().getSerializable(Utils.REDAED_FORM);
        formCode = getArguments().getString(Utils.FORM_CODE);
    }

    private void setAdapter() {
        AnswerAdapter listAdapter = new AnswerAdapter(getContext(), root.getElements());
        listView.setDivider(null);
        listView.setAdapter(listAdapter);
    }

    private void initButtonListener() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiUtils.getFormApi().saveAnswer(new SaveAnswersRequest(formCode, root)).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        makeToast("Answer added");
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        makeToast("Failed to add answer");
                    }
                });
            }
        });
    }

    private void makeToast(String status) {
        Toast.makeText(getActivity().getBaseContext(), status, Toast.LENGTH_SHORT).show();
    }
}
