package lodzka.politechnika.qrcode.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import lodzka.politechnika.qrcode.R;
import lodzka.politechnika.qrcode.api.ApiUtils;
import lodzka.politechnika.qrcode.fragment.FormsForSpecificGroupFragment;
import lodzka.politechnika.qrcode.model.Group;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Bartek on 2018-11-09.
 */

public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.GroupsViewHolder> {

    private FragmentManager fragmentManager;
    private Context context;
    private List<Group> groupList;

    public List<Group> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<Group> groupList) {
        this.groupList = groupList;
    }


    public GroupsAdapter() {

    }

    @NonNull
    @Override
    public GroupsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_groups_row, parent, false);
        return new GroupsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final GroupsViewHolder holder, final int position) {
        final Group group = groupList.get(position);
        holder.groupName.setText(group.getName());
        //holder.sizeGroup.setText(String.valueOf(group.getUsers().size()));
        holder.sizeGroup.setText("0"); //TODO Chwilowo zmienione bo back ma problem :-)
        holder.groupCode.setText(group.getCode());
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Group deleteGroup = groupList.get(position);
                ApiUtils.getGroupApi().deleteGroup(deleteGroup.getCode()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        groupList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, groupList.size());
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });


        holder.generateCsvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Group sendGroup = groupList.get(position);
                ApiUtils.getGroupApi().generateCSV(sendGroup.getCode()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(context,"CSV correctly send", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(context,"Generate CSV Failure", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }


    public GroupsAdapter(List<Group> groupList, Context context) {
        this.groupList = groupList;
        this.context = context;
    }

    public class GroupsViewHolder extends RecyclerView.ViewHolder {
        private TextView groupName;
        private Button deleteButton;
        private Button generateCsvButton;
        private TextView sizeGroup;
        private TextView groupCode;

        public GroupsViewHolder(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Log.d("Group name", groupName.getText().toString());
                    FragmentActivity fragmentActivity = (FragmentActivity) context;
                    fragmentManager = fragmentActivity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.miscFragment, new FormsForSpecificGroupFragment(groupCode.getText().toString()));
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });
            groupCode = itemView.findViewById(R.id.group_code);
            groupName = itemView.findViewById(R.id.list_name);
            sizeGroup = itemView.findViewById(R.id.sizeGroup);
            deleteButton = itemView.findViewById(R.id.delete_button);
            generateCsvButton = itemView.findViewById(R.id.csv_generate);
        }
    }
}
