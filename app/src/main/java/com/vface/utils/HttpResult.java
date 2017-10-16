package com.vface.utils;

import com.vface.common.MyHttpDataException;

import org.json.JSONArray;
import org.json.JSONObject;

public class HttpResult { 
	public Object returnObject;
	public int Status = 0;
	public String Message = "";
	public JSONObject Data;
	public JSONArray DataList;
    public String resultString;
	
	public static HttpResult createError(String msg) {
		HttpResult result =  new HttpResult();
		result.Status = 1;
		result.Message = msg;
		return result;
	}
	
	public static HttpResult createError(Exception ex) {
		return createError(ex.getMessage());
	}

	public boolean hasError() { 
		return Status == 0;
	}

	public MyHttpDataException getError() {
		return new MyHttpDataException(Message,Status);
	}
}
