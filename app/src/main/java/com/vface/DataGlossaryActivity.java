package com.vface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.vface.adapter.CommonListAdapter;
import com.vface.adapter.DataGlossaryListAdapter;
import com.vface.bizmodel.DataGlossaryModel;
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
public class DataGlossaryActivity extends BaseActivity implements CommonListAdapter.AdapterCallBack {

    private static final int GET_DATA_SUCCESS = 9;
    private static final int LOAD_MORE_SUCCESS = 8;
    private static final int GET_TOTAL_COUNTS_SUCCESS = 7;
    @ViewInject(id = R.id.imgBack, click = "imgBackClick")
    View imgBack;

    @ViewInject(id = R.id.listView)
    ListView listView;

    @ViewInject(id = R.id.txt_start_time)
    TextView startTimeView;

    @ViewInject(id = R.id.txt_end_time)
    TextView endTimeView;

    @ViewInject(id = R.id.search_btn,click = "search")
    TextView searchBtn;

    @ViewInject(id = R.id.total_amount)
    TextView totalAmountView;

    private Context context;
    private DataGlossaryListAdapter mAdapter;

//    private int pageIndex = 1;
//    private int pageSize = 10;
    private boolean ifLoading;
    private String currentStartTime;
    private String currentEndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_glossary);
        context = this;
        initMainView();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        currentStartTime = df.format(new Date());// new Date()为获取当前系统时间
        currentEndTime = df.format(new Date());
        startTimeView.setText(currentStartTime);
        endTimeView.setText(currentEndTime);
        search(searchBtn);
    }

    public void imgBackClick(View v){
        finish();
    }

    private void initMainView() {

        mAdapter = new DataGlossaryListAdapter(this,this);

        listView.addHeaderView(getHeaderView());
        listView.setAdapter(mAdapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                ExpenseListModel expenseListModel = (ExpenseListModel) mAdapter.getItem(position + 1);//有header
//                Intent intent = new Intent(context,ConsumeDetailActivity.class);
//                intent.putExtra("expenseListModel",expenseListModel);
//                startActivity(intent);
//            }
//        });

    }

    private View headerView;
    private View getHeaderView() {
        headerView = View.inflate(this,R.layout.activity_data_glossary_header,null);
        return headerView;
    }

    private void initHeaderView(DataGlossaryModel dataGlossaryModel){

        if(headerView == null){
            headerView = View.inflate(this,R.layout.activity_data_glossary_header,null);
        }

        TextView totalMembersView = (TextView)headerView.findViewById(R.id.total_members);
        TextView newMembersView = (TextView)headerView.findViewById(R.id.new_members);
        TextView rechargeMembersView = (TextView)headerView.findViewById(R.id.recharge_members);
        TextView fastExpenseCountsView = (TextView)headerView.findViewById(R.id.fast_expense_counts);
        TextView fastExpenseAmountView = (TextView) headerView.findViewById(R.id.fast_expense_amount);
        TextView timeExpenseTimeView = (TextView)headerView.findViewById(R.id.time_expense_total_time);
        TextView timeExpenseAmountView = (TextView) headerView.findViewById(R.id.time_expense_total_amount);
        TextView totalCountView = (TextView) headerView.findViewById(R.id.total_count_expense);
        TextView totalCountsExpenseAmount = (TextView) headerView.findViewById(R.id.counts_expense_total_amount);
        TextView totalAmountView = (TextView)headerView.findViewById(R.id.total_expense_amount);
        TextView discountAmountView = (TextView)headerView.findViewById(R.id.discount_amount);
        TextView cleanDecimalAmountView = (TextView)headerView.findViewById(R.id.clean_decimal_amount);

        //总会员数
        totalMembersView.setText("总会员："+dataGlossaryModel.getTotalMemberCount());
        //新会员数
        newMembersView.setText("新增会员："+dataGlossaryModel.getTotalNewMemberCount());
        //充值会员
        rechargeMembersView.setText("充值总金额："+dataGlossaryModel.getTotalChongzhiAmount()+"元");
        //快速消费次数
        fastExpenseCountsView.setText("快速消费次数："+dataGlossaryModel.getTotalFastExpenseCount());
        //快速消费金额
        fastExpenseAmountView.setText("总金额："+dataGlossaryModel.getTotalFastExpenseAmount()+"元");
        //计时消费时长
        timeExpenseTimeView.setText("总时长（分钟）："+dataGlossaryModel.getTotalTimeExpense());
        //计时消费金额
        timeExpenseAmountView.setText("总金额："+dataGlossaryModel.getTotalTimeChongzhiAmount()+"元");
        //计次消费总次数
        totalCountView.setText("总次数："+dataGlossaryModel.getTotalCountExpense());
        //计次消费总金额
        totalCountsExpenseAmount.setText("总金额："+dataGlossaryModel.getTotalCountChongzhiAmount()+"元");
        //总消费金额(商品消费和快速消费)
        totalAmountView.setText("实收(元)："+dataGlossaryModel.getTotalCommodityExpenseAmount()+"元");
        discountAmountView.setText("折扣(元)："+dataGlossaryModel.getTotalDiscountAmount()+"元");
        cleanDecimalAmountView.setText("抹零(元)："+dataGlossaryModel.getTotalCleanDecimalAmount()+"元");
    }

    public void selectStartTime(View v){
        showDataPickerDialog(TIME_TYPE_START,currentStartTime);
    }

    public void selectEndTime(View v){
        showDataPickerDialog(TIME_TYPE_END,currentEndTime);
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
                    currentStartTime = date;
                }else{
                    endTimeView.setText(date);
                    currentEndTime = date;
                }

            }
        });

        dialog.show();

    }

    public void search(View v){

        final String startTimeText = startTimeView.getText().toString();
        if(TextUtils.isEmpty(startTimeText)){
            PageUtil.DisplayToast("请选择开始时间");
            return;
        }
        final String endTimeText = endTimeView.getText().toString();
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


        if(ifLoading){
            return;
        }

        Loading.run(this, new Runnable() {
            @Override
            public void run() {
                ifLoading = true;
                String storeId = UserHelper.getCurrentUser().getStoreId();
                try {
                    mAdapter.IsEnd = false;
                    DataGlossaryModel dataGlossaryModel = ShopHelper.getDataGlossary(context, startTimeText, endTimeText);
//                    currentStartTime = startTimeText;
//                    currentEndTime = endTimeText;
                    mAdapter.IsEnd = true;
//                    try {
//                        String totalCounts = ShopHelper.getExpenseTotal(context,storeId,startTimeText,endTimeText);
//                        sendMessage(GET_TOTAL_COUNTS_SUCCESS,totalCounts);
//                    }catch (MyHttpException e) {
//                        e.printStackTrace();
//                    }
                    sendMessage(GET_DATA_SUCCESS, dataGlossaryModel);
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

    }

    @Override
    protected void handleMessage(Message msg) {

        switch (msg.what){
            case GET_DATA_SUCCESS:
                DataGlossaryModel dataGlossaryModel = (DataGlossaryModel) msg.obj;
//                mAdapter.setEntityList((ArrayList) msg.obj);
                ArrayList<DataGlossaryModel.CommodityClassSummary> commodityClassSummaryList = dataGlossaryModel.getCommodityClassSummaryList();
                mAdapter.setEntityList(commodityClassSummaryList);
                initHeaderView(dataGlossaryModel);
                totalAmountView.setText(dataGlossaryModel.getTotalExpenseAmount()+"元");
                ifLoading = false;
                break;
//            case LOAD_MORE_SUCCESS:
//                mAdapter.addEntityList((ArrayList) msg.obj);
//                ifLoading = false;
//                break;
//            case GET_TOTAL_COUNTS_SUCCESS:
//                String totalCountString = (String) msg.obj;
//                totalAmountView.setText(totalCountString+"元");
//                break;
            default:
                break;
        }

        super.handleMessage(msg);
    }
}
