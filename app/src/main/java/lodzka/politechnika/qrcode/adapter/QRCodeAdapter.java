package lodzka.politechnika.qrcode.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import lodzka.politechnika.qrcode.R;
import lodzka.politechnika.qrcode.Utils;
import lodzka.politechnika.qrcode.model.QRCodeForm;

/**
 * Created by Bartek on 2018-10-28.
 */

public class QRCodeAdapter extends RecyclerView.Adapter<QRCodeAdapter.QRCodeViewHolder> {

    private List<QRCodeForm> qrCodeFormList;
    private Context context;

    public List<QRCodeForm> getQrCodeFormList() {
        return qrCodeFormList;
    }

    public void setQrCodeFormList(List<QRCodeForm> qrCodeFormList) {
        this.qrCodeFormList = qrCodeFormList;
    }

    public QRCodeAdapter() {
    }

    public QRCodeAdapter(List<QRCodeForm> qrCodeFormList) {
        this.qrCodeFormList = qrCodeFormList;
    }

    @NonNull
    @Override
    public QRCodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.qr_code_row, parent, false);
        context = itemView.getContext();
        return new QRCodeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull QRCodeViewHolder holder, final int position) {
        if (position != RecyclerView.NO_POSITION) {
            QRCodeForm qrCodeForm = qrCodeFormList.get(position);
            holder.fieldName.setText(qrCodeForm.getFieldName());
            holder.fieldType.setText(qrCodeForm.getFieldType());

            holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    qrCodeFormList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, qrCodeFormList.size());
                    Utils.saveState(context, qrCodeFormList);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return qrCodeFormList.size();
    }

    public class QRCodeViewHolder extends RecyclerView.ViewHolder {
        private TextView fieldName;
        private TextView fieldType;
        private ImageButton deleteButton;


        public QRCodeViewHolder(View itemView) {
            super(itemView);
            fieldName = itemView.findViewById(R.id.text1);
            deleteButton = itemView.findViewById(R.id.delete_button);
            fieldType = itemView.findViewById(R.id.text2);
        }
    }
}
