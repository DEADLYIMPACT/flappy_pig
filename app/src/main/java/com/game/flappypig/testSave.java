//package com.game.flappyPig;
//
//import android.animation.AnimatorInflater;
//import android.animation.AnimatorSet;
//import android.annotation.SuppressLint;
//import android.app.Dialog;
//import android.content.SharedPreferences;
//import android.graphics.Color;
//import android.graphics.Point;
//import android.graphics.drawable.ColorDrawable;
//import android.media.MediaPlayer;
//import android.os.Bundle;
//import android.os.Handler;
//import android.util.Log;
//import android.view.Display;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import java.util.Random;
//
//public class MainActivity extends AppCompatActivity {
//    AnimatorSet flip1, flip2, flip3, flip4;
//    TextView scorer, coinScore, highscorer,
//            statsHighScore, statsGamesPlayed, statsAverage, statsTotalScore, statsCoins,
//            shopCoins;
//    RelativeLayout rl;
//    Dialog statsDialog,shopDialog;
//    Button start, pause, shop, stats, closeDialog;
//    ImageView pig, ob1, ob2, ob3, ob4, ob5, ob6, ob7, ob8,
//            gameOver,
//            coin1, coin2, coin3, coin4, coin5, coin6, coin7, coin8, displayCoin,
//            layout, circle;
//    MediaPlayer coinSound, backtrack;
//
//    static int run = 0,
//            scoreOb1, scoreOb3, scoreOb5, scoreOb7,
//            coins, newCoins, score = 0, highScore, totalScore, playedGames;
//    static float screenWidth, screenHeight,
//            speed = 5, obHeight, obWidth, obSpawnX, obStopX,
//            scale,
//            average;
//    boolean first = true;
//    private static final String TAG = "foot";
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (run == 1) {
//            pause();
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        View decorView = getWindow().getDecorView();
//        decorView.setSystemUiVisibility(
//                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
//                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
//                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//    }
//
//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        View decorView = getWindow().getDecorView();
//        decorView.setSystemUiVisibility(
//                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
//                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
//                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//    }
//
//    @SuppressLint("ClickableViewAccessibility")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        View decorView = getWindow().getDecorView();
//        decorView.setSystemUiVisibility(
//                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
//                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
//                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        // findViewById();
//        ob1 = findViewById(R.id.ob1);
//        ob2 = findViewById(R.id.ob2);
//        ob3 = findViewById(R.id.ob3);
//        ob4 = findViewById(R.id.ob4);
//        ob5 = findViewById(R.id.ob5);
//        ob6 = findViewById(R.id.ob6);
//        ob7 = findViewById(R.id.ob7);
//        ob8 = findViewById(R.id.ob8);
//        pig = findViewById(R.id.pig);
//        circle = findViewById(R.id.circle);
//        layout = findViewById(R.id.layout1);
//        start = findViewById(R.id.start);
//        shop = findViewById(R.id.shop);
//        stats = findViewById(R.id.stats);
//        pause = findViewById(R.id.pause);
//        gameOver = findViewById(R.id.gameover);
//        scorer = findViewById(R.id.score);
//        highscorer = findViewById(R.id.highscore);
//        coin1 = findViewById(R.id.coin1);
//        coin2 = findViewById(R.id.coin2);
//        coin3 = findViewById(R.id.coin3);
//        coin4 = findViewById(R.id.coin4);
//        coinScore = findViewById(R.id.coinScore);
//        displayCoin = findViewById(R.id.displayCoin);
//        rl = findViewById(R.id.rl);
//
//        statsDialog = new Dialog(this);
//        shopDialog = new Dialog(this);
//
//        // Variables
//        Display display = getWindowManager().getDefaultDisplay();
//        Point size = new Point();
//        display.getSize(size);
//        screenWidth = size.x;
//        screenHeight = size.y;
//        speed = screenWidth / 400;
//        obWidth = screenWidth / 10;
//        obHeight = screenHeight * 4 / 5;
//        obSpawnX = screenWidth + screenWidth / 5f;
//        obStopX = -(screenWidth / 5f);
//        SharedPreferences sharedPreferences = getSharedPreferences("values", 0);
//        coins = sharedPreferences.getInt("coins", 0);
//        highScore = sharedPreferences.getInt("hs", 0);
//        average = sharedPreferences.getFloat("avg",0);
//        totalScore = sharedPreferences.getInt("ts",0);
//        playedGames = sharedPreferences.getInt("gp",0);
//
//        newCoins = 0;
//        scale = getResources().getDisplayMetrics().density * 8000;
//
//        // Start Button
//        start.setY(screenHeight * 3 / 4);
//        start.getLayoutParams().width = (int) screenWidth / 4;
//        start.getLayoutParams().height = (int) screenHeight / 5;
//
//
//        // Invisible
//        ob1.setVisibility(View.INVISIBLE);
//        ob2.setVisibility(View.INVISIBLE);
//        ob3.setVisibility(View.INVISIBLE);
//        ob4.setVisibility(View.INVISIBLE);
//        ob5.setVisibility(View.INVISIBLE);
//        ob6.setVisibility(View.INVISIBLE);
//        ob7.setVisibility(View.INVISIBLE);
//        ob8.setVisibility(View.INVISIBLE);
//        gameOver.setVisibility(View.INVISIBLE);
//
//        // Pig
//        pig.getLayoutParams().height = (int) (screenHeight / 15);
//        pig.getLayoutParams().width = (int) (screenWidth / 20);
//        pig.setX(screenWidth / 10);
//        pig.setY(screenHeight / 2);
//        pig.setVisibility(View.INVISIBLE);
//
//        // Circle
//        layout.getLayoutParams().width = (int) (screenHeight / 10);
//        layout.setX(screenWidth);
//        circle.setY(pig.getY());
//        circle.setX(layout.getX());
//        circle.setVisibility(View.INVISIBLE);
//        circle.getLayoutParams().height = (int) (screenHeight / 10);
//        circle.getLayoutParams().width = (int) (screenHeight / 10);
//
//
//        // Pause
//        pause.getLayoutParams().height = (int) (screenHeight / 10);
//        pause.getLayoutParams().width = (int) (screenHeight / 10);
//        pause.setX(layout.getX() - pause.getLayoutParams().width * 1.3f);
//        pause.setY(screenHeight / 50);
//        pause.setVisibility(View.INVISIBLE);
//
//        // Obstacle
//        ob1.getLayoutParams().width = (int) obWidth;
//        ob2.getLayoutParams().width = (int) obWidth;
//        ob3.getLayoutParams().width = (int) obWidth;
//        ob4.getLayoutParams().width = (int) obWidth;
//        ob5.getLayoutParams().width = (int) obWidth;
//        ob6.getLayoutParams().width = (int) obWidth;
//        ob7.getLayoutParams().width = (int) obWidth;
//        ob8.getLayoutParams().width = (int) obWidth;
//        ob1.getLayoutParams().height = (int) obHeight;
//        ob2.getLayoutParams().height = (int) obHeight;
//        ob3.getLayoutParams().height = (int) obHeight;
//        ob4.getLayoutParams().height = (int) obHeight;
//        ob5.getLayoutParams().height = (int) obHeight;
//        ob6.getLayoutParams().height = (int) obHeight;
//        ob7.getLayoutParams().height = (int) obHeight;
//        ob8.getLayoutParams().height = (int) obHeight;
//
//
//        // Game Over
//        gameOver.setY(75);
//        gameOver.getLayoutParams().height = (int) (screenHeight / 3);
//        gameOver.getLayoutParams().width = (int) (screenWidth / 2);
//
//        // Scorer
//        scorer.bringToFront();
//        scorer.setVisibility(View.INVISIBLE);
//        highscorer.setY(start.getY() - screenHeight / 10);
//        highscorer.setText("HIGH SCORE - " + highScore);
//
//        // Coins
//        coin1.getLayoutParams().width = (int) (screenHeight / 12.5);
//        coin1.getLayoutParams().height = (int) (screenHeight / 12.5);
//        coin2.getLayoutParams().width = (int) (screenHeight / 12.5);
//        coin2.getLayoutParams().height = (int) (screenHeight / 12.5);
//        coin3.getLayoutParams().width = (int) (screenHeight / 12.5);
//        coin3.getLayoutParams().height = (int) (screenHeight / 12.5);
//        coin4.getLayoutParams().width = (int) (screenHeight / 12.5);
//        coin4.getLayoutParams().height = (int) (screenHeight / 12.5);
//        displayCoin.getLayoutParams().width = (int) (screenHeight / 10);
//        displayCoin.getLayoutParams().height = (int) (screenHeight / 10);
//        coin1.setVisibility(View.INVISIBLE);
//        coin2.setVisibility(View.INVISIBLE);
//        coin3.setVisibility(View.INVISIBLE);
//        coin4.setVisibility(View.INVISIBLE);
//        displayCoin.setX(layout.getX() - displayCoin.getLayoutParams().width * 1.3f);
//        displayCoin.setY(screenHeight / 15);
//        coinScore.setX(displayCoin.getX() - (float) displayCoin.getLayoutParams().width * 1.7f);
//        coinScore.setY(displayCoin.getY() - (float) displayCoin.getLayoutParams().height / 10);
//        if (coins < 10) {
//            coinScore.setText("    " + coins);
//        } else if (coins < 100) {
//            coinScore.setText("  " + coins);
//        } else {
//            coinScore.setText("" + coins);
//        }
//        coinSound = MediaPlayer.create(this, R.raw.coinsound);
//        coinSound.setVolume(0.3f, 0.3f);
//        flip1 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.flip1);
//        flip2 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.flip2);
//        flip3 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.flip3);
//        flip4 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.flip4);
//        coin1.setCameraDistance(scale);
//        coin2.setCameraDistance(scale);
//        coin3.setCameraDistance(scale);
//        coin4.setCameraDistance(scale);
//
//        shop.getLayoutParams().width = (int) screenWidth / 5;
//        shop.setX(screenWidth / 2 - shop.getLayoutParams().width * 2);
//        shop.setY(highscorer.getY());
//        shop.getLayoutParams().height = (int) screenHeight / 7;
//        stats.getLayoutParams().width = (int) screenWidth / 5;
//        stats.getLayoutParams().height = (int) screenHeight / 7;
//        stats.setX(screenWidth / 2 + (float) shop.getLayoutParams().width * 1.2f);
//        stats.setY(highscorer.getY());
//
//        rl.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event1) {
//                if (run == 1) {
//                    if (event1.getY() < pig.getY() + (screenHeight / 10) && event1.getY() > pig.getY() - (screenHeight / 10)) {
//                        if (event1.getAction() == MotionEvent.ACTION_MOVE) {
//                            pig.setY((event1.getY()));
//                            circle.setY((event1.getY()));
//                        }
//                    }
//                }
//                return true;
//            }
//        });
//
//        start.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        if (first) {
//                            start.setBackgroundResource(R.drawable.startbutton2);
//                        } else {
//                            start.setBackgroundResource(R.drawable.retry2);
//                        }
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        if (first) {
//                            start.setBackgroundResource(R.drawable.startbutton1);
//                        } else {
//                            start.setBackgroundResource(R.drawable.retry1);
//                        }
//                        start();
//                }
//                return true;
//            }
//        });
//
//        pause.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pause();
//            }
//        });
//        stats.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showStats();
//            }
//        });
//        shop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showShop();
//            }
//        });
//    }
//
//    public void start() {
//        run = 1;
//        speed = screenWidth / 350f;
//        gameOver.setVisibility(View.INVISIBLE);
//
//        // Obstacles
//        // 3 and 4
//        ob3.setY((obPosition()));
//        ob4.setY(ob3.getY() - ob2.getHeight() - pig.getHeight() - screenHeight / 8);
//        ob3.setX(screenWidth / 5);
//        ob4.setX(ob3.getX());
//
//        // 5 and 6
//        ob5.setY((obPosition()));
//        ob6.setY(ob3.getY() - ob2.getHeight() - pig.getHeight() - screenHeight / 8);
//        ob5.setX((screenWidth / 3) + (screenWidth / 5));
//        ob6.setX(ob5.getX());
//
//        // 7 and 8
//        ob7.setY((obPosition()));
//        ob8.setY(ob7.getY() - ob2.getHeight() - pig.getHeight() - (screenHeight / 8));
//        ob7.setX((screenWidth * 2 / 3) + (screenWidth / 5));
//        ob8.setX(ob7.getX());
//
//        // Pig & Circle
//        pig.setY(screenHeight / 2);
//        circle.setY(pig.getY());
//        pig.setVisibility(View.VISIBLE);
//        circle.setVisibility(View.VISIBLE);
//
//        // Score
//        scorer.setVisibility(View.VISIBLE);
//        scorer.setY(0);
//        score = 0;
//        scorer.setText("" + score);
//        highscorer.setVisibility(View.INVISIBLE);
//
//        // Coins
//        displayCoin.setY(screenHeight / 7.5f);
//        coinScore.setY(displayCoin.getY() - (float) displayCoin.getLayoutParams().height / 10);
//        newCoins = 0;
//        coinScore.setText("    " + newCoins);
//
//        // Pause
//        pause.setVisibility(View.VISIBLE);
//
//        // Backtrack
//        backtrack = MediaPlayer.create(this, R.raw.backtrack);
//        backtrack.setVolume(0.15f, 0.15f);
//        backtrack.isLooping();
//        backtrack.start();
//
//        // Method calls
//        ob12();
//        updateOb12();
//        updateOb34();
//        updateOb56();
//        updateOb78();
//        flipCard();
//        start.setVisibility(View.INVISIBLE);
//        shop.setVisibility(View.INVISIBLE);
//        stats.setVisibility(View.INVISIBLE);
//        Log.d(TAG, "start: started");
//
//    }
//
//    public void gameOver() {
//        first = false;
//        run = 2;
//        coins += newCoins;
//        playedGames++;
//        totalScore += score;
//        average = (float)Math.round(((float)totalScore / playedGames)*100)/100;
//        newCoins = 0;
//        gameOver.setVisibility(+View.VISIBLE);
//        start.setBackgroundResource(R.drawable.retry1);
//        start.setVisibility(View.VISIBLE);
//        shop.setVisibility(View.VISIBLE);
//        stats.setVisibility(View.VISIBLE);
//        ob1.setVisibility(View.INVISIBLE);
//        ob2.setVisibility(View.INVISIBLE);
//        ob3.setVisibility(View.INVISIBLE);
//        ob4.setVisibility(View.INVISIBLE);
//        ob5.setVisibility(View.INVISIBLE);
//        ob6.setVisibility(View.INVISIBLE);
//        ob7.setVisibility(View.INVISIBLE);
//        ob8.setVisibility(View.INVISIBLE);
//        pig.setVisibility(View.INVISIBLE);
//        circle.setVisibility(View.INVISIBLE);
//        coin1.setVisibility(View.INVISIBLE);
//        coin2.setVisibility(View.INVISIBLE);
//        coin3.setVisibility(View.INVISIBLE);
//        coin4.setVisibility(View.INVISIBLE);
//        pause.setVisibility(View.INVISIBLE);
//        displayCoin.setY(screenHeight / 15);
//        coinScore.setY(displayCoin.getY() - (float) displayCoin.getLayoutParams().height / 10);
//        if (coins < 10) {
//            coinScore.setText("    " + coins);
//        } else if (coins < 100) {
//            coinScore.setText("  " + coins);
//        } else {
//            coinScore.setText("" + coins);
//        }
//        scorer.setY(gameOver.getY() + gameOver.getHeight());
//        SharedPreferences sharedPreferences = getSharedPreferences("values", 0);
//        SharedPreferences.Editor edit = sharedPreferences.edit();
//        highscorer.setVisibility(View.VISIBLE);
//        highscorer.setText("HIGH SCORE - " + highScore);
//        if (score > highScore) {
//            highScore = score;
//            edit.putInt("hs", highScore);
//            highscorer.setText("NEW HIGH SCORE!");
//        }
//        edit.putInt("coins", coins);
//        edit.putInt("gp",playedGames);
//        edit.putInt("ts",totalScore);
//        edit.putFloat("avg",average);
//        edit.apply();
//        backtrack.stop();
//    }
//
//    public void flipCard() {
//        if (run == 1) {
//            flip1.setTarget(coin1);
//            flip1.start();
//            flip2.setTarget(coin2);
//            flip2.start();
//            flip3.setTarget(coin3);
//            flip3.start();
//            flip4.setTarget(coin4);
//            flip4.start();
//            Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    flipCard();
//                }
//            }, 20);
//        }
//    }
//
//    public void ob12() {
//        scoreOb1 = 1;
//        speed += 0.25;
//        ob1.setVisibility(View.VISIBLE);
//        ob2.setVisibility(View.VISIBLE);
//        ob1.setY(obPosition());
//        ob2.setY(ob1.getY() - ob1.getHeight() - pig.getHeight() - (screenHeight / 8));
//        ob1.setX(obSpawnX);
//        ob2.setX(obSpawnX);
//        if (isCoin()) {
//            coin1.setVisibility(View.VISIBLE);
//            coin1.setX(ob1.getX());
//            coin1.setY(ob1.getY() - pig.getHeight() - (screenHeight / 8) + (coin1.getHeight() / 1.5f));
//        }
//
//    }
//
//    public void updateOb12() {
//
//        if (run == 1) {
//            rl.bringChildToFront(scorer);
//            coin1.setX(ob1.getX() + (float) ob1.getWidth() / 2 - (float) coin1.getWidth() / 2);
//            if (pig.getX() + pig.getWidth() > ob1.getX()
//                    && pig.getX() < ob1.getX() + ob1.getWidth()) {
//                if (scoreOb1 == 1) {
//                    if (pig.getX() + pig.getWidth() > ob1.getX() + ob1.getWidth()) {
//
//                        scoreOb1 = 2;
//                        score++;
//                        scorer.setText("" + score);
//
//
//                    }
//                }
//                if (pig.getY() + pig.getHeight() >= ob1.getY()
//                        || pig.getY() <= ob2.getY() + ob2.getHeight()) {
//                    gameOver();
//
//                }
//                if (pig.getX() + pig.getWidth() >= coin1.getX()
//                        && pig.getX() < coin1.getX() + coin1.getWidth()
//                        && pig.getY() + pig.getHeight() > coin1.getY()
//                        && pig.getY() < coin1.getY() + coin1.getHeight()
//                        && coin1.getVisibility() == View.VISIBLE) {
//                    coinSound.start();
//                    coin1.setVisibility(View.INVISIBLE);
//                    newCoins++;
//                    if (newCoins < 10) {
//                        coinScore.setText("    " + newCoins);
//                    } else if (newCoins < 100) {
//                        coinScore.setText("  " + newCoins);
//                    } else {
//                        coinScore.setText("" + newCoins);
//                    }
//                }
//
//            }
//
//            if (ob1.getX() > obStopX) {
//                ob1.setX(ob1.getX() - speed);
//                ob2.setX(ob2.getX() - speed);
//
//            } else {
//                ob12();
//
//            }
//
//            Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    updateOb12();
//                }
//            }, 10);
//        }
//    }
//
//    public void ob34() {
//        scoreOb3 = 1;
//        speed += 0.25;
//        ob3.setVisibility(View.VISIBLE);
//        ob4.setVisibility(View.VISIBLE);
//        ob3.setY(obPosition());
//        ob4.setY(ob3.getY() - ob3.getHeight() - pig.getHeight() - (screenHeight / 8));
//        ob3.setX(obSpawnX);
//        ob4.setX(obSpawnX);
//        if (isCoin()) {
//            coin2.setY(ob3.getY() - pig.getHeight() - (screenHeight / 8) + coin1.getHeight() / 1.5f);
//            coin2.setX(ob3.getX());
//            coin2.setVisibility(View.VISIBLE);
//        }
//    }
//
//    public void updateOb34() {
//
//        if (run == 1) {
//            coin2.setX(ob3.getX() + (float) ob3.getWidth() / 2 - (float) coin2.getWidth() / 2);
//            if (ob3.getVisibility() == View.VISIBLE) {
//
//                if (pig.getX() + pig.getWidth() > ob3.getX()
//                        && pig.getX() < ob3.getX() + ob3.getWidth()) {
//                    if (scoreOb3 == 1) {
//                        if (pig.getX() + pig.getWidth() > ob3.getX() + ob3.getWidth()) {
//                            scoreOb3 = 2;
//                            score++;
//                            scorer.setText("" + score);
//                        }
//                    }
//                    if (pig.getY() + pig.getHeight() > ob3.getY() || pig.getY() < ob4.getY() + ob4.getHeight()) {
//                        gameOver();
//                    }
//                    if (pig.getX() + pig.getWidth() >= coin2.getX()
//                            && pig.getX() < coin2.getX() + coin2.getWidth()
//                            && pig.getY() + pig.getHeight() > coin2.getY()
//                            && pig.getY() < coin2.getY() + coin2.getHeight()
//                            && coin2.getVisibility() == View.VISIBLE) {
//                        coinSound.start();
//                        coin2.setVisibility(View.INVISIBLE);
//                        newCoins++;
//                        if (newCoins < 10) {
//                            coinScore.setText("    " + newCoins);
//                        } else if (newCoins < 100) {
//                            coinScore.setText("  " + newCoins);
//                        } else {
//                            coinScore.setText("" + newCoins);
//                        }
//                    }
//                }
//            }
//            if (ob3.getX() > obStopX) {
//                ob3.setX(ob3.getX() - speed);
//                ob4.setX(ob4.getX() - speed);
//
//            } else {
//                ob34();
//
//            }
//            Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    updateOb34();
//                }
//            }, 10);
//        }
//    }
//
//    public void ob56() {
//        scoreOb5 = 1;
//        speed += 0.25;
//        ob5.setVisibility(View.VISIBLE);
//        ob6.setVisibility(View.VISIBLE);
//        ob5.setY(obPosition());
//        ob6.setY(ob5.getY() - ob6.getHeight() - pig.getHeight() - (screenHeight / 8));
//        ob5.setX(obSpawnX);
//        ob6.setX(obSpawnX);
//        if (isCoin()) {
//            coin3.setY(ob5.getY() - pig.getHeight() - (screenHeight / 8) + coin1.getHeight() / 1.5f);
//            coin3.setX(ob5.getX());
//            coin3.setVisibility(View.VISIBLE);
//        }
//
//    }
//
//    public void updateOb56() {
//        if (run == 1) {
//            coin3.setX(ob5.getX() + (float) ob5.getWidth() / 2 - (float) coin3.getWidth() / 2);
//            if (ob5.getVisibility() == View.VISIBLE) {
//                if (pig.getX() + pig.getWidth() > ob5.getX()
//                        && pig.getX() < ob5.getX() + ob5.getWidth()) {
//                    if (scoreOb5 == 1) {
//                        if (pig.getX() + pig.getWidth() > ob5.getX() + ob5.getWidth()) {
//                            scoreOb5 = 2;
//                            score++;
//                            scorer.setText("" + score);
//                        }
//                    }
//                    if (pig.getY() + pig.getHeight() > ob5.getY() || pig.getY() < ob6.getY() + ob6.getHeight()) {
//                        gameOver();
//
//                    }
//                    if (pig.getX() + pig.getWidth() >= coin3.getX()
//                            && pig.getX() < coin3.getX() + coin3.getWidth()
//                            && pig.getY() + pig.getHeight() > coin3.getY()
//                            && pig.getY() < coin3.getY() + coin3.getHeight()
//                            && coin3.getVisibility() == View.VISIBLE) {
//                        coinSound.start();
//                        coin3.setVisibility(View.INVISIBLE);
//                        newCoins++;
//                        if (newCoins < 10) {
//                            coinScore.setText("    " + newCoins);
//                        } else if (newCoins < 100) {
//                            coinScore.setText("  " + newCoins);
//                        } else {
//                            coinScore.setText("" + newCoins);
//                        }
//                    }
//                }
//            }
//            if (ob5.getX() > obStopX) {
//                ob5.setX(ob5.getX() - speed);
//                ob6.setX(ob6.getX() - speed);
//
//            } else {
//                ob56();
//
//            }
//            Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    updateOb56();
//                }
//            }, 10);
//        }
//    }
//
//    public void ob78() {
//        scoreOb7 = 1;
//        speed += 0.25;
//        ob7.setVisibility(View.VISIBLE);
//        ob8.setVisibility(View.VISIBLE);
//        ob7.setY(obPosition());
//        ob8.setY(ob7.getY() - ob8.getHeight() - pig.getHeight() - (screenHeight / 8));
//        ob7.setX(obSpawnX);
//        ob8.setX(obSpawnX);
//        if (isCoin()) {
//            coin4.setY(ob7.getY() - pig.getHeight() - (screenHeight / 8) + coin1.getHeight() / 1.5f);
//            coin4.setX(ob7.getX());
//            coin4.setVisibility(View.VISIBLE);
//        }
//
//    }
//
//    public void updateOb78() {
//        if (run == 1) {
//            coin4.setX(ob7.getX() + (float) ob7.getWidth() / 2 - (float) coin4.getWidth() / 2);
//            if (ob7.getVisibility() == View.VISIBLE) {
//                if (pig.getX() + pig.getWidth() > ob7.getX()
//                        && pig.getX() < ob7.getX() + ob7.getWidth()) {
//                    if (scoreOb7 == 1) {
//                        if (pig.getX() + pig.getWidth() > ob7.getX() + ob7.getWidth()) {
//                            scoreOb7 = 2;
//                            score++;
//                            scorer.setText("" + score);
//                        }
//                    }
//                    if (pig.getY() + pig.getHeight() > ob7.getY() || pig.getY() < ob8.getY() + ob8.getHeight()) {
//                        gameOver();
//
//                    }
//                    if (pig.getX() + pig.getWidth() >= coin4.getX()
//                            && pig.getX() < coin4.getX() + coin4.getWidth()
//                            && pig.getY() + pig.getHeight() > coin4.getY()
//                            && pig.getY() < coin4.getY() + coin4.getHeight()
//                            && coin4.getVisibility() == View.VISIBLE) {
//                        coinSound.start();
//                        coin4.setVisibility(View.INVISIBLE);
//                        newCoins++;
//                        if (newCoins < 10) {
//                            coinScore.setText("    " + newCoins);
//                        } else if (newCoins < 100) {
//                            coinScore.setText("  " + newCoins);
//                        } else {
//                            coinScore.setText("" + newCoins);
//                        }
//                    }
//                }
//            }
//            if (ob7.getX() > obStopX) {
//                ob7.setX(ob7.getX() - speed);
//                ob8.setX(ob8.getX() - speed);
//
//            } else {
//                ob78();
//
//            }
//            Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    updateOb78();
//                }
//            }, 10);
//        }
//
//    }
//
//    public boolean isCoin() {
//        return new Random().nextInt(2) == 1;
//    }
//
//    public float obPosition() {
//        return (new Random().nextInt(80 - 20) + 20) * screenHeight / 100;
//    }
//
//    public void pause() {
//        if (run == 1) {
//            run = 2;
//            pause.setBackgroundResource(R.drawable.play);
//            backtrack.pause();
//        } else {
//            run = 1;
//            pause.setBackgroundResource(R.drawable.pause);
//            backtrack.start();
//            updateOb12();
//            updateOb34();
//            updateOb56();
//            updateOb78();
//            flipCard();
//        }
//    }
//
//    public void showStats() {
//        statsDialog.setContentView(R.layout.stats);
//        closeDialog = statsDialog.findViewById(R.id.closeStats);
//        statsHighScore = statsDialog.findViewById(R.id.hs);
//        statsTotalScore = statsDialog.findViewById(R.id.ts);
//        statsAverage = statsDialog.findViewById(R.id.avg);
//        statsGamesPlayed = statsDialog.findViewById(R.id.gp);
//        statsCoins = statsDialog.findViewById(R.id.coins);
//        statsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        statsHighScore.setText("HIGH SCORE - " + highScore);
//        statsCoins.setText("COINS - " + coins);
//        statsAverage.setText("AVERAGE - " + average);
//        statsTotalScore.setText("TOTAL SCORE - " + totalScore);
//        statsGamesPlayed.setText("GAMES PLAYED - " + playedGames);
//        closeDialog.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                statsDialog.dismiss();
//            }
//
//        });
//        statsDialog.show();
//    }
//    public void showShop(){
//        shopDialog.setContentView(R.layout.shop);
//        closeDialog = shopDialog.findViewById(R.id.closeShop);
//        shopCoins = shopDialog.findViewById(R.id.shopCoins);
//        if (coins < 10) {
//            shopCoins.setText("    " + coins);
//        } else if (coins < 100) {
//            shopCoins.setText("  " + coins);
//        } else {
//            shopCoins.setText("" + coins);
//        }
//        closeDialog.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                shopDialog.dismiss();
//            }
//        });
//        shopDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        shopDialog.getWindow().setLayout((int) ((int) screenWidth * 0.85f), (int) ((int) screenHeight * 0.85f));
//        shopDialog.show();
//    }
//}