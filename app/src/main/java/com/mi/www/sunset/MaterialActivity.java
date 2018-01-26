package com.mi.www.sunset;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MaterialActivity extends AppCompatActivity {
    private TextView mTextView;
    private ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material);
        mTextView = findViewById(R.id.tv_circular);
        mImageView = findViewById(R.id.iv_small);

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //找到点击坐标
                int[] clickCoords = new int[2];
                view.getLocationOnScreen(clickCoords);
                clickCoords[0] += view.getWidth() / 2;
                clickCoords[1] += view.getHeight() / 2;
                performRevealAnimation(mTextView, clickCoords[0], clickCoords[1]);
            }
        });

        //shared element transition
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ViewCompat.setTransitionName(view, "transitionImage");
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(MaterialActivity.this,
                                view, "transitionImage");
                Intent intent = new Intent(MaterialActivity.this, SharedElementTransitionActivity.class);
                startActivity(intent, optionsCompat.toBundle());
            }
        });

    }

    private void performRevealAnimation(TextView textView, int clickCoord, int clickCoord1) {
        int[] animateViewCoords = new int[2];
        textView.getLocationOnScreen(animateViewCoords);
        int centerX = clickCoord - animateViewCoords[0];
        int centerY = clickCoord1 - animateViewCoords[1];

        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int maxRadius = size.y;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewAnimationUtils.createCircularReveal(mTextView, centerX, centerY, 0, maxRadius)
            .setDuration(2000)
            .start();
        }
    }
}
