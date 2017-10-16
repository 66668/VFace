package com.vface.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.vface.R;
import com.vface.bizmodel.DataGlossaryModel;

/**
 * Created by HuBin on 15/4/21.
 */
public class DataGlossaryListAdapter extends CommonListAdapter{

//    private ImageLoader imgLoader;
//    private DisplayImageOptions imgOptions;

    public DataGlossaryListAdapter(Context context, AdapterCallBack callBack) {
        super(context, callBack);
//        imgLoader = ImageLoader.getInstance();
//        imgLoader.init(ImageLoaderConfiguration.createDefault(context));
//        imgOptions = ImageLoadingConfig.generateDisplayImageOptions(R.drawable.photo);
    }

    @Override
    protected View inflateConvertView() {
        return inflater.inflate(R.layout.activity_data_glossary_listitem,null);
    }

    @Override
    protected void initViewData(int position, View convertView) {

        TextView commodityCategoryView = (TextView) convertView.findViewById(R.id.commodity_category);
        TextView realMoneyView = (TextView) convertView.findViewById(R.id.real_money);
        TextView shouldMoneyView = (TextView) convertView.findViewById(R.id.should_money);

        DataGlossaryModel.CommodityClassSummary commodityClassSummary = (DataGlossaryModel.CommodityClassSummary) entityList.get(position);
        commodityCategoryView.setText(commodityClassSummary.getCommodityClassName()+"");
        realMoneyView.setText(commodityClassSummary.getAmountPayed()+"元");
        shouldMoneyView.setText(commodityClassSummary.getAmountRequired()+"元");


    }

//    public void destroy() {
//        imgLoader.destroy();
//    }
}
