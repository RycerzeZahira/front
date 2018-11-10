package lodzka.politechnika.qrcode.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import lodzka.politechnika.qrcode.R;
import lodzka.politechnika.qrcode.model.Elements;

/**
 * Created by Bartek on 2018-11-10.
 */

public class ElementsAdapter extends RecyclerView.Adapter<ElementsAdapter.ElementsViewHolder> {
    private List<Elements> elementsList;

    public ElementsAdapter(List<Elements> elements){this.elementsList = elements;}

    @NonNull
    @Override
    public ElementsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.elements_row,parent,false);
        return new ElementsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ElementsViewHolder holder, int position) {
        Elements elements = elementsList.get(position);
        holder.elementName.setText(elements.getName());
    }

    @Override
    public int getItemCount() {
        return elementsList.size();
    }


    public class ElementsViewHolder extends RecyclerView.ViewHolder{
        private TextView elementName;

        public ElementsViewHolder(View itemView){
            super(itemView);
            elementName = itemView.findViewById(R.id.element_name);
        }
    }


}
