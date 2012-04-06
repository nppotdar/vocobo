package gre.pojo.activities.animations.flip;
import android.view.View;
import android.view.animation.DecelerateInterpolator;


public final class SwapViews implements Runnable {
private boolean mIsFirstView;
View v1;
View v2;

public SwapViews(boolean isFirstView, View v1, View v2) {
 mIsFirstView = isFirstView;
 this.v1 = v1;
 this.v2 = v2;
}

public void run() {
 final float centerX = v1.getWidth() / 2.0f;
 final float centerY = v1.getHeight() / 2.0f;
 Flip3dAnimation rotation;

 if (mIsFirstView) {
  v1.setVisibility(View.GONE);
  v2.setVisibility(View.VISIBLE);
  v2.requestFocus();

     rotation = new Flip3dAnimation(-90, 0, centerX, centerY);
 } else {
  v2.setVisibility(View.GONE);
  v1.setVisibility(View.VISIBLE);
  v1.requestFocus();

     rotation = new Flip3dAnimation(90, 0, centerX, centerY);
 }

 rotation.setDuration(500);
 rotation.setFillAfter(true);
 rotation.setInterpolator(new DecelerateInterpolator());

 if (mIsFirstView) {
  v2.startAnimation(rotation);
 } else {
  v1.startAnimation(rotation);
 }
}
}