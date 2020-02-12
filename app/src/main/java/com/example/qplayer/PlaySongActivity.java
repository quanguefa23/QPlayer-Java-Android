package com.example.qplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PlaySongActivity extends AppCompatActivity {

    Intent intent;
    ArrayList<Song> listSong;
    int position_song;
    Song song;
    String playlist_name;

    MediaPlayer mp;
    SeekBar progress;
    int duration;
    TextView time_startTV;

    ImageView bannerIV;
    ImageView playIV;
    ImageView shuffleIV;
    ImageView loopIV;
    ImageView nextIV;
    ImageView previousIV;
    boolean isPlay = true;
    boolean isShuffle = false;
    float rotation = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);

        //map some text-views
        TextView playlistTV = findViewById(R.id.playlist);
        TextView nameTV = findViewById(R.id.name);
        TextView artistTV = findViewById(R.id.artist);
        TextView time_endTV = findViewById(R.id.time_end);
        bannerIV = findViewById(R.id.banner);

        //get data from intent
        intent = getIntent();
        playlist_name = intent.getStringExtra("playlist_name");
        ArrayList<Song> listSong = intent.getParcelableArrayListExtra("listsong");
        //Log.d("Quang", listSong.size() + "");
        position_song = intent.getIntExtra("position", 0);

        //set const content
        song = listSong.get(position_song);
        playlistTV.setText(playlist_name);
        nameTV.setText(song.getName());
        artistTV.setText(song.getArtist());
        bannerIV.setImageResource(song.getBannerImage());
        Log.d("State", song.getName() + "3 onCreate");

        //create media player
        mp = MediaPlayer.create(this, song.getSrcMusic());
        mp.start();

        //set timing text-views
        duration = mp.getDuration();
        int duration_sec = duration / 1000;
        int min = duration_sec / 60;
        int sec = duration_sec % 60;
        String stringSec = Integer.toString(sec);
        if (sec < 10)
            stringSec = "0" + stringSec;
        time_endTV.setText(min + ":" + stringSec);

        //setOnClick for some ImageView
        setOnClickPlayIV();
        setOnClickShuffleIV();
        setOnClickLoopIV();
        setOnClickNextIV();
        setOnClickPreviousIV();

        //implement seek-bar and update time_startTV
        time_startTV = findViewById(R.id.time_start);
        progress = findViewById(R.id.progress);
        progress.setMax(duration);
        progress.setProgress(mp.getCurrentPosition());
        countDownTimer();

        //seek function
        progress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b)
                    mp.seekTo(i);

                int instant_time = mp.getCurrentPosition();

                int time_sec = instant_time / 1000;
                int min = time_sec / 60;
                int sec = time_sec % 60;
                String stringSec = Integer.toString(sec);
                if (sec < 10)
                    stringSec = "0" + stringSec;
                time_startTV.setText(min + ":" + stringSec);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setOnClickPreviousIV() {
        previousIV = findViewById(R.id.previous);
        previousIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mp.getCurrentPosition() > 2000) {
                    mp.seekTo(0);
                    updateState();
                }
                else {
                    if (position_song == 0) {
                        mp.seekTo(0);
                        updateState();
                    }
                    else {
                        listSong = intent.getParcelableArrayListExtra("listsong");
                        seekToPreviousSong();
                    }
                }

            }
        });
    }

    private void updateState() {
        int instant_time = mp.getCurrentPosition();

        //update timing text-view
        int time_sec = instant_time / 1000;
        int min = time_sec / 60;
        int sec = time_sec % 60;
        String stringSec = Integer.toString(sec);
        if (sec < 10)
            stringSec = "0" + stringSec;
        time_startTV.setText(min + ":" + stringSec);

        //update seek-bar
        progress.setProgress(instant_time);
    }

    private void seekToPreviousSong() {
        mp.stop();
        Intent intent = new Intent(PlaySongActivity.this, PlaySongActivity.class);
        intent.putExtra("playlist_name", playlist_name);
        intent.putExtra("position", position_song - 1);
        intent.putParcelableArrayListExtra("listsong", listSong);
        startActivity(intent);
        //PlaySongActivity.this.onDestroy();
    }

    private void setOnClickNextIV() {
        nextIV = findViewById(R.id.next);
        nextIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listSong = intent.getParcelableArrayListExtra("listsong");
                if (position_song == listSong.size() - 1) {
                    mp.seekTo(duration - 100);
                    updateState();
                }
                else {
                    seekToNextSong();
                }
            }
        });
    }

    private void countDownTimer() {
        CountDownTimer timer_SB = new CountDownTimer(duration, 50) {
            @Override
            public void onTick(long l) {
                if (!isPlay) {
                    this.cancel();
                }

                //animation
                rotation += 0.2;
                if (rotation > 360)
                    rotation -= 360;
                bannerIV.setRotation(rotation);

                int instant_time = mp.getCurrentPosition();

                //update timing text-view
                int time_sec = instant_time / 1000;
                int min = time_sec / 60;
                int sec = time_sec % 60;
                String stringSec = Integer.toString(sec);
                if (sec < 10)
                    stringSec = "0" + stringSec;
                time_startTV.setText(min + ":" + stringSec);

                //check if end of media
                if (instant_time > duration - 50 && !mp.isLooping()) {
                    listSong = intent.getParcelableArrayListExtra("listsong");
                    if (position_song == listSong.size() - 1) {
                        //Log.d("Seek", "Do not seek to next song");
                        isPlay = false;
                        playIV.setImageResource(R.drawable.play);
                        mp.pause();
                        this.cancel();
                    }
                    else {
                        //Log.d("Seek", "Seek to next song");
                        this.cancel();
                        seekToNextSong();
                    }
                }

                //update seek-bar
                progress.setProgress(instant_time);
            }

            @Override
            public void onFinish() {
                this.start();
            }
        };
        timer_SB.start();
    }

    private void seekToNextSong() {
        mp.stop();
        Intent intent = new Intent(PlaySongActivity.this, PlaySongActivity.class);
        intent.putExtra("playlist_name", playlist_name);
        intent.putExtra("position", position_song + 1);
        intent.putParcelableArrayListExtra("listsong", listSong);
        startActivity(intent);
        //PlaySongActivity.this.onDestroy();
    }

    private void setOnClickLoopIV() {
        loopIV = findViewById(R.id.loop);
        loopIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mp.isLooping()) {
                    mp.setLooping(false);
                    loopIV.setImageResource(R.drawable.loop);
                    Toast.makeText(PlaySongActivity.this, "Repeat is off", Toast.LENGTH_SHORT).show();
                }
                else {
                    mp.setLooping(true);
                    loopIV.setImageResource(R.drawable.loop_selected);
                    Toast.makeText(PlaySongActivity.this, "Repeating current song", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setOnClickShuffleIV() {
        shuffleIV = findViewById(R.id.shuffle);
        shuffleIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isShuffle) {
                    isShuffle = false;
                    shuffleIV.setImageResource(R.drawable.shuffle);
                    Toast.makeText(PlaySongActivity.this, "Shuffle is off", Toast.LENGTH_SHORT).show();
                }
                else {
                    isShuffle = true;
                    shuffleIV.setImageResource(R.drawable.shuffle_selected);
                    Toast.makeText(PlaySongActivity.this, "Shuffle is on", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setOnClickPlayIV() {
        playIV = findViewById(R.id.play);
        playIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPlay) {
                    isPlay = false;
                    playIV.setImageResource(R.drawable.play);
                    mp.pause();
                }
                else {
                    isPlay = true;
                    playIV.setImageResource(R.drawable.pause);
                    mp.start();
                    countDownTimer();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mp.stop();
        Log.d("State", song.getName() + "3 onDestroy");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("State", song.getName() + "3 onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("State", song.getName() + "3 onPause");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("State", song.getName() + "3 onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("State", song.getName() + "3 onResume");
    }


}
