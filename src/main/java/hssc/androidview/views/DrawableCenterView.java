package hssc.androidview.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/4/13.
 */
public class DrawableCenterView extends TextView{
    public DrawableCenterView(Context context) {
        super(context);
    }

    public DrawableCenterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawableCenterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable[] drawables = getCompoundDrawables();
        if (drawables != null) {
            Drawable drawableLeft = drawables[0];
            Drawable drawableRight = drawables[2];

            float textWidth = 0;
            if (!TextUtils.isEmpty(getText().toString())) {
                textWidth = getPaint().measureText(getText().toString());
            }
            int drawablePadding = getCompoundDrawablePadding();
            int drawableWidth = 0;
            float bodyWidth = 0;

            if (drawableLeft == null) {
                drawableWidth = drawableRight.getIntrinsicWidth();
                bodyWidth = textWidth + drawableWidth + drawablePadding;
                setPadding(0, 0, (int) (getWidth() - bodyWidth), 0);
            } else {
                drawableWidth = drawableLeft.getIntrinsicWidth();
                bodyWidth = textWidth + drawableWidth + drawablePadding;
            }
            canvas.translate((getWidth() - bodyWidth) / 2, 0);
            super.onDraw(canvas);
        }
    }
}
