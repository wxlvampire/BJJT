package hssc.androidview.utils.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.View;
import android.widget.EditText;

/**
 * Created by li on 2017/8/1.
 */
public class DialogUtils {
    private static AlertDialog.Builder builder;
    public static AlertDialog alertDialog;
    private static OnButtonListener onButtonListener;
    private static DialogUtils INSTANCE;

    public void setOnButtonListener(OnButtonListener onButtonListener) {
        this.onButtonListener = onButtonListener;
    }

    public static  DialogUtils getInstance(){
        if (INSTANCE == null){
            return new DialogUtils();
        }
        return INSTANCE;
    }

    public static DialogUtils initDialog(Context context, View view){
        builder = new AlertDialog.Builder(context);
        builder.setView(view);
        return null;
    }
    public static void showDialog(){
        alertDialog = builder.create();
        alertDialog.show();
    }
    public static void cannotCancel(){
        alertDialog.setCancelable(false);
    }

    public static void cancelDialog(){
        alertDialog.dismiss();
    }
    public void showOriginDialog(Context context,String title) {
        android.support.v7.app.AlertDialog dialog = new android.support.v7.app.AlertDialog.Builder(context)
                .setMessage(title)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(onButtonListener != null){
                            onButtonListener.onCancelListener(dialog);
                        }
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (onButtonListener != null){
                            onButtonListener.onOkListener(dialog);
                        }
                    }
                }).create();
        dialog.show();
    }
    public void showOriginDialog(Context context,String title,String content) {
        android.support.v7.app.AlertDialog dialog = new android.support.v7.app.AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(content)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(onButtonListener != null){
                            onButtonListener.onCancelListener(dialog);
                        }
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (onButtonListener != null){
                            onButtonListener.onOkListener(dialog);
                        }
                    }
                }).create();
        dialog.show();
    }
    public static void showOriginDialog(Context context,int okResColorId,int cancelResColorId){
        android.support.v7.app.AlertDialog dialog = new android.support.v7.app.AlertDialog.Builder(context)
//                .setTitle("确定关闭该店吗？")
        .setMessage("确定关闭该店吗？")
        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(onButtonListener != null){
                    onButtonListener.onCancelListener(dialog);
                }
            }
        })
        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (onButtonListener != null){
                    onButtonListener.onOkListener(dialog);
                }
            }
        }).create();
        dialog.show();
        dialog.getButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(okResColorId));
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(cancelResColorId));
    }

}
