package net.goutalk.fowit.utils;

import android.annotation.SuppressLint;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;

import net.goutalk.fowit.R;
import net.qiujuer.genius.ui.widget.Button;


/**
 * Created on 2019/4/11.
 */
public class CommonTimer extends CountDownTimerCopyFromAPI26 {

    private TextView textView;
    private Button button;
    private String text = "";

    public CommonTimer(long millisInFuture, long countDownInterval, TextView textView) {
        super(millisInFuture, countDownInterval);
        this.textView = textView;
    }

    public CommonTimer(long millisInFuture, long countDownInterval, Button button) {
        super(millisInFuture, countDownInterval);
        this.button = button;
    }

    public CommonTimer(long millisInFuture, long countDownInterval, TextView textView, String text) {
        super(millisInFuture, countDownInterval);
        this.textView = textView;
        this.text = text;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onTick(long millisUntilFinished) {
        String code_reduce = millisUntilFinished / 1000 + "s" + text;
        if (textView != null) {
            textView.setText(code_reduce);
        }
        if (button != null) {
            button.setText(code_reduce);
        }
        assert textView != null;
        if (textView.isEnabled())
            textView.setEnabled(false);
    }

    @Override
    public void onFinish() {
        if (textView != null) {
            textView.setEnabled(true);
            textView.setText(StringUtils.getString(R.string.str_get_sms_code));
        }
        if (button != null) {
            button.setEnabled(true);
            button.setText(StringUtils.getString(R.string.str_get_sms_code));
        }
    }
}