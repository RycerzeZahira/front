package lodzka.politechnika.qrcode;

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


    @BindView(lodzka.politechnika.qrcode.R.id.input_name)
    EditText _firstName;
    @BindView(lodzka.politechnika.qrcode.R.id.input_address)
    EditText _lastName;
    @BindView(lodzka.politechnika.qrcode.R.id.input_email)
    EditText _emailText;
    @BindView(lodzka.politechnika.qrcode.R.id.input_password)
    EditText _passwordText;
    @BindView(lodzka.politechnika.qrcode.R.id.input_reEnterPassword)
    EditText _reEnterPasswordText;
    @BindView(R.id.btn_signup)
    Button _signupButton;
    @BindView(lodzka.politechnika.qrcode.R.id.link_login)
    TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(lodzka.politechnika.qrcode.R.layout.activity_signup);
        ButterKnife.bind(this);
        _signupButton = findViewById(R.id.btn_signup);
        userApi = ApiUtils.getAUserApi();

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
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
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        String firstName = _firstName.getText().toString();
        String lastName = _lastName.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        registerUserPost(firstName, lastName, email, password);

    }


    public void registerUserPost(String firstName, String lastName, String email, String password) {
        User user = new User(firstName, lastName, email, password);
        userApi.registerUser(user).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: post submitted to API");
                    onSignupSuccess();
                } else {
                    Log.d(TAG, "onFailure: unable to submit post to api");
                    onSignupFailed();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d(TAG, "onFailure: unable to submit post to api");
                onSignupFailed();
            }
        });
    }

    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        Toast.makeText(getBaseContext(), "Signup completed. Please log in", Toast.LENGTH_LONG).show();
        startActivity(intent);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Signup failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _firstName.getText().toString();
        String address = _lastName.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _firstName.setError("at least 3 characters");
            valid = false;
        } else {
            _firstName.setError(null);
        }

        if (address.isEmpty()) {
            _lastName.setError("Enter Valid Address");
            valid = false;
        } else {
            _lastName.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }


        if (password.isEmpty() || password.length() < 8) {
            _passwordText.setError("longer than 8");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 8 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

        return valid;
    }
}