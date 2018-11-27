package lodzka.politechnika.qrcode.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import lodzka.politechnika.qrcode.R;
import lodzka.politechnika.qrcode.api.ApiUtils;
import lodzka.politechnika.qrcode.model.Group;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateGroupFragment extends Fragment {

    private EditText groupName;
    private CheckBox isPublic;
    private Button saveButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.new_groups_fragment, viewGroup, false);
        initialize(view);
        listenButton();
        return view;
    }

    private void listenButton() {
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(groupName.getText().toString())) {
                    groupName.setError(getActivity().getResources().getString(R.string.title_cannot_be_empty));
                    return;
                }

                ApiUtils.getGroupApi().createGroup(new Group(groupName.getText().toString(), "", isPublic.isChecked())).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Fragment fragment = new MyGroupsFragment();
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.miscFragment, fragment).addToBackStack(null).commit();
                        } else {
                            System.out.println(response.toString());
                            makeToast(getActivity().getResources().getString(R.string.list_add_failed));
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        makeToast(getActivity().getResources().getString(R.string.error_with_app));
                    }
                });
            }
        });
    }

    void initialize(View view) {
        groupName = view.findViewById(R.id.insert_group_name);
        isPublic = view.findViewById(R.id.is_public_group);
        saveButton = view.findViewById(R.id.save_button);
    }

    public void makeToast(String status) {
        Toast.makeText(getActivity().getBaseContext(), status, Toast.LENGTH_SHORT).show();
    }
}
