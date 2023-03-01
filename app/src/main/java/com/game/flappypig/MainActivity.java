package com.game.flappypig;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Locale;
import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    AnimatorSet flip1, flip2, flip3, flip4, flip5, flip6, flip7, flip8;
    TextView scorer, coinScore, highScorer, scorer2, statsSign, shopSign;
    RelativeLayout rl;
    Button start, pause, shop, stats, settings, help;
    ImageView pig, ob1, ob2, ob3, ob4, ob5, ob6, ob7, ob8,
            gameOver,
            coin1, coin2, coin3, coin4, coin5, coin6, coin7, coin8, displayCoin,
            layout, layout2, control;
    MediaPlayer coinSound, coinSound2, backtrack, powerUpSound, lobby, open;
    private int run = -1,
            scoreOb1, scoreOb3, scoreOb5, scoreOb7,
            coins, newCoins, score, highScore, totalScore, playedGames,
            color;
    private float screenWidth, screenHeight,
            speed, obStopX, saveSpeed,
            average,
            magnetTime, speedTime, invincibleTime,
            musicVolume, audioVolume;
    private String selectedSkin, controlSide, lang;
    private boolean first = true,
            magnet, rush, invincible = false, powerUpOnScreen,
            ownSkin2, ownSkin3, ownSkin4, ownSkin5, ownSkin6, ownSkin7, ownSkin8,
            showedSpecialDialog = false;
    private final String TAG = "foot";
    private FirebaseAnalytics firebaseAnalytics;

    @Override
    protected void onPause() {
        super.onPause();
        if (run == 1) {
            pause();
        } else {
            lobby.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        if (run != 1) {
            lobby.start();
        }
        if (run != -1) setLang(lang);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        if (run != -1) setLang(lang);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        super.onCreate(savedInstanceState);
        // Get Database Values
        SharedPreferences sharedPreferences = getSharedPreferences("values", 0);
        coins = sharedPreferences.getInt("coins", 0);
        highScore = sharedPreferences.getInt("hs", 0);
        average = sharedPreferences.getFloat("avg", 0);
        totalScore = sharedPreferences.getInt("ts", 0);
        playedGames = sharedPreferences.getInt("gp", 0);
        ownSkin2 = sharedPreferences.getBoolean("skin2", false);
        ownSkin3 = sharedPreferences.getBoolean("skin3", false);
        ownSkin4 = sharedPreferences.getBoolean("skin4", false);
        ownSkin5 = sharedPreferences.getBoolean("skin5", false);
        ownSkin6 = sharedPreferences.getBoolean("skin6", false);
        ownSkin7 = sharedPreferences.getBoolean("skin7", false);
        ownSkin8 = sharedPreferences.getBoolean("skin8", false);
        audioVolume = sharedPreferences.getFloat("audioVolume", 0.5f);
        musicVolume = sharedPreferences.getFloat("musicVolume", 0.5f);
        color = sharedPreferences.getInt("color", Color.parseColor("#ff33b5e5"));
        selectedSkin = sharedPreferences.getString("selectedSkin", "pig");
        showedSpecialDialog = sharedPreferences.getBoolean("specialDialog5", false);
        controlSide = sharedPreferences.getString("controlSide", "right");
        lang = sharedPreferences.getString("lang", getResources().getConfiguration().locale.toString());
        Log.d(TAG, "onCreate: " + lang);
        if (lang.contains("es")) lang = "es";
        else if (lang.contains("ru")) lang = "ru";
        else if (lang.contains("pt")) lang = "pt";
        else if (lang.contains("fr")) lang = "fr";
        setLang(lang);
        setContentView(R.layout.activity_main);
        setLang(lang);
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Log.d(TAG, "onCreate: " + getString(R.string.high_score));
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        // findViewById();
        ob1 = findViewById(R.id.ob1);
        ob2 = findViewById(R.id.ob2);
        ob3 = findViewById(R.id.ob3);
        ob4 = findViewById(R.id.ob4);
        ob5 = findViewById(R.id.ob5);
        ob6 = findViewById(R.id.ob6);
        ob7 = findViewById(R.id.ob7);
        ob8 = findViewById(R.id.ob8);
        pig = findViewById(R.id.pig);
        control = findViewById(R.id.control);
        layout = findViewById(R.id.layout1);
        layout2 = findViewById(R.id.layout2);
        start = findViewById(R.id.start);
        shop = findViewById(R.id.shop);
        stats = findViewById(R.id.stats);
        settings = findViewById(R.id.settings);
        help = findViewById(R.id.help);
        pause = findViewById(R.id.pause);
        gameOver = findViewById(R.id.gameover);
        scorer = findViewById(R.id.score);
        scorer2 = findViewById(R.id.score2);
        highScorer = findViewById(R.id.highscore);
        statsSign = findViewById(R.id.stats_sign);
        shopSign = findViewById(R.id.shop_sign);
        coin1 = findViewById(R.id.coin1);
        coin2 = findViewById(R.id.coin2);
        coin3 = findViewById(R.id.coin3);
        coin4 = findViewById(R.id.coin4);
        coin5 = findViewById(R.id.coin5);
        coin6 = findViewById(R.id.coin6);
        coin7 = findViewById(R.id.coin7);
        coin8 = findViewById(R.id.coin8);
        coinScore = findViewById(R.id.coinScore);
        displayCoin = findViewById(R.id.displayCoin);
        rl = findViewById(R.id.rl);

        // Variables
        speed = screenWidth / 400;
        obStopX = -(screenWidth / 3f);
        newCoins = 0;


        switch (selectedSkin) {
            case "ninja":
                pig.setBackgroundResource(R.drawable.ninja);
                pig.setTag(R.drawable.ninja);
                break;
            case "detective":
                pig.setBackgroundResource(R.drawable.detective);
                pig.setTag(R.drawable.detective);
                break;
            case "superpig":
                pig.setBackgroundResource(R.drawable.superpig);
                pig.setTag(R.drawable.superpig);
                break;
            case "batpig":
                pig.setBackgroundResource(R.drawable.batpig);
                pig.setTag(R.drawable.batpig);
                break;
            case "movie":
                pig.setBackgroundResource(R.drawable.moviepig);
                pig.setTag(R.drawable.moviepig);
                break;
            case "witch":
                pig.setBackgroundResource(R.drawable.witchpig);
                pig.setTag(R.drawable.witchpig);
                break;
            case "king":
                pig.setBackgroundResource(R.drawable.kingpig);
                pig.setTag(R.drawable.kingpig);
                break;
            default:
                pig.setBackgroundResource(R.drawable.pig);
                pig.setTag(R.drawable.pig);
                break;
        }

        rl.setBackgroundColor(color);

        ob1.setVisibility(View.INVISIBLE);
        ob2.setVisibility(View.INVISIBLE);
        ob3.setVisibility(View.INVISIBLE);
        ob4.setVisibility(View.INVISIBLE);
        ob5.setVisibility(View.INVISIBLE);
        ob6.setVisibility(View.INVISIBLE);
        ob7.setVisibility(View.INVISIBLE);
        ob8.setVisibility(View.INVISIBLE);
        pig.setVisibility(View.INVISIBLE);
        control.setVisibility(View.INVISIBLE);
        pause.setVisibility(View.INVISIBLE);

        pig.setX(screenWidth / 10);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) control.getLayoutParams();
        if (controlSide.equals("right")) {
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            params.removeRule(RelativeLayout.ALIGN_PARENT_LEFT);
        } else {
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            params.removeRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        }
        control.setLayoutParams(params);
        gameOver.setBackgroundResource(R.drawable.flappypig2);

        scorer.setVisibility(View.INVISIBLE);
        scorer2.setVisibility(View.INVISIBLE);
        highScorer.setText(String.format(getString(R.string.high_score) + " %s", highScore));

        coin1.setVisibility(View.INVISIBLE);
        coin2.setVisibility(View.INVISIBLE);
        coin3.setVisibility(View.INVISIBLE);
        coin4.setVisibility(View.INVISIBLE);
        coin5.setVisibility(View.INVISIBLE);
        coin6.setVisibility(View.INVISIBLE);
        coin7.setVisibility(View.INVISIBLE);
        coin8.setVisibility(View.INVISIBLE);

        coinScore.setText(String.format("%s", coins));
        coin1.setTag(R.drawable.coin);
        coin2.setTag(R.drawable.coin);
        coin3.setTag(R.drawable.coin);
        coin4.setTag(R.drawable.coin);
        coin5.setTag(R.drawable.coin);
        coin6.setTag(R.drawable.coin);
        coin7.setTag(R.drawable.coin);
        coin8.setTag(R.drawable.coin);

        flip1 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.flip1);
        flip2 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.flip2);
        flip3 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.flip3);
        flip4 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.flip4);
        flip5 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.flip5);
        flip6 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.flip6);
        flip7 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.flip7);
        flip8 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.flip8);

        coinSound = MediaPlayer.create(this, R.raw.coinsound);
        coinSound.setVolume(audioVolume, audioVolume);
        coinSound2 = MediaPlayer.create(this, R.raw.coinsound);
        coinSound2.setVolume(audioVolume, audioVolume);
        powerUpSound = MediaPlayer.create(this, R.raw.powerup_sound);
        powerUpSound.setVolume(audioVolume, audioVolume);
        open = MediaPlayer.create(this, R.raw.open);
        open.setVolume(audioVolume, audioVolume);
        backtrack = MediaPlayer.create(this, R.raw.backtrack);
        backtrack.setVolume(musicVolume, musicVolume);
        backtrack.isLooping();
        lobby = MediaPlayer.create(this, R.raw.lobby);
        lobby.setVolume(musicVolume, musicVolume);
        lobby.isLooping();
        lobby.start();

        rl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event1) {
                if (run == 1) {
                    if (event1.getY() > control.getY() - screenHeight / 20 && event1.getY() < control.getY() + control.getHeight() + screenHeight / 20) {
                        if (event1.getAction() == MotionEvent.ACTION_MOVE) {
                            pig.setY((event1.getY()) - pig.getHeight() / 2f);
                            control.setY(pig.getY());
                        }
                    }
                }
                return true;
            }
        });

        start.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (first) {
                            start.setBackgroundResource(R.drawable.startbutton2);
                        } else {
                            start.setBackgroundResource(R.drawable.retry2);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (first) {
                            start.setBackgroundResource(R.drawable.startbutton1);
                        } else {
                            start.setBackgroundResource(R.drawable.retry1);
                        }
                        start();
                }
                return true;
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pause();
            }
        });
        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLang(lang);
                showStats();
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLang(lang);
                showHelp();
            }
        });
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLang(lang);
                showShop();
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLang(lang);
                showSettings();
            }
        });
        if (!showedSpecialDialog) {
            showSpecialDialog();
        }
        if (playedGames == 0) showHelp();
    }


    private void start() {
        Log.d(TAG, "start: ");
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        run = 1;
        speed = screenWidth / 350f;
        invincible = false;
        rush = false;
        magnet = false;
        speedTime = 0;
        magnetTime = 0;

        gameOver.setVisibility(View.INVISIBLE);

        // Obstacles
        // 3 and 4
        ob3.setY((obPosition()));
        ob4.setY(ob3.getY() - ob2.getHeight() - pig.getHeight() - screenHeight / 8);
        ob3.setX(0);
        ob4.setX(ob3.getX());

        // 5 and 6
        ob5.setY((obPosition()));
        ob6.setY(ob3.getY() - ob2.getHeight() - pig.getHeight() - screenHeight / 8);
        ob5.setX((screenWidth / 3));
        ob6.setX(ob5.getX());

        // 7 and 8
        ob7.setY((obPosition()));
        ob8.setY(ob7.getY() - ob2.getHeight() - pig.getHeight() - (screenHeight / 8));
        ob7.setX((screenWidth * 2 / 3));
        ob8.setX(ob7.getX());


        // Pig & control
        pig.setY(screenHeight / 2);
        control.setY(pig.getY());
        pig.setVisibility(View.VISIBLE);
        control.setVisibility(View.VISIBLE);

        // Score
        scorer.setVisibility(View.VISIBLE);
        score = 0;
        scorer.setText(String.format("%s", score));
        highScorer.setVisibility(View.INVISIBLE);
        scorer2.setVisibility(View.INVISIBLE);

        // Coins
        newCoins = 0;
        coinScore.setText(String.format("%s", newCoins));

        // Sounds
        lobby.stop();
        backtrack = MediaPlayer.create(this, R.raw.backtrack);
        backtrack.setVolume(musicVolume, musicVolume);
        backtrack.isLooping();
        backtrack.start();


        pig.setY(screenHeight / 2);
        pause.setVisibility(View.VISIBLE);
        control.setY(pig.getY());

        // Method calls
        ob12();
        updateOb();
        flipCard();
        start.setVisibility(View.INVISIBLE);
        shop.setVisibility(View.INVISIBLE);
        stats.setVisibility(View.INVISIBLE);
        settings.setVisibility(View.INVISIBLE);
        help.setVisibility(View.INVISIBLE);
        statsSign.setVisibility(View.INVISIBLE);
        shopSign.setVisibility(View.INVISIBLE);
    }

    private void gameOver() {
        run = 2;
        backtrack.pause();
        setLang(lang);
        first = false;
        coins += newCoins;
        playedGames++;
        totalScore += score;
        average = (float) Math.round(((float) totalScore / playedGames) * 100) / 100;
        newCoins = 0;
        gameOver.setVisibility(View.VISIBLE);
        gameOver.setBackgroundResource(R.drawable.gameover);
        start.setBackgroundResource(R.drawable.retry1);
        start.setVisibility(View.VISIBLE);
        shop.setVisibility(View.VISIBLE);
        stats.setVisibility(View.VISIBLE);
        settings.setVisibility(View.VISIBLE);
        help.setVisibility(View.VISIBLE);
        statsSign.setVisibility(View.VISIBLE);
        shopSign.setVisibility(View.VISIBLE);
        ob1.setVisibility(View.INVISIBLE);
        ob2.setVisibility(View.INVISIBLE);
        ob3.setVisibility(View.INVISIBLE);
        ob4.setVisibility(View.INVISIBLE);
        ob5.setVisibility(View.INVISIBLE);
        ob6.setVisibility(View.INVISIBLE);
        ob7.setVisibility(View.INVISIBLE);
        ob8.setVisibility(View.INVISIBLE);
        pig.setVisibility(View.INVISIBLE);
        control.setVisibility(View.INVISIBLE);
        coin1.setVisibility(View.INVISIBLE);
        coin2.setVisibility(View.INVISIBLE);
        coin3.setVisibility(View.INVISIBLE);
        coin4.setVisibility(View.INVISIBLE);
        coin5.setVisibility(View.INVISIBLE);
        coin6.setVisibility(View.INVISIBLE);
        coin7.setVisibility(View.INVISIBLE);
        coin8.setVisibility(View.INVISIBLE);
        pause.setVisibility(View.INVISIBLE);
        scorer.setVisibility(View.INVISIBLE);
        scorer2.setVisibility(View.VISIBLE);
        scorer2.setText(String.format("%s", score));
        coinScore.setText(String.format("%s", coins));

        SharedPreferences sharedPreferences = getSharedPreferences("values", 0);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        highScorer.setVisibility(View.VISIBLE);
        highScorer.setText(String.format(getString(R.string.high_score) + " %s", highScore));
        if (score > highScore) {
            highScore = score;
            edit.putInt("hs", highScore);
            highScorer.setText(getString(R.string.new_high_score));
        }
        edit.putInt("coins", coins);
        edit.putInt("gp", playedGames);
        edit.putInt("ts", totalScore);
        edit.putFloat("avg", average);
        edit.apply();
        backtrack.stop();
        lobby = MediaPlayer.create(MainActivity.this, R.raw.lobby);
        lobby.setVolume(musicVolume, musicVolume);
        lobby.isLooping();
        lobby.start();

        Bundle bundle = new Bundle();
        bundle.putInt("score", score);
        firebaseAnalytics.logEvent("game_over", bundle);
    }

    private void flipCard() {
        if (run == 1) {
            flip1.setTarget(coin1);
            flip1.start();
            flip2.setTarget(coin2);
            flip2.start();
            flip3.setTarget(coin3);
            flip3.start();
            flip4.setTarget(coin4);
            flip4.start();
            flip5.setTarget(coin5);
            flip5.start();
            flip6.setTarget(coin6);
            flip6.start();
            flip7.setTarget(coin7);
            flip7.start();
            flip8.setTarget(coin8);
            flip8.start();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    flipCard();
                }
            }, 20);
        }
    }

    private void ob12() {
        scoreOb1 = 1;
        speed += 0.25;
        ob1.setX(screenWidth);
        ob2.setX(screenWidth);
        ob1.setVisibility(View.VISIBLE);
        ob2.setVisibility(View.VISIBLE);
        ob1.setY(obPosition());
        ob2.setY(ob1.getY() - ob1.getHeight() - pig.getHeight() - (screenHeight / 8));
        if (isCoin()) {
            coin1.setVisibility(View.VISIBLE);
            coin1.setX(ob1.getX() + (float) ob1.getWidth() / 2 - (float) coin1.getWidth() / 2);
            coin1.setY(ob1.getY() - (pig.getHeight() + screenHeight / 8) / 2 - coin1.getLayoutParams().height / 2f);
            coin1.setBackgroundResource(R.drawable.coin);
            coin1.setTag(R.drawable.coin);
            if (isPowerUp() && !rush && !magnet && !powerUpOnScreen) {
                if (new Random().nextInt(2) == 1) {
                    coin1.setBackgroundResource(R.drawable.magnet);
                    coin1.setTag(R.drawable.magnet);
                } else {
                    coin1.setBackgroundResource(R.drawable.speed);
                    coin1.setTag(R.drawable.speed);
                }
                powerUpOnScreen = true;
            }
        }
        if (isCoin()) {
            coin5.setVisibility(View.VISIBLE);
            coin5.setX(ob1.getX() + screenWidth / 6 + coin5.getLayoutParams().width / 2f);
            coin5.setY(coinPosition());
            coin5.setBackgroundResource(R.drawable.coin);
            coin5.setTag(R.drawable.coin);
            if (isPowerUp() && !rush && !magnet && !powerUpOnScreen) {
                if (new Random().nextInt(2) == 1) {
                    coin5.setBackgroundResource(R.drawable.magnet);
                    coin5.setTag(R.drawable.magnet);
                } else {
                    coin5.setBackgroundResource(R.drawable.speed);
                    coin5.setTag(R.drawable.speed);
                }
                powerUpOnScreen = true;
            }
        }

    }

    private void ob34() {
        scoreOb3 = 1;
        speed += 0.25;
        ob3.setVisibility(View.VISIBLE);
        ob4.setVisibility(View.VISIBLE);
        ob3.setY(obPosition());
        ob4.setY(ob3.getY() - ob3.getHeight() - pig.getHeight() - (screenHeight / 8));
        ob3.setX(screenWidth);
        ob4.setX(screenWidth);
        if (isCoin()) {
            coin2.setVisibility(View.VISIBLE);
            coin2.setY(ob3.getY() - (pig.getHeight() + screenHeight / 8) / 2 - coin2.getLayoutParams().height / 2f);
            coin2.setX(ob3.getX() + (float) ob3.getWidth() / 2 - (float) coin2.getWidth() / 2);
            coin2.setBackgroundResource(R.drawable.coin);
            coin2.setTag(R.drawable.coin);
            if (isPowerUp() && !rush && !magnet && !powerUpOnScreen) {
                if (new Random().nextInt(2) == 1) {
                    coin2.setBackgroundResource(R.drawable.magnet);
                    coin2.setTag(R.drawable.magnet);
                } else {
                    coin2.setBackgroundResource(R.drawable.speed);
                    coin2.setTag(R.drawable.speed);
                }
                powerUpOnScreen = true;
            }
        }
        if (isCoin()) {
            coin6.setVisibility(View.VISIBLE);
            coin6.setX(ob3.getX() + screenWidth / 6 + coin6.getLayoutParams().width / 2f);
            coin6.setY(coinPosition());
            coin6.setBackgroundResource(R.drawable.coin);
            coin6.setTag(R.drawable.coin);
            if (isPowerUp() && !rush && !magnet && !powerUpOnScreen) {
                if (new Random().nextInt(2) == 1) {
                    coin6.setBackgroundResource(R.drawable.magnet);
                    coin6.setTag(R.drawable.magnet);
                } else {
                    coin6.setBackgroundResource(R.drawable.speed);
                    coin6.setTag(R.drawable.speed);
                }
                powerUpOnScreen = true;
            }
        }
    }

    private void ob56() {
        scoreOb5 = 1;
        speed += 0.25;
        ob5.setVisibility(View.VISIBLE);
        ob6.setVisibility(View.VISIBLE);
        ob5.setY(obPosition());
        ob6.setY(ob5.getY() - ob6.getHeight() - pig.getHeight() - (screenHeight / 8));
        ob5.setX(screenWidth);
        ob6.setX(screenWidth);
        if (isCoin()) {
            coin3.setVisibility(View.VISIBLE);
            coin3.setY(ob5.getY() - (pig.getHeight() + screenHeight / 8) / 2 - coin3.getLayoutParams().height / 2f);
            coin3.setX(ob5.getX() + (float) ob5.getWidth() / 2 - (float) coin3.getWidth() / 2);
            coin3.setBackgroundResource(R.drawable.coin);
            coin3.setTag(R.drawable.coin);
            if (isPowerUp() && !rush && !magnet && !powerUpOnScreen) {
                if (new Random().nextInt(2) == 1) {
                    coin3.setBackgroundResource(R.drawable.magnet);
                    coin3.setTag(R.drawable.magnet);
                } else {
                    coin3.setBackgroundResource(R.drawable.speed);
                    coin3.setTag(R.drawable.speed);
                }
                powerUpOnScreen = true;
            }
        }
        if (isCoin()) {
            coin7.setVisibility(View.VISIBLE);
            coin7.setX(ob5.getX() + screenWidth / 6 + coin7.getLayoutParams().width / 2f);
            coin7.setY(coinPosition());
            coin7.setBackgroundResource(R.drawable.coin);
            coin7.setTag(R.drawable.coin);
            if (isPowerUp() && !rush && !magnet && !powerUpOnScreen) {
                if (new Random().nextInt(2) == 1) {
                    coin7.setBackgroundResource(R.drawable.magnet);
                    coin7.setTag(R.drawable.magnet);
                } else {
                    coin7.setBackgroundResource(R.drawable.speed);
                    coin7.setTag(R.drawable.speed);
                }
                powerUpOnScreen = true;
            }
        }
    }

    private void ob78() {
        scoreOb7 = 1;
        speed += 0.25;
        ob7.setVisibility(View.VISIBLE);
        ob8.setVisibility(View.VISIBLE);
        ob7.setY(obPosition());
        ob8.setY(ob7.getY() - ob8.getHeight() - pig.getHeight() - (screenHeight / 8));
        ob7.setX(screenWidth);
        ob8.setX(screenWidth);
        if (isCoin()) {
            coin4.setVisibility(View.VISIBLE);
            coin4.setY(ob7.getY() - (pig.getHeight() + screenHeight / 8) / 2 - coin2.getLayoutParams().height / 2f);
            coin4.setX(ob7.getX() + (float) ob7.getWidth() / 2 - (float) coin4.getWidth() / 2);
            coin4.setBackgroundResource(R.drawable.coin);
            coin4.setTag(R.drawable.coin);
            if (isPowerUp() && !rush && !magnet && !powerUpOnScreen) {
                if (new Random().nextInt(2) == 1) {
                    coin4.setBackgroundResource(R.drawable.magnet);
                    coin4.setTag(R.drawable.magnet);
                } else {
                    coin4.setBackgroundResource(R.drawable.speed);
                    coin4.setTag(R.drawable.speed);
                }
                powerUpOnScreen = true;
            }
        }
        if (isCoin()) {
            coin8.setVisibility(View.VISIBLE);
            coin8.setX(ob7.getX() + screenWidth / 6 + coin8.getLayoutParams().width / 2f);
            coin8.setY(coinPosition());
            coin8.setBackgroundResource(R.drawable.coin);
            coin8.setTag(R.drawable.coin);
            if (isPowerUp() && !rush && !magnet && !powerUpOnScreen) {
                if (new Random().nextInt(2) == 1) {
                    coin8.setBackgroundResource(R.drawable.magnet);
                    coin8.setTag(R.drawable.magnet);
                } else {
                    coin8.setBackgroundResource(R.drawable.speed);
                    coin8.setTag(R.drawable.speed);
                }
                powerUpOnScreen = true;
            }
        }

    }

    private void updateOb() {
        if (run == 1) {
            rl.bringChildToFront(scorer);
            if (pig.getX() + pig.getWidth() > ob1.getX()
                    && pig.getX() < ob1.getX() + ob1.getWidth()) {
                if (scoreOb1 == 1) {
                    if (pig.getX() + pig.getWidth() > ob1.getX() + ob1.getWidth()) {
                        scoreOb1 = 2;
                        scoring();
                    }
                }
                if (!invincible &&
                        (pig.getY() + pig.getHeight() >= ob1.getY() || pig.getY() <= ob2.getY() + ob2.getHeight())) {
                    gameOver();
                }
            }

            if (ob3.getVisibility() == View.VISIBLE) {
                if (pig.getX() + pig.getWidth() > ob3.getX()
                        && pig.getX() < ob3.getX() + ob3.getWidth()) {
                    if (scoreOb3 == 1) {
                        if (pig.getX() + pig.getWidth() > ob3.getX() + ob3.getWidth()) {
                            scoreOb3 = 2;
                            scoring();
                        }
                    }
                    if (!invincible &&
                            (pig.getY() + pig.getHeight() > ob3.getY() || pig.getY() < ob4.getY() + ob4.getHeight())) {
                        gameOver();
                    }
                }
            }

            if (ob5.getVisibility() == View.VISIBLE) {
                if (pig.getX() + pig.getWidth() > ob5.getX()
                        && pig.getX() < ob5.getX() + ob5.getWidth()) {
                    if (scoreOb5 == 1) {
                        if (pig.getX() + pig.getWidth() > ob5.getX() + ob5.getWidth()) {
                            scoreOb5 = 2;
                            scoring();
                        }
                    }
                    if (!invincible &&
                            (pig.getY() + pig.getHeight() > ob5.getY() || pig.getY() < ob6.getY() + ob6.getHeight())) {
                        gameOver();
                    }
                }
            }

            if (ob7.getVisibility() == View.VISIBLE) {
                if (pig.getX() + pig.getWidth() > ob7.getX()
                        && pig.getX() < ob7.getX() + ob7.getWidth()) {
                    if (scoreOb7 == 1) {
                        if (pig.getX() + pig.getWidth() > ob7.getX() + ob7.getWidth()) {
                            scoreOb7 = 2;
                            scoring();
                        }
                    }
                    if (!invincible &&
                            (pig.getY() + pig.getHeight() > ob7.getY() || pig.getY() < ob8.getY() + ob8.getHeight())) {
                        gameOver();
                    }
                }
            }

            if (ob1.getX() > obStopX) {
                ob1.setX(ob1.getX() - speed);
                ob2.setX(ob2.getX() - speed);
            } else {
                ob12();
            }

            if (ob3.getX() > obStopX) {
                ob3.setX(ob3.getX() - speed);
                ob4.setX(ob4.getX() - speed);
            } else {
                ob34();
            }

            if (ob5.getX() > obStopX) {
                ob5.setX(ob5.getX() - speed);
                ob6.setX(ob6.getX() - speed);
            } else {
                ob56();
            }

            if (ob7.getX() > obStopX) {
                ob7.setX(ob7.getX() - speed);
                ob8.setX(ob8.getX() - speed);
            } else {
                ob78();
            }

            if (pig.getX() + pig.getWidth() >= coin1.getX()
                    && pig.getX() < coin1.getX() + coin1.getWidth()
                    && pig.getY() + pig.getHeight() > coin1.getY()
                    && pig.getY() < coin1.getY() + coin1.getLayoutParams().height
                    && coin1.getVisibility() == View.VISIBLE) {
                switch ((Integer) coin1.getTag()) {
                    case R.drawable.magnet:
                        setMagnet();
                        break;
                    case R.drawable.speed:
                        setRush();
                        break;
                    default:
                        coinCollect();
                }
                coin1.setVisibility(View.INVISIBLE);
            }

            if (pig.getX() + pig.getWidth() >= coin2.getX()
                    && pig.getX() < coin2.getX() + coin2.getWidth()
                    && pig.getY() + pig.getHeight() > coin2.getY()
                    && pig.getY() < coin2.getY() + coin2.getHeight()
                    && coin2.getVisibility() == View.VISIBLE) {
                switch ((Integer) coin2.getTag()) {
                    case R.drawable.magnet:
                        setMagnet();
                        break;
                    case R.drawable.speed:
                        setRush();
                        break;
                    default:
                        coinCollect();
                }
                coin2.setVisibility(View.INVISIBLE);
            }

            if (pig.getX() + pig.getWidth() >= coin3.getX()
                    && pig.getX() < coin3.getX() + coin3.getWidth()
                    && pig.getY() + pig.getHeight() > coin3.getY()
                    && pig.getY() < coin3.getY() + coin3.getHeight()
                    && coin3.getVisibility() == View.VISIBLE) {
                switch ((Integer) coin3.getTag()) {
                    case R.drawable.magnet:
                        setMagnet();
                        break;
                    case R.drawable.speed:
                        setRush();
                        break;
                    default:
                        coinCollect();
                }
                coin3.setVisibility(View.INVISIBLE);
            }

            if (pig.getX() + pig.getWidth() >= coin4.getX()
                    && pig.getX() < coin4.getX() + coin4.getWidth()
                    && pig.getY() + pig.getHeight() > coin4.getY()
                    && pig.getY() < coin4.getY() + coin4.getHeight()
                    && coin4.getVisibility() == View.VISIBLE) {
                switch ((Integer) coin4.getTag()) {
                    case R.drawable.magnet:
                        setMagnet();
                        break;
                    case R.drawable.speed:
                        setRush();
                        break;
                    default:
                        coinCollect();
                }
                coin4.setVisibility(View.INVISIBLE);
            }

            if (pig.getX() + pig.getWidth() >= coin5.getX()
                    && pig.getX() < coin5.getX() + coin5.getWidth()
                    && pig.getY() + pig.getHeight() > coin5.getY()
                    && pig.getY() < coin5.getY() + coin5.getHeight()
                    && coin5.getVisibility() == View.VISIBLE) {
                switch ((Integer) coin5.getTag()) {
                    case R.drawable.magnet:
                        setMagnet();
                        break;
                    case R.drawable.speed:
                        setRush();
                        break;
                    default:
                        coinCollect2();
                }
                coin5.setVisibility(View.INVISIBLE);
            }

            if (pig.getX() + pig.getWidth() >= coin6.getX()
                    && pig.getX() < coin6.getX() + coin6.getWidth()
                    && pig.getY() + pig.getHeight() > coin6.getY()
                    && pig.getY() < coin6.getY() + coin6.getHeight()
                    && coin6.getVisibility() == View.VISIBLE) {
                switch ((Integer) coin6.getTag()) {
                    case R.drawable.magnet:
                        setMagnet();
                        break;
                    case R.drawable.speed:
                        setRush();
                        break;
                    default:
                        coinCollect2();
                }
                coin6.setVisibility(View.INVISIBLE);
            }

            if (pig.getX() + pig.getWidth() >= coin7.getX()
                    && pig.getX() < coin7.getX() + coin7.getWidth()
                    && pig.getY() + pig.getHeight() > coin7.getY()
                    && pig.getY() < coin7.getY() + coin7.getHeight()
                    && coin7.getVisibility() == View.VISIBLE) {
                switch ((Integer) coin7.getTag()) {
                    case R.drawable.magnet:
                        setMagnet();
                        break;
                    case R.drawable.speed:
                        setRush();
                        break;
                    default:
                        coinCollect2();
                }
                coin7.setVisibility(View.INVISIBLE);
            }

            if (pig.getX() + pig.getWidth() >= coin8.getX()
                    && pig.getX() < coin8.getX() + coin8.getWidth()
                    && pig.getY() + pig.getHeight() > coin8.getY()
                    && pig.getY() < coin8.getY() + coin8.getHeight()
                    && coin8.getVisibility() == View.VISIBLE) {
                switch ((Integer) coin8.getTag()) {
                    case R.drawable.magnet:
                        setMagnet();
                        break;
                    case R.drawable.speed:
                        setRush();
                        break;
                    default:
                        coinCollect2();
                }
                coin8.setVisibility(View.INVISIBLE);
            }
            coin1.setX(coin1.getX() - speed);
            coin2.setX(coin2.getX() - speed);
            coin3.setX(coin3.getX() - speed);
            coin4.setX(coin4.getX() - speed);
            coin5.setX(coin5.getX() - speed);
            coin6.setX(coin6.getX() - speed);
            coin7.setX(coin7.getX() - speed);
            coin8.setX(coin8.getX() - speed);

            if (magnet) {
                magnetTime -= 10;
                coin1.setY(pig.getY());
                coin2.setY(pig.getY());
                coin3.setY(pig.getY());
                coin4.setY(pig.getY());
                coin5.setY(pig.getY());
                coin6.setY(pig.getY());
                coin7.setY(pig.getY());
                coin8.setY(pig.getY());
                coin1.setX(coin1.getX() - speed);
                coin2.setX(coin2.getX() - speed);
                coin3.setX(coin3.getX() - speed);
                coin4.setX(coin4.getX() - speed);
                coin5.setX(coin5.getX() - speed);
                coin6.setX(coin6.getX() - speed);
                coin7.setX(coin7.getX() - speed);
                coin8.setX(coin8.getX() - speed);
                if (magnetTime == 0) {
                    magnet = false;
                    coin1.setVisibility(View.INVISIBLE);
                    coin2.setVisibility(View.INVISIBLE);
                    coin3.setVisibility(View.INVISIBLE);
                    coin4.setVisibility(View.INVISIBLE);
                    coin5.setVisibility(View.INVISIBLE);
                    coin6.setVisibility(View.INVISIBLE);
                    coin7.setVisibility(View.INVISIBLE);
                    coin8.setVisibility(View.INVISIBLE);
                }
            }

            if (rush) {
                speed = screenWidth / 50f;
                speedTime -= 10;
                invincible = true;
                if (speedTime == 0) {
                    rush = false;
                    invincibleTime = 2000;
                    speed = saveSpeed;
                }
            }

            if (invincible && !rush) {
                invincibleTime -= 10;
                if (invincibleTime == 0) {
                    invincible = false;
                }
            }

            powerUpOnScreen = (Integer) coin1.getTag() != R.drawable.coin
                    || (Integer) coin2.getTag() != R.drawable.coin
                    || (Integer) coin3.getTag() != R.drawable.coin
                    || (Integer) coin4.getTag() != R.drawable.coin
                    || (Integer) coin5.getTag() != R.drawable.coin
                    || (Integer) coin6.getTag() != R.drawable.coin
                    || (Integer) coin7.getTag() != R.drawable.coin
                    || (Integer) coin8.getTag() != R.drawable.coin;

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    updateOb();
                }
            }, 10);
        }
    }

    private boolean isCoin() {
        if (magnet) {
            return new Random().nextInt(2) == 1;
        } else {
            return new Random().nextInt(4) == 1;
        }
    }

    private boolean isPowerUp() {

        return new Random().nextInt(6) == 1;
    }

    private float coinPosition() {
        return (new Random().nextInt(95 - 5) + 5) * screenHeight / 100;
    }

    private float obPosition() {
        return (new Random().nextInt(80 - 20) + 20) * screenHeight / 100;
    }

    private void setMagnet() {
        magnet = true;
        magnetTime = 10000;
        powerUpSound.start();
    }

    private void setRush() {
        rush = true;
        speedTime = 5000;
        saveSpeed = speed;
        powerUpSound.start();
    }

    private void coinCollect() {
        coinSound.start();
        newCoins++;
        coinScore.setText(String.format("%s", newCoins));
    }

    private void coinCollect2() {
        coinSound2.start();
        newCoins++;
        coinScore.setText(String.format("%s", newCoins));
    }

    private void scoring() {
        score++;
        scorer.setText(String.format("%s", score));
    }

    private void pause() {
        if (run == 1) {
            run = 2;
            pause.setBackgroundResource(R.drawable.play);
            backtrack.pause();
        } else {
            run = 1;
            pause.setBackgroundResource(R.drawable.pause);
            backtrack.start();
            updateOb();
            flipCard();
        }
    }

    private void showHelp() {
        final Dialog help = new Dialog(MainActivity.this);
        help.setContentView(R.layout.instructions);
        LinearLayout dialogLl = help.findViewById(R.id.helpLl);
        Button closeDialog = help.findViewById(R.id.closeInstructions);
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                help.dismiss();
            }
        });
        dialogLl.setBackgroundColor(color);
        Objects.requireNonNull(help.getWindow()).setLayout((int) (screenWidth * 0.9f), (int) (screenHeight * 0.95f));
        help.show();
        gameOver.setBackgroundResource(R.drawable.flappypig2);
    }

    private void showSettings() {
        open.start();
        final Dialog settingsDialog = new Dialog(MainActivity.this);
        settingsDialog.setContentView(R.layout.settings);

        SeekBar settingsAudio = settingsDialog.findViewById(R.id.audioBar),
                settingsMusic = settingsDialog.findViewById(R.id.musicBar);
        final LinearLayout dialogLl = settingsDialog.findViewById(R.id.settingRl);
        dialogLl.setBackgroundColor(color);
        final Button settingsChangeColor = settingsDialog.findViewById(R.id.changeColor),
                closeDialog = settingsDialog.findViewById(R.id.closeSettings),
                settingsChangeControl = settingsDialog.findViewById(R.id.changeControl),
                settingsChangeLanguage = settingsDialog.findViewById(R.id.changeLang);
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsDialog.dismiss();
            }
        });
        settingsChangeControl.setText(controlSide.equals("right") ? getString(R.string.change_control_side_right) : getString(R.string.change_control_side_left));
        settingsAudio.setProgress((int) (audioVolume * 100));
        settingsMusic.setProgress((int) (musicVolume * 100));
        Objects.requireNonNull(settingsDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        settingsAudio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioVolume = progress / 100f;
                coinSound.setVolume(audioVolume, audioVolume);
                coinSound2.setVolume(audioVolume, audioVolume);
                open.setVolume(audioVolume, audioVolume);
                powerUpSound.setVolume(audioVolume, audioVolume);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        settingsMusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                musicVolume = progress / 100f;
                backtrack.setVolume(musicVolume, musicVolume);
                lobby.setVolume(musicVolume, musicVolume);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        settingsChangeControl.setTextColor(color);
        settingsChangeControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controlSide = controlSide.equals("left") ? "right" : "left";
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) control.getLayoutParams();
                if (controlSide.equals("right")) {
                    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    params.removeRule(RelativeLayout.ALIGN_PARENT_LEFT);
                } else {
                    params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    params.removeRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                }
                control.setLayoutParams(params);
                settingsChangeControl.setText(controlSide.equals("right") ? getString(R.string.change_control_side_right) : getString(R.string.change_control_side_left));
                SharedPreferences sharedPreferences = getSharedPreferences("values", 0);
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putString("controlSide", controlSide);
                edit.apply();
            }
        });
        settingsDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                SharedPreferences sharedPreferences = getSharedPreferences("values", 0);
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putFloat("musicVolume", musicVolume);
                edit.putFloat("audioVolume", audioVolume);
                edit.putString("controlSide", controlSide);
                edit.apply();

            }
        });
        settingsChangeColor.setTextColor(color);
        settingsChangeColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (color) {
                    case Color.MAGENTA:
                        color = Color.RED;
                        break;
                    case Color.RED:
                        color = Color.GREEN;
                        break;
                    case Color.GREEN:
                        color = Color.parseColor("#ff33b5e5");
                        break;
                    default:
                        color = Color.MAGENTA;
                }
                settingsChangeColor.setTextColor(color);
                settingsChangeControl.setTextColor(color);
                settingsChangeLanguage.setTextColor(color);
                dialogLl.setBackgroundColor(color);
                rl.setBackgroundColor(color);
                SharedPreferences sharedPreferences = getSharedPreferences("values", 0);
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putInt("color", color);
                edit.apply();

            }
        });
        settingsChangeLanguage.setTextColor(color);
        settingsChangeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog changeLangDialog = new Dialog(MainActivity.this);
                changeLangDialog.setContentView(R.layout.language);
                LinearLayout langLl = changeLangDialog.findViewById(R.id.langLl);
                Button english = changeLangDialog.findViewById(R.id.english),
                        spanish = changeLangDialog.findViewById(R.id.spanish),
                        russian = changeLangDialog.findViewById(R.id.russian),
                        portuguese = changeLangDialog.findViewById(R.id.portuguese),
                        french = changeLangDialog.findViewById(R.id.french),
                        close = changeLangDialog.findViewById(R.id.closeLang);
                english.setTextColor(color);
                english.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setLang("en");
                        recreate();
                        changeLangDialog.dismiss();
                    }
                });
                spanish.setTextColor(color);
                spanish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setLang("es");
                        recreate();
                        changeLangDialog.dismiss();
                    }
                });
                russian.setTextColor(color);
                russian.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setLang("ru");
                        recreate();
                        changeLangDialog.dismiss();
                    }
                });
                portuguese.setTextColor(color);
                portuguese.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setLang("pt");
                        recreate();
                        changeLangDialog.dismiss();
                    }
                });
                french.setTextColor(color);
                french.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setLang("fr");
                        recreate();
                        changeLangDialog.dismiss();
                    }
                });
                close.setTextColor(color);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        changeLangDialog.dismiss();
                    }
                });
                langLl.setBackgroundColor(color);
                changeLangDialog.getWindow().setLayout((int) (screenWidth * 0.45f), (int) (screenHeight));
                changeLangDialog.show();
            }
        });
        settingsDialog.getWindow().setLayout((int) (screenWidth * 0.65f), (int) (screenHeight * 0.95f));
        settingsDialog.show();
        gameOver.setBackgroundResource(R.drawable.flappypig2);

    }

    private void setLang(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
        lang = language;
        SharedPreferences sharedPreferences = getSharedPreferences("values", 0);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString("lang", language);
        edit.apply();
    }

    private void showStats() {
        open.start();
        final Dialog statsDialog = new Dialog(MainActivity.this);
        statsDialog.setContentView(R.layout.stats);
        Button closeDialog = statsDialog.findViewById(R.id.closeStats);
        TextView statsHighScore = statsDialog.findViewById(R.id.hs),
                statsTotalScore = statsDialog.findViewById(R.id.ts),
                statsAverage = statsDialog.findViewById(R.id.avg),
                statsGamesPlayed = statsDialog.findViewById(R.id.gp),
                statsCoins = statsDialog.findViewById(R.id.coins);
        LinearLayout dialogLl = statsDialog.findViewById(R.id.statsLl);
        dialogLl.setBackgroundColor(color);

        Objects.requireNonNull(statsDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        statsHighScore.setText(String.format(getString(R.string.high_score) + " %s", highScore));
        statsCoins.setText(String.format(getString(R.string.coins) + " %s", coins));
        statsAverage.setText(String.format(getString(R.string.average) + " %s", average));
        statsTotalScore.setText(String.format(getString(R.string.total_score) + " %s", totalScore));
        statsGamesPlayed.setText(String.format(getString(R.string.games_played) + " %s", playedGames));

        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statsDialog.dismiss();
            }
        });
        statsDialog.getWindow().setLayout((int) (screenWidth * 0.5f), (int) (screenHeight * 0.90f));
        statsDialog.show();
        gameOver.setBackgroundResource(R.drawable.flappypig2);
    }

    private void showShop() {
        open.start();
        final Dialog shopDialog = new Dialog(MainActivity.this);
        shopDialog.setContentView(R.layout.shop);
        Button closeDialog = shopDialog.findViewById(R.id.closeShop);
        final TextView shopCoins = shopDialog.findViewById(R.id.shopCoins), shopTitle = shopDialog.findViewById(R.id.shopTitle);
        LinearLayout skin1 = shopDialog.findViewById(R.id.skin1),
                skin2 = shopDialog.findViewById(R.id.skin2),
                skin3 = shopDialog.findViewById(R.id.skin3),
                skin4 = shopDialog.findViewById(R.id.skin4),
                skin5 = shopDialog.findViewById(R.id.skin5),
                skin6 = shopDialog.findViewById(R.id.skin6),
                skin7 = shopDialog.findViewById(R.id.skin7),
                skin8 = shopDialog.findViewById(R.id.skin8),
                dialogLl = shopDialog.findViewById(R.id.shopLl);
        dialogLl.setBackgroundColor(color);
        shopTitle.setText(getString(R.string.shop));
        switch ((Integer) pig.getTag()) {
            case R.drawable.ninja:
                skin2.setBackgroundResource(R.drawable.popup_back);
                break;
            case R.drawable.detective:
                skin3.setBackgroundResource(R.drawable.popup_back);
                break;
            case R.drawable.superpig:
                skin4.setBackgroundResource(R.drawable.popup_back);
                break;
            case R.drawable.batpig:
                skin5.setBackgroundResource(R.drawable.popup_back);
                break;
            case R.drawable.moviepig:
                skin6.setBackgroundResource(R.drawable.popup_back);
                break;
            case R.drawable.witchpig:
                skin7.setBackgroundResource(R.drawable.popup_back);
                break;
            case R.drawable.kingpig:
                skin8.setBackgroundResource(R.drawable.popup_back);
                break;
            default:
                skin1.setBackgroundResource(R.drawable.popup_back);

        }
        final ImageView display2 = shopDialog.findViewById(R.id.coinDisplay2),
                display3 = shopDialog.findViewById(R.id.coinDisplay3),
                display4 = shopDialog.findViewById(R.id.coinDisplay4),
                display5 = shopDialog.findViewById(R.id.coinDisplay5),
                display6 = shopDialog.findViewById(R.id.coinDisplay6),
                display7 = shopDialog.findViewById(R.id.coinDisplay7),
                display8 = shopDialog.findViewById(R.id.coinDisplay8);
        final TextView cost2 = shopDialog.findViewById(R.id.cost2),
                cost3 = shopDialog.findViewById(R.id.cost3),
                cost4 = shopDialog.findViewById(R.id.cost4),
                cost5 = shopDialog.findViewById(R.id.cost5),
                cost6 = shopDialog.findViewById(R.id.cost6),
                cost7 = shopDialog.findViewById(R.id.cost7),
                cost8 = shopDialog.findViewById(R.id.cost8);

        shopCoins.setText(String.format("%s", coins));

        if (ownSkin2) {
            display2.setVisibility(View.INVISIBLE);
            cost2.setVisibility(View.INVISIBLE);
        }
        if (ownSkin3) {
            display3.setVisibility(View.INVISIBLE);
            cost3.setVisibility(View.INVISIBLE);
        }
        if (ownSkin4) {
            display4.setVisibility(View.INVISIBLE);
            cost4.setVisibility(View.INVISIBLE);
        }
        if (ownSkin5) {
            display5.setVisibility(View.INVISIBLE);
            cost5.setVisibility(View.INVISIBLE);
        }
        if (ownSkin6) {
            display6.setVisibility(View.INVISIBLE);
            cost6.setVisibility(View.INVISIBLE);
        }
        if (ownSkin7) {
            display7.setVisibility(View.INVISIBLE);
            cost7.setVisibility(View.INVISIBLE);
        }
        if (ownSkin8) {
            display8.setVisibility(View.INVISIBLE);
            cost8.setVisibility(View.INVISIBLE);
        }

        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopDialog.dismiss();
            }
        });
        skin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pig.setBackgroundResource(R.drawable.pig);
                pig.setTag(R.drawable.pig);
                shopDialog.dismiss();
            }
        });
        skin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ownSkin2) {
                    pig.setBackgroundResource(R.drawable.ninja);
                    pig.setTag(R.drawable.ninja);
                } else if (buySkin(2)) {
                    ownSkin2 = true;
                    pig.setBackgroundResource(R.drawable.ninja);
                    pig.setTag(R.drawable.ninja);
                    shopCoins.setText(String.format("%s", coins));
                    display2.setVisibility(View.INVISIBLE);
                    cost2.setVisibility(View.INVISIBLE);
                    save();
                }
                shopDialog.dismiss();
            }
        });
        skin3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ownSkin3) {
                    pig.setBackgroundResource(R.drawable.detective);
                    pig.setTag(R.drawable.detective);
                } else if (buySkin(3)) {
                    ownSkin3 = true;
                    pig.setBackgroundResource(R.drawable.detective);
                    pig.setTag(R.drawable.detective);
                    shopCoins.setText(String.format("%s", coins));
                    display3.setVisibility(View.INVISIBLE);
                    cost3.setVisibility(View.INVISIBLE);
                    save();
                }
                shopDialog.dismiss();
            }
        });
        skin4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ownSkin4) {
                    pig.setBackgroundResource(R.drawable.superpig);
                    pig.setTag(R.drawable.superpig);
                } else if (buySkin(4)) {
                    ownSkin4 = true;
                    pig.setBackgroundResource(R.drawable.superpig);
                    pig.setTag(R.drawable.superpig);
                    shopCoins.setText(String.format("%s", coins));
                    display4.setVisibility(View.INVISIBLE);
                    cost4.setVisibility(View.INVISIBLE);
                    save();
                }
                shopDialog.dismiss();
            }
        });
        skin5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ownSkin5) {
                    pig.setBackgroundResource(R.drawable.batpig);
                    pig.setTag(R.drawable.batpig);
                } else if (buySkin(5)) {
                    ownSkin5 = true;
                    pig.setBackgroundResource(R.drawable.batpig);
                    pig.setTag(R.drawable.batpig);
                    shopCoins.setText(String.format("%s", coins));
                    display5.setVisibility(View.INVISIBLE);
                    cost5.setVisibility(View.INVISIBLE);
                    save();
                }
                shopDialog.dismiss();
            }
        });
        skin6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ownSkin6) {
                    pig.setBackgroundResource(R.drawable.moviepig);
                    pig.setTag(R.drawable.moviepig);
                } else if (buySkin(6)) {
                    ownSkin6 = true;
                    pig.setBackgroundResource(R.drawable.moviepig);
                    pig.setTag(R.drawable.moviepig);
                    shopCoins.setText(String.format("%s", coins));
                    display6.setVisibility(View.INVISIBLE);
                    cost6.setVisibility(View.INVISIBLE);
                    save();
                }
                shopDialog.dismiss();
            }
        });
        skin7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ownSkin7) {
                    pig.setBackgroundResource(R.drawable.witchpig);
                    pig.setTag(R.drawable.witchpig);
                } else if (buySkin(7)) {
                    ownSkin7 = true;
                    pig.setBackgroundResource(R.drawable.witchpig);
                    pig.setTag(R.drawable.witchpig);
                    shopCoins.setText(String.format("%s", coins));
                    display7.setVisibility(View.INVISIBLE);
                    cost7.setVisibility(View.INVISIBLE);
                    save();
                }
                shopDialog.dismiss();
            }
        });
        skin8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ownSkin8) {
                    pig.setBackgroundResource(R.drawable.kingpig);
                    pig.setTag(R.drawable.kingpig);
                } else if (buySkin(8)) {
                    ownSkin8 = true;
                    pig.setBackgroundResource(R.drawable.kingpig);
                    pig.setTag(R.drawable.kingpig);
                    shopCoins.setText(String.format("%s", coins));
                    display8.setVisibility(View.INVISIBLE);
                    cost8.setVisibility(View.INVISIBLE);
                    save();
                }
                shopDialog.dismiss();
            }
        });
        shopDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                switch ((Integer) pig.getTag()) {
                    case R.drawable.ninja:
                        selectedSkin = "ninja";
                        break;
                    case R.drawable.detective:
                        selectedSkin = "detective";
                        break;
                    case R.drawable.superpig:
                        selectedSkin = "superpig";
                        break;
                    case R.drawable.batpig:
                        selectedSkin = "batpig";
                        break;
                    case R.drawable.moviepig:
                        selectedSkin = "movie";
                        break;
                    case R.drawable.witchpig:
                        selectedSkin = "witch";
                        break;
                    case R.drawable.kingpig:
                        selectedSkin = "king";
                        break;
                    default:
                        selectedSkin = "pig";
                }
                SharedPreferences sharedPreferences = getSharedPreferences("values", 0);
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putString("selectedSkin", selectedSkin);
                edit.apply();
            }
        });
        Objects.requireNonNull(shopDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        shopDialog.getWindow().setLayout((int) ((int) screenWidth * 0.85f), (int) ((int) screenHeight * 0.85f));
        shopDialog.show();
        gameOver.setBackgroundResource(R.drawable.flappypig2);
    }

    private boolean buySkin(int skinNo) {
        String skinName;
        int skinPrice;
        final Dialog buySkin = new Dialog(MainActivity.this);
        buySkin.setContentView(R.layout.buy_skin);
        LinearLayout buySkinLl = buySkin.findViewById(R.id.buySkinLl);
        buySkinLl.setBackgroundColor(color);
        TextView skinBuyTitle = buySkin.findViewById(R.id.title);
        TextView skinBuyMessage = buySkin.findViewById(R.id.message);
        Button close = buySkin.findViewById(R.id.closeBuySkin);
        Objects.requireNonNull(buySkin.getWindow()).setLayout((int) ((int) screenWidth * 0.65f), (int) ((int) screenHeight * 0.65f));
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buySkin.dismiss();
            }
        });

        switch (skinNo) {
            case 2:
                skinName = getString(R.string.ninja);
                skinPrice = 10;
                break;
            case 3:
                skinName = getString(R.string.detective);
                skinPrice = 50;
                break;
            case 4:
                skinName = getString(R.string.superpig);
                skinPrice = 85;
                break;
            case 5:
                skinName = getString(R.string.batpig);
                skinPrice = 125;
                break;
            case 6:
                skinName = getString(R.string.movie_pig);
                skinPrice = 175;
                break;
            case 7:
                skinName = getString(R.string.witch);
                skinPrice = 200;
                break;
            case 8:
                skinName = getString(R.string.king);
                skinPrice = 250;
                break;
            default:
                skinName = getString(R.string.pig);
                skinPrice = 0;
        }
        skinBuyTitle.setText(String.format(getString(R.string.buy_skin) + " %s", skinName));
        if (!(coins >= skinPrice)) {
            skinBuyMessage.setText(String.format(getString(R.string.skin_NO) + " %s", skinName));
            buySkin.show();
            return false;
        } else {
            coins -= skinPrice;
            skinBuyMessage.setText(String.format(getString(R.string.skin_YES) + " %s", skinName));
            coinScore.setText(String.format("%s", coins));
            buySkin.show();
            return true;
        }
    }

    private void showSpecialDialog() {
        final Dialog specialDialog = new Dialog(MainActivity.this);
        specialDialog.setContentView(R.layout.special_dialog);
        LinearLayout dialogLl = specialDialog.findViewById(R.id.specialDialogLl);
        Button closeDialog = specialDialog.findViewById(R.id.closeInstructions),
                viper = specialDialog.findViewById(R.id.viper);
        viper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String appPackageName = "com.games.viper";
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                specialDialog.dismiss();
            }
        });
        dialogLl.setBackgroundColor(color);
        Objects.requireNonNull(specialDialog.getWindow()).setLayout((int) (screenWidth * 0.9f), (int) (screenHeight * 0.95f));
        specialDialog.show();
        showedSpecialDialog = true;
        SharedPreferences sharedPreferences = getSharedPreferences("values", 0);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean("specialDialog5", showedSpecialDialog);
        edit.apply();
        save();
    }

    private void save() {
        SharedPreferences sharedPreferences = getSharedPreferences("values", 0);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt("coins", coins);
        edit.putBoolean("skin2", ownSkin2);
        edit.putBoolean("skin3", ownSkin3);
        edit.putBoolean("skin4", ownSkin4);
        edit.putBoolean("skin5", ownSkin5);
        edit.putBoolean("skin6", ownSkin6);
        edit.putBoolean("skin7", ownSkin7);
        edit.putBoolean("skin8", ownSkin8);
        edit.apply();
    }
}