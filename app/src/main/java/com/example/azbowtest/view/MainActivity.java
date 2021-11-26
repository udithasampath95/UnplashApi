package com.example.azbowtest.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.azbowtest.R;
import com.example.azbowtest.adapter.PhotosAdapter;
import com.example.azbowtest.model.Photo;
import com.example.azbowtest.network.APIService;
import com.example.azbowtest.network.RetrofitInstance;
import com.example.azbowtest.utill.EndlessRecyclerViewScrollListener;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private int pageNumber = 1;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private PhotosAdapter adapter;
    private PhotosAdapter.OnPhotoClickedListener photoClickListener;
    private APIService apiService;
    private Toolbar toolbar;
    private Button followBtn,messageBtn;
    private TextView photosCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        apiService = RetrofitInstance.getRetroClient().create(APIService.class);
        setListners();
        loadPhotos();
        setButtonBackGround();
    }

    private void setButtonBackGround() {
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            followBtn.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.menu_btn_follow_bg));
        } else {
            followBtn.setBackground(ContextCompat.getDrawable(this, R.drawable.menu_btn_follow_bg));
        }

        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            messageBtn.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.menu_btn_msg_bg));
        } else {
            messageBtn.setBackground(ContextCompat.getDrawable(this, R.drawable.menu_btn_msg_bg));
        }
    }

    private void setListners() {
        photoClickListener = new PhotosAdapter.OnPhotoClickedListener() {
            @Override
            public void photoClicked(Photo photo, ImageView imageView) {
                Log.d("MainActivity ", "Photo clicked!");
            }
        };

        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PhotosAdapter(new ArrayList<Photo>(), this, photoClickListener);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadPhotos();
            }
        });
    }

    private void initViews() {
        photosCount=findViewById(R.id.photosCount);
        messageBtn=findViewById(R.id.messageBtn);
        followBtn=findViewById(R.id.followBtn);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
    }


    private void loadPhotos() {
        progressBar.setVisibility(View.VISIBLE);
        apiService.getPhotos(pageNumber, null, "latest")
                .enqueue(new Callback<List<Photo>>() {
                    @Override
                    public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                        List<Photo> photos = response.body();
                        Log.d("Photos", "Photos Fetched " + photos.size());
                        pageNumber++;
                        adapter.addPhotos(photos);
                        recyclerView.setAdapter(adapter);
                        photosCount.setText(adapter.getItemCount()+"\n"+"Photos");
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<List<Photo>> call, Throwable t) {
                        Log.d("Photos", "onFailure " + t.getMessage());
                        progressBar.setVisibility(View.GONE);
                    }
                });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}