package com.example.batcomputer.mediaplayer;

import android.app.Activity;
import android.icu.util.TimeUnit;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
//import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity {

    public TextView songName, startTimeField, endTimeField;
    private MediaPlayer mediaPlayer;
    private double startTime = 0;
    private double finalTime = 0;
    private Handler myHandler = new Handler();
    private int forwardTime = 5000;
    private int backwardTime = 5000;
    private SeekBar seekBar;
    private ImageButton playButton, pauseButton;
    public static int oneTimeOnly = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        songName = (TextView)findViewById(R.id.textView4);
        startTimeField = (TextView)findViewById(R.id.textView1);
        endTimeField = (TextView)findViewById(R.id.textView2);
        seekBar = (seekBar)findViewById(R.id.seekBar1);
        playButton = (ImageButton)findViewById(R.id.imageButton1);
        pauseButton = (ImageButton)findViewById(R.id.imageButton2);
        songName.setText("paradise.mp3");
        mediaPlayer = MediaPlayer.create(this, R.raw.paradise);
        seekBar.setClickable(false);
        pauseButton.setEnabled(false);
    }

    public void play(View view){
        Toast.makeText(getApplicationContext(), "playing song", Toast.LENGTH_SHORT).show();
        mediaPlayer.start();
        finalTime = mediaPlayer.getDuration();
        startTime = mediaPlayer.getCurrentPosition();
        if (oneTimeOnly == 0){
            seekBar.setMax((int) finalTime);
            oneTimeOnly = 1;
        }

        /*endTimeField.setText(String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                TimeUnit.MILLISECONDS.toSeconds((long) finalTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime))));
*/
        seekBar.setProgress((int)startTime);
        myHandler.postDelayed(UpdateSongTime,100);
        pauseButton.setEnabled(true);
        playButton.setEnabled(false);
    }

    private Runnable UpdateSongTime = new Runnable() {
        @Override
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
  /*          startTimeField.setText(String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime))));
*/
            seekBar.setProgress((int) startTime);
            myHandler.postDelayed(this, 100);
        }
    };

    public void pause(View view){
        Toast.makeText(getApplicationContext(), "Pausing Paradise", Toast.LENGTH_SHORT).show();
        mediaPlayer.pause();
        pauseButton.setEnabled(false);
        playButton.setEnabled(true);
    }

    public void forward(View view){
        int temp =(int)startTime;
        if ((temp + forwardTime)<=finalTime){
            startTime = startTime + forwardTime;
            mediaPlayer.seekTo((int) startTime);
        }
        else {
            Toast.makeText(getApplicationContext(), "cannot jump forward 5 seconds", Toast.LENGTH_SHORT).show();
        }
    }

    public void rewind(View view){
        int temp = (int)startTime;

        if (temp-backwardTime>0){
            startTime = startTime -backwardTime;
            mediaPlayer.seekTo((int) startTime);
        } else {
            Toast.makeText(getApplicationContext(), "cannot rewind by 5 seconds", Toast.LENGTH_SHORT).show();
        }
    }
}
