package com.opentravel.web.controller.demo.domain;

import java.util.Date;
import com.opentravel.common.annotation.Excel;
import com.opentravel.common.annotation.Excel.Type;
import com.opentravel.common.core.domain.BaseEntity;
import com.opentravel.common.utils.DateUtils;

public class UserOperateModel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private int userId;

    @Excel(name = "用戶編號")
    private String userCode;

    @Excel(name = "用戶姓名")
    private String userName;

    @Excel(name = "用戶性別", readConverterExp = "0=男,1=女,2=未知")
    private String userSex;

    @Excel(name = "用戶手機")
    private String userPhone;

    @Excel(name = "用戶郵箱")
    private String userEmail;

    @Excel(name = "用戶余額")
    private double userBalance;

    @Excel(name = "用戶狀態", readConverterExp = "0=正常,1=停用")
    private String status;

    @Excel(name = "創建時間", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Type.EXPORT)
    private Date createTime;

    public UserOperateModel()
    {

    }

    public UserOperateModel(int userId, String userCode, String userName, String userSex, String userPhone,
                            String userEmail, double userBalance, String status)
    {
        this.userId = userId;
        this.userCode = userCode;
        this.userName = userName;
        this.userSex = userSex;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
        this.userBalance = userBalance;
        this.status = status;
        this.createTime = DateUtils.getNowDate();
    }

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public String getUserCode()
    {
        return userCode;
    }

    public void setUserCode(String userCode)
    {
        this.userCode = userCode;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getUserSex()
    {
        return userSex;
    }

    public void setUserSex(String userSex)
    {
        this.userSex = userSex;
    }

    public String getUserPhone()
    {
        return userPhone;
    }

    public void setUserPhone(String userPhone)
    {
        this.userPhone = userPhone;
    }

    public String getUserEmail()
    {
        return userEmail;
    }

    public void setUserEmail(String userEmail)
    {
        this.userEmail = userEmail;
    }

    public double getUserBalance()
    {
        return userBalance;
    }

    public void setUserBalance(double userBalance)
    {
        this.userBalance = userBalance;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    @Override
    public Date getCreateTime()
    {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
}