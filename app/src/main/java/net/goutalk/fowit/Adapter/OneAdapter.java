package net.goutalk.fowit.Adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.goutalk.fowit.R;


/**
 * @author geyifeng
 * @date 2017/6/3
 */
public class OneAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public OneAdapter() {
        super(R.layout.item_phone);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
       // helper.setText(R.id.text, item);
    }
}
