package com.vface.utils;

import android.content.Context;

import com.vface.R;

public class GenderUtil {
	/**
	 * 
	 * @param context
	 * @param gender
	 * @return
	 */
	public static String getGenderString(Context context,int gender){
		
		switch (gender) {
		case 1:
			return context.getString(R.string.male);
		case 2:
			return context.getString(R.string.female);
		default:
			return context.getString(R.string.unknow);
		}
		
	}
	
}
