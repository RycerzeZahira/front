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
import lodzka.politechnika.qrcode.api.payload.AuthenticationRequest;
import lodzka.politechnika.qrcode.api.payload.JwtAuthenticationResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private UserApi userApi;
    private Validator validator = new Validator();

    @BindView(lodzka.politechnika.qrcode.R.id.input_email)
    EditText emailText;
    @BindView(lodzka.politechnika.qrcode.R.id.input_password)
    EditText passwordText;
    @BindView(lodzka.politechnika.qrcode.R.id.btn_login)
    Button loginButton;
    @BindView(lodzka.politechnika.qrcode.R.id.link_signup)
    TextView signupLink;

    ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(lodzka.politechnika.qrcode.R.layout.activity_login);
        ButterKnife.bind(this);
        userApi = ApiUtils.getAUserApi();
        validator.setContext(this);
        progressDialog = new ProgressDialog(LoginActivity.this,
                lodzka.politechnika.qrcode.R.style.AppTheme_Dark_Dialog);

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(lodzka.politechnika.qrcode.R.anim.push_left_in, lodzka.politechnika.qrcode.R.anim.push_left_out);
            }
        });
    }

    public void login() {
        if (!validator.validate(emailText, passwordText)) {
            onLoginFailed();
            return;
        }

        loginButton.setEnabled(false);

        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        loginUserPost(email, password);
    }

    private void loginUserPost(String email, String password) {
        AuthenticationRequest request = new AuthenticationRequest(email, password);
        userApi.loginUser(request).enqueue(new Callback<JwtAuthenticationResponse>() {
            @Override
            public void onResponse(Call<JwtAuthenticationResponse> call, Response<JwtAuthenticationResponse> response) {
                if (response.isSuccessful()) {
                    ApiUtils.setToken(response.body().getAccessToken());
                    progressDialog.dismiss();
                    onLoginSuccess();

                } else {
                    progressDialog.dismiss();
                    onLoginFailed();

                }
            }

            @Override
            public void onFailure(Call<JwtAuthenticationResponse> call, Throwable t) {
                progressDialog.dismiss();
                onLoginFailed();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    public void onLoginSuccess() {
        loginButton.setEnabled(true);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        loginButton.setEnabled(true);
    }
}
