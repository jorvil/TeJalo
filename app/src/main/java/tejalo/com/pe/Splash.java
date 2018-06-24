package tejalo.com.pe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final ImageView img = (ImageView) findViewById(R.id.imgLogo);
        final Animation ani = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate);
        final Animation ani2 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.abc_fade_out);

        img.startAnimation(ani);
        ani.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                img.startAnimation(ani2);
                finish();
                Intent intent = new Intent(getBaseContext(), Login.class);
                startActivity(intent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

        });
    }

}