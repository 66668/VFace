package com.vface;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.vface.adapter.CommonListAdapter.AdapterCallBack;
import com.vface.adapter.UserListAdapter;
import com.vface.bizmodel.VisitRecordModel;
import com.vface.common.BaseActivity;
import com.vface.common.ImageLoadingConfig;
import com.vface.common.MyHttpException;
import com.vface.dialog.Loading;
import com.vface.helper.MemberHelper;
import com.vface.helper.UserHelper;
import com.vface.inject.ViewInject;

public class UserListActivity extends BaseActivity {

	@ViewInject(id = R.id.imgBack, click = "imgBackClick")
	View imgBack;

	@ViewInject(id = R.id.listview)
	ListView listView;
	UserListAdapter mAdapter;

	@SuppressWarnings("unused")
	private DisplayImageOptions imgOption;

	@SuppressWarnings("unused")
	private final int PAGE_SIZE = 10;
	private boolean ifLoading = false;

	private final int GET_DATA_SUCCESS = -9;
	private final int GET_DATA_FAILED = -8;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_list);
		imgOption = ImageLoadingConfig
				.generateDisplayImageOptions(R.drawable.photo);
		mAdapter = new UserListAdapter(this, adapterCallBack);
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				VisitRecordModel visitRecordModel = ((VisitRecordModel) mAdapter.getItem(position));

                if(TextUtils.isEmpty(visitRecordModel.getMemberId())){//若为唯脸会员
                    Intent intent = new Intent(UserListActivity.this,UserInfoActivity.class);
                    intent.putExtra("visitRecordModel", visitRecordModel);
                    startActivity(intent);
                }else{
                    String memberId = visitRecordModel.getMemberId();
                    String visitTime = visitRecordModel.getCreateTime();
                    Bundle bundle = new Bundle();
                    bundle.putString("memberId", memberId);
                    bundle.putString("visitTime", visitTime);
                    Intent intent = new Intent(UserListActivity.this,UserInfoActivity.class);
                    intent.putExtras(bundle);
                    startActivityForResult(intent,UserInfoActivity.REQUEST_CODE_FOR_EDIT_USER);
                }
			}
		});
		getData();
	}

	AdapterCallBack adapterCallBack = new AdapterCallBack() {

		@Override
		public void loadMore() {
//			if (!ifLoading) {
//				ifLoading = true;
//				getData();
//			}
		}
	};

	private void getData() {
		Loading.run(this, new Runnable() {

			@Override
			public void run() {
				List<VisitRecordModel> arrayList = new ArrayList<VisitRecordModel>();
				try {
					arrayList = MemberHelper.getVisitRecord(
							UserListActivity.this, UserHelper.getCurrentUser()
									.getStoreId());
					mAdapter.IsEnd = true;// 标识列表获取结束了，没有翻页
				} catch (MyHttpException e) {
					e.printStackTrace();
					sendToastMessage(e.getMessage());
				}
				ifLoading = false;
				sendMessage(GET_DATA_SUCCESS, arrayList);
			}
		});
	}

	public void imgBackClick(View v) {
		this.finish();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void handleMessage(Message msg) {

		switch (msg.what) {
		case GET_DATA_SUCCESS:
			mAdapter.setEntityList((ArrayList<VisitRecordModel>) msg.obj);
			break;
		case GET_DATA_FAILED:
			Toast.makeText(this, R.string.network_invalid, Toast.LENGTH_SHORT)
					.show();
			break;
		default:
			break;
		}
		super.handleMessage(msg);
	}

	@Override
	protected void onDestroy() {
		mAdapter.destroy();
		super.onDestroy();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == UserInfoActivity.REQUEST_CODE_FOR_EDIT_USER){
    		if(resultCode == EditUserActivity.RESULT_CODE_FOR_EDIT_USER_SUCCESS){
    			getData();
    		}
    	}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
