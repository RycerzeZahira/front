package lodzka.politechnika.qrcode;

import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class SignupActivityTest {

    @Rule
    public ActivityTestRule<SignupActivity> signupActivity = new ActivityTestRule<SignupActivity>(SignupActivity.class);

    private SignupActivity sActivity;

    @Before
    public void setUp() {
        sActivity = signupActivity.getActivity();
        sActivity.emailText.setText("test@gmail.com");
        sActivity.passwordText.setText("Password1!");
        sActivity.reEnterPasswordText.setText("Password1!");

    }

    @UiThreadTest
    @Test
    public void shouldPassWhenAllFieldsAreCorrect() {
        assertTrue(sActivity.validate());
    }


    @UiThreadTest
    @Test
    public void shouldCorrectlyValidateEmail() {
        sActivity.emailText.setText("wrong mail");
        assertFalse(sActivity.validate());
    }

    @UiThreadTest
    @Test
    public void shouldCorrectlyValidatePassword() {
        sActivity.passwordText.setText("short");
        assertFalse(sActivity.validate());
    }

    @UiThreadTest
    @Test
    public void shouldCorrectlyValidateReEnterPassword() {
        sActivity.reEnterPasswordText.setText("short");
        assertFalse(sActivity.validate());
    }
}