package com.vface.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.vface.R;
import com.vface.bizmodel.MemberModel;
import com.vface.common.ImageLoadingConfig;

/**
 * Created by HuBin on 15/4/21.
 */
public class ManagerUserListAdapter extends CommonListAdapter{

    private ImageLoader imgLoader;
    private DisplayImageOptions imgOptions;

    public ManagerUserListAdapter(Context context,AdapterCallBack callBack) {
        super(context, callBack);
        imgLoader = ImageLoader.getInstance();
        imgLoader.init(ImageLoaderConfiguration.createDefault(context));
        imgOptions = ImageLoadingConfig.generateDisplayImageOptions(R.drawable.photo);
    }

    @Override
    protected View inflateConvertView() {
        return inflater.inflate(R.layout.activity_manage_user_list_item,null);
    }

    @Override
    protected void initViewData(int position, View convertView) {

        ImageView userImgView = (ImageView) convertView.findViewById(R.id.imgUserHeader);
        TextView userNameView = (TextView) convertView.findViewById(R.id.text_name);
        TextView surplusPointsView = (TextView) convertView.findViewById(R.id.text_surplus_points);
        TextView phoneView = (TextView) convertView.findViewById(R.id.text_phone);

        MemberModel memberModel = (MemberModel) entityList.get(position);

        imgLoader.displayImage(memberModel.getPicPath(),userImgView,imgOptions);
        userNameView.setText(memberModel.getFullName()+"");
        surplusPointsView.setText(memberModel.getTotalPoints()+"");
        phoneView.setText(memberModel.getPhoneNumber()+"");

    }

    public void destroy() {
        imgLoader.destroy();
    }
}
