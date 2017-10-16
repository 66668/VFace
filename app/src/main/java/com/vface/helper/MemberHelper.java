package com.vface.helper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vface.R;
import com.vface.bizmodel.ExpenseListModel;
import com.vface.bizmodel.MemberGradeModel;
import com.vface.bizmodel.MemberModel;
import com.vface.bizmodel.MemberPageModel;
import com.vface.bizmodel.VisitRecordModel;
import com.vface.common.MyHttpException;
import com.vface.common.WebAPI;
import com.vface.utils.APIUtils;
import com.vface.utils.HttpParameter;
import com.vface.utils.HttpResult;
import com.vface.utils.JSONUtils;
import com.vface.utils.NetworkManager;

public class MemberHelper {

    /**
     * 获取会员列表
     * @param context
     * @param pageIndex
     * @param pageSize
     * @return
     * @throws MyHttpException
     */
    public static MemberPageModel getMemberList(Context context,int pageIndex,int pageSize,String storeId,String searchTxt) throws MyHttpException {

        if (!NetworkManager.isNetworkAvailable(context)) {
            throw new MyHttpException(R.string.network_invalid);
        }

        String url = WebAPI.Member.GET_STORE_MEMBER_LIST + pageIndex + "/" + pageSize;

        url += "?StoreId=";
        url += storeId;
        url += "&SearchTxt=";
        url += searchTxt;

        HttpResult hr = APIUtils.getResult(url);

        if (hr.hasError()) {
            throw hr.getError();
        }

        MemberPageModel model = new MemberPageModel();
        model.setTotal(JSONUtils.getInt(hr.Data,"total"));

        JSONArray ja = JSONUtils.getJSONArray(hr.Data, "rows");
        ArrayList<MemberModel> rows = (new Gson()).fromJson(ja.toString(),
                new TypeToken<List<MemberModel>>() {
                }.getType());

        model.setRows(rows);

        return model;
    }

	/**
	 * 获取会员信息
	 * 
	 * @param context
	 * @throws MyHttpException
	 */
	public static MemberModel getMember(Context context, String memberId)
			throws MyHttpException {

		if (!NetworkManager.isNetworkAvailable(context)) {
			throw new MyHttpException(R.string.network_invalid);
		}

        String url = WebAPI.Member.GET_STORE_MEMBER_INFO + memberId;

        HttpResult hr = APIUtils.getResult(url);

		if (hr.hasError()) {
			throw hr.getError();
		}
		return (new Gson()).fromJson(hr.Data.toString(), MemberModel.class);
	}

	/**
	 * 获取当日会员到店记录
	 * 
	 * @param context
	 * @throws MyHttpException
	 */
	public static List<VisitRecordModel> getVisitRecord(Context context, String storeId) throws MyHttpException {

		if (!NetworkManager.isNetworkAvailable(context)) {
			throw new MyHttpException(R.string.network_invalid);
		}

        String url = WebAPI.Member.GET_VISIT_RECORD + storeId;

        HttpResult hr = APIUtils.getResult(url);

		if (hr.hasError()) {
			throw hr.getError();
		}
		return (new Gson()).fromJson(hr.DataList.toString(),
				new TypeToken<List<VisitRecordModel>>() {
				}.getType());
	}

    /**
     * 检查会员登记情况
     * @param context
     * @param storeId
     * @param phoneNum
     * @throws MyHttpException
     */
    public static MemberModel checkMemberRegister(Context context,String storeId,String phoneNum) throws MyHttpException{

        if (!NetworkManager.isNetworkAvailable(context)) {
            throw new MyHttpException(R.string.network_invalid);
        }

        String url = WebAPI.Member.CHECK_MEMBER_REGISTER + storeId + "/" +phoneNum;

        HttpResult hr = APIUtils.getResult(url);

        if (hr.hasError()) {
            throw hr.getError();
        }

        if(hr.Data != null){
            return getMemberFromJson(context,hr.Data.toString());
        }

        return null;


    }

	/**
	 * 创建会员
	 * 
	 * @param context
	 * @param parameters
	 * @throws MyHttpException
	 */
	public static void createMember(Context context,
			HashMap<String, String> parameters) throws MyHttpException {

		if (!NetworkManager.isNetworkAvailable(context)) {
			throw new MyHttpException(R.string.network_invalid);
		}
		HttpParameter httpParameter = HttpParameter.create();
		for (String key : parameters.keySet()) {
			httpParameter.add(key, parameters.get(key));
		}
		HttpResult hr = APIUtils.postForObject(WebAPI.Member.CREATE_MEMBER,
				httpParameter);
		if (hr.hasError()) {
			throw hr.getError();
		}
	}

    /**
     * 修改会员
     * @param context
     * @param parameters
     * @throws MyHttpException
     */
    public static void editMember(Context context,
                                    HashMap<String, String> parameters) throws MyHttpException {

        if (!NetworkManager.isNetworkAvailable(context)) {
            throw new MyHttpException(R.string.network_invalid);
        }
        HttpParameter httpParameter = HttpParameter.create();
        for (String key : parameters.keySet()) {
            httpParameter.add(key, parameters.get(key));
        }
        HttpResult hr = APIUtils.postForObject(WebAPI.Member.EDIT_MEMBER,
                httpParameter);
        if (hr.hasError()) {
            throw hr.getError();
        }
    }

    /**
     * 获取此店铺会员等级
     * @param context
     * @param storeId
     * @return
     * @throws MyHttpException
     */
    public static List<MemberGradeModel> getMemberGrade(Context context, String storeId) throws MyHttpException {

        if (!NetworkManager.isNetworkAvailable(context)) {
            throw new MyHttpException(R.string.network_invalid);
        }

        String url = WebAPI.Member.GET_MEMBER_GRADE + storeId;

        HttpResult hr = APIUtils.getResult(url);

        if (hr.hasError()) {
            throw hr.getError();
        }
        return (new Gson()).fromJson(hr.DataList.toString(),
                new TypeToken<List<MemberGradeModel>>() {
                }.getType());
    }


	public static MemberModel getMemberFromJson(Context context, String data) {
		return (new Gson()).fromJson(data, MemberModel.class);
	}

    /**
     * 获取用户消费记录
     * @param context
     * @param pageIndex
     * @param pageSize
     * @return
     * @throws MyHttpException
     */
    public static ArrayList<ExpenseListModel> getExpenseList(Context context,String vfaceMemberCode,int pageIndex,int pageSize) throws MyHttpException{

        if (!NetworkManager.isNetworkAvailable(context)) {
            throw new MyHttpException(R.string.network_invalid);
        }

        int expenseType = 0;//TODO

        String storeName = UserHelper.getCurrentUser().getStoreName();
        String url = WebAPI.Member.GET_USER_EXPENSE_LIST_URL;
        String startDate = "1970/1/1";
//        String endDate =

        Date dt = new Date();
        //最后的aa表示“上午”或“下午”    HH表示24小时制    如果换成hh表示12小时制
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String endDate=sdf.format(dt);

        url += "vfaceMemberCode=" + vfaceMemberCode;
        url += "&expenseType=" +expenseType;
        url += "&pageIndex=" +pageIndex;
        url += "&pageSize=" +pageSize;
        url += "&storename=" + storeName;
        url += "&startDate=" + startDate;
        url += "&endDate="+ endDate;

        HttpResult hr = APIUtils.getResult(url);

        if (hr.hasError()) {
            throw hr.getError();
        }

        return (new Gson()).fromJson(hr.DataList.toString(),
                new TypeToken<List<ExpenseListModel>>() {
                }.getType());

    }
}
