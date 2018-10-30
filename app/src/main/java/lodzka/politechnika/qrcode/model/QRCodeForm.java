package lodzka.politechnika.qrcode.model;

/**
 * Created by Bartek on 2018-10-29.
 */

public class QRCodeForm {
    private String fieldName;

    public QRCodeForm(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public String toString() {
        return "QRCodeForm{" +
                "fieldName='" + fieldName + '\'' +
                '}';
    }
}
