package lodzka.politechnika.qrcode.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import lodzka.politechnika.qrcode.R;
import lodzka.politechnika.qrcode.model.Elements;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.AnswerViewHolder> {

    private ArrayList<Elements> elements;


    public ArrayList<Elements> getElements() {
        return elements;
    }

    public AnswerAdapter(@NonNull Context context, ArrayList<Elements> elements) {
        this.elements = elements;
    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        Elements element = getItem(position);
//        if (convertView == null) {
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.answer_row, parent, false);
//        }
//
//        TextView key = (TextView) convertView.findViewById(R.id.key);
//        EditText value = (EditText) convertView.findViewById(R.id.value);
//
//        key.setText(element.getName());
//        element.setCode(value.getText().toString());
//
//        return convertView;
//    }

    @NonNull
    @Override
    public AnswerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_row, parent, false);
        return new AnswerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerViewHolder holder, int position) {
        holder.key.setText(elements.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return elements.size();
    }


    class AnswerViewHolder extends RecyclerView.ViewHolder {

        private EditText editText;
        private TextView key;

        public AnswerViewHolder(View itemView) {
            super(itemView);

            editText = itemView.findViewById(R.id.value);
            key = itemView.findViewById(R.id.key);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    elements.get(getAdapterPosition()).setName(editText.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

}
