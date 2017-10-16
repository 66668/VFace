package com.vface.helper;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.vface.R;
import com.vface.bizmodel.UpgradeModel;
import com.vface.common.MyHttpException;
import com.vface.common.WebAPI;
import com.vface.utils.APIUtils;
import com.vface.utils.HttpResult;
import com.vface.utils.NetworkManager;

/**
 * 系统相关 - 检查更新之类的
 * 
 * @author Fangweidong
 * 
 */
public class AppHelper {


    /**
     * 检测是否有可升级的版本
     * @param context
     * @param currentVersion
     * @return
     * @throws MyHttpException
     */
	public static UpgradeModel getUpgradeInfo(Context context, int currentVersion) throws MyHttpException {

        if (!NetworkManager.isNetworkAvailable(context)) {
            throw new MyHttpException(R.string.network_invalid);
        }

        String url = WebAPI.Common.CLIENT_UPGRADE_URL;

        url += "2/";//手机类型DeviceType(1,iOS 2,Android)
        url += "1/";//客户端类型ClientType(1,商家2,消费者)
        url += currentVersion;//currentVersion

        HttpResult hr = APIUtils.getResult(url);

        if (hr.hasError()) {
            throw hr.getError();
        }

        UpgradeModel model = (new Gson()).fromJson(hr.Data.toString(), UpgradeModel.class);


        if(!TextUtils.isEmpty(model.getPackageUrl())){
            model.setIsexistsnewversion(true);
        }

        return model;

    }

    /**
     * 获取接口调用凭证Accesstoken
     * @param context
     * @return
     * @throws MyHttpException
     */
    public static String getAccessToken(Context context) throws MyHttpException {

        if (!NetworkManager.isNetworkAvailable(context)) {
            throw new MyHttpException(R.string.network_invalid);
        }

        String url = WebAPI.Common.GetAccessToken;

        HttpResult hr = APIUtils.getResult(url);

        if (hr.hasError()) {
            throw hr.getError();
        }

        return hr.Message;

    }




}
