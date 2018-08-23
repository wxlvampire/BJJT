package hssc.androidview.views;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by sks on 2015/11/11.
 */
public class CountDownView extends CountDownTimer{
    /**
     * * @param millisInFuture
     * *
     * 表示以毫秒为单位 倒计时的总数
     * *
     * *
     * 例如 millisInFuture=1000 表示1秒
     * *
     * * @param countDownInterval
     * *
     * 表示 间隔 多少微秒 调用一次 onTick 方法
     * *
     * *
     * 例如: countDownInterval =1000 ; 表示每1000毫秒调用一次onTick()
     * *
     */
    private TextView textView;
    private boolean isFinish;
    private Context context;

    private String startContent;
    private String finishContent;

    public CountDownView(long millisInFuture, long countDownInterval,TextView textView) {
        super(millisInFuture, countDownInterval);
        this.textView = textView;
//        this.context = context;
    }

    public CountDownView(TextView textView) {
        super(10 * 1000, 1000);
        this.textView = textView;
//        this.context = context;
    }

    public void setStartContent(String startContent) {
        this.startContent = startContent;
    }

    public void setFinishContent(String finishContent) {
        this.finishContent = finishContent;
    }

    @Override
    public void onFinish() {
        textView.setText(startContent);
        textView.setClickable(true);
        isFinish = true;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        textView.setText(finishContent+"(" + millisUntilFinished / 1000 + ")");
        textView.setClickable(false);
    }
    public boolean isFinish(){
        return isFinish;
    }
}
