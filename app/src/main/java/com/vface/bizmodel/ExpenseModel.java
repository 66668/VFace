package com.vface.bizmodel;

/**
 * Created by HuBin on 15/4/23.
 */
public class ExpenseModel {

    private String ExpenseId;
    private int ExpenseType;
    private String ExpenseDate;
    private String OrderNumber;
    private String TotalQuantity;
    private String TotalAmount;
    private String TotalPoints;
    private String StoreName;
    private String StoreId;

    public String getExpenseId() {
        return ExpenseId;
    }

    public void setExpenseId(String expenseId) {
        ExpenseId = expenseId;
    }

    public int getExpenseType() {
        return ExpenseType;
    }

    public void setExpenseType(int expenseType) {
        ExpenseType = expenseType;
    }

    public String getExpenseDate() {
        return ExpenseDate;
    }

    public void setExpenseDate(String expenseDate) {
        ExpenseDate = expenseDate;
    }

    public String getOrderNumber() {
        return OrderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        OrderNumber = orderNumber;
    }

    public String getTotalQuantity() {
        return TotalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        TotalQuantity = totalQuantity;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }

    public String getTotalPoints() {
        return TotalPoints;
    }

    public void setTotalPoints(String totalPoints) {
        TotalPoints = totalPoints;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }

    public String getStoreId() {
        return StoreId;
    }

    public void setStoreId(String storeId) {
        StoreId = storeId;
    }
}
