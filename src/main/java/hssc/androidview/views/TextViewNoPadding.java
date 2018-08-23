package hssc.androidview.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * hssc.androidview.views.TextViewNoPadding
 * Created by li on 2017/8/3.
 */
public class TextViewNoPadding extends TextView{
    Paint.FontMetricsInt fontMetricsInt;
    public TextViewNoPadding(Context context) {
        super(context);
    }

    public TextViewNoPadding(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextViewNoPadding(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.e("TextViewNoPadding","--onDraw---");
//        if (fontMetricsInt == null){
//            fontMetricsInt = new Paint.FontMetricsInt();
//            getPaint().getFontMetricsInt(fontMetricsInt);
//        }
//        //fontMetricsInt.top - fontMetricsInt.bottom
//        canvas.translate(0,fontMetricsInt.top - fontMetricsInt.ascent+1 );
//        setIncludeFontPadding(false);
//        super.onDraw(canvas);
        Rect targetRect = new Rect(50, 50, 1000, 200);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(3);
        paint.setTextSize(80);
//        String testString = "测试：ijkJQKA:1234";
        String testString = "测试：ijkJQKA:1234";
        paint.setColor(Color.CYAN);
        canvas.drawRect(targetRect, paint);
        paint.setColor(Color.RED);
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        // 转载请注明出处：http://blog.csdn.net/hursing
        int baseline = targetRect.top + (targetRect.bottom - targetRect.top - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        // 下面这行是实现水平居中，drawText对应改为传入targetRect.centerX()
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(testString, targetRect.centerX(), baseline, paint);
        super.onDraw(canvas);
    }

}
