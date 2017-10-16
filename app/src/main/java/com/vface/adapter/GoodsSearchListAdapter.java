package com.vface.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.vface.R;
import com.vface.bizmodel.CommodityModel;
import com.vface.common.ImageLoadingConfig;

/**
 * Created by HuBin on 15/4/21.
 */
public class GoodsSearchListAdapter extends CommonListAdapter{

    private ImageLoader imgLoader;
    private DisplayImageOptions imgOptions;

    public GoodsSearchListAdapter(Context context, AdapterCallBack callBack) {
        super(context, callBack);
        imgLoader = ImageLoader.getInstance();
        imgLoader.init(ImageLoaderConfiguration.createDefault(context));
        imgOptions = ImageLoadingConfig.generateDisplayImageOptions(R.drawable.photo);
    }

    @Override
    protected View inflateConvertView() {
        return inflater.inflate(R.layout.activity_goods_search_list_item,null);
    }

    @Override
    protected void initViewData(int position, View convertView) {

        TextView goodsNameView = (TextView) convertView.findViewById(R.id.txt_goods_name);
        TextView goodsIdView = (TextView) convertView.findViewById(R.id.txt_goods_id);
        TextView goodsPriceView = (TextView) convertView.findViewById(R.id.txt_goods_price);
        TextView goodsCountsView = (TextView) convertView.findViewById(R.id.txt_goods_counts);

        CommodityModel model = (CommodityModel) entityList.get(position);
        goodsNameView.setText(model.getCommodityName());
        goodsIdView.setText(model.getCommodityCode()+"");
        goodsPriceView.setText(model.getPrice()+"");
        goodsCountsView.setText(model.getInventoryData()+model.getUnit()+"");
    }

    public void destroy() {
        imgLoader.destroy();
    }
}
