package lodzka.politechnika.qrcode.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import lodzka.politechnika.qrcode.R;
import lodzka.politechnika.qrcode.model.Elements;
import lodzka.politechnika.qrcode.model.Root;

/**
 * Created by Bartek on 2018-11-10.
 */

public class ListsAdapter extends RecyclerView.Adapter<ListsAdapter.ListsViewHolder> {

    private List<Root> formList;
    private ElementsAdapter elementsAdapter;

    public List<Root> getFormList() {
        return formList;
    }

    public void setFormList(List<Root> formList) {
        this.formList = formList;
    }

    public ListsAdapter(List<Root> formList) {
        this.formList = formList;
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
    public void onBindViewHolder(@NonNull ListsViewHolder holder, int position) {
        Root root = formList.get(position);
        List<Elements> elements = root.getElements();
        elementsAdapter = new ElementsAdapter(elements);
        holder.listName.setText(root.getName());
        holder.recyclerView.setAdapter(elementsAdapter);


    }

    @Override
    public int getItemCount() {
        return formList.size();
    }

    public class ListsViewHolder extends RecyclerView.ViewHolder {
        private TextView listName;
        private RecyclerView recyclerView;

        public ListsViewHolder(View itemView) {
            super(itemView);
            listName = itemView.findViewById(R.id.list_name);
            recyclerView = itemView.findViewById(R.id.data_list_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
        }

    }
}
