package net.goutalk.fowit.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * 复制到剪切板
 */
public class ClipUtils {
    public static void copyText(Context context, String text, String toastStr) {
        //1. 复制字符串到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setPrimaryClip(ClipData.newPlainText(null, text));
        if (!TextUtils.isEmpty(toastStr)) {
            Toast.makeText(context, toastStr, Toast.LENGTH_SHORT).show();
        }
    }
}