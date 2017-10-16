package com.vface.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.vface.R;
import com.vface.bizmodel.ExpenseListModel;
import com.vface.common.ImageLoadingConfig;
import com.vface.utils.DateUtils;

/**
 * Created by HuBin on 15/4/21.
 */
public class ConsumeHistoryListAdapter extends CommonListAdapter{

    private ImageLoader imgLoader;
    private DisplayImageOptions imgOptions;

    public ConsumeHistoryListAdapter(Context context, AdapterCallBack callBack) {
        super(context, callBack);
        imgLoader = ImageLoader.getInstance();
        imgLoader.init(ImageLoaderConfiguration.createDefault(context));
        imgOptions = ImageLoadingConfig.generateDisplayImageOptions(R.drawable.photo);
    }

    @Override
    protected View inflateConvertView() {
        return inflater.inflate(R.layout.activity_consume_history_listitem,null);
    }

    @Override
    protected void initViewData(int position, View convertView) {

        TextView userNameView = (TextView) convertView.findViewById(R.id.user_name);
        TextView serialNumberView = (TextView) convertView.findViewById(R.id.serial_number);
        TextView dateTimeView = (TextView) convertView.findViewById(R.id.date_time);
        TextView moneyView = (TextView) convertView.findViewById(R.id.money);
        TextView expenseTypeView = (TextView) convertView.findViewById(R.id.expenseType);

        ExpenseListModel expenseListModel = (ExpenseListModel) entityList.get(position);
        if(TextUtils.isEmpty(expenseListModel.getMemberName()) && TextUtils.isEmpty(expenseListModel.getStoreMemberId())){
            userNameView.setText("客户：非会员");
        }else{
            userNameView.setText("客户："+expenseListModel.getMemberName());
        }
        serialNumberView.setText(expenseListModel.getOrderNumber());
        dateTimeView.setText(DateUtils.getFriendlyDate(expenseListModel.getExpenseDate()));
        moneyView.setText(expenseListModel.getTotalAmount()+"");
        expenseTypeView.setText(getExpenseName(expenseListModel.getExpenseType()));

    }

    public void destroy() {
        imgLoader.destroy();
    }

    public static String getExpenseName(int type){

        String name = "商品消费";
        switch (type){
            case 1:
                name = "商品消费";
                break;
            case 2:
                name = "快速消费";
                break;
            case 5:
                name = "计时充值";
                break;
            case 6:
                name = "计次充值";
                break;
            default:
                break;
        }
        return name;
    }
}
