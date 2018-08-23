package hssc.androidview.utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by sks on 2015/8/27.
 */
public class ToastUtil {
    /** ============== Toast ============ **/
    public static void showShort(Context context,String msg){
        if (context == null){
            return;
        }
        if (TextUtils.isEmpty(msg)){
            return;
        }
        if (AppUtils.isLetter(msg)){
            return;
        }
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    public static void showShort(Context context,@StringRes int resId){
        Toast toast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
    public static void showLong(Context context,@StringRes int resId){
        Toast toast = Toast.makeText(context, resId, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
    public static void showLong(Context context,String msg){
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        toast.cancel();
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    public void lightOff(Activity context){
        WindowManager.LayoutParams lp=context.getWindow().getAttributes();
        lp.alpha=0.3f;
        context.getWindow().setAttributes(lp);
    }

    public void lightOn(Activity context){
        WindowManager.LayoutParams lp=context.getWindow().getAttributes();
        lp.alpha=1.0f;
        context.getWindow().setAttributes(lp);
    }


    //防止连续快速点击
    private static long lastClickTime;
    public static boolean isFastDoubleClick() {
            long time = System.currentTimeMillis();
            long timeD = time - lastClickTime;
            if ( 0 < timeD && timeD < 1000) {
                return true;
            }
            lastClickTime = time;
            return false;
        }
}
