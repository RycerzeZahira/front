package lodzka.politechnika.qrcode.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lodzka.politechnika.qrcode.R;
import lodzka.politechnika.qrcode.Utils;
import lodzka.politechnika.qrcode.adapter.AnswerAdapter;
import lodzka.politechnika.qrcode.api.ApiUtils;
import lodzka.politechnika.qrcode.model.Element;
import lodzka.politechnika.qrcode.model.Elements;
import lodzka.politechnika.qrcode.model.Root;
import lodzka.politechnika.qrcode.model.RootNew;
import lodzka.politechnika.qrcode.model.SaveAnswersRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnswerFragment extends Fragment {

    private RootNew rootNew;
    private Root root;
    private RecyclerView recyclerView;
    private Button saveButton;
    private AnswerAdapter answerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.anwer_fragment, viewGroup, false);
        initialize(view);
        initBundles();
        setAdapter();
        rootToNewRoot();
        initButtonListener();
        return view;
    }

    private void rootToNewRoot() {
        rootNew = new RootNew();
        rootNew.setCode(root.getCode());
        List<Element> newElements = new ArrayList<>();
        for (Elements elements : root.getElements()) {
            newElements.add(new Element(elements.getType(), elements.getCode(), elements.getName()));
        }
        rootNew.setElement(newElements);
        rootNew.setType(root.getType());
    }

    private void initialize(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        saveButton = view.findViewById(R.id.save_button);
    }

    private void initBundles() {
        root = (Root) getArguments().getSerializable(Utils.REDAED_FORM);
    }

    private void setAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
        answerAdapter = new AnswerAdapter(getContext(), root.getElements());
        recyclerView.setAdapter(answerAdapter);
    }

    private void initButtonListener() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Elements elements : answerAdapter.getElements()) {
                    elements.getName();
                }
                ApiUtils.getGroupApi().addMeToGroup(root.getFormCode()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        rootToNewRoot();
                        SaveAnswersRequest saveAnswersRequest = new SaveAnswersRequest(root.getFormCode(), rootNew);
                        ApiUtils.getFormApi().saveAnswer(saveAnswersRequest).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.code() == 400) {
                                    Toast.makeText(getContext(), "Odpowiedź została już wcześniej dodana", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "Odpowiedź dodana", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {

                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });
    }
}
