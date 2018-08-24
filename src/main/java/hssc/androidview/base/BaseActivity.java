package hssc.androidview.base;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

//import butterknife.ButterKnife;
import hssc.androidview.R;
import hssc.androidview.utils.AppUtils;
import hssc.androidview.utils.StatusBarCompat;
import hssc.androidview.utils.ToastUtil;

/**
 * Created by Administrator on 2016/4/8.
 */
public abstract class BaseActivity extends AppCompatActivity{
    public static String TAG = "BaseActivity";
    public abstract Class getTag();
    private int statusColor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doBeforeContentView();
        setContentView(getLayoutId());
//        ButterKnife.inject(this);
        TAG = getTag().getSimpleName().toString();
        Log.e(TAG,"=====onCreate=====");

        initView();
        initData();
        initEvents();
    }

//    protected void onCreate(Bundle savedInstanceState, @LayoutRes int layoutResID) {
//        super.onCreate(savedInstanceState);
//        doBeforeContentView();
//        setContentView(layoutResID);
//        ButterKnife.bind(this);
//        TAG = getTag().getSimpleName().toString();
//
//        initView();
//        initData();
//        initEvents();
//    }


    public void setStatusColor(int statusColor) {
        this.statusColor = statusColor;
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, statusColor));
    }

    public abstract int getStatusBarColor();
    public abstract int getLayoutId();
    /** 初始化控件及赋值操作 */
    public abstract void initView();
    /** 初始化变量 */
    public abstract void initData();
    /** 事件 */
    public abstract void initEvents();

    /* 获取状态栏高度
    * @param context
    * @return
            */
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        ButterKnife.reset(this);
    }
    public void doBeforeContentView(){
        //设置昼夜主题
//        initTheme();
        // 把actvity放到application栈中管理
        AppManager.getAppManager().addActivity(this);
        // 无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // 默认着色状态栏
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, getStatusBarColor()));
    }

    public void setTranslucent(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
//        StatusBarCompat.translucentStatusBar(this);
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


    public void setTag(Class className){
        this.TAG = className.getClass().getName();
    }

    public void fullscreen(){
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 隐藏应用程序的标题栏，即当前activity的标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
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

//    public SlidingMenu setMenu(View menuView) {
//        SlidingMenu slidingMenu = new SlidingMenu(this);
//        slidingMenu.setMenu(menuView);
//        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
//        slidingMenu.setBehindWidth((int) (AppUtils.getWidth(this) * 0.17));
//        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
//        return slidingMenu;
//    }

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
        return TextUtils.isEmpty(getText(editText));
    }

    /**
     * 判断List是否为空
     * @param sourceList
     * @return
     */
    public <V> boolean isEmpty(List<V> sourceList){
        return (sourceList == null || sourceList.size() == 0);
    }

    public void showToast(String str){
        ToastUtil.showShort(this, str);
    }

    public void showToast(int resId){
        ToastUtil.showShort(this,resId);
    }
    public void showToastL(String str){
        ToastUtil.showLong(this,str);
    }
    public void showToastL(int resId){
        ToastUtil.showLong(this,resId);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
