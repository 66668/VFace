package com.vface.adapter;

import java.util.ArrayList;

import com.vface.R;
import com.vface.bizmodel.DistrictModel;
import com.vface.bizmodel.ProvinceModel;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DistrictSpinnerAdapter extends BaseAdapter{
	
	private Context context;
	private ArrayList<DistrictModel> districtList = new ArrayList<DistrictModel>();
	
	public DistrictSpinnerAdapter(Context context,ArrayList<DistrictModel> districtList) {
		this.context = context;
		this.districtList.addAll(districtList);
	}
	
	@Override
	public int getCount() {
		return districtList.size();
	}

	@Override
	public DistrictModel getItem(int position) {
		return districtList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView == null)
			convertView = View.inflate(context,R.layout.activity_create_user_spinner_item, null);
		TextView textView = (TextView) convertView.findViewById(R.id.item_txt);
		textView.setText(districtList.get(position).getDistrictName());
		return convertView;
	}

}
