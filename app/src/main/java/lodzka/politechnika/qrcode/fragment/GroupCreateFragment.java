package lodzka.politechnika.qrcode.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import lodzka.politechnika.qrcode.R;
import lodzka.politechnika.qrcode.api.ApiUtils;
import lodzka.politechnika.qrcode.api.GroupApi;
import lodzka.politechnika.qrcode.model.Group;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Bartek on 2018-10-29.
 */

public class GroupCreateFragment extends Fragment {
    private EditText editText;
    private CheckBox checkBox;
    private Button button;
    private GroupApi groupApi;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.group_create, viewGroup, false);
        editText = view.findViewById(R.id.editText2);
        checkBox = view.findViewById(R.id.checkBox2);
        button = view.findViewById(R.id.button5);
        groupApi = ApiUtils.getGroupApi();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Group group = new Group(editText.getText().toString(), checkBox.isChecked());
                groupApi.createGroup(group).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            success();
                        } else {
                            System.out.println(response.code());
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
        Toast.makeText(getActivity().getBaseContext(), "Group Created", Toast.LENGTH_SHORT).show();
    }

    public void failed() {
        Toast.makeText(getActivity().getBaseContext(), "Group failed", Toast.LENGTH_SHORT).show();
    }
}
