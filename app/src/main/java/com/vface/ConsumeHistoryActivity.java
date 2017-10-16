package com.vface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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
import android.widget.TextView;

import com.vface.adapter.CommonListAdapter;
import com.vface.adapter.ConsumeHistoryListAdapter;
import com.vface.bizmodel.ExpenseListModel;
import com.vface.common.BaseActivity;
import com.vface.common.MyHttpException;
import com.vface.dialog.DatePickerDialog;
import com.vface.dialog.Loading;
import com.vface.helper.ShopHelper;
import com.vface.helper.UserHelper;
import com.vface.inject.ViewInject;
import com.vface.utils.PageUtil;

/**
 * Created by HuBin on 15/4/22.
 */
public class ConsumeHistoryActivity extends BaseActivity implements CommonListAdapter.AdapterCallBack {


    private static final int GET_DATA_SUCCESS = 9;
    private static final int LOAD_MORE_SUCCESS = 8;
    @ViewInject(id = R.id.imgBack, click = "imgBackClick")
    View imgBack;

    @ViewInject(id = R.id.title_textview)
    TextView titleView;

    @ViewInject(id = R.id.searchKeyEdit)
    EditText searchKeyEdit;

    @ViewInject(id = R.id.listView)
    ListView listView;

    @ViewInject(id = R.id.btn_clear_edit,click = "clearEdit")
    ImageView btnClearEdit;

    @ViewInject(id = R.id.txt_start_time)
    TextView startTimeView;

    @ViewInject(id = R.id.txt_end_time)
    TextView endTimeView;

    @ViewInject(id = R.id.search_btn)
    TextView searchBtn;

    private Context context;
    private ConsumeHistoryListAdapter mAdapter;

    private int pageIndex = 1;
    private int pageSize = 10;
    private boolean ifLoading;
    private String currentSearchKey = "";
    private String startTimeText;
    private String endTimeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consume_history);
        context = this;
        titleView.setText("消费记录");
        initMainView();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        startTimeText = df.format(new Date());// new Date()为获取当前系统时间
        endTimeText = df.format(new Date());// new Date()为获取当前系统时间
        startTimeView.setText(startTimeText);
        endTimeView.setText(endTimeText);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchKey = searchKeyEdit.getText().toString();
//                if(TextUtils.isEmpty(searchKey.trim())){
//                    PageUtil.DisplayToast(R.string.please_input_key);
//                }else{
                    search(searchKey);
//                }
            }
        });
        search(currentSearchKey);//TODO
    }

    private void initMainView() {

        searchKeyEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {//keyCode == 66 获取Enter键

                    String searchKey = searchKeyEdit.getText().toString();
//                    if(TextUtils.isEmpty(searchKey.trim())){
//                        PageUtil.DisplayToast(R.string.please_input_phone_num_card_num_name);
//                    }else{
                        search(searchKey);
//                    }
                    return true;
                }
                return false;
            }
        });

        mAdapter = new ConsumeHistoryListAdapter(this, this);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ExpenseListModel expenseListModel = (ExpenseListModel) mAdapter.getItem(position);
                Intent intent = new Intent(context,ConsumeDetailActivity.class);
                intent.putExtra("expenseListModel",expenseListModel);
                startActivity(intent);
            }
        });
    }

    private void search(final String searchKey) {

        if(ifLoading){
            return;
        }

        startTimeText = startTimeView.getText().toString();
        if(TextUtils.isEmpty(startTimeText)){
            PageUtil.DisplayToast("请选择开始时间");
            return;
        }
        endTimeText = endTimeView.getText().toString();
        if(TextUtils.isEmpty(endTimeText)){
            PageUtil.DisplayToast("请选择结束时间");
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        try {
            if(!startTimeText.equals(endTimeText)){
                Date startTimeDate = sdf.parse(startTimeText);
                Date endTimeDate = sdf.parse(endTimeText);
                boolean ifRightDate = startTimeDate.before(endTimeDate);
                if(!ifRightDate){
                    PageUtil.DisplayToast("结束时间要大于开始时间!");
                    return;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Loading.run(this,new Runnable() {
            @Override
            public void run() {
                ifLoading = true;
                String storeId = UserHelper.getCurrentUser().getStoreId();
                try {
                    pageIndex = 1;
                    mAdapter.IsEnd = false;
                    ArrayList<ExpenseListModel> expenseListModels = ShopHelper.getExpenseListModel(ConsumeHistoryActivity.this,
                            storeId, searchKey, endTimeText, startTimeText, pageIndex, pageSize, 0);
                    if(expenseListModels.size() < pageSize){
                        mAdapter.IsEnd = true;
                    }
                    sendMessage(GET_DATA_SUCCESS,expenseListModels);
                    currentSearchKey = searchKey;
                    pageIndex ++;
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
                ifLoading = true;
                String storeId = UserHelper.getCurrentUser().getStoreId();
                try {
                    ArrayList<ExpenseListModel> expenseListModels = ShopHelper.getExpenseListModel(ConsumeHistoryActivity.this,
                            storeId, currentSearchKey, endTimeText, startTimeText, pageIndex, pageSize, 0);
                    if(expenseListModels.size() < pageSize){
                        mAdapter.IsEnd = true;
                    }
                    sendMessage(LOAD_MORE_SUCCESS,expenseListModels);
                    pageIndex ++;
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

    public void imgBackClick(View v){
        finish();
    }
    public void clearEdit(View v){
        searchKeyEdit.setText("");
    }


    public void selectStartTime(View v){
        showDataPickerDialog(TIME_TYPE_START,startTimeText);
    }

    public void selectEndTime(View v){
        showDataPickerDialog(TIME_TYPE_END,endTimeText);
    }

    private final int TIME_TYPE_START = 0;
    private final int TIME_TYPE_END = 1;
    private void showDataPickerDialog(final int whichTime,String currentDate){

        DatePickerDialog dialog = new DatePickerDialog(this,currentDate,new DatePickerDialog.DatePickerDialogCallBack() {
            @Override
            public void confirm(String date) {
                PageUtil.DisplayToast(date);
                if(whichTime == TIME_TYPE_START){
                    startTimeView.setText(date);
                    startTimeText = date;
                }else{
                    endTimeView.setText(date);
                    endTimeText = date;
                }

            }
        });

        dialog.show();

    }
}
