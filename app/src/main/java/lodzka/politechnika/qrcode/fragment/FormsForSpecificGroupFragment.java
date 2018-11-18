package lodzka.politechnika.qrcode.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lodzka.politechnika.qrcode.R;

/**
 * Created by Bartek on 2018-11-18.
 */

public class FormsForSpecificGroupFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forms_for_specific_group_fragment,viewGroup,false);

        return view;
    }
}
