package com.vface;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.vface.adapter.GuideShopingListAdapter;
import com.vface.bizmodel.CommodityModel;
import com.vface.bizmodel.GuidePendModel;
import com.vface.bizmodel.MemberModel;
import com.vface.bizmodel.VisitRecordModel;
import com.vface.common.BaseActivity;
import com.vface.common.ImageLoadingConfig;
import com.vface.common.MyHttpException;
import com.vface.dialog.ConfirmDialog;
import com.vface.dialog.Loading;
import com.vface.helper.ShopHelper;
import com.vface.inject.ViewInject;
import com.vface.utils.PageUtil;

/**
 * Created by HuBin on 15/4/22.
 */
public class GuideShopingActivity extends BaseActivity implements GuideShopingListAdapter.GuideShopAdapterCallback{

    @ViewInject(id = R.id.imgBack, click = "imgBackClick")
    ImageView imgBack;

    @ViewInject(id = R.id.imgUserHeader,click = "addGoodsClick")
    ImageView rightTopImg;

    @ViewInject(id = R.id.title_textview)
    TextView titleView;

    //////
    @ViewInject(id = R.id.imgHeader)
    ImageView imgHeader;

    @ViewInject(id = R.id.txtUserName)
    TextView userNameView;

    @ViewInject(id = R.id.txtPhoneNum)
    TextView phoneNumView;

    @ViewInject(id = R.id.txtCardNumber)
    TextView cardNumView;

    @ViewInject(id = R.id.txtGradeName)
    TextView cardLevelView;

    @ViewInject(id = R.id.txtMoney)
    TextView txtMoney;

    @ViewInject(id = R.id.txtPoints)
    TextView txtPoints;

    @ViewInject(id = R.id.btnLayout)
    LinearLayout btnLayout;

    @ViewInject(id = R.id.listView)
    ListView listView;

    @ViewInject(id = R.id.submit_btn)
    Button submitBtn;

    private GuideShopingListAdapter mAdapter;

    private final int REQUEST_CODE_FOR_ADD_GOODS = 99;
    private static final int PEND_OK = 9;

    private MemberModel memberModel;
    private VisitRecordModel visitRecordModel;
    private ImageLoader imgLoader;
    private DisplayImageOptions imgOption;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_shop);

        imgLoader = ImageLoader.getInstance();
        imgLoader.init(ImageLoaderConfiguration.createDefault(this));
        imgOption = ImageLoadingConfig.generateDisplayImageOptions(R.drawable.photo);

        initMainView();

    }

    private void initMainView() {
        rightTopImg.setImageResource(R.drawable.icon_add);
        memberModel = (MemberModel) getIntent().getSerializableExtra("memberModel");
        visitRecordModel = (VisitRecordModel) getIntent().getSerializableExtra("visitRecordModel");

        titleView.setText("导购");

        if(memberModel != null){

            imgLoader.displayImage(memberModel.getPicPath(), imgHeader,imgOption);

            userNameView.setText(memberModel.getFullName() + "");
            phoneNumView.setAutoLinkMask(Linkify.PHONE_NUMBERS);
            phoneNumView.setText("手机号："+memberModel.getPhoneNumber() + "");
            if(TextUtils.isEmpty(memberModel.getMemberCardNumber().trim())){
                cardNumView.setText("卡号：未开通");
            }else{
                cardNumView.setText("卡号："+memberModel.getMemberCardNumber()+"");
            }
            if(TextUtils.isEmpty(memberModel.getGradeName().trim())){
                cardLevelView.setText("级别：无");
            }else{
                cardLevelView.setText("级别："+memberModel.getGradeName()+"");
            }

            txtMoney.setText("余额："+memberModel.getTotalMoney()+"元");
            txtPoints.setText("积分："+memberModel.getTotalPoints());

        }else if(visitRecordModel != null){

            imgLoader.displayImage(visitRecordModel.getPicPath(), imgHeader,imgOption);

            userNameView.setText(visitRecordModel.getVFaceFullName()+"(唯脸会员)");
            phoneNumView.setText("手机号："+UserInfoActivity.getHidePhoneNum(visitRecordModel.getPhoneNumber() + ""));
            cardNumView.setText("未开通");
            if(TextUtils.isEmpty(visitRecordModel.getGradeName().trim())){
                cardLevelView.setText("级别：无");
            }else{
                cardLevelView.setText("级别："+visitRecordModel.getGradeName()+"");
            }
        }else{
            PageUtil.DisplayToast("暂无客人信息");
            finish();
            return;
        }

        btnLayout.setVisibility(View.GONE);
        mAdapter = new GuideShopingListAdapter(this,this);
        listView.setAdapter(mAdapter);
    }

    private void showRemoveDialog(final int position) {

        ConfirmDialog dialog = new ConfirmDialog(this,"移除此商品？","",new ConfirmDialog.ConfirmDialogCallBack() {
            @Override
            public void confirm() {
                mAdapter.getEntityList().remove(position);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void cancel() {

            }
        });
        dialog.show();
    }


    public void imgBackClick(View v) {
        finish();
    }

    public void addGoodsClick(View v){
        Intent intent = new Intent(this,GoodsSearchActivity.class);
        intent.putExtra("ifComeForAddGoods",true);
        startActivityForResult(intent,REQUEST_CODE_FOR_ADD_GOODS);
    }

    public void submit(View v){

        Loading.run(this,new Runnable() {
            @Override
            public void run() {
                try {
                    ArrayList<CommodityModel> guideBuyCommodityModels = mAdapter.entityList;
                    ArrayList<GuidePendModel> guidePendModels = new ArrayList<GuidePendModel>();
                    for(CommodityModel guideBuyCommodityModel:guideBuyCommodityModels){

                        GuidePendModel guidePendModel = new GuidePendModel();
                        guidePendModel.setCommodityId(guideBuyCommodityModel.getCommodityId());
                        guidePendModel.setCount(guideBuyCommodityModel.getSeleteCounts());
                        guidePendModels.add(guidePendModel);
                    }

                    if(guidePendModels.size() == 0){
                        sendToastMessage("请选择商品");
                        return;
                    }

                    String orderDetailsJsonString = (new Gson()).toJson(guidePendModels,
                            new TypeToken<List<CommodityModel>>() {
                            }.getType());
                    try {
                        orderDetailsJsonString = URLEncoder.encode(orderDetailsJsonString,"utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    String memberId = "";
                    String vfaceMemberCode = "";

                    //若不是本店会员
                    if(visitRecordModel != null){
                        memberId = visitRecordModel.getMemberId();
                        vfaceMemberCode = visitRecordModel.getVFaceMemberCode();
                    }
                    //本店会员
                    if(memberModel != null){
                        memberId = memberModel.getStoreMemberId();
                        vfaceMemberCode = memberModel.getVFACEMemberCode();
                    }

                    String result = ShopHelper.pendOrder(GuideShopingActivity.this,
                            memberId,
                            vfaceMemberCode,
                            orderDetailsJsonString);
                    sendMessage(PEND_OK,result);
                } catch (MyHttpException e) {
                    e.printStackTrace();
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
            case PEND_OK:
                PageUtil.DisplayToast((String) msg.obj);
                submitBtn.setEnabled(false);
                break;
            default:
                break;
        }
        super.handleMessage(msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_CODE_FOR_ADD_GOODS){

            if(resultCode == GoodsSearchActivity.RESULT_CODE_FOR_ADD_GOODS){
                CommodityModel commodityModel = (CommodityModel) data.getSerializableExtra("commodityModel");
                addCommodity(commodityModel);
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void addCommodity(CommodityModel commodityModel){

        if(commodityModel != null){
            ArrayList<CommodityModel> commodityModels = mAdapter.getEntityList();
            for(CommodityModel model:commodityModels){
                if(model.getCommodityId().equals(commodityModel.getCommodityId()) ){//同一款商品
                    if(!(model.getSeleteCounts() < commodityModel.getInventoryData())){
                        PageUtil.DisplayToast("已无库存");
                    }else{
                        model.setSeleteCounts(model.getSeleteCounts() + 1);
                        mAdapter.notifyDataSetChanged();
                    }
                    return;
                }
            }
            commodityModel.setSeleteCounts(1);
            mAdapter.addEntity(commodityModel);
        }

    }

    @Override
    public void onItemLongClick(int position) {
        showRemoveDialog(position);
    }
}
