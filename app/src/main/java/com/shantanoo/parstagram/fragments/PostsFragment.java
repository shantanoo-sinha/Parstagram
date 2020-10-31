package com.shantanoo.parstagram.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.shantanoo.parstagram.R;
import com.shantanoo.parstagram.adapter.PostsAdapter;
import com.shantanoo.parstagram.decoration.DividerItemDecoration;
import com.shantanoo.parstagram.model.Post;

import java.util.ArrayList;
import java.util.List;

import static com.parse.Parse.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostsFragment extends Fragment {

    private static final String TAG = "PostsFragment";

    private RecyclerView rvPosts;
    protected List<Post> allPosts;
    protected PostsAdapter adapter;
    protected SwipeRefreshLayout swipeRefreshLayout;

    public PostsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        allPosts = new ArrayList<>();

        rvPosts = view.findViewById(R.id.rvPosts);
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PostsAdapter(getContext(), allPosts);
        rvPosts.setAdapter(adapter);
        rvPosts.setHasFixedSize(true);
        rvPosts.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getApplicationContext(), R.drawable.recycler_view_divider)));

        swipeRefreshLayout = view.findViewById(R.id.swipeContainerRecycler);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            Log.d(TAG, "onRefresh: Swipe Refresh");
            queryPosts(view);
        });

        queryPosts(view);
    }

    protected void queryPosts(View view) {

        ProgressBar pb = view.findViewById(R.id.pbLoadingRecycler);
        pb.setVisibility(ProgressBar.VISIBLE);

        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.setLimit(20);
        query.addDescendingOrder(Post.KEY_UPDATED_AT);
        query.findInBackground((posts, e) -> {
            if (e != null) {
                Log.e(TAG, "queryPosts: Exception", e);
                Toast.makeText(getContext(), "Failed to query posts", Toast.LENGTH_SHORT).show();
                return;
            }

            adapter.clear();
            adapter.addAll(posts);
            adapter.notifyDataSetChanged();

            swipeRefreshLayout.setRefreshing(false);

            for (Post post : posts) {
                Log.d(TAG, "Post: " + post.getDescription() + ", username:" + post.getUser().getUsername());
            }
        });

        pb.setVisibility(ProgressBar.INVISIBLE);
    }
}