package net.goutalk.fowit.Base;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.coder.zzq.smartshow.dialog.LoadingDialog;
import com.jaeger.library.StatusBarUtil;


import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import kotlin.jvm.internal.Intrinsics;


public abstract class BaseFragment extends Fragment {

    private Context context;
    private Unbinder unBinder;
    private LoadingDialog loadingDialog;
    public double baifen=50;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(this.getLayoutId(), container, false);
        unBinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        setStatusBar();
        initData();
    }

    public static String roundByScale(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The   scale   must   be   a   positive   integer   or   zero");
        }
        if (scale == 0) {
            return new DecimalFormat("0").format(v);
        }
        String formatStr = "0.";
        for (int i = 0; i < scale; i++) {
            formatStr = formatStr + "0";
        }
        return new DecimalFormat(formatStr).format(v);
    }
    public  byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    protected void setStatusBar() {
        StatusBarUtil.setTranslucent(getActivity(), 0);
        StatusBarUtil.setLightMode(Objects.requireNonNull(getActivity()));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fetchData();
    }

    protected void initData() {

    }

    public void ShowLoading() {
        if (loadingDialog == null)
            loadingDialog = new LoadingDialog().middle();
        loadingDialog.showInFragment(this);
    }

    public void HideLoading() {
        if (loadingDialog == null) return;
        loadingDialog.dismiss();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unBinder.unbind();
    }

    @Nullable
    @Override
    public Context getContext() {
        return context==null?Objects.requireNonNull(getActivity()).getApplicationContext():context;
    }

    protected abstract void initView(View view);

    protected abstract int getLayoutId();

    protected abstract void fetchData();

    public void Goto(Class<?> clz) {
        startActivity(new Intent(getContext(), clz), null);
    }

    public void Goto(Class<?> clz,int flag) {
        startActivity(new Intent(getContext(), clz).setFlags(flag), null);
    }

    public void Goto(Class<?> clz, String key, int value) {
        startActivity(new Intent(getContext(), clz).putExtra(key, value), null);
    }

    public void Goto(Class<?> clz, String key, String value) {
        startActivity(new Intent(getContext(), clz).putExtra(key, value), null);
    }

    public void Goto(Class<?> clz, String key, String value, String key1, String value1) {
        startActivity(new Intent(getContext(), clz).putExtra(key, value).putExtra(key1, value1), null);
    }

    public void Goto(Class<?> clz, String key, String value, String key1, int value1) {
        startActivity(new Intent(getContext(), clz).putExtra(key, value).putExtra(key1, value1), null);
    }

    public void Goto(Class<?> clz, String key, String value, String key1, String value1, String key2, String value2) {
        startActivity(new Intent(getContext(), clz).putExtra(key, value).putExtra(key1, value1).putExtra(key2, value2), null);
    }

    public void Goto(Class<?> clz, String key, int value, String key1, int value1, String key2, String value2) {
        startActivity(new Intent(getContext(), clz).putExtra(key, value).putExtra(key1, value1).putExtra(key2, value2), null);
    }
    public void Goto(Class<?> clz, String key, String value, String key1, String value1, String key2, String value2, String key3, String value3) {
        startActivity(new Intent(getContext(), clz).putExtra(key, value).putExtra(key1, value1).putExtra(key2, value2).putExtra(key3, value3), null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
    public final int getSystemBarHeight() {
        Resources var10000 = this.getResources();
        Intrinsics.checkExpressionValueIsNotNull(var10000, "resources");
        Resources resources = var10000;
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }
}
