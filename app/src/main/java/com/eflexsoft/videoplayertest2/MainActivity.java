package com.eflexsoft.videoplayertest2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import com.eflexsoft.videoplayertest2.adapter.VideoAdapter;
import com.eflexsoft.videoplayertest2.model.VideoModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    int id;
    int folder_name;
    int data;
    int thumb;

    String absoluteUri;
    Uri uri;
    Cursor cursor;

    List<VideoModel> videoModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.mainRecycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET, Manifest.permission.CAMERA}, 7);
        } else {

            getVideos();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 7 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getVideos();
        }

    }

    private void getVideos() {

        uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        String orderBy= MediaStore.Images.Media.DATE_TAKEN;


        String projection[] = {
                MediaStore.MediaColumns.DATA,
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Thumbnails.DATA
        };

        cursor = getApplicationContext().getContentResolver().query(uri,projection,null,null,orderBy+" DESC");

        id = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
        data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        thumb = cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails.DATA);
        folder_name = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME);

        while (cursor.moveToNext()){
            absoluteUri = cursor.getString(data);

            VideoModel videoModel = new VideoModel();
            videoModel.setSelected(false);
            videoModel.setVideoPath(absoluteUri);
            videoModel.setVideoThumbnail(cursor.getString(thumb));

            videoModelList.add(videoModel);

        }

        recyclerView.setAdapter(new VideoAdapter(videoModelList, this));
    }
}