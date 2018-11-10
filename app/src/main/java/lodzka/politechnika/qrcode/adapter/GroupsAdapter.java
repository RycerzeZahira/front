package lodzka.politechnika.qrcode.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import lodzka.politechnika.qrcode.R;
import lodzka.politechnika.qrcode.model.Group;

/**
 * Created by Bartek on 2018-11-09.
 */

public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.GroupsViewHolder> {

   private List<Group> groupList;

    public List<Group> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<Group> groupList) {
        this.groupList = groupList;
    }


    public GroupsAdapter(){
    }

    @NonNull
    @Override
    public GroupsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_groups_row,parent,false);
        return new GroupsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupsViewHolder holder, final int position) {
        Group group = groupList.get(position);
        holder.groupName.setText(group.getName());

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,groupList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }


    public GroupsAdapter(List<Group> groupList){
        this.groupList = groupList;
    }

    public class GroupsViewHolder extends RecyclerView.ViewHolder {
        private TextView groupName;
        private Button deleteButton;

        public GroupsViewHolder(View itemView){
            super(itemView);
            groupName = itemView.findViewById(R.id.group_name);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}
