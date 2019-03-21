package njj.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Author: NieJ
 * Created by niejianjian on 2019/3/4.
 * 说明：
 */

public class FileUtils {

    private static final String TAG = "AndroidUtil-FileUtils";

    /**
     * 获取sd卡路径
     */
    public static String getSDCardPath() {
        // 默认为 /mnt/sdcard/
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * 外置内存卡，一般都是这个路径
     */
    public static String getTFPath() {
        return "/mnt/external_sd";
    }

    /**
     * 拷贝单个文件
     *
     * @param oldPath
     * @param newPath
     * @return
     */
    public static boolean copyFile(String oldPath, String newPath) {
        try {
            File oldFile = new File(oldPath);
            if (!oldFile.exists()) {
                Log.e(TAG, "copyFile: oldFile not exists!");
                return false;
            }
            if (!oldFile.isFile()) {
                Log.e(TAG, "copyFile: oldFile not file!");
                return false;
            }
            if (!oldFile.canRead()) {
                Log.e(TAG, "copyFile: oldFile cannot read!");
                return false;
            }

//            // 不打印Log
//            if (!oldFile.exists() || !oldFile.isFile() || !oldFile.canRead()) {
//                return false;
//            }

            FileInputStream fileInputStream = new FileInputStream(oldPath);
            FileOutputStream fileOutputStream = new FileOutputStream(newPath);
            byte[] buffer = new byte[1024];
            int byteRead;
            while (-1 != (byteRead = fileInputStream.read(buffer))) {
                fileOutputStream.write(buffer, 0, byteRead);
            }
            fileInputStream.close();
            fileOutputStream.flush();
            return true;
//            FileChannel inChannel = new FileOutputStream("").getChannel();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "copyFile: e :" + e.toString());
            return false;
        }
    }

    /**
     * 拷贝整个文件夹
     *
     * @param oldPath
     * @param newPath
     * @return
     */
    public static boolean copyFolder(String oldPath, String newPath) {
        try {
            File newFile = new File(newPath);
            if (!newFile.exists()) {
                if (!newFile.mkdirs()) {
                    Log.e(TAG, "copyFolder: Cannot create directory!");
                    return false;
                }
            }
            File oldFile = new File(oldPath);
            String[] files = oldFile.list();
            File temp;
            for (String file : files) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file);
                } else {
                    temp = new File(oldPath + File.separator + file);
                }

                if (temp.isDirectory()) {   //如果是子文件夹
                    copyFolder(oldPath + "/" + file, newPath + "/" + file);
                } else if (!temp.exists()) {
                    Log.e(TAG, "copyFolder:  oldFile not exist.");
                    return false;
                } else if (!temp.isFile()) {
                    Log.e(TAG, "copyFolder:  oldFile not file.");
                    return false;
                } else if (!temp.canRead()) {
                    Log.e(TAG, "copyFolder:  oldFile cannot read.");
                    return false;
                } else {
                    FileInputStream fileInputStream = new FileInputStream(temp);
                    FileOutputStream fileOutputStream = new FileOutputStream(newPath + "/" + temp.getName());
                    byte[] buffer = new byte[1024];
                    int byteRead;
                    while ((byteRead = fileInputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, byteRead);
                    }
                    fileInputStream.close();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }

                /* 如果不需要打log，可以使用下面的语句
                if (temp.isDirectory()) {   //如果是子文件夹
                    copyFolder(oldPath + "/" + file, newPath + "/" + file);
                } else if (temp.exists() && temp.isFile() && temp.canRead()) {
                    FileInputStream fileInputStream = new FileInputStream(temp);
                    FileOutputStream fileOutputStream = new FileOutputStream(newPath + "/" + temp.getName());
                    byte[] buffer = new byte[1024];
                    int byteRead;
                    while ((byteRead = fileInputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, byteRead);
                    }
                    fileInputStream.close();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }*/
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "copyFolder: e :" + e.toString());
            return false;
        }
    }

    /**
     * 删除文件
     *
     * @return 返回删除结果是否成功，如果文件不存在，默认返回true，表示删除成功
     */

    public static boolean deleteFile(String path) {
        File file = new File(path);
        return !file.exists() || file.delete();
    }
}
