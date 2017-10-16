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
import com.vface.adapter.GoodsSearchListAdapter;
import com.vface.bizmodel.CommodityModel;
import com.vface.common.BaseActivity;
import com.vface.common.MyHttpException;
import com.vface.dialog.Loading;
import com.vface.helper.ShopHelper;
import com.vface.helper.UserHelper;
import com.vface.inject.ViewInject;
import com.vface.utils.PageUtil;

/**
 * Created by HuBin on 15/4/22.
 */
public class GoodsSearchActivity extends BaseActivity implements CommonListAdapter.AdapterCallBack {

    public static final int RESULT_CODE_FOR_ADD_GOODS = 98;
    private static final int SEARCH_SUCCESS = 9;
    private static final int LOAD_MORE_SUCCESS = 8 ;


    @ViewInject(id = R.id.imgBack, click = "imgBackClick")
    View imgBack;

    @ViewInject(id = R.id.searchKeyEdit)
    EditText searchKeyEdit;

    @ViewInject(id = R.id.listView)
    ListView listView;

    @ViewInject(id = R.id.btn_clear_edit,click = "clearEdit")
    ImageView btnClearEdit;

    private GoodsSearchListAdapter mAdapter;

    private boolean ifComeForAddGoods;

    private Context context;

    private int pageIndex = 1;
    private final int pageSize =10;
    private boolean ifLoading;

    private String currentSearchKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_search);

        context = this;
        ifComeForAddGoods = getIntent().getBooleanExtra("ifComeForAddGoods",false);
        initMainView();
        search("");
    }

    private void initMainView() {

        searchKeyEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {//keyCode == 66 获取Enter键

                    String searchKey = searchKeyEdit.getText().toString();
                    if(TextUtils.isEmpty(searchKey.trim())){
                        PageUtil.DisplayToast(R.string.please_input_goods_name_or_num);
                    }else{
                        search(searchKey);
                    }
                    return true;
                }
                return false;
            }
        });

        mAdapter = new GoodsSearchListAdapter(this, this);
        listView.setAdapter(mAdapter);
        if(ifComeForAddGoods){
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CommodityModel commodityModel = (CommodityModel) mAdapter.getItem(position);
                addGoods(commodityModel);
                }
            });
        }
    }

    private void addGoods(CommodityModel commodityModel) {

        if(commodityModel.getInventoryData() > 0){
            Intent intent = new Intent();
            intent.putExtra("commodityModel",commodityModel);
            setResult(RESULT_CODE_FOR_ADD_GOODS,intent);
            finish();
        }else{
            PageUtil.DisplayToast("已无库存");
        }

    }

    private void search(final String searchKey) {

        if(ifLoading){
            return;
        }

        Loading.run(context,new Runnable() {
            @Override
            public void run() {

                String storeId = UserHelper.getCurrentUser().getStoreId();
                try {

                    pageIndex = 1;
                    mAdapter.IsEnd = false;
                    currentSearchKey = searchKey;
                    ArrayList<CommodityModel> commodityModels = ShopHelper.getCommodityModel(context
                            , storeId, searchKey, pageIndex, pageSize);

                    if(commodityModels.size() < pageSize){
                        mAdapter.IsEnd = true;
                    }

                    pageIndex ++;
                    sendMessage(SEARCH_SUCCESS,commodityModels);

                } catch (MyHttpException e) {
                    e.printStackTrace();
                    sendToastMessage(e.getMessage());
                    ifLoading = false;
                }
            }
        });


    }

    private void getData(){

    }

    @Override
    public void loadMore() {

        if(ifLoading){
            return;
        }

        Loading.run(context,new Runnable() {
            @Override
            public void run() {

                String storeId = UserHelper.getCurrentUser().getStoreId();
                try {

                    ArrayList<CommodityModel> commodityModels = ShopHelper.getCommodityModel(context
                            , storeId, currentSearchKey, pageIndex, pageSize);

                    if(commodityModels.size() < pageSize){
                        mAdapter.IsEnd = true;
                    }

                    pageIndex ++;
                    sendMessage(LOAD_MORE_SUCCESS,commodityModels);

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
            case SEARCH_SUCCESS:
                mAdapter.setEntityList((ArrayList) msg.obj);
                ifLoading = false;
                break;
            case LOAD_MORE_SUCCESS:
                mAdapter.addEntityList((ArrayList) msg.obj);
                ifLoading = false;
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

}
