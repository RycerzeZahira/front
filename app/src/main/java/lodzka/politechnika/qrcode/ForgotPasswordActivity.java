package lodzka.politechnika.qrcode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import lodzka.politechnika.qrcode.api.ApiUtils;
import lodzka.politechnika.qrcode.api.UserApi;
import lodzka.politechnika.qrcode.model.Email;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Bartek on 2018-11-26.
 */

public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText forgotPasswordEditText;
    private Email email;
    private Button resetButton;
    private UserApi userApi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_forgot);
        userApi = ApiUtils.getAUserApi();
        forgotPasswordEditText = findViewById(R.id.forgotPasswordEditText);
        resetButton = findViewById(R.id.buttonReset);
        email = new Email();

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!forgotPasswordEditText.getText().toString().isEmpty()) {
                    email.setEmail(forgotPasswordEditText.getText().toString());
                    userApi.resetPassword(email).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Toast.makeText(getBaseContext(),"Hasło zostało zresetowane. Sprawdź maila",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                        }
                    });
                } else {
                    Toast.makeText(getBaseContext(), "Pole maila nie może być puste", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }


}
