package lodzka.politechnika.qrcode;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import lodzka.politechnika.qrcode.api.ApiUtils;
import lodzka.politechnika.qrcode.api.UserApi;
import lodzka.politechnika.qrcode.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = SignupActivity.class.getSimpleName();
    private UserApi userApi;

    @BindView(lodzka.politechnika.qrcode.R.id.input_email)
    EditText emailText;
    @BindView(lodzka.politechnika.qrcode.R.id.input_password)
    EditText passwordText;
    @BindView(lodzka.politechnika.qrcode.R.id.input_reEnterPassword)
    EditText reEnterPasswordText;
    @BindView(R.id.btn_signup)
    Button signupButton;
    @BindView(lodzka.politechnika.qrcode.R.id.link_login)
    TextView loginLink;

    ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(lodzka.politechnika.qrcode.R.layout.activity_signup);
        ButterKnife.bind(this);
        signupButton = findViewById(R.id.btn_signup);
        userApi = ApiUtils.getAUserApi();

        progressDialog = new ProgressDialog(SignupActivity.this,
                lodzka.politechnika.qrcode.R.style.AppTheme_Dark_Dialog);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(lodzka.politechnika.qrcode.R.anim.push_left_in, lodzka.politechnika.qrcode.R.anim.push_left_out);
            }
        });
    }

    public void signup() {

        if (!validate()) {
            onSignupFailed();
            return;
        }

        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Signing up...");
        progressDialog.show();

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        registerUserPost(email, password);

    }


    public void registerUserPost(String email, String password) {
        User user = new User(email, password);
        userApi.registerUser(user).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    onSignupSuccess();
                } else {
                    progressDialog.dismiss();
                    onSignupFailed();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                progressDialog.dismiss();
                onSignupFailed();
            }
        });
    }

    public void onSignupSuccess() {
        signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        Toast.makeText(getBaseContext(), "Sign up completed. Please log in", Toast.LENGTH_LONG).show();
        startActivity(intent);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Sign up failed", Toast.LENGTH_LONG).show();

        signupButton.setEnabled(true);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public boolean validate() {
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        String reEnterPassword = reEnterPasswordText.getText().toString();

        return isValidEmail(email) && isValidPassword(password, reEnterPassword);
    }

    private boolean isValidPassword(String password, String reEnterPassword) {
        if (password.isEmpty() || password.length() < 8) {
            passwordText.setError(getBaseContext().getResources().getString(R.string.longer_than_8));
            return false;
        }
        else if(reEnterPassword.isEmpty() || !(reEnterPassword.equals(password))) {
            reEnterPasswordText.setError(getBaseContext().getResources().getString(R.string.password_do_not_match));
            return false;
        }
        else {
            passwordText.setError(null);
            reEnterPasswordText.setError(null);
            return true;
        }
    }

    private boolean isValidEmail(String email) {
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError(getBaseContext().getResources().getString(R.string.enter_valid_email));
            return false;
        } else {
            emailText.setError(null);
            return true;
        }
    }
}