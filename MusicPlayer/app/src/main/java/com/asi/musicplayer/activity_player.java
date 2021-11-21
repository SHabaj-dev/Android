package com.asi.musicplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.gauravk.audiovisualizer.visualizer.BarVisualizer;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class activity_player extends AppCompatActivity {

    Button btnPlay, btnNext, btnPrev, btnFastForward, btnFastPrev;
    TextView tsongName, songStart, songStop;
    SeekBar seekBar;

    BarVisualizer barVisualizer;
    ImageView imageView;
    Thread updateSeekbar;

    String sname;
    public static final String EXTRA_NAME = "song_name";
    static MediaPlayer mediaPlayer;
    int position;
    ArrayList<File> mySongs;
  
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        if(barVisualizer != null){
            barVisualizer.release();
        }
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        getSupportActionBar().setTitle("Now Playing");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnPlay = findViewById(R.id.playbtn);
        btnPrev = findViewById(R.id.prevBtn);
        btnNext = findViewById(R.id.nextBtn);
        btnFastForward = findViewById(R.id.fastForward);
        btnFastPrev = findViewById(R.id.fastRewind);
        seekBar = findViewById(R.id.seekBar);
        tsongName = findViewById(R.id.textViewSongName);
        songStart = findViewById(R.id.startSongTime);
        songStop = findViewById(R.id.endSongTime);
        imageView = findViewById(R.id.playerImageView);
        barVisualizer = findViewById(R.id.blast);

        generateRandomColorForBarVisualiser();
        if(mediaPlayer != null){
            mediaPlayer.stop();;
            mediaPlayer.release();
        }

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        mySongs = (ArrayList) bundle.getParcelableArrayList("songs");
        String songName = intent.getStringExtra("songName");
        position = bundle.getInt("pos", 0);

        tsongName.setSelected(true);
        Uri uri = Uri.parse(mySongs.get(position).toString());
        sname = mySongs.get(position).toString();
        tsongName.setText(sname);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
        mediaPlayer.start();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                    mediaPlayer.seekTo(seekBar.getProgress());
            }
        });

        String endTime = createTime(mediaPlayer.getDuration());
        songStop.setText(endTime);

        final Handler handler = new Handler();
        final int delay = 1000;

        updateSeekbarSts();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String currentPosition = createTime(mediaPlayer.getCurrentPosition());
                songStart.setText(currentPosition);
                handler.postDelayed(this, delay);
            }
        }, delay);


        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    btnPlay.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
                    mediaPlayer.pause();
                }else{
                    btnPlay.setBackgroundResource(R.drawable.ic_baseline_pause_24);
                    mediaPlayer.start();
                    generateRandomColorForBarVisualiser();
                }
            }
        });

        audioSession();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();;
                mediaPlayer.release();
                position = ((position + 1) % mySongs.size());
                Uri u = Uri.parse(mySongs.get(position).toString());
                sname = mySongs.get(position).toString();
                tsongName.setText(sname);
                generateRandomColorForBarVisualiser();

                mediaPlayer = MediaPlayer.create(getApplicationContext(), u);
                mediaPlayer.start();
                btnPlay.setBackgroundResource(R.drawable.ic_baseline_pause_24);
                startAnimation(imageView);
                audioSession();
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();;
                mediaPlayer.release();
                position = ((position - 1) < 0)?((mySongs.size()) - 1): (position - 1);
                Uri u = Uri.parse(mySongs.get(position).toString());
                sname = mySongs.get(position).toString();
                tsongName.setText(sname);
                generateRandomColorForBarVisualiser();

                mediaPlayer = MediaPlayer.create(getApplicationContext(), u);
                mediaPlayer.start();
                btnPlay.setBackgroundResource(R.drawable.ic_baseline_pause_24);
                startAnimation(imageView);
                audioSession();
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                btnNext.performClick();
                generateRandomColorForBarVisualiser();
            }
        });

        btnFastForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = mediaPlayer.getCurrentPosition();
                mediaPlayer.seekTo(position + 10000);
                updateSeekbarSts();
            }
        });

        btnFastPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = mediaPlayer.getCurrentPosition();
                mediaPlayer.seekTo(position - 10000);
                updateSeekbarSts();
            }
        });



    }

    public void startAnimation(View view){
        ObjectAnimator animation = ObjectAnimator.ofFloat(imageView, "rotation", 0f, 360f);
        animation.setDuration(1000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animation);
        animatorSet.start();
    }

    public String createTime(int duration){
        String time = "";
        int min = duration / 1000 / 60;
        int sec = duration / 1000 % 60;

        time += min + ":";

        if(sec < 10){
            time += "0";
        }
        time += sec;

        return time;
    }

    public void audioSession(){
        int audioSessionId = mediaPlayer.getAudioSessionId();

        if(audioSessionId != -1){
            barVisualizer.setAudioSessionId(audioSessionId);
        }
    }

    public void generateRandomColorForBarVisualiser(){
        Random random = new Random();
        int color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
        barVisualizer.setColor(color);
    }


    public void updateSeekbarSts(){
        updateSeekbar = new Thread(){
            @Override
            public void run() {
                int songDuration = mediaPlayer.getDuration();
                int currentPosition = 0;

                while(currentPosition < songDuration){

                    try{
                        sleep(500);
                        currentPosition = mediaPlayer.getCurrentPosition();
                        seekBar.setProgress(currentPosition);

                    }catch (IllegalStateException | InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        };
        seekBar.setMax(mediaPlayer.getDuration());
        updateSeekbar.start();
        seekBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.design_default_color_primary), PorterDuff.Mode.MULTIPLY);
        seekBar.getThumb().setColorFilter(getResources().getColor(R.color.design_default_color_on_primary), PorterDuff.Mode.SRC_IN);

    }
}