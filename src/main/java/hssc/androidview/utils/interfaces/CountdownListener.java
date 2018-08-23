package hssc.androidview.utils.interfaces;

/**
 * 倒计时CountDown
 * Created by li on 2016/11/29.
 */
public interface CountdownListener {
    /**
     * 当倒计时开始
     */
    public void onStart();

    /**
     * 当倒计时结束
     */
    public void onFinish();

    /**
     * 更新
     * @param currentRemainingSeconds 剩余时间
     */
    public void onUpdate(int currentRemainingSeconds);
}
