package com.shantanoo.parstagram.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.shantanoo.parstagram.LoginActivity;
import com.shantanoo.parstagram.R;
import com.shantanoo.parstagram.adapter.PostsAdapter;
import com.shantanoo.parstagram.decoration.DividerItemDecoration;
import com.shantanoo.parstagram.model.Post;

import java.util.ArrayList;
import java.util.List;

public class RecyclerActivity extends AppCompatActivity {

    private static final String TAG = "RecyclerActivity";

    private List<Post> posts;
    private RecyclerView rvPosts;
    private PostsAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        posts = new ArrayList<>();

        rvPosts = findViewById(R.id.rvPosts);
        rvPosts.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PostsAdapter(this, posts);
        rvPosts.setAdapter(adapter);
        rvPosts.setHasFixedSize(true);
        rvPosts.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getApplicationContext(), R.drawable.recycler_view_divider)));

        swipeRefreshLayout = findViewById(R.id.swipeContainer);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d(TAG, "onRefresh: Swipe Refresh");
                queryPosts();
            }
        });

        queryPosts();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.mnuLogout:
                ParseUser currentUser = ParseUser.getCurrentUser();
                if (currentUser == null)
                    return true;

                Log.d(TAG, "onOptionsItemSelected: Logging out.");
                ParseUser.logOut();

                Log.d(TAG, "onOptionsItemSelected: Log out done. Sending to Login.");
                intent = new Intent(RecyclerActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.mnuCompose:
                Log.d(TAG, "onOptionsItemSelected: Compose, Navigating to Main Activity");
                intent = new Intent(RecyclerActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                Log.e(TAG, "onOptionsItemSelected: Unknown menu item");
                Toast.makeText(RecyclerActivity.this, "Unknown menu item", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void queryPosts() {

        ProgressBar pb = (ProgressBar) findViewById(R.id.pbLoading);
        pb.setVisibility(ProgressBar.VISIBLE);

        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.addDescendingOrder(Post.KEY_UPDATED_AT);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "queryPosts: Exception", e);
                    Toast.makeText(RecyclerActivity.this, "Failed to query posts", Toast.LENGTH_SHORT).show();
                    return;
                }

                RecyclerActivity.this.posts.addAll(posts);
                adapter.notifyDataSetChanged();
                adapter.clear();
                adapter.addAll(posts);
                swipeRefreshLayout.setRefreshing(false);

                for (Post post : posts) {
                    Log.d(TAG, "Post: " + post.getDescription() + ", username:" + post.getUser().getUsername());
                }
            }
        });

        pb.setVisibility(ProgressBar.INVISIBLE);
    }

}