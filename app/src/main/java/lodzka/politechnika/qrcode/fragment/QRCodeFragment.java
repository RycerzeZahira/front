package lodzka.politechnika.qrcode.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;

import java.util.HashMap;

import lodzka.politechnika.qrcode.QRCodeGenerator;
import lodzka.politechnika.qrcode.R;

public class QRCodeFragment extends Fragment {

    private ImageView qrcode;
    private TextView nameTextInput;
    private TextView surnameTextInput;
    private Button generateQRCode;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.qrcode_fragment, container, false);

        qrcode = view.findViewById(R.id.qrcode);
        nameTextInput = view.findViewById(R.id.nameTextInput);
        surnameTextInput = view.findViewById(R.id.surnameTextInput);
        generateQRCode = view.findViewById(R.id.generateQRCode);

        generateQRCode.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                generateQRCode();
            }
        });

        return view;
    }

    public void generateQRCode() {
        HashMap data = new HashMap();
        data.put("name", nameTextInput.getText().toString());
        data.put("surname", surnameTextInput.getText().toString());
        try {
            qrcode.setImageBitmap(QRCodeGenerator.generate(data, 500));
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
