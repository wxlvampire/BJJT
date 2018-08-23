package hssc.androidview.base;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

//import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;


import java.util.List;

import hssc.androidview.R;
import hssc.androidview.utils.AppUtils;
import hssc.androidview.utils.StatusBarCompat;
import hssc.androidview.utils.ToastUtil;

/**
 * Created by Administrator on 2016/4/8.
 */
public abstract class BaseFragmentActivity extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState, @LayoutRes int layoutResId) {
        super.onCreate(savedInstanceState);
        doBeforeContentView();
        setContentView(layoutResId);
//        setTranslucent();

        initView();
        initData();
        initEvents();
    }


    public abstract void doBeforeContentView();

    /** 初始化控件及赋值操作 */
    public abstract void initView();
    /** 初始化变量 */
    public abstract void initData();
    /** 事件 */
    public abstract void initEvents();

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setTranslucent(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
    @TargetApi(19)
    public void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


    /** ============== 打开Activity ============ **/
    /**
     * 根据类打开Activity
     * @param clazz
     */
    public void startActivity(Class<?> clazz) {
        startActivity(clazz, null, 0);
    }

    /**
     * 根据类打开Activity,并携带参数
     * @param clazz
     * @param bundle
     */
    public void startActivity(Class<?> clazz, Bundle bundle) {
        startActivity(clazz, bundle, 0);
    }

    /**
     * 根据类打开Activity,并携带参数
     * @param clazz
     * @param requestCode
     */
    public void startActivity(Class<?> clazz, int requestCode) {
        startActivity(clazz, null, requestCode);
    }

    public void initActionBar(int customViewId){
        View actionBarView = LayoutInflater.from(this).inflate(customViewId,null);
        ActionBar actionBar= getActionBar();
        actionBar.show();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(customViewId);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
    }

    public void fullscreen(){
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 隐藏应用程序的标题栏，即当前activity的标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

//    public SlidingMenu setMenu(View menuView) {
//        SlidingMenu slidingMenu = new SlidingMenu(this);
//        slidingMenu.setMenu(menuView);
//        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
//        slidingMenu.setBehindWidth((int) (AppUtils.getWidth(this) * 0.17));
//        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
//        return slidingMenu;
//    }

    /**
     * 根据类打开Activity,并携带参数
     * @param clazz
     * @param bundle
     * @param requestCode
     */
    public void startActivity(Class<?> clazz, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (requestCode == 0) {
            startActivity(intent);
        } else {
            startActivityForResult(intent, requestCode);
        }
    }
    /**============== 获取控件的文字 ============*/
    public String getText(TextView view){
        return getText(view.getText().toString().trim());
    }
    public String getText(EditText editText){
        return getText(editText.getText().toString().trim());
    }
    public String getText(Button btn){
        return getText(btn.getText().toString().trim());
    }
    private String getText(String str){
        return AppUtils.StringFilter(str);
    }

    /** ============== String处理 ============ **/
    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public boolean isEmpty(CharSequence str){
        return TextUtils.isEmpty(str);
    }

    /**
     * 判断EditText对象是否为空
     * @param editText
     * @return
     */
    public boolean isEmpty(EditText editText){
        if(editText == null){
            throw new NullPointerException("EditText 对象为空");
        }
        return TextUtils.isEmpty(editText.getText().toString().trim());
    }

    /**
     * 判断List是否为空
     * @param sourceList
     * @return
     */
    public <V> boolean isEmpty(List<V> sourceList){
        return (sourceList == null || sourceList.size() == 0);
    }

    public void showToast(Context context,String str){
        ToastUtil.showShort(context, str);
    }

    public void showToast(Context context,int resId){
        ToastUtil.showShort(context,resId);
    }
    public void showToastL(Context context,String str){
        ToastUtil.showLong(context,str);
    }
    public void showToastL(Context context,int resId){
        ToastUtil.showLong(context,resId);
    }
}
