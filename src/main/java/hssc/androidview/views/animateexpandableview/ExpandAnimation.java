package hssc.androidview.views.animateexpandableview;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by sks on 2016/1/20.
 */
public class ExpandAnimation extends Animation {
    private int baseHeight;
    private int delta;
    private View view;
    private GroupInfo groupInfo;

    public ExpandAnimation(View v, int startHeight, int endHeight, GroupInfo info) {
        baseHeight = startHeight;
        delta = endHeight - startHeight;
        view = v;
        groupInfo = info;

        view.getLayoutParams().height = startHeight;
        view.requestLayout();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        if (interpolatedTime < 1.0f) {
            int val = baseHeight + (int) (delta * interpolatedTime);
            view.getLayoutParams().height = val;
            groupInfo.dummyHeight = val;
            view.requestLayout();
        } else {
            int val = baseHeight + delta;
            view.getLayoutParams().height = val;
            groupInfo.dummyHeight = val;
            view.requestLayout();
        }
    }
}
