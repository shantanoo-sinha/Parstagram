package com.shantanoo.parstagram.fragments;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.shantanoo.parstagram.R;
import com.shantanoo.parstagram.model.Post;

import java.util.List;

/**
 * Created by Shantanoo on 10/30/2020.
 */
public class ProfileFragment extends PostsFragment {

    private static final String TAG = "ProfileFragment";

    @Override
    protected void queryPosts(View view) {
        super.queryPosts(view);

        ProgressBar pb = (ProgressBar) view.findViewById(R.id.pbLoadingRecycler);
        pb.setVisibility(ProgressBar.VISIBLE);

        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        query.setLimit(20);
        query.addDescendingOrder(Post.KEY_UPDATED_AT);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "queryPosts: Exception", e);
                    Toast.makeText(getContext(), "Failed to query posts", Toast.LENGTH_SHORT).show();
                    return;
                }

                adapter.clear();
                adapter.addAll(posts);
                adapter.notifyDataSetChanged();
                //swipeRefreshLayout.setRefreshing(false);

                for (Post post : posts) {
                    Log.d(TAG, "Post: " + post.getDescription() + ", username:" + post.getUser().getUsername());
                }
            }
        });

        pb.setVisibility(ProgressBar.INVISIBLE);
    }
}
