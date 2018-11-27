package lodzka.politechnika.qrcode;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

import lodzka.politechnika.qrcode.model.QRCodeForm;

/**
 * Created by Bartek on 2018-10-30.
 */

public class Utils {

    public final static String LIST = "list";
    public final static String REDAED_FORM = "readedForm";
    public final static String REDAED_FORM_CODE = "readedFormCode";
    public final static String FORM = "form";
    public final static String FORM_CODE = "formCode";
    public final static Integer SAVE_LENGTH = 32;
    public final static Character SPACE_CHAR = '_';
    private final static String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final static Random rng = new SecureRandom();

    private static char randomChar() {
        return ALPHABET.charAt(rng.nextInt(ALPHABET.length()));
    }

    public static String randomUUID(int length, int spacing, char spacerChar) {
        StringBuilder sb = new StringBuilder();
        int spacer = 0;
        while (length > 0) {
            if (spacer == spacing) {
                sb.append(spacerChar);
                spacer = 0;
            }
            length--;
            spacer++;
            sb.append(randomChar());
        }
        return sb.toString();
    }

    public static void saveState(Context context, List<QRCodeForm> qrCodeFormList){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences.contains(Utils.LIST)) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove(Utils.LIST);
            editor.apply();
        }
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(qrCodeFormList);
        editor.putString(Utils.LIST, json);
        editor.apply();
    }
}
