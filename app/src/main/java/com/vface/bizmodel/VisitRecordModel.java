package com.vface.bizmodel;

import java.io.Serializable;

/**
 * 拜访记录
 */
public class VisitRecordModel implements Serializable {

    private String MemberId;

    private String PhoneNumber;
    private String FullName;
    private String PicPath;
    private String CreateTime;
    private String CityName;

    private String VFaceMemberId;
    private String VFaceFullName;
    private String VFaceNickName;
    private String VFaceSex;
    private String VFaceBirthday;
    private String TotalVisitCount;
    private String GradeName;
    private String VFaceMemberCode;

    public String getTotalVisitCount() {
        return TotalVisitCount;
    }

    public void setTotalVisitCount(String totalVisitCount) {
        TotalVisitCount = totalVisitCount;
    }

    public String getGradeName() {
        return GradeName;
    }

    public void setGradeName(String gradeName) {
        GradeName = gradeName;
    }

    public String getVFaceMemberCode() {
        return VFaceMemberCode;
    }

    public void setVFaceMemberCode(String VFaceMemberCode) {
        this.VFaceMemberCode = VFaceMemberCode;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
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


    public String getVFaceSex() {
        return VFaceSex;
    }

    public void setVFaceSex(String VFaceSex) {
        this.VFaceSex = VFaceSex;
    }

    public String getVFaceBirthday() {
        return VFaceBirthday;
    }

    public void setVFaceBirthday(String VFaceBirthday) {
        this.VFaceBirthday = VFaceBirthday;
    }

    public String getMemberId() {
        return MemberId;
    }

    public void setMemberId(String memberId) {
        MemberId = memberId;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getPicPath() {
        return PicPath;
    }

    public void setPicPath(String picPath) {
        PicPath = picPath;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public String getVFaceMemberId() {
        return VFaceMemberId;
    }

    public void setVFaceMemberId(String VFaceMemberId) {
        this.VFaceMemberId = VFaceMemberId;
    }
}
