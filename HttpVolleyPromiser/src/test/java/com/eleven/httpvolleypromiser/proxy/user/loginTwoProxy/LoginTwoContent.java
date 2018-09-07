package com.eleven.httpvolleypromiser.proxy.user.loginTwoProxy;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.eleven.httpvolleypromiser.proxy.base.BaseModelDto;

public class LoginTwoContent extends BaseModelDto{

    private String UserName;
    private String CurrentAppUrl;
    private String Message;
    private String UserData;
    private int RoleType;
    private String UserPicture;
    private String ChannelID;
    private int Timestamp;
    private String RefreshToken;
    private String CurrentAppID;
    private String UserID;
    private String AccessToken;
    private int State;

    public void setUserName(String userName){
        this.UserName = userName;
    }
    public String getUserName(){
        return this.UserName;
    }

    public void setCurrentAppUrl(String currentAppUrl){
        this.CurrentAppUrl = currentAppUrl;
    }
    public String getCurrentAppUrl(){
        return this.CurrentAppUrl;
    }

    public void setMessage(String message){
        this.Message = message;
    }
    public String getMessage(){
        return this.Message;
    }

    public void setUserData(String userData){
        this.UserData = userData;
    }
    public String getUserData(){
        return this.UserData;
    }

    public void setRoleType(int roleType){
        this.RoleType = roleType;
    }
    public int getRoleType(){
        return this.RoleType;
    }

    public void setUserPicture(String userPicture){
        this.UserPicture = userPicture;
    }
    public String getUserPicture(){
        return this.UserPicture;
    }

    public void setChannelID(String channelID){
        this.ChannelID = channelID;
    }
    public String getChannelID(){
        return this.ChannelID;
    }

    public void setTimestamp(int timestamp){
        this.Timestamp = timestamp;
    }
    public int getTimestamp(){
        return this.Timestamp;
    }

    public void setRefreshToken(String refreshToken){
        this.RefreshToken = refreshToken;
    }
    public String getRefreshToken(){
        return this.RefreshToken;
    }

    public void setCurrentAppID(String currentAppID){
        this.CurrentAppID = currentAppID;
    }
    public String getCurrentAppID(){
        return this.CurrentAppID;
    }

    public void setUserID(String userID){
        this.UserID = userID;
    }
    public String getUserID(){
        return this.UserID;
    }

    public void setAccessToken(String accessToken){
        this.AccessToken = accessToken;
    }
    public String getAccessToken(){
        return this.AccessToken;
    }

    public void setState(int state){
        this.State = state;
    }
    public int getState(){
        return this.State;
    }


    @Override
    public Map<String, String> getFieldMap() {
        Map<String, String> map = new HashMap<String, String>();

        map.put("UserName", safeGetHttpData(this.UserName));
        map.put("CurrentAppUrl", safeGetHttpData(this.CurrentAppUrl));
        map.put("Message", safeGetHttpData(this.Message));
        map.put("UserData", safeGetHttpData(this.UserData));
        map.put("RoleType", safeGetHttpData(this.RoleType));
        map.put("UserPicture", safeGetHttpData(this.UserPicture));
        map.put("ChannelID", safeGetHttpData(this.ChannelID));
        map.put("Timestamp", safeGetHttpData(this.Timestamp));
        map.put("RefreshToken", safeGetHttpData(this.RefreshToken));
        map.put("CurrentAppID", safeGetHttpData(this.CurrentAppID));
        map.put("UserID", safeGetHttpData(this.UserID));
        map.put("AccessToken", safeGetHttpData(this.AccessToken));
        map.put("State", safeGetHttpData(this.State));
        return map;
    }

}