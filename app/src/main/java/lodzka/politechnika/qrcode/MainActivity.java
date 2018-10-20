package lodzka.politechnika.qrcode;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "userPrefs";
    public static final String LOGIN_STATUS = "loginStatus";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(lodzka.politechnika.qrcode.R.layout.activity_main);
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        Boolean loginStatus = preferences.getBoolean(LOGIN_STATUS,false);
        if (loginStatus == false) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(lodzka.politechnika.qrcode.R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == lodzka.politechnika.qrcode.R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
