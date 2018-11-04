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
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> loginActivity = new ActivityTestRule<LoginActivity>(LoginActivity.class);

    private LoginActivity lActivity;

    @Before
    public void setUp() {
        lActivity = loginActivity.getActivity();
        lActivity.emailText.setText("test@gmail.com");
        lActivity.passwordText.setText("Password1!");
    }

    @UiThreadTest
    @Test
    public void shouldPassWhenAllFieldsAreCorrect() {
        assertTrue(lActivity.validate());
    }


    @UiThreadTest
    @Test
    public void shouldCorrectlyValidateEmail() {
        lActivity.emailText.setText("wrong mail");
        assertFalse(lActivity.validate());
    }

    @UiThreadTest
    @Test
    public void shouldCorrectlyValidatePassword() {
        lActivity.passwordText.setText("short");
        assertFalse(lActivity.validate());
    }
}