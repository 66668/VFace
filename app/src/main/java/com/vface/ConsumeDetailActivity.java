package com.vface;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.vface.adapter.CommonListAdapter;
import com.vface.adapter.ConsumeDetailListAdapter;
import com.vface.adapter.ConsumeHistoryListAdapter;
import com.vface.bizmodel.ExpenseDetailModel;
import com.vface.bizmodel.ExpenseListModel;
import com.vface.common.BaseActivity;
import com.vface.common.MyHttpException;
import com.vface.dialog.Loading;
import com.vface.helper.ShopHelper;
import com.vface.utils.DateUtils;
import com.vface.utils.PageUtil;

/**
 * Created by HuBin on 15/4/22.
 */
public class ConsumeDetailActivity extends BaseActivity implements View.OnClickListener, CommonListAdapter.AdapterCallBack {

    private ListView listView;
    private ConsumeDetailListAdapter mAdapter;

    private boolean ifLoading;
    private final int MSG_GET_DATA_SUCCESS = -9;
    private final int MSG_GET_DATA_FAILED = -8;

    private ExpenseListModel expenseListModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consume_detail);
        expenseListModel = (ExpenseListModel) getIntent().getSerializableExtra("expenseListModel");
        if(expenseListModel == null){
            PageUtil.DisplayToast("数据有误");
            finish();
            return;
        }
        initMainView();
        getData();

    }

    private TextView userNameView;
    private TextView expenseTypeView;
    private TextView serialNumberView;
    private TextView moneyView;
//    private TextView shopNameView;
    private TextView timeView;
    private TextView operatorView;
    private TextView countView;
    private TextView priceView;
    private LinearLayout listBar;

    private void initMainView() {

        initTopBar();
        serialNumberView = (TextView) findViewById(R.id.serial_number);
        moneyView = (TextView) findViewById(R.id.money);
//        shopNameView = (TextView) findViewById(R.id.shop_name);
        timeView = (TextView) findViewById(R.id.time);
        operatorView = (TextView)findViewById(R.id.operator);
        userNameView = (TextView) findViewById(R.id.user_name);
        expenseTypeView = (TextView) findViewById(R.id.expenseType);

        userNameView.setText(expenseListModel.getMemberName());
        expenseTypeView.setText(ConsumeHistoryListAdapter.getExpenseName(expenseListModel.getExpenseType()));
        serialNumberView.setText(expenseListModel.getOrderNumber());
        moneyView.setText(expenseListModel.getTotalAmount()+"元");
//        shopNameView.setText(expenseListModel.getStoreName());
        timeView.setText(DateUtils.getFriendlyDate(expenseListModel.getExpenseDate()));
        operatorView.setText(expenseListModel.getOperator());

        //列表部分
        listBar = (LinearLayout) findViewById(R.id.list_bar);
        countView = (TextView) findViewById(R.id.counts);
        priceView = (TextView) findViewById(R.id.price);
        listView = (ListView) findViewById(R.id.listView);
        mAdapter = new ConsumeDetailListAdapter(this,this);
        listView.setAdapter(mAdapter);

        if(expenseListModel.getExpenseType() == 2){//快速消费
            listBar.setVisibility(View.GONE);
            listView.setVisibility(View.GONE);
        }else if(expenseListModel.getExpenseType() == 5){ //计时消费
            countView.setText("分钟");
            priceView.setVisibility(View.GONE);
            mAdapter.setExpenseType(6);

        }else if(expenseListModel.getExpenseType() == 6){ //计次消费
            countView.setText("数量(次)");
        }



    }

    private void initTopBar() {

        ImageView backBtn = (ImageView) findViewById(R.id.imgBack);
        TextView titleView = (TextView) findViewById(R.id.title_textview);
        backBtn.setOnClickListener(this);
        titleView.setText(getString(R.string.electronic_invoice));

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.imgBack:
                finish();
                break;
            default:
                break;
        }

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
                    ArrayList<ExpenseDetailModel> expenseDetailModels = ShopHelper.getExpenseDetailModel(ConsumeDetailActivity.this, expenseListModel.getStoreId(), expenseListModel.getExpenseId(), expenseListModel.getExpenseType());
                    mAdapter.IsEnd = true;
                    sendMessage(MSG_GET_DATA_SUCCESS, expenseDetailModels);
                } catch (MyHttpException e) {
                    e.printStackTrace();
                    sendMessage(MSG_GET_DATA_FAILED);
                }
            }
        });

    }

    @Override
    public void loadMore() {
        //TODO
        getData();
    }

    @Override
    protected void handleMessage(Message msg) {

        switch (msg.what){
            case MSG_GET_DATA_SUCCESS:
                mAdapter.addEntityList((ArrayList) msg.obj);
                ifLoading = false;
                break;
            case MSG_GET_DATA_FAILED:
                ifLoading = false;
                break;
        }

        super.handleMessage(msg);
    }
}
