package com.github.hakkazuu.prettyload_sample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<User> mUserList;

    public UserAdapter(List<User> userList) {
        mUserList = new ArrayList<>(userList);
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        holder.bind(mUserList.get(position));
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private User mUser;

        private ConstraintLayout mLayout;
        private TextView mNameTextView;
        private TextView mCountryTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mLayout = (ConstraintLayout) itemView;
            mNameTextView = mLayout.findViewById(R.id.item_user_name_text_view);
            mCountryTextView = mLayout.findViewById(R.id.item_user_country_text_view);
        }

        public void bind(User user) {
            mUser = user;

            mNameTextView.setText(mUser.getName());
            mCountryTextView.setText(mUser.getCountry());
        }

    }

}