package com.vface.adapter;
 
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.vface.R;
import com.vface.bizmodel.VisitRecordModel;
import com.vface.common.ImageLoadingConfig;
/**
 * 列表适配
 * @author 
 *
 */
public class UserListAdapter extends CommonListAdapter{
	
	private ImageLoader imgLoader;
	private DisplayImageOptions imgOptions;
	
	class ViewPlaceHolder{
		ImageView imageHeader;
		TextView txtName;
		TextView txtTime;
        TextView txtTotalTimes;
	}
	
	public UserListAdapter(Context context,AdapterCallBack callBack) {
		super(context, callBack); 
		imgLoader = ImageLoader.getInstance();
		imgLoader.init(ImageLoaderConfiguration.createDefault(context));
		imgOptions = ImageLoadingConfig.generateDisplayImageOptions(R.drawable.photo);
	} 

	@Override
	protected View inflateConvertView() {
		View view = inflater.inflate(R.layout.activity_user_list_item,null);
		ViewPlaceHolder holder = new ViewPlaceHolder();
		holder.imageHeader = (ImageView)view.findViewById(R.id.imgUserHeader);
		holder.txtName = (TextView)view.findViewById(R.id.txtName);
		holder.txtTime = (TextView)view.findViewById(R.id.txtTime);
        holder.txtTotalTimes = (TextView)view.findViewById(R.id.txtTotalTimes);
		view.setTag(holder);
		return view;
	}

	@Override
	protected void initViewData(int position, View convertView) {
		ViewPlaceHolder holder = (ViewPlaceHolder)convertView.getTag();
		VisitRecordModel model = (VisitRecordModel)(getEntityList().get(position));
		imgLoader.displayImage(model.getPicPath(), holder.imageHeader,imgOptions);
        if(TextUtils.isEmpty(model.getMemberId())){
            holder.txtName.setText(model.getFullName()+"(唯脸会员)");
        }else{
            holder.txtName.setText(model.getFullName());
        }
        holder.txtTime.setText("进店时间："+model.getCreateTime());
        holder.txtTotalTimes.setText("到店总次数："+model.getTotalVisitCount()+"次");//TODO
    }
	
	public void destroy() {
		imgLoader.destroy();
	}
}
