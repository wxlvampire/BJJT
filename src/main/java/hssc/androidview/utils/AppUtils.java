package hssc.androidview.utils;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Looper;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.TextAppearanceSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import hssc.androidview.utils.netutil.NetWorkUtil;


/**
 * Created by sks on 2015/8/20.
 */
public class AppUtils {

    public static void showToast(final String toast, final Context context)
    {
        new Thread(new Runnable() {

            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }).start();
    }
    public  static  void setWebView(WebView webView) {
        //声明WebSettings子类
        WebSettings webSettings = webView.getSettings();

        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        //设置自适应屏幕，两者合用
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webSettings.setDomStorageEnabled(true);

        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //其他细节操作
        if (AppUtils.noNetWork(webView.getContext())) {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//没网，则从本地获取，即离线加载
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);//根据cache-control决定是否从网络上取数据。
        }
        webSettings.setAppCacheEnabled(true);
        final String cachePath = webView.getContext().getApplicationContext().getDir("cache",Context.MODE_PRIVATE).getPath();
        webSettings.setAppCachePath(cachePath);
        webSettings.setAppCacheMaxSize(5*1024*1024);
//        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
    }

    /**
     * get app version code
     *
     * @param context
     * @return
     */
    public static int getAppVersionCode(Context context) {
        if (context != null) {
            PackageManager pm = context.getPackageManager();
            if (pm != null) {
                PackageInfo pi;
                try {
                    pi = pm.getPackageInfo(context.getPackageName(), 0);
                    if (pi != null) {
                        return pi.versionCode;
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return -1;
    }
    /**
     * 安装app
     * @param context
     * @param app
     */
    public static void installApp(Context context, String app) {
        InstallUtil.install(context, app);
    }

    public static SpannableStringBuilder setTextColor(String startStr,String text,int color){
        SpannableStringBuilder sp = new  SpannableStringBuilder(text);
        sp.setSpan(new ForegroundColorSpan(color), startStr.length(), text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
        return sp;
    }
    public static Spannable setTextSize(Context context,int style,int startIndex,int endIndex,String text){
        SpannableString styledText = new SpannableString(text);
        styledText.setSpan(new TextAppearanceSpan(context, style),startIndex,endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return styledText;
    }


    /**
     * 获取当前版本
     * @param packageName
     * @return
     */
    public static String getAppVersion(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packInfo;
        String version = null;
        try {
            packInfo = packageManager.getPackageInfo(packageName, 0);
            version = packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "0.0";
        }
        return version;
    }

    public static <V> boolean isEmptyList(List<V> list){
        return (list == null || list.size() == 0);
    }
    /**
     * 调用拨号界面
     * @param phone 电话号码
     */
    public static void call(Context context,String phone) {
        if (TextUtils.isEmpty(phone)){
            return;
        }
        Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    /**
     * 判断手机号
     * @param mobiles
     * @return
     */
    public static boolean isMobileNum(String mobiles) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(14[0-9])|(17[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        System.out.println(m.matches() + "---");
        return m.matches();
    }

    /**
     * 身份证号码验证
     * @param code
     * @return
     */
    public static boolean isIdCard(String code){
        String[] ValCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4",
                "3", "2" };
        String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
                "9", "10", "5", "8", "4", "2" };
        String Ai = "";
        boolean rightLength;
        // ================ 号码的长度 15位或18位 ================
        if (code.length() != 15 && code.length() != 18) {
           return false;
        }
        // ================ 数字 除最后以为都为数字 ================
        if (code.length() == 18) {
            Ai = code.substring(0, 17);
        } else if (code.length() == 15) {
            Ai = code.substring(0, 6) + "19" + code.substring(6, 15);
        }
        if (isNumeric(Ai) == false) {
            return false;
//            errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
        }

        // ================ 出生年月是否有效 ================
        String strYear = Ai.substring(6, 10);// 年份
        String strMonth = Ai.substring(10, 12);// 月份
        String strDay = Ai.substring(12, 14);// 月份
        if (isDataFormat(strYear + "-" + strMonth + "-" + strDay) == false) {
//            errorInfo = "身份证生日无效。";
            return false;
        }
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = s.parse(strYear + "-" + strMonth + "-" + strDay);
        } catch (ParseException e) {
            e.printStackTrace();
            date = null;
        }
        if (date != null) {
            if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
                    || (gc.getTime().getTime() - date.getTime()) < 0) {
//            errorInfo = "身份证生日不在有效范围。";
                return false;
            }
        }else {
            return false;
        }

        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
//            errorInfo = "身份证月份无效";
            return false;
        }
        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
//            errorInfo = "身份证日期无效";
            return false;
        }

        // ================ 地区码时候有效 ================
        Hashtable h = GetAreaCode();
        if (h.get(Ai.substring(0, 2)) == null) {
//            errorInfo = "身份证地区编码错误。";
            return false;
        }

        // ================ 判断最后一位的值 ================
        int TotalmulAiWi = 0;
        for (int i = 0; i < 17; i++) {
            TotalmulAiWi = TotalmulAiWi
                    + Integer.parseInt(String.valueOf(Ai.charAt(i)))
                    * Integer.parseInt(Wi[i]);
        }
        int modValue = TotalmulAiWi % 11;
        String strVerifyCode = ValCodeArr[modValue];
        Ai = Ai + strVerifyCode;

        if (code.length() == 18) {
            if (Ai.equals(code) == false) {
//                errorInfo = "身份证无效，不是合法的身份证号码";
                return false;
            }
        }
        return true;

    }
    public static void showInput(Activity context){
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
    /**
     * 隐藏软键盘
     * @param context
     */
    public static void hideInput(Activity context, View view){
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.showSoftInput(view,InputMethodManager.SHOW_FORCED);//强制显示键盘
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
    }

    /**
     * 判断软键盘是否显示
     * @param rootView 布局文件的根布局
     * @return
     */
    public static boolean isKeyboardShown(View rootView) {
        final int softKeyboardHeight = 100;
        Rect r = new Rect();
        rootView.getWindowVisibleDisplayFrame(r);
        DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
        int heightDiff = rootView.getBottom() - r.bottom;
        return heightDiff > softKeyboardHeight * dm.density;
    }

    /**
     * 功能：设置地区编码
     * @return Hashtable 对象
     */
    private static Hashtable GetAreaCode() {
        Hashtable hashtable = new Hashtable();
        hashtable.put("11", "北京");
        hashtable.put("12", "天津");
        hashtable.put("13", "河北");
        hashtable.put("14", "山西");
        hashtable.put("15", "内蒙古");
        hashtable.put("21", "辽宁");
        hashtable.put("22", "吉林");
        hashtable.put("23", "黑龙江");
        hashtable.put("31", "上海");
        hashtable.put("32", "江苏");
        hashtable.put("33", "浙江");
        hashtable.put("34", "安徽");
        hashtable.put("35", "福建");
        hashtable.put("36", "江西");
        hashtable.put("37", "山东");
        hashtable.put("41", "河南");
        hashtable.put("42", "湖北");
        hashtable.put("43", "湖南");
        hashtable.put("44", "广东");
        hashtable.put("45", "广西");
        hashtable.put("46", "海南");
        hashtable.put("50", "重庆");
        hashtable.put("51", "四川");
        hashtable.put("52", "贵州");
        hashtable.put("53", "云南");
        hashtable.put("54", "西藏");
        hashtable.put("61", "陕西");
        hashtable.put("62", "甘肃");
        hashtable.put("63", "青海");
        hashtable.put("64", "宁夏");
        hashtable.put("65", "新疆");
        hashtable.put("71", "台湾");
        hashtable.put("81", "香港");
        hashtable.put("82", "澳门");
        hashtable.put("91", "国外");
        return hashtable;
    }

    public static String[] strToArray(String content){
        if (TextUtils.isEmpty(content)){
            return null;
        }
        String[]  strArray  = content.split(",");
        if (strArray.length < 0){
            return null;
        }
        return strArray;
    }

    /**验证日期字符串是否是YYYY-MM-DD格式
     * @param str
     * @return
     */
    public static boolean isDataFormat(String str){
        boolean flag=false;
        //String regxStr="[1-9][0-9]{3}-[0-1][0-2]-((0[1-9])|([12][0-9])|(3[01]))";
        String regxStr="^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
        Pattern pattern1=Pattern.compile(regxStr);
        Matcher isNo=pattern1.matcher(str);
        if(isNo.matches()){
            flag=true;
        }
        return flag;
    }
    public static String formatDate(long time,String format){
        Date date = new Date(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    /**
     * 日期转成时间戳
     * @param time
     * @return
     */
    public static String formatData(String time,String format) {
        SimpleDateFormat sdr = new SimpleDateFormat(format, Locale.CHINA);
        Date date;
        String times = "0";
        try {
            date = sdr.parse(time);
            long l = date.getTime();
            String stf = String.valueOf(l);
            if (stf.length() >= 13) {
                times = stf.substring(0, 13);
            }else {
                times = stf;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ApppUtils","==Exception==="+e.getMessage());
        }
        return times;
    }

    /**
     * 计算时间差
     *
     * @param starTime
     *            开始时间
     * @param endTime
     *            结束时间
     *            返回类型 ==1----天，时，分。 ==2----时
     * @return 返回时间差
     */
    public static String getTimeDiffrence(long starTime, long endTime) {
        String timeString = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
//            Date parse = dateFormat.parse(starTime);
//            Date parse1 = dateFormat.parse(endTime);
            Date parse = new Date(starTime);
            Date parse1 = new Date(endTime);

            long diff = parse1.getTime() - parse.getTime();

            long day = diff / (24 * 60 * 60 * 1000);
            long hour = (diff / (60 * 60 * 1000) - day * 24);
            long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long s = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            long ms = (diff - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000
                    - min * 60 * 1000 - s * 1000);
//            Log.e("AppUtils",day + "天" + hour + "小时" + min + "分" + s +
//             "秒");
            long hour1 = diff / (60 * 60 * 1000);
            String hourString = hour1 + "";
//            long min1 = ((diff / (60 * 1000)) - hour1 * 60);
            long min1 = (diff / (60 * 1000));
            long s1 = diff / 1000;

            if (day > 0){
                return day+"天前";
            }
            if (hour > 0){
                return hour1+"小时前";
            }
            if (min1 > 0){
                return min1+"分钟前";
            }
            if (s1> 0){
                return s1+"秒前";
            }
//            timeString = hour1 + "小时" + min1 + "分";
//            Log.e("AppUtils",day + "天" + hour + "小时" + min + "分" + s +
//                    "秒");

        } catch (Exception e) {
            Log.e("AppUtils","----getTimeDiffrence---"+e.getMessage());
            e.printStackTrace();
        }
        return timeString;

    }

    /**
     * 功能：判断字符串是否为数字
     * @param str
     * @return
     */
    private static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否是电话号
     * @param phoneNumber
     * @return
     */

    public static boolean isPhone(String phoneNumber) {
        boolean isValid = false;
	   /*
	    * 可接受的电话格式有：
	    */
        String expression = "^(010|02\\d|0[3-9]\\d{2})?\\d{6,8}$";
//	   String str= "^(010|02\\d|0[3-9]\\d{2})?\\d{6,8}$";
        /*
	    * 可接受的电话格式有：
	    */
        String expression2 = "^\\(?(\\d{3})\\)?[- ]?(\\d{4})[- ]?(\\d{4})$";
        CharSequence inputStr = phoneNumber;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);

        Pattern pattern2 = Pattern.compile(expression2);
        Matcher matcher2 = pattern2.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    /***
     * 验证邮箱
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }

    /**
     * 过滤用户的输入内容
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    public static String StringFilter(String str) throws PatternSyntaxException {
//        String regEx = "[/\\:*?<>|\"\n\t]"; //要过滤掉的字符
        String regEx = "[`~!#$%^&*()+=|{}':;',\\\\[\\\\]<>/?~！#￥%……&*（）——+|{}【】‘；：“”’。，、？]"; //要过滤掉的字符
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
//        if (TextUtils.isEmpty(m.replaceAll("").trim())){
//            return null;
//        }
//        Log.e("AppUtils","---m.replaceAll.trim()---"+m.replaceAll("").trim());
        return m.replaceAll("").trim();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (spValue * scale + 0.5f);
    }

    /**
     * 获取屏幕的宽度
     * @return
     */
    public static int getWidth(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return manager.getDefaultDisplay().getWidth();
    }

    public static int getHeight(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return manager.getDefaultDisplay().getHeight();
    }


    public static boolean isServiceWork(Context context, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(40);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }

    public static final int LEFT = 1;
    public static final int TOP = LEFT+1;
    public static final int RIGHT = TOP+1;
    public static final int BOTTOM = RIGHT+1;

    public static void setDrawable(Context context, TextView button, int pic, int loc,float padding,int color) {
        Drawable dra_money = context.getResources().getDrawable(pic);
        dra_money.setBounds(0, 0, dra_money.getMinimumWidth(), dra_money.getMinimumHeight());
        switch (loc){
            case LEFT:
                button.setCompoundDrawables(dra_money, null, null, null); //设置上图标
                break;
            case TOP:
                button.setCompoundDrawables(null, dra_money, null, null); //设置上图标
                break;
            case RIGHT:
                button.setCompoundDrawables(null, null, dra_money, null); //设置上图标
                break;
            case BOTTOM:
                button.setCompoundDrawables(null, null, null, dra_money); //设置上图标
                break;
        }
        button.setCompoundDrawablePadding((int) padding);
        if (color != 0) {
            button.setTextColor(context.getResources().getColor(color));
        }
    }

    /**
     * 判断某个app是否安装
     * @param context
     * @param packageName
     * @return
     */

    public static boolean isAppInstalled(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        List<String> pName = new ArrayList<String>();
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);
    }

    // 获取imei号
    public static String getImei(Context context) {
        TelephonyManager mTm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return (mTm.getDeviceId() == null) ? "" : mTm.getDeviceId();
    }

     public static String getMacAddress(Context context) {
        // 获取mac地址：
         String macAddress = "";
         try {
            WifiManager wifiMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = (null == wifiMgr ? null : wifiMgr.getConnectionInfo());
            if (null != info) {
                if (!TextUtils.isEmpty(info.getMacAddress())) {
                    macAddress = info.getMacAddress().replace(":", "");
                }
            }else{
                return macAddress;
            }
         } catch (Exception e) {
             e.printStackTrace();
            return macAddress;
         }
        return macAddress;
}

    private static final int MODEL = 1;
    private static final int BRAND = 2;

    // 获取imsi号
    public static String getImsi(Context context) {
        TelephonyManager mTm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return (mTm.getSubscriberId());
    }

    public static String getDeviceInfo(int type) {
        Build bd = new Build();
        switch (type) {
            case MODEL:
                return bd.MODEL;
            case BRAND:
                return bd.BRAND;
        }
        return "";
    }

    /**
     * 判断当前网络是否是3G网络
     *
     * @param context
     * @return boolean
     */
    public static boolean is3G(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
        }
        return false;
    }

    public static boolean hasNetWork(Context context){
        return NetWorkUtil.isNetworkConnected(context);
    }
    public static boolean noNetWork(Context context){
        return !NetWorkUtil.isNetworkConnected(context);
    }
    /**
     * 判断当前网络是否是wifi网络
     * if(activeNetInfo.getType()==ConnectivityManager.TYPE_MOBILE) { //判断3G网
     *
     * @param context
     * @return boolean
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    public static String isWifiOr3G(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return "wifi";
        }else if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return "3g";
        }
        return null;
    }

    private static Matcher getMatcher(String text,String pattern){
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(text);
        return m;
    }
    public static boolean isNum(String text){
        Matcher matcher = getMatcher(text, "[0-9]*");
        return matcher.find();
    }
    public static boolean isNum(String text,int start){
        Matcher matcher = getMatcher(text, "[0-9]*");
        return matcher.find(start);
    }
    public static boolean isLetter(String text){
        Matcher matcher = getMatcher(text,"[a-zA-Z]");
        return matcher.find();
    }
    public static boolean isLetter(String text,int start){
        Matcher matcher = getMatcher(text, "[a-zA-Z]");
        return matcher.find(start);
    }
    public static boolean isChinese(String text){
        Matcher matcher = getMatcher(text, "^[\\u4e00-\\u9fa5]*$");
        return matcher.find();
    }
    public static boolean isChinese(String text,int start){
        Matcher matcher = getMatcher(text, "^[\\u4e00-\\u9fa5]*$");
        return matcher.find(start);
    }

    /**
     * 加密
     * @param val
     * @return
     */
    public static String setMd5(String val) {

        // 版本号
        StringBuffer buf = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (val != null){
                md.update(val.getBytes());
                byte b[] = md.digest();

                int i;

                buf = new StringBuffer("");
                for (int offset = 0; offset < b.length; offset++) {
                    i = b[offset];
                    if (i < 0)
                        i += 256;
                    if (i < 16)
                        buf.append("0");
                    buf.append(Integer.toHexString(i));
                }

            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return buf.toString();

    }

    private static String getString(byte[] b) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            sb.append(b[i]);
        }
        return sb.toString();
    }
    public static String uri2FilePath(Activity context,Uri uri){
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.managedQuery(uri, proj, null, null, null);
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(index);
        return path;
    }
}
