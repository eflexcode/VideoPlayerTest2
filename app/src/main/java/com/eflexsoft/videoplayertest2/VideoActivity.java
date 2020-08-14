package com.eflexsoft.videoplayertest2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.VideoView;

public class VideoActivity extends AppCompatActivity {

    VideoView videoView;
    AppCompatSeekBar seekBar;
    boolean isPlaying;
    ImageView imageView;

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        videoView = findViewById(R.id.video);
        seekBar = findViewById(R.id.seek);
        imageView = findViewById(R.id.playOrPause);

        init();

    }

    private void init() {

        String url = getIntent().getStringExtra("video");

        videoView.setVideoPath(url);
        videoView.start();
        isPlaying = true;

        imageView.setImageResource(R.drawable.ic_pause);
        handler = new Handler();
        updateSeekBar();
    }

    private void updateSeekBar() {

        handler.postDelayed(seekRunnable,100);
    }

    Runnable seekRunnable = new Runnable() {
        @Override
        public void run() {
            seekBar.setMax(videoView.getDuration());
            seekBar.setProgress(videoView.getCurrentPosition());

            handler.postDelayed(seekRunnable,100);

            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    handler.removeCallbacks(seekRunnable);
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    handler.removeCallbacks(seekRunnable);
                    videoView.seekTo(seekBar.getProgress());
                    updateSeekBar();
                }
            });
        }
    };

    public void playAndPause(View view) {
        if (isPlaying) {
            videoView.pause();
            imageView.setImageResource(R.drawable.ic_play);
            isPlaying = false;
        } else {
            videoView.start();
            imageView.setImageResource(R.drawable.ic_pause);
            isPlaying = true;
        }
    }
}