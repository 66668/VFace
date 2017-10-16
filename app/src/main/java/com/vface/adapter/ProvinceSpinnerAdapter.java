package com.vface.adapter;

import java.util.ArrayList;

import com.vface.R;
import com.vface.bizmodel.ProvinceModel;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ProvinceSpinnerAdapter extends BaseAdapter{
	
	private Context context;
	private ArrayList<ProvinceModel> provinceList = new ArrayList<ProvinceModel>();
	
	public ProvinceSpinnerAdapter(Context context,ArrayList<ProvinceModel> provinceList) {
		this.context = context;
		this.provinceList.addAll(provinceList);
	}
	
	@Override
	public int getCount() {
		return provinceList.size();
	}

	@Override
	public ProvinceModel getItem(int position) {
		return provinceList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView == null){
			convertView = View.inflate(context,R.layout.activity_create_user_spinner_item, null);
		}
		TextView textView = (TextView) convertView.findViewById(R.id.item_txt);
		textView.setText(provinceList.get(position).getProvinceName());
		return convertView;
	}

}
