package lodzka.politechnika.qrcode;

import android.content.Context;
import android.content.res.Resources;
import android.text.Editable;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ValidatorTest {


    private Context context;
    private Validator validator;
    private EditText email;
    private EditText password;
    private EditText passwordConfirmation;

    @Before
    public void setUp() {
        context = mock(Context.class);
        validator = new Validator();
        validator.setContext(context);
        when(context.getResources()).thenReturn(mock(Resources.class));
        email = mock(EditText.class);
        password = mock(EditText.class);
        passwordConfirmation = mock(EditText.class);

        when(context.getResources().getString(R.string.longer_than_8)).thenReturn("Password is too short");
        when(context.getResources().getString(R.string.password_do_not_match)).thenReturn("Passwords does not match");
        when(context.getResources().getString(R.string.enter_valid_email)).thenReturn("Enter correct email");

        when(email.getText()).thenReturn(mock(Editable.class));
        when(password.getText()).thenReturn(mock(Editable.class));
        when(passwordConfirmation.getText()).thenReturn(mock(Editable.class));

        when(email.getText().toString()).thenReturn("test@gmail.com");
        when(password.getText().toString()).thenReturn("Password1!");
        when(passwordConfirmation.getText().toString()).thenReturn("Password1!");
    }

    @Test
    public void validateShouldPass() {
        assertTrue(validator.validate(email, password));
        assertTrue(validator.validate(email, password, passwordConfirmation));
    }

    @Test
    public void shouldCorrectlyValidateEmail() {
        when(email.getText().toString()).thenReturn("test com");
        assertFalse(validator.validate(email, password));
        assertFalse(validator.validate(email, password, passwordConfirmation));
    }

    @Test
    public void shouldCorrectlyValidatePassword() {
        when(password.getText().toString()).thenReturn("short");
        assertFalse(validator.validate(email, password));
        assertFalse(validator.validate(email, password, passwordConfirmation));
    }

    @Test
    public void shouldCorrectlyValidateReEnterPassword() {
        when(passwordConfirmation.getText().toString()).thenReturn("short");
        assertFalse(validator.validate(email, password, passwordConfirmation));
    }
}