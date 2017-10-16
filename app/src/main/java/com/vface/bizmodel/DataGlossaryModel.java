package com.vface.bizmodel;

import java.util.ArrayList;

/**
 * Created by HuBin on 15/5/6.
 */
public class DataGlossaryModel {

    private String StoreId;
    private String TotalExpenseAmount;
    private String TotalMemberCount;
    private String TotalNewMemberCount;
    private String TotalChongzhiMemberCount;
    private String TotalChongzhiAmount;
    private String TotalFastExpenseCount;
    private String TotalFastExpenseAmount;
    private String TotalCommodityExpenseAmount;
    private String TotalTimeExpense;
    private String TotalTimeChongzhiAmount;
    private String TotalCountExpense;
    private String TotalDiscountAmount;
    private String TotalCleanDecimalAmount;
    private String TotalCountChongzhiAmount;
    private ArrayList<CommodityClassSummary> CommodityClassSummaryList;


    public class CommodityClassSummary{

        private String CommodityClassName;
        private String AmountRequired;
        private String AmountPayed;
//        private String OringTableSchema;
//        private DataCollectionModel DataCollection;
//        private String State;
//        private ArrayList<String> Keys;
//        private ArrayList<String> Values;
//        private String Count;
//        private boolean IsReadOnly;

        public String getCommodityClassName() {
            return CommodityClassName;
        }

        public void setCommodityClassName(String commodityClassName) {
            CommodityClassName = commodityClassName;
        }

        public String getAmountRequired() {
            return AmountRequired;
        }

        public void setAmountRequired(String amountRequired) {
            AmountRequired = amountRequired;
        }

        public String getAmountPayed() {
            return AmountPayed;
        }

        public void setAmountPayed(String amountPayed) {
            AmountPayed = amountPayed;
        }

//        public String getOringTableSchema() {
//            return OringTableSchema;
//        }
//
//        public void setOringTableSchema(String oringTableSchema) {
//            OringTableSchema = oringTableSchema;
//        }
//
//        public DataCollectionModel getDataCollection() {
//            return DataCollection;
//        }
//
//        public void setDataCollection(DataCollectionModel dataCollection) {
//            DataCollection = dataCollection;
//        }
//
//        public String getState() {
//            return State;
//        }
//
//        public void setState(String state) {
//            State = state;
//        }
//
//        public ArrayList<String> getKeys() {
//            return Keys;
//        }
//
//        public void setKeys(ArrayList<String> keys) {
//            Keys = keys;
//        }
//
//        public ArrayList<String> getValues() {
//            return Values;
//        }
//
//        public void setValues(ArrayList<String> values) {
//            Values = values;
//        }
//
//        public String getCount() {
//            return Count;
//        }
//
//        public void setCount(String count) {
//            Count = count;
//        }
//
//        public boolean isReadOnly() {
//            return IsReadOnly;
//        }
//
//        public void setReadOnly(boolean isReadOnly) {
//            IsReadOnly = isReadOnly;
//        }
    }

//    class DataCollectionModel{
//
//        private String CommodityClassName;
//        private String AmountRequired;
//        private String AmountPayed;
//
//        public String getCommodityClassName() {
//            return CommodityClassName;
//        }
//
//        public void setCommodityClassName(String commodityClassName) {
//            CommodityClassName = commodityClassName;
//        }
//
//        public String getAmountRequired() {
//            return AmountRequired;
//        }
//
//        public void setAmountRequired(String amountRequired) {
//            AmountRequired = amountRequired;
//        }
//
//        public String getAmountPayed() {
//            return AmountPayed;
//        }
//
//        public void setAmountPayed(String amountPayed) {
//            AmountPayed = amountPayed;
//        }
//    }


    public String getStoreId() {
        return StoreId;
    }

    public void setStoreId(String storeId) {
        StoreId = storeId;
    }

    public String getTotalExpenseAmount() {
        return TotalExpenseAmount;
    }

    public void setTotalExpenseAmount(String totalExpenseAmount) {
        TotalExpenseAmount = totalExpenseAmount;
    }

    public String getTotalMemberCount() {
        return TotalMemberCount;
    }

    public void setTotalMemberCount(String totalMemberCount) {
        TotalMemberCount = totalMemberCount;
    }

    public String getTotalNewMemberCount() {
        return TotalNewMemberCount;
    }

    public void setTotalNewMemberCount(String totalNewMemberCount) {
        TotalNewMemberCount = totalNewMemberCount;
    }

    public String getTotalChongzhiMemberCount() {
        return TotalChongzhiMemberCount;
    }

    public void setTotalChongzhiMemberCount(String totalChongzhiMemberCount) {
        TotalChongzhiMemberCount = totalChongzhiMemberCount;
    }

    public String getTotalFastExpenseAmount() {
        return TotalFastExpenseAmount;
    }

    public void setTotalFastExpenseAmount(String totalFastExpenseAmount) {
        TotalFastExpenseAmount = totalFastExpenseAmount;
    }

    public String getTotalCommodityExpenseAmount() {
        return TotalCommodityExpenseAmount;
    }

    public void setTotalCommodityExpenseAmount(String totalCommodityExpenseAmount) {
        TotalCommodityExpenseAmount = totalCommodityExpenseAmount;
    }

    public String getTotalTimeExpense() {
        return TotalTimeExpense;
    }

    public void setTotalTimeExpense(String totalTimeExpense) {
        TotalTimeExpense = totalTimeExpense;
    }

    public String getTotalTimeChongzhiAmount() {
        return TotalTimeChongzhiAmount;
    }

    public void setTotalTimeChongzhiAmount(String totalTimeChongzhiAmount) {
        TotalTimeChongzhiAmount = totalTimeChongzhiAmount;
    }

    public String getTotalCountExpense() {
        return TotalCountExpense;
    }

    public void setTotalCountExpense(String totalCountExpense) {
        TotalCountExpense = totalCountExpense;
    }

    public String getTotalCountChongzhiAmount() {
        return TotalCountChongzhiAmount;
    }

    public void setTotalCountChongzhiAmount(String totalCountChongzhiAmount) {
        TotalCountChongzhiAmount = totalCountChongzhiAmount;
    }

    public ArrayList<CommodityClassSummary> getCommodityClassSummaryList() {
        return CommodityClassSummaryList;
    }

    public void setCommodityClassSummaryList(ArrayList<CommodityClassSummary> commodityClassSummaryList) {
        CommodityClassSummaryList = commodityClassSummaryList;
    }

    public String getTotalFastExpenseCount() {
        return TotalFastExpenseCount;
    }

    public void setTotalFastExpenseCount(String totalFastExpenseCount) {
        TotalFastExpenseCount = totalFastExpenseCount;
    }

    public String getTotalChongzhiAmount() {
        return TotalChongzhiAmount;
    }

    public void setTotalChongzhiAmount(String totalChongzhiAmount) {
        TotalChongzhiAmount = totalChongzhiAmount;
    }

	public String getTotalDiscountAmount() {
		return TotalDiscountAmount;
	}

	public void setTotalDiscountAmount(String totalDiscountAmount) {
		TotalDiscountAmount = totalDiscountAmount;
	}

	public String getTotalCleanDecimalAmount() {
		return TotalCleanDecimalAmount;
	}

	public void setTotalCleanDecimalAmount(String totalCleanDecimalAmount) {
		TotalCleanDecimalAmount = totalCleanDecimalAmount;
	}
    
    
}
