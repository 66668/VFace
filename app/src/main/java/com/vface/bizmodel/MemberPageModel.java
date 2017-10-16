package com.vface.bizmodel;

import java.util.ArrayList;

/**
 * Created by HuBin on 15/4/23.
 */
public class MemberPageModel {

    private int total;
    private ArrayList<MemberModel> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<MemberModel> getRows() {
        return rows;
    }

    public void setRows(ArrayList<MemberModel> rows) {
        this.rows = rows;
    }
}
