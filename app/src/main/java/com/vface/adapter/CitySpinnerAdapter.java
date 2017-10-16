package com.vface.adapter;

import java.util.ArrayList;

import com.vface.R;
import com.vface.bizmodel.CityModel;
import com.vface.bizmodel.ProvinceModel;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CitySpinnerAdapter extends BaseAdapter{
	
	private Context context;
	private ArrayList<CityModel> cityList = new ArrayList<CityModel>();
	
	public CitySpinnerAdapter(Context context,ArrayList<CityModel> cityList) {
		this.context = context;
		this.cityList.addAll(cityList);
	}
	
	@Override
	public int getCount() {
		return cityList.size();
	}

	@Override
	public CityModel getItem(int position) {
		return cityList.get(position);
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
		textView.setText(cityList.get(position).getCityName());
		return convertView;
	}

}
