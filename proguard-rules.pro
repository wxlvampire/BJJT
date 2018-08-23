# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\Android\studiosdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-ignorewarnings
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-keepattributes *Annotation*
-keepattributes Signature

-keep public class * extends android.view
-keep public class * extends android.viewGroup
-keep public class * extends android.app.Fragment
-keep public class * extends android.support.v4.app.FragmentActivity
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.widget.BaseAdapter
-keep public class * extends android.widget.BaseExpandableListAdapter
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.support.v4.**
-keep public class com.android.vending.licensing.ILicensingService

#-dontwarn   retrofit2.*
-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
   public <methods>;
}
-keepclassmembers class * extends android.support.v4.app.FragmentActivity {
   public void *(android.view.View);
   public <methods>;
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keep class hssc.androidview.base.CommonAdapter{
    <methods>;
    <fields>;
}
-keep class hssc.androidview.http.ResponseListener{
    public <methods>;
}

-keep class hssc.androidview.http.VolleyError{
    public <methods>;
}

-keep interface org.apache.http.NameValuePair{
      <methods>;
 }
 -keep interface org.apache.http.client.HttpClient{
      <methods>;
 }
  -keep interface org.apache.http.client.methods.HttpUriRequest{
      <methods>;
  }

-keep interface org.apache.http.HttpRequest{
     <methods>;
}
-keep interface org.apache.http.HttpResponse{
     <methods>;
}
-keep interface org.apache.http.params.HttpParams{
     <methods>;
}
-keep interface org.apache.http.HttpMessage{
     <methods>;
}

-keep class hssc.androidview.utils.AppUtils{
    public static <methods>;
}
-keep class hssc.androidview.utils.PopUtils{
    <methods>;
    <fields>;
}

-keep class hssc.androidview.utils.ToastUtil{
    <methods>;
}
-keep class hssc.androidview.utils.SimpleCache{
     <methods>;
}
-keep interface hssc.androidview.base.BaseInterface{
    <methods>;
}

-keep interface hssc.androidview.views.TabsTitleInterface{
    <methods>;
}
-keep class hssc.androidview.base.CommonAdapter{
    public <fields>;
    public <methods>;
}
-keep class hssc.androidview.views.PinnedHeaderListView{
    <methods>;
    <fields>;
}
-keep interface hssc.androidview.views.PinnedHeaderAdapter{
    <methods>;
}

#-keep class hssc.androidview.views.animateexpandableview.**{*;}
#-keep class hssc.androidview.views.pickerview.**{*;}
#-libraryjars libs/httpclient-4.2.3.jar
#-libraryjars libs/httpcore-4.2.2.jar
#-libraryjars libs/httpmime-4.1.1.jar

-keep class com.google.code.gson.**{*;}

-keep class hssc.androidview.views.**{*;}
-keep class hssc.androidview.views.percent**{*;}
-keep class hssc.androidview.views.arcmenu**{*;}
-keep class hssc.androidview.base.**{*;}
-keep class hssc.androidview.http.file.**{*;}
-keep class hssc.androidview.http.**{*;}
-keep class hssc.androidview.nextinputs.**{*;}
-keep class hssc.androidview.utils.netutil.**{*;}
-keep class org.apache.http.**{*;}
-keep class org.apache.http.client.**{*;}
-keep class org.apache.http.conn.**{*;}
-keep class org.apache.http.entity.mime.** { *; }
-keep class hssc.androidview.views.ViewPager{}
-keep class hssc.androidview.utils.**{*;}
-keep class hssc.androidview.utils.interface.**{*;}

-keepclassmembernames class hssc.androidview.views.OptionsPickerView$OnOptionsSelectListener{
     <fields>;
     <methods>;
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public enum hssc.androidview.views.pickerview.view.Type {
#    public static **[] values();
#    public static ** valueOf(java.lang.String);
    *;
}


-dontwarn java.nio.file.Files
-dontwarn java.nio.file.Path
-dontwarn java.nio.file.OpenOption
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
