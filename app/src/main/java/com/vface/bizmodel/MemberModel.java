package com.vface.bizmodel;

import java.io.Serializable;

/**
 *
 *
 */
public class MemberModel implements Serializable{

    //
    private String MemberId="";
    private String MemberCode="";
    private String VFaceFullName="";
    private String VFaceNickName="";
    private int VFaceSex=0;
    private String VFaceBirthday="";


    //
    private String StoreMemberId;
    private String StoreId;
    private String VFACEMemberCode = "";
    private String MemberCardNumber = "";

    private String NickName = "";
    private int TotalVisitCount = 0;
    private boolean IsStealth = false;
    private boolean IsSpecialFocus = false;
    private int IsLock = 0;
    private String MemberGradeId = "";
    private String TotalMoney = "0";
    private String TopStoreId = "";
    private String TotalPoints = "0";
    private String ExpiredDate;
    private boolean IsForever;
    private int Sex = 0;
    private String FullName = "";
    private String IdNumber = "";
    private int ProvinceId;
    private int CityId = 0;
    private int AreaId;
    private String Birthday = "";
    private String GradeName = "";
    private String Email = "";
    private String Address = "";
    private String Remark = "";
    private String PicPath = "";
    private String PhoneNumber = "";


    //    private String StoreName = "";
//    private String KeyId = "";
//    private int RenId = 0;
//    private int RenqunId = 0;
//    private int FaceId = 0;
    private String CityName = "";
//    private String StrBirthday = "";
    //    private String CreateTime = "";
//    private String StrCreateTime = "";
//    private boolean ActiveFlg = false;


    public String getTotalMoney() {
        return TotalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        TotalMoney = totalMoney;
    }

    public String getTotalPoints() {
        return TotalPoints;
    }

    public void setTotalPoints(String totalPoints) {
        TotalPoints = totalPoints;
    }

    public String getStoreMemberId() {
        return StoreMemberId;
    }

    public void setStoreMemberId(String storeMemberId) {
        StoreMemberId = storeMemberId;
    }

    public String getStoreId() {
        return StoreId;
    }

    public void setStoreId(String storeId) {
        StoreId = storeId;
    }

    public String getVFACEMemberCode() {
        return VFACEMemberCode;
    }

    public void setVFACEMemberCode(String VFACEMemberCode) {
        this.VFACEMemberCode = VFACEMemberCode;
    }

    public String getMemberCardNumber() {
        return MemberCardNumber;
    }

    public void setMemberCardNumber(String memberCardNumber) {
        MemberCardNumber = memberCardNumber;
    }

    public boolean isStealth() {
        return IsStealth;
    }

    public void setStealth(boolean isStealth) {
        IsStealth = isStealth;
    }

    public boolean isSpecialFocus() {
        return IsSpecialFocus;
    }

    public void setSpecialFocus(boolean isSpecialFocus) {
        IsSpecialFocus = isSpecialFocus;
    }

    public int getIsLock() {
        return IsLock;
    }

    public void setIsLock(int isLock) {
        IsLock = isLock;
    }

    public String getMemberGradeId() {
        return MemberGradeId;
    }

    public void setMemberGradeId(String memberGradeId) {
        MemberGradeId = memberGradeId;
    }



    public String getTopStoreId() {
        return TopStoreId;
    }

    public void setTopStoreId(String topStoreId) {
        TopStoreId = topStoreId;
    }



    public String getExpiredDate() {
        return ExpiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        ExpiredDate = expiredDate;
    }

    public boolean isForever() {
        return IsForever;
    }

    public void setForever(boolean isForever) {
        IsForever = isForever;
    }

    public int getProvinceId() {
        return ProvinceId;
    }

    public void setProvinceId(int provinceId) {
        ProvinceId = provinceId;
    }

    public int getAreaId() {
        return AreaId;
    }

    public void setAreaId(int areaId) {
        AreaId = areaId;
    }

    public String getGradeName() {
        return GradeName;
    }

    public void setGradeName(String gradeName) {
        GradeName = gradeName;
    }

    //    public String getStoreName() {
//        return StoreName;
//    }
//
//    public void setStoreName(String storeName) {
//        StoreName = storeName;
//    }
//
//    public String getKeyId() {
//        return KeyId;
//    }
//
//    public void setKeyId(String keyId) {
//        KeyId = keyId;
//    }
//
//    public int getRenId() {
//        return RenId;
//    }
//
//    public void setRenId(int renId) {
//        RenId = renId;
//    }
//
//    public int getRenqunId() {
//        return RenqunId;
//    }
//
//    public void setRenqunId(int renqunId) {
//        RenqunId = renqunId;
//    }

    public String getPicPath() {
        return PicPath;
    }

    public void setPicPath(String picPath) {
        PicPath = picPath;
    }

//    public int getFaceId() {
//        return FaceId;
//    }
//
//    public void setFaceId(int faceId) {
//        FaceId = faceId;
//    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public int getSex() {
        return Sex;
    }

    public void setSex(int sex) {
        Sex = sex;
    }

    public String getIdNumber() {
        return IdNumber;
    }

    public void setIdNumber(String idNumber) {
        IdNumber = idNumber;
    }

    public int getCityId() {
        return CityId;
    }

    public void setCityId(int cityId) {
        CityId = cityId;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

//    public String getStrBirthday() {
//        return StrBirthday;
//    }
//
//    public void setStrBirthday(String strBirthday) {
//        StrBirthday = strBirthday;
//    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public int getTotalVisitCount() {
        return TotalVisitCount;
    }

    public void setTotalVisitCount(int totalVisitCount) {
        TotalVisitCount = totalVisitCount;
    }

    public boolean isIsStealth() {
        return IsStealth;
    }

    public void setIsStealth(boolean isStealth) {
        IsStealth = isStealth;
    }

    public boolean isIsSpecialFocus() {
        return IsSpecialFocus;
    }

    public void setIsSpecialFocus(boolean isSpecialFocus) {
        IsSpecialFocus = isSpecialFocus;
    }

    //    public String getCreateTime() {
//        return CreateTime;
//    }
//
//    public void setCreateTime(String createTime) {
//        CreateTime = createTime;
//    }
//
//    public boolean isActiveFlg() {
//        return ActiveFlg;
//    }
//
//    public void setActiveFlg(boolean activeFlg) {
//        ActiveFlg = activeFlg;
//    }
//
//    public String getStrCreateTime() {
//        return StrCreateTime;
//    }
//
//    public void setStrCreateTime(String strCreateTime) {
//        StrCreateTime = strCreateTime;
//    }

    public String getMemberId() {
        return MemberId;
    }

    public void setMemberId(String memberId) {
        MemberId = memberId;
    }

    public String getMemberCode() {
        return MemberCode;
    }

    public void setMemberCode(String memberCode) {
        MemberCode = memberCode;
    }

    public String getVFaceFullName() {
        return VFaceFullName;
    }

    public void setVFaceFullName(String VFaceFullName) {
        this.VFaceFullName = VFaceFullName;
    }

    public String getVFaceNickName() {
        return VFaceNickName;
    }

    public void setVFaceNickName(String VFaceNickName) {
        this.VFaceNickName = VFaceNickName;
    }

    public int getVFaceSex() {
        return VFaceSex;
    }

    public void setVFaceSex(int VFaceSex) {
        this.VFaceSex = VFaceSex;
    }

    public String getVFaceBirthday() {
        return VFaceBirthday;
    }

    public void setVFaceBirthday(String VFaceBirthday) {
        this.VFaceBirthday = VFaceBirthday;
    }
}
