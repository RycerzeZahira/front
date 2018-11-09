package lodzka.politechnika.qrcode.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import lodzka.politechnika.qrcode.R;
import lodzka.politechnika.qrcode.model.Elements;

public class AnswerAdapter extends ArrayAdapter<Elements> {

    public AnswerAdapter(@NonNull Context context, ArrayList<Elements> root) {
        super(context, 0, root);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Elements element = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.answer_row, parent, false);
        }

        TextView key = (TextView) convertView.findViewById(R.id.key);
        EditText value = (EditText) convertView.findViewById(R.id.value);

        key.setText(element.getName());
        element.setCode(value.getText().toString());

        return convertView;
    }

}
