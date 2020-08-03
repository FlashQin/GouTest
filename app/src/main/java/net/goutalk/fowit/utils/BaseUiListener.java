package net.goutalk.fowit.utils;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

public class BaseUiListener implements IUiListener {

    protected void doComplete(JSONObject values) {
    }

    @Override
    public void onComplete(Object o) {
       // doComplete(o);
    }

    @Override
    public void onError(UiError e) {
//        showResult("onError:", "code:" + e.errorCode + ", msg:"
//                + e.errorMessage + ", detail:" + e.errorDetail);
    }
    @Override
    public void onCancel() {
//        showResult("onCancel", "");
    }
}