package com.vface.db.entity;


import com.vface.db.EntityBase;

public class UserEntity extends EntityBase {
    public UserTable TableSchema() {
        return (UserTable) _tableSchema;
    }

    public UserEntity() {
        _tableSchema = UserTable.Current();
    }

    public String getUserId() {
        return (String) GetData(UserTable.C_UserId);
    }

    public void setUserId(String value) {
        SetData(UserTable.C_UserId, value);
    }

    public String getFullname() {
        return (String) GetData(UserTable.C_Fullname);
    }

    public void setFullname(String value) {
        SetData(UserTable.C_Fullname, value);
    }

    public String getStoreId() {
        return (String) GetData(UserTable.C_StoreId);
    }

    public void setStoreId(String value) {
        SetData(UserTable.C_StoreId, value);
    }

    public String getStoreName() {
        return (String) GetData(UserTable.C_StoreName);
    }

    public void setStoreName(String value) {
        SetData(UserTable.C_StoreName, value);
    }


    public String getAccount() {
        return (String) GetData(UserTable.C_Account);
    }

    public void setAccount(String value) {
        SetData(UserTable.C_Account, value);
    }

    public String getPassword() {
        return (String) GetData(UserTable.C_Password);
    }

    public void setPassword(String value) {
        SetData(UserTable.C_Password, value);
    }
    public String getUserPicture() {
        return (String) GetData(UserTable.C_UserPicture);
    }

    public void setUserPicture(String value) {
        SetData(UserTable.C_UserPicture, value);
    }

}
