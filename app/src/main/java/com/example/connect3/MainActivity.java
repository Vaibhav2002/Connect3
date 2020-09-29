package com.example.connect3;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.AudioManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    GridLayout grid;
    boolean gameActive=true;
    int user=0;
    TextView win;
    Button res;
    int c=0;
    final int[][] winState ={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    int[] gameState ={2,2,2,2,2,2,2,2,2};
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        grid=findViewById(R.id.grid);
        win=findViewById(R.id.winnerName);
        res=findViewById(R.id.reset);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.clicks6);
    }
    public void btnPress(View view) throws IOException {
        int tag=Integer.parseInt(view.getTag().toString());
        if(gameState[tag]==2&&gameActive) {
            c++;
            ImageView im = (ImageView) view;
            gameState[tag] = user;
            if (user == 0)
                im.setImageResource(R.drawable.red);
            else
                im.setImageResource(R.drawable.yellow);
            im.setTranslationY(-1000);
            im.animate().translationYBy(1000).rotation(1000).setDuration(500);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mediaPlayer.start();
                }
            },300);

            if(checkwin()) {
                gameActive = false;
                String winName=(user==0)?"Red":"Yellow";
                win.setText(String.format("%s wins !", winName));
                win.setVisibility(View.VISIBLE);
                res.setVisibility(View.VISIBLE);
            }
            else if(c==9)
            {
                gameActive = false;
                win.setText("Game Draw");
                win.setVisibility(View.VISIBLE);
                res.setVisibility(View.VISIBLE);
            }
            user = 1 - user;

        }
    }

    private boolean checkwin() {
        for(int[] a:winState)
        {
            if(gameState[a[0]]==gameState[a[1]]&&gameState[a[1]]==gameState[a[2]]&&gameState[a[0]]!=2)
                return true;
        }
        return false;
    }

    public void resetGame(View view) {
        res.setVisibility(View.INVISIBLE);
        win.setVisibility(View.INVISIBLE);
        for(int i=0;i<grid.getChildCount();i++)
        {
            ImageView im= (ImageView) grid.getChildAt(i);
            im.setImageBitmap(null);
        }
        Arrays.fill(gameState, 2);
        gameActive=true;
        c=0;
        user=0;

    }
}