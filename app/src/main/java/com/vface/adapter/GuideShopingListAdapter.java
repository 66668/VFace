package com.vface.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.vface.R;
import com.vface.bizmodel.CommodityModel;
import com.vface.common.ImageLoadingConfig;
import com.vface.utils.PageUtil;

/**
 * Created by HuBin on 15/4/21.
 */
public class GuideShopingListAdapter extends CommonListAdapter{

    private ImageLoader imgLoader;
    private DisplayImageOptions imgOptions;

    public GuideShopingListAdapter(Context context, GuideShopAdapterCallback callBack) {
        super(context, callBack);
        imgLoader = ImageLoader.getInstance();
        imgLoader.init(ImageLoaderConfiguration.createDefault(context));
        imgOptions = ImageLoadingConfig.generateDisplayImageOptions(R.drawable.photo);
    }

    public interface GuideShopAdapterCallback extends AdapterCallBack{

        void onItemLongClick(int position);

    }

    @Override
    protected View inflateConvertView() {
        return inflater.inflate(R.layout.activity_guide_shop_list_item,null);
    }

    @Override
    protected void initViewData(final int position, View convertView) {

        TextView goodsNameView = (TextView) convertView.findViewById(R.id.txt_goods_name);
        TextView goodsIdView = (TextView) convertView.findViewById(R.id.txt_goods_id);
        TextView goodsPriceView = (TextView) convertView.findViewById(R.id.txt_goods_price);
        final TextView goodsCountsView = (TextView) convertView.findViewById(R.id.txt_goods_counts);
        Button subtractBtn = (Button) convertView.findViewById(R.id.subtract_btn);
        Button plusBtn = (Button) convertView.findViewById(R.id.plus_btn);

        final CommodityModel commodityModel = (CommodityModel) entityList.get(position);
        goodsNameView.setText(commodityModel.getCommodityName());
        goodsIdView.setText(commodityModel.getCommodityCode());
        goodsPriceView.setText(commodityModel.getPrice());
        goodsCountsView.setText(commodityModel.getSeleteCounts()+"");

        subtractBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int counts = commodityModel.getSeleteCounts();
                if(counts > 0){
                    counts--;
                    commodityModel.setSeleteCounts(counts);
                    goodsCountsView.setText(counts+"");
                }
            }
        });

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int counts = commodityModel.getSeleteCounts();
                if(!(counts < commodityModel.getInventoryData())){
                    PageUtil.DisplayToast("已无库存");
                }else{
                    counts++;
                    commodityModel.setSeleteCounts(counts);
                    goodsCountsView.setText(counts+"");
                }
            }
        });

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ((GuideShopAdapterCallback)callBack).onItemLongClick(position);
                return true;
            }
        });

    }

    public void destroy() {
        imgLoader.destroy();
    }
}
