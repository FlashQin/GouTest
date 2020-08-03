package net.goutalk.fowit;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ToastUtils;
import com.jaeger.library.StatusBarUtil;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.rxjava.rxlife.RxLife;

import net.goutalk.fowit.Base.BaseActivity;
import net.goutalk.fowit.Base.BaseFragment;
import net.goutalk.fowit.Base.BaseMsgBean;
import net.goutalk.fowit.Bean.TestBean;
import net.goutalk.fowit.Bean.VersionBean;
import net.goutalk.fowit.fragment.GameFragment;
import net.goutalk.fowit.fragment.HomeFragment;
import net.goutalk.fowit.fragment.HomeThreeFragment;
import net.goutalk.fowit.fragment.PersonFragment;
import net.goutalk.fowit.fragment.StorFragment;
import net.goutalk.fowit.fragment.VideoFragment;
import net.goutalk.fowit.net.BaseObserver;
import net.goutalk.fowit.wigde.BottomNavigationView;
import net.goutalk.fowit.wigde.CustomViewPager;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import cn.jzvd.Jzvd;
import rxhttp.wrapper.param.RxHttp;

import static cn.jzvd.Jzvd.backPress;

public class MainActivity extends BaseActivity {

    @BindView(R.id.navigation)
    BottomNavigationView navigationView;
    @BindView(R.id.viewPager)
    CustomViewPager viewPager;
    @BindView(R.id.layout_main)
    FrameLayout layout_main;

    Boolean isjump = true;

    private List<BaseFragment> fragments = null;
    private DialogPlus dialog_spec;
     BaseDownloadTask singleTask ;

    private int downloadId1;
    public String singleFileSaveName  ;
    @Override
    protected void SetStatusBar() {
        StatusBarUtil.setTransparentForImageViewInFragment(this, null);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        checkVersion();

        if (fragments == null)
            fragments = Arrays.asList(
                    //主页
                    new HomeFragment(),
                    //游戏
                    new GameFragment(),
                    //商城
                   // new StorFragment(),
                    //视频
                    new VideoFragment(),
                    //个人
                    new PersonFragment());

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });

        viewPager.setOffscreenPageLimit(1);

        navigationView.setupWithViewPager(viewPager);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Jzvd.releaseAllVideos();
                if (position == 1) {
                  //  navigationView.setVisibility(View.GONE);
                    //  layout_float.setVisibility(View.VISIBLE);
                } else {
                  //  navigationView.setVisibility(View.VISIBLE);
                    viewPager.setOffscreenPageLimit(fragments.size());
                    //layout_float.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



    }
    private void checkVersion() {
        RxHttp.get("/versionConfig/android/read.json?versionNumber="+getVersionName(MainActivity.this))

                .asObject(TestBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<TestBean>() {
                    @Override
                    public void onNext(TestBean baseMsgBean) {
                        if (baseMsgBean.getStatus() == 0) {
                            VersionBean codeBean = JSONObject.parseObject(JSONObject.toJSONString(baseMsgBean), VersionBean.class);

                            if (codeBean.getData()!=null&&codeBean.getData().getDownloadUrl()!=null&&codeBean.getData().getDownloadUrl().startsWith("http")){
                                initSpecDialog(codeBean);
                            }


                        } else {
                            ToastUtils.showShort(baseMsgBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        ToastUtils.showShort(e.toString());
                    }
                });

    }


    public String getRootDirPath(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File file = ContextCompat.getExternalFilesDirs(context,
                    null)[0];
            return file.getAbsolutePath();
        } else {
            return context.getApplicationContext().getFilesDir().getAbsolutePath();
        }

    }

    //规格参数dialog
    private void initSpecDialog(VersionBean versionBean) {
        singleFileSaveName =getRootDirPath(MainActivity.this) + "/gouzhuanapk/gouzhuan.apk" ;
        ViewHolder viewHolder = new ViewHolder(R.layout.layout_version);
        dialog_spec = DialogPlus.newDialog(MainActivity.this)
                .setContentHolder(viewHolder)
                .setGravity(Gravity.CENTER)
                .setCancelable(false)
                .setContentBackgroundResource(R.color.transparent)
                .setOverlayBackgroundResource(R.color.dialog_overlay_bg)
                .create();
        ProgressBar progressBar=viewHolder.getInflatedView().findViewById(R.id.progress_bar);
        TextView txtdown=viewHolder.getInflatedView().findViewById(R.id.txt_download);
        TextView txtcontent=viewHolder.getInflatedView().findViewById(R.id.txt_content);
        TextView txtcancle=viewHolder.getInflatedView().findViewById(R.id.txt_cancle);
        if (versionBean.getData().getForceInstall()==1){
            txtcancle.setText("退出APP");
        }
        if (versionBean.getData().getUpgradeLog()!=null){
            txtcontent.setText(versionBean.getData().getUpgradeLog().toString());
        }
        txtdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txtdown.getText().toString().equals("下载")) {
                    txtdown.setText("准备中...");
                    StrtDown(versionBean, progressBar,txtdown);
                }
            }
        });
        txtcancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (versionBean.getData().getForceInstall()==1){
                    dialog_spec.dismiss();
                   MainActivity.this.finish();

                }else {
                    dialog_spec.dismiss();
                }

            }
        });

        dialog_spec.show();
    }
    public void StrtDown(VersionBean versionBean,ProgressBar progressBar,TextView textView){
        downloadId1=  createDownloadTask(versionBean,singleFileSaveName,progressBar,textView).start();
    }
    private BaseDownloadTask createDownloadTask(VersionBean versionBean,String llsApkFilePath,ProgressBar progressBar,TextView textView) {
        final ViewHolder tag;

        boolean isDir = false;
        String path;


               // tag = new ViewHolder(new WeakReference<>(this), progressBar1, null, speedTv1, 1);
                path = llsApkFilePath;
                //tag.setFilenameTv(filenameTv1);



       return   FileDownloader.getImpl().create(versionBean.getData().getDownloadUrl())
                .setPath(path, isDir)
                .setCallbackProgressTimes(3000)
                .setMinIntervalUpdateSpeed(400)
                .setListener(new FileDownloadSampleListener() {

                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        super.pending(task, soFarBytes, totalBytes);
                       // ((ViewHolder) task.getTag()).updatePending(task);
                       // textView.setText("");
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        super.progress(task, soFarBytes, totalBytes);
                        textView.setText("下载中");
//                        ((ViewHolder) task.getTag()).updateProgress(soFarBytes, totalBytes,
//                                task.getSpeed());
                        if (totalBytes == -1) {
                            // chunked transfer encoding data
                            progressBar.setIndeterminate(true);
                        } else {
                            progressBar.setMax(totalBytes);
                            progressBar.setProgress(soFarBytes);
                        }
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        super.error(task, e);
                      //  ToastUtils.showShort(e.toString());
                       StrtDown(versionBean,progressBar,textView);
                    }

                    @Override
                    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                        super.connected(task, etag, isContinue, soFarBytes, totalBytes);
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        super.paused(task, soFarBytes, totalBytes);
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        super.completed(task);
                       // ToastUtils.showShort("OK");
                        install(singleFileSaveName);
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        super.warn(task);
                    }
                });
    }
    private void install(String filePath) {
        Log.i(TAG, "开始执行安装: " + filePath);
        File apkFile = new File(filePath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Log.w(TAG, "版本大于 N ，开始使用 fileProvider 进行安装");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(
                    this
                    , "net.goutalk.fowit.fileprovider"
                    , apkFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            Log.w(TAG, "正常进行安装");
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Jzvd.goOnPlayOnResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.goOnPlayOnPause();
    }

    @Override
    public void onBackPressed() {
        if (backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Jzvd.releaseAllVideos();

    }
}