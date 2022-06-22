package uni.fmi.bachelors.minesweeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity  {

    public static MainActivity instance;
    GameView gameView;
    MyCustomAdapter myCustomAdapter;
    RecyclerView recyclerView;
    ImageView smileIV;
    TextView bombsTV;
    Chronometer timeTV;
    TextView bannerTV;
    int height;
    int bombs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        height = bundle.getInt("height");
        bombs = bundle.getInt("bombs");
        instance = this;

        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.activity_main_grid);
        bombsTV = findViewById(R.id.numberOfBombsTextView);
        timeTV = (Chronometer) findViewById(R.id.timeChronometer);
        bannerTV = findViewById(R.id.bannerTextView);
        smileIV = findViewById(R.id.smileImageView);

        createGame();

        smileIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                smileIV.setImageResource(R.drawable.smile);
                bombsTV.setText("" + gameView.bombs);
                timeTV.stop();
                timeTV.setBase(SystemClock.elapsedRealtime());
                bannerTV.setText("");
                createGame();
            }
        });

    }

    public void createGame(){
        timeTV.start();
        gameView = new GameView(height, bombs);
        bombsTV.setText("" + gameView.bombs);
        recyclerView.setLayoutManager(new GridLayoutManager(this, gameView.height));
        myCustomAdapter = new MyCustomAdapter(gameView.cellGrid, gameView);
        recyclerView.setAdapter(myCustomAdapter);
    }

    public void onGameWon(){
        bannerTV.setText("Congrats, You Won!");
        timeTV.stop();
        //find new emoji for a win
    }

    public void onGameLost(){
        bannerTV.setText("Sorry, You Lost!");
        timeTV.stop();
        smileIV.setImageResource(R.drawable.sad);
    }

    public void updateBombTV(int bombs){
        bombsTV.setText(bombs + "");
    }


}