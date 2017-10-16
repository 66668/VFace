package com.vface;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.vface.adapter.CommonListAdapter;
import com.vface.adapter.ManagerUserListAdapter;
import com.vface.bizmodel.MemberModel;
import com.vface.bizmodel.MemberPageModel;
import com.vface.common.BaseActivity;
import com.vface.common.MyHttpException;
import com.vface.dialog.Loading;
import com.vface.helper.MemberHelper;
import com.vface.helper.UserHelper;
import com.vface.inject.ViewInject;
import com.vface.utils.PageUtil;

/**
 * Created by HuBin on 15/4/21.
 */
public class ManageUserActivity extends BaseActivity implements CommonListAdapter.AdapterCallBack {

    private static final int GET_DATA_SUCCESS = 9;
    private static final int LOAD_MORE_SUCCESS = 8;


    @ViewInject(id = R.id.imgBack, click = "imgBackClick")
    View imgBack;

    @ViewInject(id = R.id.searchKeyEdit)
    EditText searchKeyEdit;

    @ViewInject(id = R.id.listView)
    ListView listView;

    @ViewInject(id = R.id.btn_clear_edit,click = "clearEdit")
    ImageView btnClearEdit;

    private ManagerUserListAdapter mAdapter;

    private int currentPage = 1;
    private final int pageSize = 10;
    private boolean ifLoading;

    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_user);
        context = this;
        initMainView();
        search(currentSearchKey);
    }

    private void initMainView() {

        searchKeyEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {//keyCode == 66 获取Enter键

                    String searchKey = searchKeyEdit.getText().toString();
                    if(TextUtils.isEmpty(searchKey.trim())){
                        PageUtil.DisplayToast(R.string.please_input_user_cardnum_or_phone_num);
                    }else{
                        search(searchKey);
                    }
                    return true;
                }
                return false;
            }
        });

        mAdapter = new ManagerUserListAdapter(this, this);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String memberId = ((MemberModel)mAdapter.getItem(position)).getStoreMemberId();
//                String visitTime = ((MemberModel)mAdapter.getItem(position)).getStrCreateTime();
                Bundle bundle = new Bundle();
                bundle.putString("memberId", memberId);
//                bundle.putString("visitTime", visitTime);
                Intent intent = new Intent(ManageUserActivity.this,UserInfoActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent,UserInfoActivity.REQUEST_CODE_FOR_EDIT_USER);
            }
        });
    }

    private String currentSearchKey="";
    private void search(final String searchKey) {

        if(ifLoading){
            return;
        }
        ifLoading = true;
        currentSearchKey = searchKey;

        Loading.run(this,new Runnable() {
            @Override
            public void run() {
                String storeId = UserHelper.getCurrentUser().getStoreId();
                currentPage = 1;
                mAdapter.IsEnd = false;
                try {
                    MemberPageModel model = MemberHelper.getMemberList(context, currentPage, pageSize, storeId,currentSearchKey);
                    ArrayList<MemberModel> members = model.getRows();
                    if(currentPage ==  model.getTotal() || members.size() < pageSize){
                        mAdapter.IsEnd = true;
                    }
                    currentPage ++;
                    sendMessage(GET_DATA_SUCCESS,members);
                } catch (MyHttpException e) {
                    e.printStackTrace();
                    sendToastMessage(e.getMessage());
                    ifLoading = false;
                }
            }
        });

    }

    @Override
    public void loadMore() {
        if(ifLoading){
            return;
        }
        Loading.run(this,new Runnable() {
            @Override
            public void run() {
                String storeId = UserHelper.getCurrentUser().getStoreId();
                try {
                    MemberPageModel model = MemberHelper.getMemberList(context, currentPage, pageSize, storeId,currentSearchKey);
                    ArrayList<MemberModel> members = model.getRows();
                    if(currentPage ==  model.getTotal() || members.size() < pageSize){
                        mAdapter.IsEnd = true;
                    }
                    currentPage ++;
                    sendMessage(LOAD_MORE_SUCCESS,members);
                } catch (MyHttpException e) {
                    e.printStackTrace();
                    sendToastMessage(e.getMessage());
                    ifLoading = false;
                }
            }
        });
    }

    @Override
    protected void handleMessage(Message msg) {

        switch (msg.what){
            case GET_DATA_SUCCESS:
                mAdapter.setEntityList((ArrayList) msg.obj);
                ifLoading = false;
                break;
            case LOAD_MORE_SUCCESS:
                mAdapter.addEntityList((ArrayList) msg.obj);
                ifLoading = false;
                break;
            default:
                break;
        }

        super.handleMessage(msg);
    }

    public void clearEdit(View v){
        searchKeyEdit.setText("");
    }

    public void imgBackClick(View v) {
        this.finish();
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(requestCode == UserInfoActivity.REQUEST_CODE_FOR_EDIT_USER){
    		if(resultCode == EditUserActivity.RESULT_CODE_FOR_EDIT_USER_SUCCESS){
    			search(currentSearchKey);
    		}
    	}
    	super.onActivityResult(requestCode, resultCode, data);
    }
}
