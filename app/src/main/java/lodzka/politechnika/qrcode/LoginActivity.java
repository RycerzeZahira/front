package lodzka.politechnika.qrcode;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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

    @BindView(lodzka.politechnika.qrcode.R.id.input_email)
    EditText _emailText;
    @BindView(lodzka.politechnika.qrcode.R.id.input_password)
    EditText _passwordText;
    @BindView(lodzka.politechnika.qrcode.R.id.btn_login)
    Button _loginButton;
    @BindView(lodzka.politechnika.qrcode.R.id.link_signup)
    TextView _signupLink;

    ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(lodzka.politechnika.qrcode.R.layout.activity_login);
        ButterKnife.bind(this);
        userApi = ApiUtils.getAUserApi();
        progressDialog = new ProgressDialog(LoginActivity.this,
                lodzka.politechnika.qrcode.R.style.AppTheme_Dark_Dialog);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

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
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);


        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        loginUserPost(email, password);

    }

    private void loginUserPost(String email, String password) {
        AuthenticationRequest request = new AuthenticationRequest(email, password);
        userApi.loginUser(request).enqueue(new Callback<JwtAuthenticationResponse>() {
            @Override
            public void onResponse(Call<JwtAuthenticationResponse> call, Response<JwtAuthenticationResponse> response) {
                if (response.isSuccessful()) {
                    ApiUtils.setToken(response.body().getAccessToken());
                    Log.d(TAG, "onResponse: post submitted to API");
                    progressDialog.dismiss();
                    onLoginSuccess();

                } else {
                    Log.d(TAG, "onFailure: unable to submit post to api");
                    progressDialog.dismiss();
                    onLoginFailed();

                }
            }

            @Override
            public void onFailure(Call<JwtAuthenticationResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: unable to submit post to api");
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

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {

        SharedPreferences.Editor editor = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE).edit();
        editor.putBoolean(MainActivity.LOGIN_STATUS, true);
        editor.apply();


        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
