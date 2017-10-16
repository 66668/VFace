package com.vface.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.vface.db.entity.UserEntity;

import org.json.JSONObject;

public class ConfigUtil {
    protected SharedPreferences sp;
    protected SharedPreferences.Editor editor;
	private static final String USERID = "userid";


    private static final String SHOP_ACCOUNT = "shop_account";
    private static final String ACCOUNT = "account";
    private static final String AUTO_LOGIN = "auto_login";
	private static final String USERENTITY = "user_entity";
	private static final String PUSH_ID = "push_id";
    private int shopAccount;

    @SuppressLint("CommitPrefEdits")
	public ConfigUtil(Context context) {
		try {
			sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
			editor = sp.edit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void resetConfig() {
		setUserId(null);
		setAccount(""); 
		setAutoLogin(true);
	}

	public void put(String key, String value) {
		editor.putString(key, value);
		editor.commit();
	}

	public String get(String key) {
		return sp.getString(key, "");
	}

	public void putBoolean(String key, Boolean value) {
		editor.putBoolean(key, value);
		editor.commit();
	}

	public Boolean getBoolean(String key) {
		return sp.getBoolean(key, false);
	}
	

	public UserEntity getUserEntity() {
		String string = sp.getString(USERENTITY, null);
		if(string!=null && string.length()>0){
			try {
				return (UserEntity)UserEntity.toEntityBase(new JSONObject(string), UserEntity.class);
			} catch (Exception e) {
				e.printStackTrace(); 
			} 
		}
		return null;
	}
	
	public void setUserEneity(UserEntity userEntity) {
		editor.putString(USERENTITY, userEntity.toJSON().toString());
		editor.commit();
	}
	
	
	public String getUserId() {
		return sp.getString(USERID, null);
	}
	
	public void setUserId(String id) {
		editor.putString(USERID, id);
		editor.commit();
	}

	public String getAccount() {
		return sp.getString(ACCOUNT, "");
	}

	public void setAccount(String account) {
		editor.putString(ACCOUNT, account);
		editor.commit();
	}
 
	public boolean getAutoLogin() {
		return sp.getBoolean(AUTO_LOGIN, true);
	}

	public void setAutoLogin(boolean play) {
		editor.putBoolean(AUTO_LOGIN, play);
		editor.commit();
	} 

	public void setPushId(String pushId) {
		editor.putString(PUSH_ID, pushId);
		editor.commit();
	}
	
	public String getPushId() {
		return sp.getString(PUSH_ID, "");
	}


    public String getShopAccount() {
        return sp.getString(SHOP_ACCOUNT, "");
    }

    public void setShopAccount(String shopAccount){
        editor.putString(SHOP_ACCOUNT, shopAccount);
        editor.commit();

    }
}
