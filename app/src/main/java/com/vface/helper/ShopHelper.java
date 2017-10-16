package com.vface.helper;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vface.R;
import com.vface.bizmodel.CommodityModel;
import com.vface.bizmodel.DataGlossaryModel;
import com.vface.bizmodel.ExpenseDetailModel;
import com.vface.bizmodel.ExpenseListModel;
import com.vface.bizmodel.ShopModel;
import com.vface.common.MyHttpException;
import com.vface.common.WebAPI;
import com.vface.utils.APIUtils;
import com.vface.utils.ConfigUtil;
import com.vface.utils.HttpParameter;
import com.vface.utils.HttpResult;
import com.vface.utils.NetworkManager;

/**
 * Created by HuBin on 15/4/23.
 */
public class ShopHelper {

    /**
     * 商家详细信息
     * @param context
     * @return
     * @throws MyHttpException
     */
    public static ShopModel getShopDetailInfo(Context context,String storeId) throws MyHttpException {

        if (!NetworkManager.isNetworkAvailable(context)) {
            throw new MyHttpException(R.string.network_invalid);
        }

        String url = WebAPI.Shop.SHOP_DETAIL_INFO + storeId;

        HttpResult hr = APIUtils.getResult(url);

        if (hr.hasError()) {
            throw hr.getError();
        }

        return (new Gson()).fromJson(hr.returnObject.toString(), ShopModel.class);

    }

    /**
     * 获取门店消费列表
     * @param context
     * @return
     * @throws MyHttpException
     */
    public static ArrayList<ExpenseListModel> getExpenseListModel(Context context,String storeId,String keywords
        ,String endDate,String startDate,int pageIndex,int pageSize,int expenseType) throws MyHttpException {

        if (!NetworkManager.isNetworkAvailable(context)) {
            throw new MyHttpException(R.string.network_invalid);
        }

        String url = WebAPI.Shop.SHOP_CONSUME_LIST_URLV2;

        url += "storeId=" + storeId;
        url += "&keywords=" + keywords;
        url += "&endDate=" +endDate;
        url += "&expenseType=" +expenseType;
        url += "&pageIndex=" +pageIndex;
        url += "&pageSize=" +pageSize;
        url += "&startDate=" +startDate;

        HttpResult hr = APIUtils.getResult(url);

        if (hr.hasError()) {
            throw hr.getError();
        }

        return (new Gson()).fromJson(hr.DataList.toString(),
                new TypeToken<List<ExpenseListModel>>() {
                }.getType());

    }

    /**
     * 获取门店消费明细
     * @param context
     * @param storeId
     * @param commodityExpenseId
     * @param expenseType
     * @return
     * @throws MyHttpException
     */
    public static ArrayList<ExpenseDetailModel> getExpenseDetailModel(Context context,String storeId,String commodityExpenseId
            ,int expenseType) throws MyHttpException {

        if (!NetworkManager.isNetworkAvailable(context)) {
            throw new MyHttpException(R.string.network_invalid);
        }

        String url = WebAPI.Shop.SHOP_CONSUME_DETAIL_URL;

        url += "commodityExpenseId=" + commodityExpenseId;
        url += "&storeId=" + storeId;
        url += "&expenseType=" +expenseType;

        HttpResult hr = APIUtils.getResult(url);

        if (hr.hasError()) {
            throw hr.getError();
        }

        return (new Gson()).fromJson(hr.DataList.toString(),
                new TypeToken<List<ExpenseDetailModel>>() {
                }.getType());

    }

    /**
     * 获取总消费金额
     * @param context
     * @param storeId
     * @param startDate
     * @param endDate
     * @return
     * @throws MyHttpException
     */
    public static String getExpenseTotal(Context context,String storeId,String startDate,String endDate) throws MyHttpException {

        if (!NetworkManager.isNetworkAvailable(context)) {
            throw new MyHttpException(R.string.network_invalid);
        }

        String url = WebAPI.Shop.SHOP_TOTAL_COMMODITY_URL;

        url += "?storeId=" + storeId;
        url += "&startDate=" + startDate;
        url += "&endDate=" +endDate;

        HttpResult hr = APIUtils.getResult(url);

        if (hr.hasError()) {
            throw hr.getError();
        }

        return hr.resultString;

    }

    /**
     * 商品查询
     * @param context
     * @param storeId
     * @param commodityNameOrCode
     * @param pageIndex
     * @param pageSize
     * @return
     * @throws MyHttpException
     */
    public static ArrayList<CommodityModel> getCommodityModel(Context context,String storeId,String commodityNameOrCode
            ,int pageIndex,int pageSize) throws MyHttpException {

        if (!NetworkManager.isNetworkAvailable(context)) {
            throw new MyHttpException(R.string.network_invalid);
        }

        String url = WebAPI.Shop.SHOP_COMMODITY_LIST_URL;

        url += "storeId=" + storeId;
        url += "&commodityNameOrCode=" + commodityNameOrCode;
        url += "&pageIndex=" +pageIndex;
        url += "&pageSize=" +pageSize;

        HttpResult hr = APIUtils.getResult(url);

        if (hr.hasError()) {
            throw hr.getError();
        }

        return (new Gson()).fromJson(hr.DataList.toString(),
                new TypeToken<List<CommodityModel>>() {
                }.getType());

    }


    /**
     * 挂单
     * @param context
     * @param storeMemberId
     * @param vfaceMemberCode
     * @param orderDetailsJsonString
     * @return
     * @throws MyHttpException
     */
    public static String pendOrder(Context context,String storeMemberId,String vfaceMemberCode,String orderDetailsJsonString) throws MyHttpException {
        if (!NetworkManager.isNetworkAvailable(context)) {
            throw new MyHttpException(R.string.network_invalid);
        }

        String storeId = UserHelper.getCurrentUser().getStoreId();

        String Operator = new ConfigUtil(context).getAccount();

        String url = WebAPI.Shop.PEND_ORDER_URL + "storeId="+storeId
                +"&storeMemberId="+storeMemberId
                +"&vfaceMemberCode="+vfaceMemberCode
                +"&Operator="+Operator
                +"&orderDetailsJsonString="+orderDetailsJsonString;
        HttpResult hr = APIUtils.postForObject(
                url,
                HttpParameter.create()
        );

        if (hr.hasError()) {
            throw hr.getError();
        }

        return hr.resultString;
    }

    public static DataGlossaryModel getDataGlossary(Context context,String startDate,String endDate) throws MyHttpException {

        if (!NetworkManager.isNetworkAvailable(context)) {
            throw new MyHttpException(R.string.network_invalid);
        }

        String storeId = UserHelper.getCurrentUser().getStoreId();

        String url = WebAPI.Shop.SHOP_DATA_GLOSSARY + "storeId="+storeId
                +"&startDate="+startDate
                +"&endDate="+endDate;

        HttpResult hr = APIUtils.getResult(url);

        if (hr.hasError()) {
            throw hr.getError();
        }

        return (new Gson()).fromJson(hr.Data.toString(), DataGlossaryModel.class);

    }


}
