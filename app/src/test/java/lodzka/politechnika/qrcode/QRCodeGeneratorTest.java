package lodzka.politechnika.qrcode;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.zxing.WriterException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;

@RunWith(MockitoJUnitRunner.class)
public class QRCodeGeneratorTest {

    private HashMap data = new HashMap();
    private int QRCodeWidth = 500;
    private Bitmap exampleBitmap = BitmapFactory.decodeFile("./app/src/test/resources/grcode.png");

    @Before
    public void setup() {
        data.put("Surname", "Kowalski");
        data.put("Name", "Mateusz");
    }

    @Test
    public void shouldCorrectlyGenerateQRCodeBitmap() throws WriterException {
//        if (QRCodeGenerator.generate(data, QRCodeWidth).sameAs(exampleBitmap)) {
//            Log.d("Wynik", "True");
//        } else {
//            QRCodeGenerator.generate(data, QRCodeWidth);
//            Log.d("Wynik", "False");
//        }
    }

}