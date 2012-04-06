package gre.pojo.activities.animations.flip;

import android.view.View;
import android.view.animation.Animation;


public final class DisplayNextView implements Animation.AnimationListener {
private boolean mCurrentView;
View v1;
View v2;

public DisplayNextView(boolean currentView, View v1, View v2) {
mCurrentView = currentView;
this.v1 = v1;
this.v2 = v2;
}

public void onAnimationStart(Animation animation) {
}

public void onAnimationEnd(Animation animation) {
v1.post(new SwapViews(mCurrentView, v1, v2));
}

public void onAnimationRepeat(Animation animation) {
}
}
