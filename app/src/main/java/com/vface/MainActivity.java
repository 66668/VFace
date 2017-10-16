package com.vface;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.igexin.sdk.PushManager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.vface.bizmodel.UpgradeModel;
import com.vface.common.BaseActivity;
import com.vface.common.ImageLoadingConfig;
import com.vface.common.MyApplication;
import com.vface.common.MyHttpException;
import com.vface.dialog.Loading;
import com.vface.dialog.UpdateAppDialog;
import com.vface.dialog.UpdateAppDialog.UpdateAppDialogCallBack;
import com.vface.helper.AppHelper;
import com.vface.helper.UserHelper;
import com.vface.inject.ViewInject;
import com.vface.utils.DeleteFileUtil;
import com.vface.utils.IntentUtil;
import com.vface.utils.Utils;

public class MainActivity extends BaseActivity {

	private boolean ifClearing = false;// 是否正在清除缓存

	@ViewInject(id = R.id.imgHeader)
    ImageView imgHeader;

	@ViewInject(id = R.id.txtUserName)
	TextView txtUserName;

	@ViewInject(id = R.id.txtCompany)
	TextView txtCompany;

	final int UPGRADE = 2001;
	final int LOGOUT = 2002;

	private ImageLoader imgLoader;
	@SuppressWarnings("unused")
	private DisplayImageOptions imgOptions;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        imgLoader = ImageLoader.getInstance();
        imgOptions = ImageLoadingConfig.generateDisplayImageOptions(R.drawable.icon_header);
		if (!MyApplication.getInstance().isLogin()) {
			this.finish();
			startActivity(WelcomeActivity.class);
			return;
		} else {
            if(!TextUtils.isEmpty(UserHelper.getCurrentUser().getUserPicture())){
                imgLoader.displayImage(UserHelper.getCurrentUser().getUserPicture(),
                        imgHeader,imgOptions);
            }
            if(TextUtils.isEmpty(UserHelper.getCurrentUser().getFullname())){
                txtUserName.setText(UserHelper.getCurrentUser().getAccount());
            }else{
                txtUserName.setText(UserHelper.getCurrentUser().getFullname());
            }
			txtCompany.setText(UserHelper.getCurrentUser().getStoreName());
		}
		imgLoader = ImageLoader.getInstance();
		imgLoader.init(ImageLoaderConfiguration.createDefault(this));
		imgOptions = ImageLoadingConfig
				.generateDisplayImageOptions(R.drawable.photo);
		PushManager.getInstance().initialize(this.getApplicationContext());
	}
	
	

	@Override
	protected void onResume() { 
		super.onResume();
		if(MyApplication.getInstance().ClickMember!=null){
			String storeMemberId = MyApplication.getInstance().ClickMember.getStoreMemberId();
            String memberId = MyApplication.getInstance().ClickMember.getMemberId();
//			String visitTime = MyApplication.getInstance().ClickMember.getStrCreateTime();
            if(memberId != null || storeMemberId != null){
                Bundle bundle = new Bundle();
                bundle.putString("memberId", memberId);
//			bundle.putString("visitTime", visitTime);
                bundle.putString("from","PUSH");
                startActivity(UserInfoActivity.class, bundle);
            }
		}
	}


    /**
     * 创建会员
     * @param v
     */
	public void btnCreateUserClick(View v) {
		startActivity(CreateUserActivity1.class);
	}

	/**
	 * 会员轨迹
	 * 
	 * @param v
	 */
	public void btnPathClick(View v) {
		startActivity(UserListActivity.class);
	}

	/**
	 * 修改密码
	 * 
	 * @param v
	 */
	public void btnChangePwdClick(View v) {
		startActivity(ChangePasswordActivity.class);
	}

	/**
	 * 升级版本
	 * 
	 * @param v
	 */
	public void btnUpgradeClick(View v) {
		Loading.run(this, new Runnable() {

			@Override
			public void run() {
				try {
					UpgradeModel upgradeModel = AppHelper.getUpgradeInfo(
							MainActivity.this,
							Utils.getVersionCode(MainActivity.this));
					if (upgradeModel.isIsexistsnewversion()) {
						sendMessage(UPGRADE, upgradeModel);
					} else {
						sendToastMessage(R.string.last_version_already);
					}
				} catch (MyHttpException e) {
					e.printStackTrace();
					sendToastMessage(e.getMessage());
				}
			}
		});
	}

	@Override
	protected void handleMessage(Message msg) {
		super.handleMessage(msg);
		switch (msg.what) {
		case LOGOUT:
            PushManager.getInstance().turnOffPush(this);
			this.finish();
			ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
			manager.killBackgroundProcesses(getPackageName());
			break;
		case UPGRADE:
			final UpgradeModel upgradeModel = (UpgradeModel) msg.obj;
			new UpdateAppDialog(this,
					upgradeModel.getMessage(), false,
					new UpdateAppDialogCallBack() {

						@Override
						public void confirm() {
							Intent intent = IntentUtil.getBrowserIntent(upgradeModel.getPackageUrl());
							startActivity(intent);
						}

						@Override
						public void cancel() {

						}
					}).show();
			break;

		default:
			break;
		}
	}

	/**
	 * 清除缓存
	 * 
	 * @param v
	 */
	public void btnClearClick(View v) {
		if (!ifClearing) {
			Toast.makeText(this, R.string.clearing_cache, Toast.LENGTH_SHORT)
					.show();
			ifClearing = true;
			new ClearCacheThread().start();
		}
	}


    class ClearCacheThread extends Thread {

        @Override
        public void run() {
            DeleteFileUtil.deleteFolder(MyApplication
                    .getPicCachePath(MainActivity.this));
            handler.post(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(MainActivity.this,
                            R.string.clear_cache_success, Toast.LENGTH_SHORT)
                            .show();
                    ifClearing = false;
                }
            });
        }

    }

	/**
	 * 退出
	 * 
	 * @param v
	 */
	public void btnExitClick(View v) {
		Loading.run(this, new Runnable() {
			@Override
			public void run() {
				try {
					UserHelper.logout(MainActivity.this);
					sendMessage(LOGOUT);
				} catch (MyHttpException e) {
					e.printStackTrace();
					if (TextUtils.isEmpty(e.getMessage())) {
						sendToastMessage("注销失败!");
					} else {
						sendToastMessage(e.getMessage());
					}
				}
			}
		});
	}

    /**
     * 会员管理
     */
    public void btnManageUserClick(View v){

        startActivity(ManageUserActivity.class);

    }


    /**
     * 消费管理
     */
    public void btnManageConsumeClick(View v){

        startActivity(ConsumeHistoryActivity.class);
    }

    /**
     * 导购
     */
    public void btnGuideShopingClick(View v){
        startActivity(GuideShopingActivity.class);
    }

    /**
     * 商品查询
     */
    public void btnGoodsSearchClick(View v){
        startActivity(GoodsSearchActivity.class);
    }

    /**
     * 数据汇总
     */
    public void btnDataGlossaryClick(View v){
        startActivity(DataGlossaryActivity.class);
    }

}
