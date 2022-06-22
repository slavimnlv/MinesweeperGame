package uni.fmi.bachelors.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;

public class LoginMenu extends AppCompatActivity {

    Button gameTree;
    Button gameSix;
    Button gameNine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_menu);

        gameSix = findViewById(R.id.buttonSix);
        gameNine = findViewById(R.id.buttonNine);

        gameSix.setOnClickListener(onClickListener);
        gameNine.setOnClickListener(onClickListener);

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Bundle bundle = new Bundle();
            Intent intent = new Intent(LoginMenu.this, MainActivity.class);

            switch (v.getId()){
                case R.id.buttonSix:

                    bundle.putInt("height", 6);
                    bundle.putInt("bombs", 5);
                    intent.putExtras(bundle);

                    break;
                case R.id.buttonNine:

                    bundle.putInt("height", 9);
                    bundle.putInt("bombs", 10);
                    intent.putExtras(bundle);

                    break;
            }
            startActivity(intent);
        }
    };
}