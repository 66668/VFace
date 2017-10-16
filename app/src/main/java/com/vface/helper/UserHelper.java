package com.vface.helper;

import android.content.Context;

import com.vface.R;
import com.vface.common.MyApplication;
import com.vface.common.MyHttpException;
import com.vface.common.WebAPI;
import com.vface.db.entity.UserEntity;
import com.vface.utils.APIUtils;
import com.vface.utils.ConfigUtil;
import com.vface.utils.HttpParameter;
import com.vface.utils.HttpResult;
import com.vface.utils.JSONUtils;
import com.vface.utils.NetworkManager;

/**
 * 用户帮助类（包含当前登录的用户信息和用户的一些基本操作）
 * 
 * @author don
 * 
 */
public class UserHelper {
	static UserEntity mCurrentUser = null;
 

	static String configAccount = null;

	/**
	 * 获取配置的用户账号
	 * 
	 * @return
	 */
	public static String getConfigUserAccount() {
		if (configAccount == null) {
			ConfigUtil config = new ConfigUtil(MyApplication.getInstance());
			configAccount = config.getAccount();
		}
		return configAccount;
	}

	static String configUserId = null;

	/**
	 * 获取配置的用户账号
	 * 
	 * @return
	 */
	public static String getConfigUserId() {
		if (configUserId == null) {
			ConfigUtil config = new ConfigUtil(MyApplication.getInstance());
			configUserId = config.getUserId() + "";
		}
		return configUserId;
	}

	/**
	 * 获取当前登录的用户
	 * 
	 * @param isAutoLoad 
	 * @return
	 */
	public static UserEntity getCurrentUser(boolean isAutoLoad) {
		if (mCurrentUser == null && isAutoLoad) {
			ConfigUtil config = new ConfigUtil(MyApplication.getInstance());
			String account = config.getAccount();
			if (!"".equals(account)) {
				mCurrentUser = config.getUserEntity();
			}
		}
		return mCurrentUser;
	}

	/**
	 * 修改密码
	 * 
	 * @param context
	 * @param oldPwd
	 * @param newPwd
	 * @return
	 * @throws MyHttpException
	 */
	public static String changePassword(Context context, String oldPwd,
			String newPwd) throws MyHttpException {
		if (!NetworkManager.isNetworkAvailable(context)) {
			throw new MyHttpException(R.string.network_invalid);
		}

		HttpResult hr = APIUtils.postForObject(
				WebAPI.User.CHANGE_PASSWORD_URL,
				HttpParameter.create().add("UserId", getCurrentUser().getUserId() + "")
						.add("OldPassword", oldPwd)
						.add("newpassword", newPwd)
                        );
		if (hr.hasError()) {
			throw hr.getError();
		}
		return hr.Message;
	}

	public static void setPushKey(Context context, String pushKey)
			throws MyHttpException {
		if (!NetworkManager.isNetworkAvailable(context)) {
			throw new MyHttpException(R.string.network_invalid);
		}

		HttpResult hr = APIUtils.postForObject(
				WebAPI.Common.SEND_PUSH_KEY_URL,
				HttpParameter.create()
						.add("StoreUserId", getCurrentUser().getUserId() + "")
						.add("DeviceType", "2")
                        .add("PushKey", pushKey)
						.add("IsOnline", "true"));
		if (hr.hasError()) {
			throw hr.getError();
		}
	}
	
	public static void setPushOffline(Context context, String pushKey)
			throws MyHttpException {
		if (!NetworkManager.isNetworkAvailable(context)) {
			throw new MyHttpException(R.string.network_invalid);
		}


        HttpResult hr = APIUtils.postForObject(
                WebAPI.Common.SEND_PUSH_KEY_URL,
                HttpParameter.create()
                        .add("StoreUserId", getCurrentUser().getUserId() + "")
                        .add("DeviceType", "2")
                        .add("PushKey", pushKey)
                        .add("IsOnline", "false"));
        if (hr.hasError()) {
            throw hr.getError();
        }
	}

	/**
	 * 获取当前用户
	 * 
	 * @return
	 */
	public static UserEntity getCurrentUser() {
		return getCurrentUser(true);
	}

	/**
	 * 在线登录
	 */
	public static void loginOnline(Context context,String adminusername, String username,
			String password) throws MyHttpException {
		if (!NetworkManager.isNetworkAvailable(context)) {
			throw new MyHttpException(R.string.network_invalid);
		}

		HttpResult hr = APIUtils.postForObject(
				WebAPI.User.LOGIN_URL,
				HttpParameter.create()
                        .add("adminUserName", adminusername)
						.add("userName", username)
                        .add("password", password));
		if (hr.hasError()) {
			throw hr.getError();
		}
		UserEntity uEntity = new UserEntity();
		uEntity.setUserId(JSONUtils.getString(hr.Data, "StoreUserId"));
		uEntity.setFullname(JSONUtils.getString(hr.Data, "FullName"));
		uEntity.setStoreId(JSONUtils.getString(hr.Data, "StoreId"));
		uEntity.setStoreName(JSONUtils.getString(hr.Data, "StoreName"));
        uEntity.setUserPicture(JSONUtils.getString(hr.Data,"UserPicture"));
		uEntity.setAccount(username);
		uEntity.setPassword(password);
		//中断保存数据
		ConfigUtil config = new ConfigUtil(MyApplication.getInstance());
		config.setAccount(username);
		config.setUserEneity(uEntity);
        config.setShopAccount(adminusername);
		mCurrentUser = uEntity;
	} 
	/**
	 * Execute Logout
	 * 
	 * @param context
	 * @throws MyHttpException
	 */
	public static void logout(Context context)
			throws MyHttpException {
		try {
			ConfigUtil configUtil = new ConfigUtil(context);
			configUtil.setAutoLogin(false);
			MyApplication.getInstance().setIsLogin(false);
			setPushOffline(context, configUtil.getPushId());  
		} finally {
			configAccount = null;
			mCurrentUser = null;
		}
	}

}
