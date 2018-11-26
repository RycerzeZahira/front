package lodzka.politechnika.qrcode.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import lodzka.politechnika.qrcode.R;
import lodzka.politechnika.qrcode.api.ApiUtils;
import lodzka.politechnika.qrcode.fragment.MyGroupsFragment;
import lodzka.politechnika.qrcode.model.Group;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PublicGroupsAdapter extends RecyclerView.Adapter<PublicGroupsAdapter.PublicGroupsViewHolder> {

    private FragmentManager fragmentManager;
    private Context context;
    private List<Group> groupList;

    public List<Group> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<Group> groupList) {
        this.groupList = groupList;
    }


    public PublicGroupsAdapter() {

    }

    @NonNull
    @Override
    public PublicGroupsAdapter.PublicGroupsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_public_groups_row, parent, false);
        return new PublicGroupsAdapter.PublicGroupsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final PublicGroupsAdapter.PublicGroupsViewHolder holder, final int position) {
        final Group group = groupList.get(position);
        holder.groupName.setText(group.getName());
        //holder.sizeGroup.setText(String.valueOf(group.getUsers().size()));
        holder.sizeGroup.setText("0"); //TODO Chwilowo zmienione bo back ma problem :-)
        holder.groupCode.setText(group.getCode());

    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }


    public PublicGroupsAdapter(List<Group> groupList, Context context) {
        this.groupList = groupList;
        this.context = context;
    }

    public class PublicGroupsViewHolder extends RecyclerView.ViewHolder {
        private TextView groupName;
        private TextView sizeGroup;
        private TextView groupCode;

        public PublicGroupsViewHolder(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    ApiUtils.getGroupApi().addMeToGroup(groupCode.getText().toString()).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.code() == 200) Toast.makeText(context, "Added to group", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(context, "Cannot add to group", Toast.LENGTH_LONG).show();
                        }
                    });
                    FragmentActivity fragmentActivity = (FragmentActivity) context;
                    fragmentManager = fragmentActivity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.miscFragment, new MyGroupsFragment());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });
            groupCode = itemView.findViewById(R.id.group_code);
            groupName = itemView.findViewById(R.id.list_name);
            sizeGroup = itemView.findViewById(R.id.sizeGroup);
        }
    }
}

