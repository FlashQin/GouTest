package net.goutalk.fowit.utils;

/**
 * Created by Lzy On 2019/8/19
 * Describe:
 */

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;

import androidx.fragment.app.FragmentActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import net.goutalk.fowit.net.Url;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.vondear.rxtool.RxActivityTool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Created by Lzy On 2019/8/19
 * Describe:
 */
public class CommonUtils {
    public static ArrayList<String> imgsgoodspic = new ArrayList<>();
    public static int share=0;
    public static String mVerticalCodeId="945297766";//激励视频 竖版
    public static String startPageCode="887343547";//启动页
    public static String mfullvideo="945297766";//全屏视频
    public static String mgameWindowvideo="945297772";//游戏退出弹框，信息流广告，模板渲染
    public static String mgamelist="945297773";  //游戏列表，信息流广告，模板渲染
    public static String mPicInfo="945297102";  //图文信息流，信息流广告，模板渲染
    public static String mlockAd="945309440";  //锁屏信息流
    public static String mQustionBanner="945309229";  //答题信息流

    private static final String SD_APP_DIR_NAME = "TestDir"; //存储程序在外部SD卡上的根目录的名字
    private static final String PHOTO_DIR_NAME = "photo";    //存储照片在根目录下的文件夹名字
    private static long mBackPressedTime;
    public static int type=0;//0秒杀1团购
    public static int istm=0;//0秒杀1团购
    private static int HOUR = 1000 * 60 * 60;
    public static String wxphone="";
    public static String provcename="";
    public static String areaname="";
    public static String cityname="";
    public static String provce="";
    public static String city="";
    public static String area="";
    public static int WXCODE=9;
    public static int pingtype=9;
    public static String code="";
    public static String likeid="";
    /**
     * 微信key
     * */
    public static final String APP_ID_WX = "wxa4082d35f8e7b39d";
    public static final String WEI_SECRET = "54f1a49d58c444d33fa8e7aa9534a8ad";
    public static final String QQAPP_ID_WX = "101880481";
    public static final String TAOAPPKEY = "5f115e3f5ec30";//大淘客key
    public static final String TAOSERCT = "d4cd646cf905e27b13c1519390233c36";//大淘客sercirt
    public static String checkImgUrl(String url) {
        return url.startsWith("http") ? url : Url.baseUrl + url;
    }

    public static String GetTimeFromMills(long mills) {
        int d = Integer.parseInt(String.valueOf(mills / (HOUR * 24)));
        int h = Integer.parseInt(String.valueOf((mills % (HOUR * 24)) / (HOUR)));
        int m = Integer.parseInt(String.valueOf((mills % (HOUR)) / (1000 * 60)));
        int s = Integer.parseInt(String.valueOf((mills % (1000 * 60)) / 1000));
        String f_h = String.valueOf(h).length() == 1 ? "0" + h : String.valueOf(h);
        String f_m = String.valueOf(m).length() == 1 ? "0" + m : String.valueOf(m);
        String f_s = String.valueOf(s).length() == 1 ? "0" + s : String.valueOf(s);
        return d == 0 ? f_h + ":" + f_m + ":" + f_s : d + ":" + f_h + ":" + f_m + ":" + f_s;
    }

    //回车监听工具
    public static void enterListener(EditText editText, onChangeText onChangeText) {
        editText.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                if (editText.getText().length() == 0) return false;
                String trim_his = editText.getText().toString().trim();
                onChangeText.onChangeText(trim_his);
                editText.setText("");
                return true;
            }
            return false;
        });
    }

    public static void doubleClickExitApp() {
        long curTime = SystemClock.uptimeMillis();
        if ((curTime - mBackPressedTime) < (3 * 1000)) {
            RxActivityTool.finishAllActivity();
            //根据进程ID，杀死该进程
            android.os.Process.killProcess(android.os.Process.myPid());
            //退出应用程序
            System.exit(0);
        } else {
            mBackPressedTime = curTime;
            ToastUtils.showShort("再按一次退出");
        }
    }

    /**
     * 从raw包下读取数据
     *
     * @param rawName R.raw.jx
     * @return
     */
    public static String readFileFromRaw(int rawName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(Utils.getApp().getResources().openRawResource(rawName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String result = "";
            while ((line = bufReader.readLine()) != null)
                result += line;
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getJson(String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = Utils.getApp().getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    public static String getVersionName(Context context) {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = packInfo.versionName;
        return version;
    }

    /**
     * 复制内容到剪切板
     *
     * @param context 上下文
     * @param content 需要复制到剪切板的文字
     */
    public static void copyToClipboard(Context context, CharSequence content) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard != null) {
            clipboard.setPrimaryClip(ClipData.newPlainText(null, content));//参数一：标签，可为空，参数二：要复制到剪贴板的文本
            if (clipboard.hasPrimaryClip()) {
                clipboard.getPrimaryClip().getItemAt(0).getText();
            }
        }
    }

    public static void saveImageToGallery(FragmentActivity fragmentActivity, Bitmap bmp) {
        RxPermissions rxPermissions = new RxPermissions(fragmentActivity);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        Log.i("tag", "checkPermission22--:" + aBoolean);
                        if (aBoolean) {
                            // 首先保存图片
                            File appDir = new File(Environment.getExternalStorageDirectory(), "/DCIM/Camera");
                            if (!appDir.exists()) {
                                appDir.mkdir();
                            }
                            String fileName = System.currentTimeMillis() + ".jpg";
                            File file = new File(appDir, fileName);
                            try {
                                FileOutputStream fos = new FileOutputStream(file);
                                bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                                fos.flush();
                                fos.close();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            displayToGallery(fragmentActivity, file);
                        } else {
                            ToastUtils.showShort("未获得文件读写权限，无法保存图片");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 保存到系统相册
     *
     * @param context   上下文
     * @param photoFile 源文件地址
     */
    public static void displayToGallery(Context context, File photoFile) {
        if (photoFile == null || !photoFile.exists()) {
            return;
        }
        String photoPath = photoFile.getAbsolutePath();
        String photoName = photoFile.getName();
        // 把文件插入到系统图库
        try {
            ContentResolver contentResolver = context.getContentResolver();
            MediaStore.Images.Media.insertImage(contentResolver, photoPath, photoName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + photoPath)));
    }

    /**
     * 打开微信
     */
    public static void GotoWX(Context context) {
        Intent intent = new Intent();
        ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setComponent(cmp);
        context.startActivity(intent);
    }

    /**
     * 将Base64编码后的图片转码为bitmap
     *
     * @param bmMsg 名称
     * @return bitmap
     */
    public static Bitmap SaveImage(String bmMsg) {
        byte[] input = Base64.decode(bmMsg, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(input, 0, input.length);
    }

    public static File saveBitmap(String bitName, Bitmap mBitmap) {
        File f = new File("/sdcard/temp/" + bitName + ".png");
        try {
            f.getParentFile().mkdirs();
            f.createNewFile();
        } catch (IOException e) {
            ToastUtils.showShort("在保存图片时出错：" + e.toString());
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f;
    }

    /**
     * 获取当前app渠道号
     *
     * @param context 上下文
     * @return 渠道号
     */
    public static String getApplicationMetadata(Context context) {
        ApplicationInfo info = null;
        try {
            PackageManager pm = context.getPackageManager();
            info = pm.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            return String.valueOf(info.metaData.getString("CHANNEL_VALUE"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    public static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    public static File saveImg(String filePath, String type) {
        String destPath = Utils.getApp().getExternalFilesDir("tempImg") + File.separator + type + ".png";
        File file = new File(destPath);
        if (file.exists()) {
            file.delete();
        }
        return file;
    }

    /**
     * 获取cache路径
     *
     * @return
     */
    public static String getDiskCachePath() {
        Context context = Utils.getApp();
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            return context.getExternalCacheDir().getPath();
        } else {
            return context.getCacheDir().getPath();
        }
    }

    public static File uriToFile(Context context, Uri uri) {
        String path = null;
        if ("file".equals(uri.getScheme())) {
            path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = context.getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=").append("'" + path + "'").append(")");
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.ImageColumns._ID, MediaStore.Images.ImageColumns.DATA}, buff.toString(), null, null);
                int index = 0;
                int dataIdx = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    index = cur.getInt(index);
                    dataIdx = cur.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    path = cur.getString(dataIdx);
                }
                cur.close();
                if (index == 0) {
                } else {
                    Uri u = Uri.parse("content://media/external/images/media/" + index);
                    System.out.println("temp uri is :" + u);
                }
            }
            if (path != null) {
                return new File(path);
            }
        } else if ("content".equals(uri.getScheme())) {
            // 4.2.2以后
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                path = cursor.getString(columnIndex);
            }
            cursor.close();
            return new File(path);
        } else {
            Log.i("tag", "Uri Scheme:" + uri.getScheme());
        }
        return null;
    }

    /**
     * 通过Uri获取文件路径
     *
     * @param pUri
     * @return
     */
    public static String getPathByUri(Uri pUri) {
//      pUri.getPath()
//      拍照后输出：  /mq_external_cache/storage/emulated/0/Pictures/JPEG_20190326_225011.jpg
//      选择照片后的输出：  /external/images/media/52325
        String _Path = pUri.getPath();

        if (_Path.endsWith(".jpg")) {
            System.out.println("path-->" + subPath(_Path));
            return subPath(_Path);
        }
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = Utils.getApp().getContentResolver().query(pUri,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return picturePath;
    }

    private static String subPath(String pPath) {
        String[] array = pPath.split("/");
        return pPath.substring(array[1].length() + 1);
    }

    public static String createPathIfNotExist() {
        boolean sdExist = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
        if (!sdExist) {
            Log.e("path", "SD卡不存在");
            return null;
        }
        String _AbsolutePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + SD_APP_DIR_NAME + "/" + PHOTO_DIR_NAME;
        System.out.println("dbDir->" + _AbsolutePath);
        File dirFile = new File(_AbsolutePath);
        if (!dirFile.exists()) {
            if (!dirFile.mkdirs()) {
                Log.e("path", "文件夹创建失败");
                return null;
            }
        }
        return _AbsolutePath;
    }

    /**
     * 判断图片是不是base64编码的图片
     *
     * @param imgurl base64
     * @return true 是 false 不是
     */
    public static boolean isBase64Img(String imgurl) {
        return !TextUtils.isEmpty(imgurl) && (imgurl.startsWith("data:image/png;base64,")
                || imgurl.startsWith("data:image/*;base64,") || imgurl.startsWith("data:image/jpg;base64,")
        );
    }

    private void CheckPermission(FragmentActivity fragmentActivity) {

    }

    public interface onChangeText {
        void onChangeText(String text);
    }
}
