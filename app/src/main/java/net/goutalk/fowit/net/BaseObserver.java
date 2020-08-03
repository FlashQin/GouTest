package net.goutalk.fowit.net;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseObserver<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {
      //  String sds="";
    }

    @Override
    public void onNext(T t) {
     //   String sds="";

    }

    @Override
    public void onError(Throwable e) {
      //  ToastUtils.showShort(e.getMessage());
    }

    @Override
    public void onComplete() {

    }
}