package lodzka.politechnika.qrcode;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import lodzka.politechnika.qrcode.model.Root;

public class QRCodeGenerator {

    public static Bitmap generate(Root root, int QRcodeWidth) throws WriterException {
        Gson gson = new Gson();
        JSONObject json = null;
        try {
            json = new JSONObject(gson.toJson(root));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    json.toString(),
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );
        } catch (IllegalArgumentException Illegalargumentexception) {
            return null;
        }
        int[] pixels = new int[bitMatrix.getWidth() * bitMatrix.getHeight()];
        for (int y = 0; y < bitMatrix.getHeight(); y++) {
            int offset = y * bitMatrix.getWidth();
            for (int x = 0; x < bitMatrix.getWidth(); x++) {
                pixels[offset + x] = bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrix.getWidth(), bitMatrix.getHeight(), Bitmap.Config.ARGB_4444);
        bitmap.setPixels(pixels, 0, 350, 0, 0, bitMatrix.getWidth(), bitMatrix.getHeight());
        return bitmap;
    }
}
