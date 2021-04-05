package com.androexp.ada247;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.VH> {

    private Context context;
    private List<UserModel> userModelList;

    public UserAdapter(Context context, List<UserModel> userModelList) {
        this.context = context;
        this.userModelList = userModelList;
    }

    @NonNull
    @Override
    public UserAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_des, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.VH holder, int position) {
        UserModel model = userModelList.get(position);
        holder.user_name.setText(model.getUserName());
        holder.user_gender.setText(model.getUserGender());
        holder.user_email.setText(model.getUserEmail());
        holder.user_status.setText(model.getUserStatus());
    }

    @Override
    public int getItemCount() {
        return userModelList.size();
    }

    public static class VH extends RecyclerView.ViewHolder {

        private TextView user_name, user_gender, user_email, user_status;

        public VH(@NonNull View itemView) {
            super(itemView);

            user_name = itemView.findViewById(R.id.tv_user_name);
            user_gender = itemView.findViewById(R.id.tv_gender);
            user_email = itemView.findViewById(R.id.tv_email);
            user_status = itemView.findViewById(R.id.tv_status);
        }
    }
}
