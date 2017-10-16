package com.vface;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.vface.adapter.CommonListAdapter;
import com.vface.adapter.ConsumeHistoryListAdapter;
import com.vface.bizmodel.ExpenseListModel;
import com.vface.common.BaseActivity;
import com.vface.common.MyHttpException;
import com.vface.dialog.Loading;
import com.vface.helper.MemberHelper;
import com.vface.inject.ViewInject;

/**
 * 用户消费记录
 * Created by HuBin on 15/4/30.
 */
public class UserConsumeHistory extends BaseActivity implements CommonListAdapter.AdapterCallBack  {

    @ViewInject(id = R.id.imgBack, click = "imgBackClick")
    View imgBack;
    
    @ViewInject(id = R.id.no_result_view)
    View noResultView;

    private ListView listView;
    private ConsumeHistoryListAdapter mAdapter;

    private int pageIndex = 1;
    private int pageSize = 10;
    private boolean ifLoading;
    private final int MSG_GET_DATA_SUCCESS = -9;
    private final int MSG_GET_DATA_FAILED = -8;
    private String vfaceMemberCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_consume_history);
        vfaceMemberCode = getIntent().getStringExtra("vfaceMemberCode");
        if(vfaceMemberCode == null){
            finish();
            return;
        }
        initMainView();
        getData();

    }

    private void initMainView() {

        initTopBar();
        listView = (ListView) findViewById(R.id.listView);
        mAdapter = new ConsumeHistoryListAdapter(this,this);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ExpenseListModel expenseListModel = (ExpenseListModel) mAdapter.getItem(position);
                Intent intent = new Intent(UserConsumeHistory.this, ConsumeDetailActivity.class);
                intent.putExtra("expenseListModel", expenseListModel);
                startActivity(intent);
            }
        });

    }

    private void initTopBar() {

        TextView titleView = (TextView) findViewById(R.id.title_textview);
        titleView.setText(getString(R.string.consume_history));

    }


    private void getData() {

        if(ifLoading){
            return;
        }
        //TODO
        Loading.run(this, new Runnable() {
            @Override
            public void run() {
                ifLoading = true;

                try {
                    ArrayList<ExpenseListModel> expenseListModels = MemberHelper.getExpenseList(UserConsumeHistory.this, vfaceMemberCode,pageIndex, pageSize);

                    if (expenseListModels.size() < pageSize) {
                        mAdapter.IsEnd = true;
                    }
                    sendMessage(MSG_GET_DATA_SUCCESS, expenseListModels);
                    pageIndex++;

                } catch (MyHttpException e) {
                    e.printStackTrace();
                    sendMessage(MSG_GET_DATA_FAILED);
                }

            }
        });

    }

    @Override
    public void loadMore() {
        getData();
    }

    @Override
    protected void handleMessage(Message msg) {

        switch (msg.what){
            case MSG_GET_DATA_SUCCESS:
            	ArrayList<ExpenseListModel> expenseListModels = (ArrayList<ExpenseListModel>) msg.obj;
                if(expenseListModels != null && expenseListModels.size() > 0){
                	noResultView.setVisibility(View.GONE);
                }
                mAdapter.addEntityList(expenseListModels);
                ifLoading = false;

                break;
            case MSG_GET_DATA_FAILED:
                ifLoading = false;
                break;
        }

        super.handleMessage(msg);
    }

    public void imgBackClick(View v){
        finish();
    }
}
