package lodzka.politechnika.qrcode;

import android.content.Context;
import android.widget.EditText;

public class Validator {

    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public boolean validate(EditText email, EditText password) {
        return isValidEmail(email) && isValidPassword(password);
    }

    public boolean validate(EditText email, EditText password, EditText passwordConfirmation) {
        return isValidEmail(email) && isValidPassword(password, passwordConfirmation);
    }

    private boolean isValidPassword(EditText password) {
        String passwordValue = password.getText().toString();

        if (passwordValue.isEmpty() || passwordValue.length() < 8) {
            password.setError(context.getResources().getString(R.string.longer_than_8));
            return false;
        }
        else {
            password.setError(null);
            return true;
        }
    }

    private boolean isValidPassword(EditText password, EditText reEnterPassword) {
        String passwordValue = password.getText().toString();
        String reEnterPasswordValue = reEnterPassword.getText().toString();

        if (passwordValue.isEmpty() || passwordValue.length() < 8) {
            password.setError(context.getResources().getString(R.string.longer_than_8));
            return false;
        }
        else if(reEnterPasswordValue.isEmpty() || !(reEnterPasswordValue.equals(passwordValue))) {
            reEnterPassword.setError(context.getResources().getString(R.string.password_do_not_match));
            return false;
        }
        else {
            password.setError(null);
            reEnterPassword.setError(null);
            return true;
        }
    }

    private boolean isValidEmail(EditText email) {
        String emailValue = email.getText().toString();

        if (emailValue.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailValue).matches()) {
            email.setError(context.getResources().getString(R.string.enter_valid_email));
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }
}
