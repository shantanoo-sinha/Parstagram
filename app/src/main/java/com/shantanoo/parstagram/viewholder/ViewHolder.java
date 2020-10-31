package com.shantanoo.parstagram.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;
import com.shantanoo.parstagram.model.Post;
import com.shantanoo.parstagram.R;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Shantanoo on 10/23/2020.
 */
public class ViewHolder extends RecyclerView.ViewHolder {

    private Context context;

    private TextView tvPostUserName;
    private TextView tvTimestamp;
    private ImageView ivPostImage;
    private ImageView ivLike;
    private TextView tvLikeNum;
    private ImageView ivComment;
    private TextView tvPostDescription;

    public ViewHolder(@NonNull View itemView, Context context) {
        super(itemView);

        this.context = context;

        tvPostUserName = itemView.findViewById(R.id.tvPostUserName);
        tvTimestamp = itemView.findViewById(R.id.tvTimestamp);
        ivPostImage = itemView.findViewById(R.id.ivPostImage);
        ivLike = itemView.findViewById(R.id.ivLike);
        tvLikeNum = itemView.findViewById(R.id.tvLikeNum);
        ivComment = itemView.findViewById(R.id.ivComment);
        tvPostDescription = itemView.findViewById(R.id.tvPostDescription);
    }

    public void bind(Post post) {
        int radius = 50; // corner radius, higher value = more rounded
        int margin = 30; // crop margin, set to 0 for corners with no crop

        tvPostUserName.setText(post.getUser().getUsername());
        tvPostDescription.setText(post.getDescription());
        tvTimestamp.setText(post.getRelativeTime());

        ParseFile image = post.getImage();
        if (image != null) {
            Glide.with(context)
                    .load(image.getUrl())
                    .placeholder(R.drawable.image_loading)
                    .error(R.drawable.image_error)
                    .fallback(R.drawable.image_error)
                    //.transition(withCrossFade(500))
                    .transform(new RoundedCornersTransformation(radius, margin))
                    .into(ivPostImage);
        }
    }
}
