package lodzka.politechnika.qrcode.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import lodzka.politechnika.qrcode.R;
import lodzka.politechnika.qrcode.Utils;
import lodzka.politechnika.qrcode.api.ApiUtils;
import lodzka.politechnika.qrcode.fragment.QRCodeGenerateFragment;
import lodzka.politechnika.qrcode.model.Elements;
import lodzka.politechnika.qrcode.model.Root;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Bartek on 2018-11-10.
 */

public class ListsAdapter extends RecyclerView.Adapter<ListsAdapter.ListsViewHolder> {

    private List<Root> formList;
    private ElementsAdapter elementsAdapter;
    private FragmentManager fragmentManager;
    private Context context;

    public List<Root> getFormList() {
        return formList;
    }

    public void setFormList(List<Root> formList) {
        this.formList = formList;
    }

    public ListsAdapter(List<Root> formList, Context context) {
        this.formList = formList;
        this.context = context;
    }

    public ListsAdapter() {
    }

    @NonNull
    @Override
    public ListsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_lists_row, parent, false);
        return new ListsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListsViewHolder holder, final int position) {
        final Root root = formList.get(position);
        List<Elements> elements = root.getElements();
        elementsAdapter = new ElementsAdapter(elements);
        holder.listName.setText(root.getName());
        holder.recyclerView.setAdapter(elementsAdapter);

        holder.generateCsvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiUtils.getFormApi().generateCSV(root.getFormCode()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(context,"CSV zostało wysłane", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(context,"Nie udało się wygenerować CSV", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return formList.size();
    }

    public class ListsViewHolder extends RecyclerView.ViewHolder {
        private TextView listName;
        private RecyclerView recyclerView;
        private Button generateCsvButton;


        public ListsViewHolder(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    FragmentActivity fragmentActivity = (FragmentActivity) context;
                    fragmentManager = fragmentActivity.getSupportFragmentManager();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Utils.FORM, (Serializable) getFormList().get(getAdapterPosition()));
                    bundle.putString(Utils.FORM_CODE, getFormList().get(getAdapterPosition()).getCode());
                    Fragment fragment = new QRCodeGenerateFragment();
                    fragment.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.miscFragment, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();


                }
            });
            listName = itemView.findViewById(R.id.list_name);
            recyclerView = itemView.findViewById(R.id.data_list_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            generateCsvButton = itemView.findViewById(R.id.csv_button);
        }

    }
}
