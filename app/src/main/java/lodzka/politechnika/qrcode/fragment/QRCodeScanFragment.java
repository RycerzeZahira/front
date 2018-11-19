package lodzka.politechnika.qrcode.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.zxing.integration.android.IntentIntegrator;

import lodzka.politechnika.qrcode.R;

/**
 * Created by Bartek on 2018-11-19.
 */

public class QRCodeScanFragment extends android.support.v4.app.Fragment {

    private IntentIntegrator integrator;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.main_fragment, viewGroup, false);
        integrator = new IntentIntegrator(getActivity());
        integrator.initiateScan();
        return  view;
    }
}
