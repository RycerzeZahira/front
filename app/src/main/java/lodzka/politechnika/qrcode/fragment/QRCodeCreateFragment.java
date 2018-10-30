package lodzka.politechnika.qrcode.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lodzka.politechnika.qrcode.R;
import lodzka.politechnika.qrcode.Utils;
import lodzka.politechnika.qrcode.adapter.QRCodeAdapter;
import lodzka.politechnika.qrcode.api.ApiUtils;
import lodzka.politechnika.qrcode.api.FormApi;
import lodzka.politechnika.qrcode.model.Elements;
import lodzka.politechnika.qrcode.model.QRCodeForm;
import lodzka.politechnika.qrcode.model.QRCodeJsonObject;
import lodzka.politechnika.qrcode.model.Root;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Bartek on 2018-10-28.
 */

public class QRCodeCreateFragment extends Fragment {

    private QRCodeAdapter qrCodeAdapter;
    private List<QRCodeForm> qrCodeFormList;
    private RecyclerView recyclerView;
    private Button addField;
    private TextView fieldName;
    private Button saveButton;
    private ArrayList<Elements> elementsArrayList;
    private EditText formName;
    private FormApi formApi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.qr_code_create, viewGroup, false);
        addField = view.findViewById(R.id.add_field);
        fieldName = view.findViewById(R.id.input_name);
        qrCodeFormList = new ArrayList<>();
        qrCodeAdapter = new QRCodeAdapter(qrCodeFormList);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(qrCodeAdapter);
        saveButton = view.findViewById(R.id.save_button);
        formName = view.findViewById(R.id.insert_list_name_edit_text);
        elementsArrayList = new ArrayList<>();
        formApi = ApiUtils.getFormApi();


        addField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fieldName.getText().toString().matches("")) {
//                    Toast.makeText(v.getContext(), "Please fill inputs", Toast.LENGTH_SHORT);
                } else {
                    qrCodeFormList.add(new QRCodeForm(fieldName.getText().toString()));
                    qrCodeAdapter.notifyDataSetChanged();
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            String rootName = Utils.randomUUID(32, -1, '_');

            @Override
            public void onClick(View v) {
                if (qrCodeFormList.size() <= 0) {
                    noData();
                    return;
                }

                if (TextUtils.isEmpty(formName.getText().toString())) {
                    formName.setError(getActivity().getResources().getString(R.string.title_cannot_be_empty));
                    return;
                }

                for (QRCodeForm qrCodeForm : qrCodeFormList) {
                    Elements elements = new Elements();
                    elements.setType("STRING");
                    elements.setCode(Utils.randomUUID(32, -1, '_'));
                    elements.setName(qrCodeForm.getFieldName());
                    elements.setParent(rootName);
                    elementsArrayList.add(elements);
                }
                Root root = new Root();
                root.setType("GROUP");
                root.setCode(rootName);
                root.setName(formName.getText().toString());
                root.setElements(elementsArrayList);
                root.setParent(null);

                QRCodeJsonObject qrCodeJsonObject = new QRCodeJsonObject();
                qrCodeJsonObject.setGroupCode("TXXPSZDVKI904LQ3OYJCQWVWVT8BSI1D");
                qrCodeJsonObject.setExpiredDate("2019-01-12");
                qrCodeJsonObject.setRoot(root);

                formApi.createForm(qrCodeJsonObject).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            success();
                        } else {
                            System.out.println(response.toString());
                            failed();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });


            }
        });

        return view;
    }

    public void success() {
        Toast.makeText(getActivity().getBaseContext(), getActivity().getResources().getString(R.string.form_created), Toast.LENGTH_SHORT).show();
    }

    public void failed() {
        Toast.makeText(getActivity().getBaseContext(), getActivity().getResources().getString(R.string.form_creation_failed), Toast.LENGTH_SHORT).show();
    }

    public void noData() {
        Toast.makeText(getActivity().getBaseContext(), getActivity().getResources().getString(R.string.no_fields_added), Toast.LENGTH_SHORT).show();
    }
}
