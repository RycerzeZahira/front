package lodzka.politechnika.qrcode.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.WriterException;

import java.util.HashMap;

import lodzka.politechnika.qrcode.QRCodeGenerator;
import lodzka.politechnika.qrcode.R;
import lodzka.politechnika.qrcode.Utils;
import lodzka.politechnika.qrcode.model.Elements;
import lodzka.politechnika.qrcode.model.Root;

/**
 * Created by Bartek on 2018-11-19.
 */

public class QRCodeGenerateFragment extends Fragment {
    private Root root;
    private String formCode;
    private ImageView barcode;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.qrcode_fragment, viewGroup, false);
        barcode = view.findViewById(R.id.barcode);
        initBundles();
        initData();
        return  view;
    }

    private void initBundles() {
        root = (Root) getArguments().getSerializable(Utils.FORM);
        formCode = getArguments().getString(Utils.FORM_CODE);
    }

    private void initData(){
        try {
            barcode.setImageBitmap(QRCodeGenerator.generate(root,350));
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
