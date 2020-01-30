package com.abalone.ymlchallenge.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.abalone.ymlchallenge.R;
import com.abalone.ymlchallenge.UserDetailActivity;
import com.abalone.ymlchallenge.model.Profile;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AvatarGalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Profile> profiles;
    private Context ctx;

    public AvatarGalleryAdapter(Context ctx, List<Profile> profiles){
        this.profiles = profiles;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.avatar_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.img_avatar2);

        Glide.with(holder.itemView.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(profiles.get(position).getAvatarUrl())
                .into(((ViewHolder)holder).avatar_image);

        //Needs unique transition name so use profile username
        ViewCompat.setTransitionName(((ViewHolder) holder).avatar_image, profiles.get(position).getUsername());

        ((ViewHolder)holder).username_txt.setText(profiles.get(position).getUsername());
    }

    @Override
    public int getItemCount() {
        if(profiles == null){
            return 0;
        }else{
            return profiles.size();
        }
    }

    public void setProfiles(List<Profile> profiles){
        this.profiles = profiles;
        notifyDataSetChanged();
    }

    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CircleImageView avatar_image;
        private TextView username_txt;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            avatar_image = itemView.findViewById(R.id.avatar_image);
            username_txt = itemView.findViewById(R.id.username_txt);
        }

        /* When an item is selected, send to detail */
        @Override
        public void onClick(View view){
            int position = getAdapterPosition();

            Intent intent = new Intent(ctx, UserDetailActivity.class);
            intent.putExtra("username", profiles.get(position).getUsername());
            intent.putExtra("transition_name", ViewCompat.getTransitionName(avatar_image));
            intent.putExtra("avatar_url", profiles.get(position).getAvatarUrl());

            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    (Activity) ctx, avatar_image, ViewCompat.getTransitionName(avatar_image));

            ctx.startActivity(intent, options.toBundle());
        }

    }
}
