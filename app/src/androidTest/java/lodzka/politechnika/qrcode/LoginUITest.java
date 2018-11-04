package lodzka.politechnika.qrcode;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class LoginUITest {

    @Rule
    public ActivityTestRule<SignupActivity> signupActivityActivityTestRule = new ActivityTestRule<SignupActivity>(SignupActivity.class);

    @Test
    public void ensureTextTypingWorks() {
        onView(withId(R.id.input_email)).perform(typeText("frank@gmail.com"),closeSoftKeyboard());
        onView(withId(R.id.input_email)).check(matches(withText("frank@gmail.com")));
        onView(withId(R.id.input_password)).perform(typeText("Password1!"),closeSoftKeyboard());
        onView(withId(R.id.input_password)).check(matches(withText("Password1!")));
        onView(withId(R.id.input_reEnterPassword)).perform(typeText("Password1!"),closeSoftKeyboard());
        onView(withId(R.id.input_reEnterPassword)).check(matches(withText("Password1!")));
    }

    @Test
    public void ensureTextsAreRight(){
        onView(withId(R.id.btn_signup)).check(matches(withText("Sign up")));
        onView(withId(R.id.link_login)).check(matches(withText("Already a member? Log in")));
    }

}
