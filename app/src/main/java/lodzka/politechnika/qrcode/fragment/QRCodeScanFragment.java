package lodzka.politechnika.qrcode.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import lodzka.politechnika.qrcode.R;
import lodzka.politechnika.qrcode.Utils;
import lodzka.politechnika.qrcode.model.Root;

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
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                try {
                    JSONObject obj = new JSONObject(result.getContents());
                    Gson gson = new Gson();
                    Root root = gson.fromJson(obj.toString(), Root.class);

                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Utils.REDAED_FORM, (Serializable) root);

                    Fragment fragment = new AnswerFragment();
                    fragment.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.miscFragment, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commitAllowingStateLoss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
    }
}
