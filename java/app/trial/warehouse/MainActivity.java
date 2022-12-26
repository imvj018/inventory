package app.trial.warehouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {
    ImageView background;
    TextView text1, text2, text3;
    LottieAnimationView animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        background = findViewById(R.id.whousebg);
        text1 = findViewById(R.id.title1);
        text2 = findViewById(R.id.title2);
        text3 = findViewById(R.id.title3);
        animation = findViewById(R.id.anim);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            background.animate().translationY(-2400).setDuration(1000).setStartDelay(4000);
            text1.animate().translationY(-2400).setDuration(1200).setStartDelay(4000);
            text2.animate().translationX(-2400).setDuration(1200).setStartDelay(4000);
            text3.animate().translationX(2400).setDuration(1200).setStartDelay(4000);
            animation.animate().translationY(1800).setDuration(1000).setStartDelay(4000);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                Intent intent = new Intent(MainActivity.this, app.trial.warehouse.gr.creategr.class);
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
        }, 5150);
    }
}