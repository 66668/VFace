package com.vface.helper;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vface.R;
import com.vface.bizmodel.GradeModel;
import com.vface.common.MyHttpException;
import com.vface.common.WebAPI;
import com.vface.utils.APIUtils;
import com.vface.utils.HttpResult;
import com.vface.utils.NetworkManager;

/**
 * 用户的一些信息的处理接口
 * @author Fangweidong
 *
 */
public class ProfileHelper {

    /**
     * 获取省份列表
     * @param context
     * @return
     * @throws MyHttpException
     */
    public static JSONArray getProvinceList(Context context) throws MyHttpException {

        if (!NetworkManager.isNetworkAvailable(context)) {
            throw new MyHttpException(R.string.network_invalid);
        }

        String url = WebAPI.Profile.GET_PROVINCE_URL;

        HttpResult hr = APIUtils.getResult(url);

        if (hr.hasError()) {
            throw hr.getError();
        }
        return hr.DataList;

    }

    /**
     * 获取该省城市列表
     * @param context
     * @param provinceID
     * @return
     * @throws MyHttpException
     */
    public static JSONArray getCityList(Context context, String provinceID) throws MyHttpException {

        if (!NetworkManager.isNetworkAvailable(context)) {
            throw new MyHttpException(R.string.network_invalid);
        }

        String url = WebAPI.Profile.GET_CITY_URL+provinceID;

        HttpResult hr = APIUtils.getResult(url);

        if (hr.hasError()) {
            throw hr.getError();
        }
        return hr.DataList;
    }


    public static JSONArray getAreaList(Context context, String cityId) throws MyHttpException {

        if (!NetworkManager.isNetworkAvailable(context)) {
            throw new MyHttpException(R.string.network_invalid);
        }

        String url = WebAPI.Profile.GET_DISTRICT_URL+cityId;

        HttpResult hr = APIUtils.getResult(url);

        if (hr.hasError()) {
            throw hr.getError();
        }
        return hr.DataList;

    }

    /**
     * 获取该城市区域列表
     * @param context
     * @param cityID
     * @return
     * @throws MyHttpException
     */
    public static JSONArray getDistrictList(Context context, String cityID) throws MyHttpException {

        if (!NetworkManager.isNetworkAvailable(context)) {
            throw new MyHttpException(R.string.network_invalid);
        }

        String url = WebAPI.Profile.GET_DISTRICT_URL+cityID;

        HttpResult hr = APIUtils.getResult(url);

        if (hr.hasError()) {
            throw hr.getError();
        }
        return hr.DataList;
    }

    public static ArrayList<GradeModel> getGradeList(Context context) throws MyHttpException {

        if (!NetworkManager.isNetworkAvailable(context)) {
            throw new MyHttpException(R.string.network_invalid);
        }

        String storeId = UserHelper.getCurrentUser().getStoreId();
        String url = WebAPI.Profile.GET_MEMBER_GRADE+storeId;

        HttpResult hr = APIUtils.getResult(url);

        if (hr.hasError()) {
            throw hr.getError();
        }
        return (new Gson()).fromJson(hr.DataList.toString(),
                new TypeToken<List<GradeModel>>() {
                }.getType());

    }


    /**
	 * 修改头像
	 * 
	 * @param context
	 */
//	public static String updateAvatar(Context context, String avatarBase64)
//			throws MyHttpException {
//		HttpResult result = APIUtils.postForObject(WebAPI.User.USER_URL,
//				HttpParameter.create().add("avatar", avatarBase64));
//		if (result.hasError()) {
//			throw result.getError();
//		}
//		try {
//			return result.Data.getString("avatar");
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}

//		return null;
//	}



}
