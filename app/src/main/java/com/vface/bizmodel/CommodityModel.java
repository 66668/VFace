package com.vface.bizmodel;

import java.io.Serializable;

/**
 * Created by HuBin on 15/4/24.
 */
public class CommodityModel implements Serializable {

    private String CommodityId;
    private String CommodityCode;
    private String CommodityName;
    private String Unit;
    private String Price;
    private String IMPPrice;
    private String CommodityClassId;
    private String StoreId;
    private String PictureURL;
    private String Remark;
    private String CommodityDesc;
    private String IsServiceCommodity;
    private int InventoryData;
    private String CreateTime;
    private String Operator;
    private String ActiveFlg;
    private String CommodityNameShort;

    private int seleteCounts;

    public int getSeleteCounts() {
        return seleteCounts;
    }

    public void setSeleteCounts(int seleteCounts) {
        this.seleteCounts = seleteCounts;
    }

    public String getCommodityId() {
        return CommodityId;
    }

    public void setCommodityId(String commodityId) {
        CommodityId = commodityId;
    }

    public String getCommodityCode() {
        return CommodityCode;
    }

    public void setCommodityCode(String commodityCode) {
        CommodityCode = commodityCode;
    }

    public String getCommodityName() {
        return CommodityName;
    }

    public void setCommodityName(String commodityName) {
        CommodityName = commodityName;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getIMPPrice() {
        return IMPPrice;
    }

    public void setIMPPrice(String IMPPrice) {
        this.IMPPrice = IMPPrice;
    }

    public String getCommodityClassId() {
        return CommodityClassId;
    }

    public void setCommodityClassId(String commodityClassId) {
        CommodityClassId = commodityClassId;
    }

    public String getStoreId() {
        return StoreId;
    }

    public void setStoreId(String storeId) {
        StoreId = storeId;
    }

    public String getPictureURL() {
        return PictureURL;
    }

    public void setPictureURL(String pictureURL) {
        PictureURL = pictureURL;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getCommodityDesc() {
        return CommodityDesc;
    }

    public void setCommodityDesc(String commodityDesc) {
        CommodityDesc = commodityDesc;
    }

    public String getIsServiceCommodity() {
        return IsServiceCommodity;
    }

    public void setIsServiceCommodity(String isServiceCommodity) {
        IsServiceCommodity = isServiceCommodity;
    }

    public int getInventoryData() {
        return InventoryData;
    }

    public void setInventoryData(int inventoryData) {
        InventoryData = inventoryData;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getOperator() {
        return Operator;
    }

    public void setOperator(String operator) {
        Operator = operator;
    }

    public String getActiveFlg() {
        return ActiveFlg;
    }

    public void setActiveFlg(String activeFlg) {
        ActiveFlg = activeFlg;
    }

    public String getCommodityNameShort() {
        return CommodityNameShort;
    }

    public void setCommodityNameShort(String commodityNameShort) {
        CommodityNameShort = commodityNameShort;
    }
}
