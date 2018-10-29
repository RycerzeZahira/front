package lodzka.politechnika.qrcode.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lodzka.politechnika.qrcode.R;
import lodzka.politechnika.qrcode.adapter.QRCodeAdapter;
import lodzka.politechnika.qrcode.model.QRCodeForm;

/**
 * Created by Bartek on 2018-10-28.
 */

public class QRCodeCreateFragment extends Fragment {

    private QRCodeAdapter qrCodeAdapter;
    private List<QRCodeForm> qrCodeFormList;
    private RecyclerView recyclerView;
    private Button addField;
    private TextView fieldName;
    private TextView fieldValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.qr_code_create, viewGroup, false);
        addField = view.findViewById(R.id.add_field);
        fieldName = view.findViewById(R.id.input_name);
        fieldValue = view.findViewById(R.id.input_data);
        qrCodeFormList = new ArrayList<>();
        qrCodeAdapter = new QRCodeAdapter(qrCodeFormList);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(qrCodeAdapter);


        addField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fieldName.getText().toString().matches("") || fieldValue.getText().toString().matches("")) {
//                    Toast.makeText(v.getContext(), "Please fill inputs", Toast.LENGTH_SHORT);
                } else {
                    qrCodeFormList.add(new QRCodeForm(fieldName.getText().toString(), fieldValue.getText().toString()));
                    qrCodeAdapter.notifyDataSetChanged();
                }
            }
        });


        return view;
    }
}
