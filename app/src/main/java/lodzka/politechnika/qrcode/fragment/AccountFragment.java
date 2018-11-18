package lodzka.politechnika.qrcode.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import lodzka.politechnika.qrcode.LoginActivity;
import lodzka.politechnika.qrcode.R;
import lodzka.politechnika.qrcode.Validator;
import lodzka.politechnika.qrcode.api.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFragment extends Fragment {

    private EditText oldPassword;
    private EditText newPassword;
    private EditText reNewPassword;
    private Button submitButton;

    private Validator validator = new Validator();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.account_fragment, viewGroup, false);
        initialize(view);
        return view;
    }

    private void initialize(View view) {
        oldPassword = view.findViewById(R.id.oldPassword);
        newPassword = view.findViewById(R.id.newPassword);
        reNewPassword = view.findViewById(R.id.reNewPassword);
        submitButton = view.findViewById(R.id.submitButton);
        validator.setContext(getContext());

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });
    }

    private void changePassword() {
        if (!validator.validate(newPassword, reNewPassword, true)) {
            makeToast("Validate failed");
            return;
        }

        ApiUtils.getAUserApi().changePassword(oldPassword.getText().toString(), newPassword.getText().toString()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    makeToast("Password changed");
                    logout();
                } else {
                    makeToast("Bad old password");
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                makeToast("Error");
            }
        });
    }

    private void makeToast(String status) {
        Toast.makeText(getContext(), status, Toast.LENGTH_SHORT).show();
    }

    private void logout() {
        ApiUtils.setToken("");
        SharedPreferences prefs = this.getActivity().getSharedPreferences(ApiUtils.getPreferences(), Context.MODE_PRIVATE);
        prefs.edit().putString(ApiUtils.getTokenName(), "").apply();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
    }
}
