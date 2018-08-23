package hssc.androidview.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import java.util.ArrayList;

import hssc.androidview.R;
import hssc.androidview.utils.interfaces.OnDismissListener;

/**
 * Created by li on 2016/8/14.
 */
public class PopUtils {
    public static final int TOP = 1;
    public static final int BOTTOM = 2;
    private Activity context;
    private PopupWindow popupWindow;
    private int showStyle = BOTTOM;
    public OnDismissListener onDismissListener;
    private int marginRight;
    private int marginLeft;
    private int marginTop;
    private int marginBottom;
    private int popHeight;

    public PopUtils(Activity context) {
        this.context = context;
    }
    public void setShowStyle(int showStyle) {
        this.showStyle = showStyle;
    }

    public void setPopHeight(int popHeight) {
        this.popHeight = popHeight;
    }

    public void setMargin(int marginLeft, int marginTop, int marginRight, int marginBottom){
        this.marginLeft = marginLeft;
        this.marginTop = marginTop;
        this.marginRight = marginRight;
        this.marginBottom = marginBottom;
    }

    public void initPop(View contentView, int background){
        if (popHeight == 0){
            popHeight =  LinearLayout.LayoutParams.WRAP_CONTENT;
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,popHeight);
        layoutParams.setMargins(marginLeft, marginTop,marginRight,marginBottom);
        popupWindow = new PopupWindow(contentView, layoutParams.width,layoutParams.height);
//        popupWindow.setWidth(AppUtils.getWidth(context));
//        popupWindow.setHeight(AppUtils.getHeight(context));

//        popupWindow.setWidth(contentView.getWidth());
//        popupWindow.setHeight(contentView.getHeight());
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        if (background == -1) {
            popupWindow.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.color_4c000000)));
        }else {
            popupWindow.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(background)));
        }
        popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        popupWindow.setAnimationStyle(showStyle == TOP ?0 : R.style.PopStype);
//        popupWindow.showAtLocation(parentView, Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 10, 10);
//        popupWindow.showAsDropDown(parentView);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (onDismissListener != null) {
                    onDismissListener.onDismiss();
                }
                lightOn();
            }
        });

    }

    public void lightOff(){
        WindowManager.LayoutParams lp=context.getWindow().getAttributes();
        lp.alpha=0.3f;
        context.getWindow().setAttributes(lp);
    }

    public void lightOn(){
        WindowManager.LayoutParams lp=context.getWindow().getAttributes();
        lp.alpha=1.0f;
        context.getWindow().setAttributes(lp);
    }
    /**
     * 底部
     * @param parentView
     * @return
     */
    public Button showPopFromBottom(View parentView){
        int location[] = new int[2];
//        int x, y;
//
//        parentView.getLocationOnScreen(location);
//        x = location[0];
//        y = location[1];
//        popupWindow.showAsDropDown(parentView,x,0);
        popupWindow.setWidth(AppUtils.getWidth(context));
        popupWindow.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);

        return null;
    }

    /**
     * 在某个控件的下方显示，横向的全屏
     * @param parentView
     * @param gravity
     */
    public void showAtLocation(View parentView,int gravity){
        int location[] = new int[2];
        int x, y;
        parentView.getLocationOnScreen(location);
        x = location[0];
        y = location[1];
        int h = parentView.getHeight();
        popupWindow.setWidth(AppUtils.getWidth(context));//使布局横向全屏显示（不设置距离屏幕左右会有间距）
        popupWindow.showAtLocation(parentView,gravity,x, h + y);
    }

    /**
     * 中间显示
     * @param parentView
     */
    public void showPopAsLocation(View parentView){
        popupWindow.showAtLocation(parentView, Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 10, 10);
    }

    /**
     * 在某个控件的下方显示
     */
    public void showPopAsDropDown(View parentView,int width){
        int[] location = new int[2];
        parentView.getLocationOnScreen(location);
        if (width != 0) {
            popupWindow.setWidth(width);
        }
        popupWindow.showAsDropDown(parentView);
    }

    public void dismissPop(){
        if (popupWindow != null && popupWindow.isShowing()){
            popupWindow.dismiss();
        }
    }
    public boolean isShow(){
        return popupWindow.isShowing();
    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

}
