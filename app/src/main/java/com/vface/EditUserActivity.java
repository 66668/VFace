package com.vface;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.vface.adapter.CitySpinnerAdapter;
import com.vface.adapter.DistrictSpinnerAdapter;
import com.vface.adapter.ProvinceSpinnerAdapter;
import com.vface.bizmodel.CityModel;
import com.vface.bizmodel.DistrictModel;
import com.vface.bizmodel.GradeModel;
import com.vface.bizmodel.MemberModel;
import com.vface.bizmodel.ProvinceModel;
import com.vface.common.BaseActivity;
import com.vface.common.ImageLoadingConfig;
import com.vface.common.MyHttpException;
import com.vface.dialog.DatePickerDialog;
import com.vface.dialog.DatePickerDialog.DatePickerDialogCallBack;
import com.vface.dialog.Loading;
import com.vface.helper.MemberHelper;
import com.vface.helper.ProfileHelper;
import com.vface.helper.UserHelper;
import com.vface.inject.ViewInject;
import com.vface.utils.LogUtil;
import com.vface.utils.UpdateAvatarUtil;
import com.vface.utils.UpdateAvatarUtil.ChoosePicCallBack;
import com.vface.widget.CircleImageView;

public class EditUserActivity extends BaseActivity implements ChoosePicCallBack {

	@ViewInject(id = R.id.imgBack, click = "imgBackClick")
	View imgBack;

	@ViewInject(id = R.id.btnSave, click = "btnSaveClick")
	View btnSave;

	@ViewInject(id = R.id.imgPhoto, click = "imgPhotoClick")
	CircleImageView imgPhoto;

	@ViewInject(id = R.id.txtUsername)
	EditText txtUsername;

	@ViewInject(id = R.id.txtNickname)
	EditText txtNickname;

	@ViewInject(id = R.id.radioGender)
	RadioGroup radioGender;

	@ViewInject(id = R.id.txtId)
	EditText txtId;

	@ViewInject(id = R.id.txtBirthday)
	TextView txtBirthday;

	@ViewInject(id = R.id.spinnerProvince)
	Spinner spinnerProvince;

	@ViewInject(id = R.id.spinnerCity)
	Spinner spinnerCity;

	@ViewInject(id = R.id.spinnerArea)
	Spinner spinnerArea;

	@ViewInject(id = R.id.txtAddress)
	EditText txtAddress;

	@ViewInject(id = R.id.txtPhone)
	EditText txtPhone;

	@ViewInject(id = R.id.txtEmail)
	EditText txtEmail;

	@ViewInject(id = R.id.radioStealth)
	RadioGroup radioStealth;

	@ViewInject(id = R.id.radioSpecialFocus)
	RadioGroup radioSpecialFocus;

	@ViewInject(id = R.id.radioCardForever)
	RadioGroup radioCardForever;

	@ViewInject(id = R.id.txtRemark)
	EditText txtRemark;

	@ViewInject(id = R.id.card_num)
	EditText cardNumEdit;

	@ViewInject(id = R.id.card_pwd)
	EditText cardPwdEdit;

	@ViewInject(id = R.id.spinnerGrade)
	Spinner gradeSpinner;

	@ViewInject(id = R.id.card_expired_date)
	TextView txtExpiredDate;

	private UpdateAvatarUtil updateAvatarUtil;
	private DisplayImageOptions imgOption;

	private final int GET_PROVINCE = 2001;
	private final int GET_CITY = 2002;
	final int SUCESS = 2003;
	private static final int GET_GRADE_LIST = 2004;
	private static final int GET_AREA = 2005;
	public static final int RESULT_CODE_FOR_EDIT_USER_SUCCESS = 3003;

	ArrayList<ProvinceModel> provinceList;
	ArrayList<CityModel> cityList;
	ArrayList<DistrictModel> districtList;
	ArrayList<GradeModel> gradeModels;

	ProvinceSpinnerAdapter provinceSpinnerAdapter;
	CitySpinnerAdapter citySpinnerAdapter;
	DistrictSpinnerAdapter districtSpinnerAdapter;

	String avatar = "";
	String areaId = "";
	String cityId = "";
	String provinceId = "";
	String memberGradeId = "";

	private MemberModel vfaceMemberModel;
	private String phoneNum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_user);

		vfaceMemberModel = (MemberModel) getIntent().getSerializableExtra(
				"memberModel");
		phoneNum = getIntent().getStringExtra("phoneNum");
		if (TextUtils.isEmpty(phoneNum)) {
			phoneNum = vfaceMemberModel.getPhoneNumber();
		}

		updateAvatarUtil = new UpdateAvatarUtil(this, handler, this, false);
		imgOption = ImageLoadingConfig
				.generateDisplayImageOptionsNoCatchDisc(R.drawable.photo);
		Loading.run(this, new Runnable() {

			@Override
			public void run() {
				try {
					JSONArray priovinceArray = ProfileHelper
							.getProvinceList(EditUserActivity.this);
					sendMessage(GET_PROVINCE, priovinceArray);
				} catch (MyHttpException e) {
					e.printStackTrace();
					sendToastMessage(e.getMessage());
				}
			}
		});

		Loading.run(this, new Runnable() {

			@Override
			public void run() {
				try {
					ArrayList<GradeModel> gradeList = ProfileHelper
							.getGradeList(EditUserActivity.this);
					sendMessage(GET_GRADE_LIST, gradeList);
				} catch (MyHttpException e) {
					e.printStackTrace();
					sendToastMessage(e.getMessage());
				}
			}
		});

		spinnerProvince.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				try {
					if (provinceList == null) {
						return;
					}
					provinceId = provinceSpinnerAdapter.getItem(position).getProvinceId()+"";
					getCity(provinceId);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		spinnerCity.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				try {
					if (cityList != null) {
						cityId = citySpinnerAdapter.getItem(position).getCityId()+"";
					}
					getArea(cityId);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				cityId = "";

			}
		});

		spinnerArea.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				try {
					if (districtList != null) {
						areaId = districtSpinnerAdapter.getItem(position).getDistrictId()+"";
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		gradeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				if (gradeModels == null) {
					return;
				}
				memberGradeId = gradeModels.get(position).getGradeId();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		txtBirthday.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DatePickerDialog dialog = new DatePickerDialog(
						EditUserActivity.this,
						txtBirthday.getText().toString(),
						new DatePickerDialogCallBack() {

							@Override
							public void confirm(String date) {
								txtBirthday.setText(date);
							}
						});
				dialog.show();
			}
		});

		txtExpiredDate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DatePickerDialog dialog = new DatePickerDialog(
						EditUserActivity.this, txtExpiredDate.getText()
								.toString(), new DatePickerDialogCallBack() {

							@Override
							public void confirm(String date) {
								txtExpiredDate.setText(date);
							}
						});
				dialog.show();
			}
		});

		if (vfaceMemberModel != null)
			initMemeberView();

		if (!TextUtils.isEmpty(phoneNum)) {
			txtPhone.setText(phoneNum);
			txtPhone.setTextColor(getResources().getColor(
					R.color.edit_unclickable_color));
			txtPhone.setClickable(false);
			txtPhone.setLongClickable(false);
			txtPhone.setFocusable(false);
		}
	}

	void initMemeberView() {
		try {
			ImageLoader.getInstance().displayImage(
					vfaceMemberModel.getPicPath(), imgPhoto, imgOption);
			txtUsername.setText(vfaceMemberModel.getFullName());
			txtPhone.setText(vfaceMemberModel.getPhoneNumber());
			txtId.setText(vfaceMemberModel.getIdNumber());
			txtBirthday.setText(vfaceMemberModel.getBirthday());

			txtAddress.setText(vfaceMemberModel.getAddress());
			txtEmail.setText(vfaceMemberModel.getEmail());
			txtNickname.setText(vfaceMemberModel.getNickName());
			if (vfaceMemberModel.getSex() == 0) {
				((RadioButton) radioGender.getChildAt(1)).setChecked(true);
			}
			cardNumEdit.setText(vfaceMemberModel.getMemberCardNumber());
			// cardPwdEdit.setText(vfaceMemberModel.getMemberCode());TODO
			// if (memberModel.get()) {
			// ((RadioButton) radioSpecialFocus.getChildAt(1))
			// .setChecked(true);
			// }
			// if (memberModel.isIsStealth()) {
			// ((RadioButton) radioSpecialFocus.getChildAt(1))
			// .setChecked(true);
			// }
			txtRemark.setText(vfaceMemberModel.getRemark());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void handleMessage(Message msg) {
		super.handleMessage(msg);
		switch (msg.what) {
		case SUCESS:
			sendToastMessage("编辑成功！");
			setResult(RESULT_CODE_FOR_EDIT_USER_SUCCESS);
			this.finish();
			break;
		case GET_PROVINCE:
			JSONArray jsonArray = (JSONArray) msg.obj;
			bindProvince(jsonArray);
			break;
		case GET_CITY:
			JSONArray jsonCityArray = (JSONArray) msg.obj;
			bindCity(jsonCityArray);
			break;
		case GET_AREA:
			JSONArray jsonAreaArray = (JSONArray) msg.obj;
			bindArea(jsonAreaArray);
			break;
		case GET_GRADE_LIST:
			gradeModels = (ArrayList<GradeModel>) msg.obj;
			bindGrade();
			break;
		default:
			break;
		}
	}

	protected void getCity(final String provinceId) {
		Loading.run(this, new Runnable() {

			@Override
			public void run() {
				try {
					JSONArray cityArray = ProfileHelper.getCityList(
							EditUserActivity.this, provinceId);
					sendMessage(GET_CITY, cityArray);
				} catch (MyHttpException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					sendToastMessage(e.getMessage());
				}
			}
		});
	}

	protected void getArea(final String cityId) {
		Loading.run(this, new Runnable() {

			@Override
			public void run() {
				try {
					JSONArray areaArray = ProfileHelper.getAreaList(
							EditUserActivity.this, cityId);
					sendMessage(GET_AREA, areaArray);
				} catch (MyHttpException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					sendToastMessage(e.getMessage());
				}
			}
		});
	}

	private void bindProvince(JSONArray jsonArray) {

		provinceList = new ArrayList<ProvinceModel>();
		try {
			for (int i = 0; i < jsonArray.length(); i++) {
				int provinceId = jsonArray.getJSONObject(i)
						.getInt("ProvinceId");
				String provinceName = jsonArray.getJSONObject(i).getString(
						"ProvinceName");
				ProvinceModel provinceModel = new ProvinceModel();
				provinceModel.setProvinceId(provinceId);
				provinceModel.setProvinceName(provinceName);
				provinceList.add(provinceModel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		provinceSpinnerAdapter = new ProvinceSpinnerAdapter(this, provinceList);
		spinnerProvince.setAdapter(provinceSpinnerAdapter);
		try {
			spinnerProvince.setSelection(getProvinceIndex(vfaceMemberModel.getProvinceId()));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private int getProvinceIndex(int provinceId) {
		for (int i = 0; i < provinceList.size(); i++) {
			if (provinceId == provinceList.get(i).getProvinceId()) {
				return i;
			}
		}
		return 0;
	}

	private void bindCity(JSONArray jsonArray) {
		cityList = new ArrayList<CityModel>();
		try {
			for (int i = 0; i < jsonArray.length(); i++) {
				CityModel cityModel = new CityModel();
				cityModel
						.setCityId(jsonArray.getJSONObject(i).getInt("CityId"));
				cityModel.setCityName(jsonArray.getJSONObject(i).getString(
						"CityName"));
				cityList.add(cityModel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		citySpinnerAdapter = new CitySpinnerAdapter(this, cityList);
		spinnerCity.setAdapter(citySpinnerAdapter);

		try {
			spinnerCity.setSelection(getCityIndex(vfaceMemberModel.getCityId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private int getCityIndex(int cityId) {
		for (int i = 0; i < cityList.size(); i++) {
			if (cityId == cityList.get(i).getCityId()) {
				return i;
			}
		}
		return 0;
	}

	private void bindArea(JSONArray jsonArray) {
		districtList = new ArrayList<DistrictModel>();
		try {
			for (int i = 0; i < jsonArray.length(); i++) {
				DistrictModel districtModel = new DistrictModel();
				districtModel.setDistrictId(jsonArray.getJSONObject(i).getInt("DistrictId"));
				districtModel.setDistrictName(jsonArray.getJSONObject(i).getString("DistrictName"));
				districtList.add(districtModel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		districtSpinnerAdapter = new DistrictSpinnerAdapter(this, districtList);
		spinnerArea.setAdapter(districtSpinnerAdapter);

		try {
			spinnerArea.setSelection(getDistrictIndex(vfaceMemberModel.getAreaId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private int getDistrictIndex(int areaId) {
		for (int i = 0; i < districtList.size(); i++) {
			if (areaId == districtList.get(i).getDistrictId()) {
				return i;
			}
		}
		return 0;
	}

	private void bindGrade() {
		ArrayList<String> grades = new ArrayList<String>();
		try {
			for (int i = 0; i < gradeModels.size(); i++) {
				grades.add(gradeModels.get(i).getGradeName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
				R.layout.spinner_my, grades);
		gradeSpinner.setAdapter(arrayAdapter);
	}

	public void btnSaveClick(View v) {

		Loading.run(this, new Runnable() {

			@Override
			public void run() {

				String userName = txtUsername.getText().toString();
				String nickName = txtNickname.getText().toString();
				String sex = radioGender.getCheckedRadioButtonId() == R.id.radio0 ? "1"
						: "0";
				String idNumber = txtId.getText().toString();

				String address = txtAddress.getText().toString();
				String birthday = txtBirthday.getText().toString();
				String PhoneNumber = txtPhone.getText().toString();
				String email = txtEmail.getText().toString();
				String IsStealth = radioStealth.getCheckedRadioButtonId() == R.id.radioStealth0 ? "0"
						: "1";
				String IsSpecialFocus = radioSpecialFocus
						.getCheckedRadioButtonId() == R.id.radioSpecialFocus0 ? "0"
						: "1";
				String IsForever = radioCardForever.getCheckedRadioButtonId() == R.id.radioCardForever0 ? "0"
						: "1";
				String remark = txtRemark.getText().toString();
				String memberCardNumber = cardNumEdit.getText().toString();
				String cardPassword = cardPwdEdit.getText().toString();
				String expiredDate = txtExpiredDate.getText().toString();

				HashMap<String, String> hashMap = new HashMap<String, String>();
				hashMap.put("TopStoreid", "");// TODO
				hashMap.put("StoreId", UserHelper.getCurrentUser().getStoreId()
						+ "");
				hashMap.put("CardPassword", cardPassword);
				hashMap.put("MemberCardNumber", memberCardNumber);
				hashMap.put("MemberGradeId", memberGradeId);
				hashMap.put("picStream", avatar);
				hashMap.put("FullName", userName);
				hashMap.put("NickName", nickName);
				hashMap.put("Sex", sex);
				hashMap.put("IdNumber", idNumber);
				hashMap.put("ProvinceId", provinceId);
				hashMap.put("CityId", cityId);
				hashMap.put("AreaId", areaId);
				hashMap.put("Address", address);
				hashMap.put("Birthday", birthday);
				hashMap.put("PhoneNumber", PhoneNumber);
				hashMap.put("Email", email);
				hashMap.put("IsStealth", IsStealth);// 隐身会员
				hashMap.put("IsSpecialFocus", IsSpecialFocus);// 特别关注会员
				hashMap.put("Remark", remark);// 备注
				hashMap.put("Operator", UserHelper.getCurrentUser()
						.getFullname());
				hashMap.put("IsForever", IsForever);

				hashMap.put("ExpiredDate", expiredDate);

				if (vfaceMemberModel != null) {
					hashMap.put("StoreMemberId",
							vfaceMemberModel.getStoreMemberId());
					hashMap.put("MemberId", vfaceMemberModel.getMemberId());
				}

				try {
					MemberHelper.editMember(EditUserActivity.this, hashMap);
					sendMessage(SUCESS);
				} catch (MyHttpException e) {
					e.printStackTrace();
					sendToastMessage(e.getMessage());
				}
			}
		});
		// this.finish();
	}

	public void imgPhotoClick(View v) {
		updateAvatarUtil
				.showChoosePhotoDialog(UpdateAvatarUtil.IMG_TYPE_AVATAR);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		updateAvatarUtil.onActivityResultAction(requestCode, resultCode, data);
	}

	public void imgBackClick(View v) {
		this.finish();
	}

	@Override
	public void updateAvatarSuccess(int updateType, String avatar,
			String avatarBase64) {
		LogUtil.d(avatar);
		imgPhoto.setImageResource(R.drawable.login_logo);
		if (avatar.toLowerCase().startsWith("http")) {
			ImageLoader.getInstance().displayImage(avatar, imgPhoto, imgOption);
		} else {
			// imgPhoto.setImageURI(Uri.fromFile(new File(avatar)));
			ImageLoader.getInstance().displayImage("file://" + avatar,
					imgPhoto, imgOption);
		}

		this.avatar = avatarBase64;
	}

	@Override
	public void updateAvatarFailed(int updateType) {
		Toast.makeText(this, R.string.upload_avatar_failed, Toast.LENGTH_SHORT)
				.show();
	}

	@Override
	public void cancel() {

	}

}
