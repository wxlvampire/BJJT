package hssc.androidview.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import hssc.androidview.utils.StatusBarCompat;
import hssc.androidview.utils.SystemBarTintManager;
import hssc.androidview.utils.ToastUtil;

/**
 * Created by Administrator on 2016/4/8.
 */
public abstract class BaseFragment extends Fragment {
    public static String TAG = "BaseActivity";
    public abstract Class getLogTag();

    /**
     * Fragment加载的View
     */
    public View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 在Fragment onCreateView方法中缓存View
        if(view == null){
            view = bootView(inflater, container, savedInstanceState);
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if(parent != null){
            parent.removeView(view);
        }
        TAG = getLogTag().getSimpleName().toString();

//        setTransparentBar();

        initView();
        initData();
        initEvents();
        return view;
    }

    private void setTransparentBar(){
        getActivity().getWindow() .addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }
    public void setStatusBar(int color){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(getActivity());
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(color);//通知栏所需颜色
    }
    @TargetApi(19)
    public void setTranslucentStatus(boolean on) {
        Window win = getActivity().getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
    /**
     * 初始化View
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public abstract View bootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
    /** 初始化控件及赋值操作 */
    public abstract void initView();
    /** 初始化变量 */
    public abstract void initData();
    /** 事件 */
    public abstract void initEvents();
    /**
     * 弹出Toast提示
     * @param msg		提示内容
     */
    public void showToast(String msg){
        ToastUtil.showShort(getActivity(), msg);
    }

    /**
     * 弹出Toast提示
     * @param resId		资源Id
     */
    public void showToast(@StringRes int resId){
        showToast(getString(resId));
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


    /** ============== String处理 非空判断 ============ **/
    /**
     * 判断字符串是否不为空
     * @param str
     * @return
     */
    public boolean isNotEmpty(CharSequence str){
        return !isEmpty(str);
    }

    /**
     * 判断EditText对象是否不为空
     * @param editText
     * @return
     */
    public boolean isNotEmpty(EditText editText){
        return !isEmpty(editText);
    }

    /**
     * 获取TextView的文本信息
     * @param textView
     * @return
     */
    public String getText(TextView textView){
        if(textView == null){
            throw new NullPointerException("TextView 对象为空");
        }
        return textView.getText().toString().trim();
    }

    /**
     * 获取EditText的文本信息
     * @param editText
     * @return
     */
    public String getText(EditText editText){
        if(editText == null){
            throw new NullPointerException("EditText 对象为空");
        }
        return editText.getText().toString().trim();
    }

    public String getText(Button btn){
        if(btn == null){
            throw new NullPointerException("Button 对象为空");
        }
        return btn.getText().toString().trim();
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

    /**
     * 根据类打开Activity,并携带参数
     * @param clazz
     * @param bundle
     * @param requestCode
     */
    public void startActivity(Class<?> clazz, Bundle bundle, int requestCode) {
        Intent intent = new Intent(getActivity(), clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (requestCode == 0) {
            startActivity(intent);
        } else {
            startActivityForResult(intent, requestCode);
        }
    }
}
