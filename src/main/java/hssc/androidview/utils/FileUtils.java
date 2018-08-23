package hssc.androidview.utils;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;

/**
 * Created by Administrator on 2016/5/16.
 */
public class FileUtils {
    private static final File parentPath = Environment.getExternalStorageDirectory();
    private static String storagePath = "";
    private static String DST_FOLDER_NAME = "";

    public static void setFolderName(String folderName){
        DST_FOLDER_NAME = folderName;
    }
    public static boolean isSDCARDMounted() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)){
            return true;
        }
        return false;
    }
    /**
     * 初始化保存路径
     *
     * @return
     */
    public static String initPath() {
        if (TextUtils.isEmpty(storagePath)) {
            storagePath = parentPath.getAbsolutePath() + "/" + DST_FOLDER_NAME;
            File f = new File(storagePath);
            if (!f.exists()) {
                f.mkdir();
            }
        }
        return storagePath;
    }
}
