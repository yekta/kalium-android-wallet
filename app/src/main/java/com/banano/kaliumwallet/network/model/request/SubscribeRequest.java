package com.banano.kaliumwallet.network.model.request;

import com.banano.kaliumwallet.network.model.Actions;
import com.banano.kaliumwallet.network.model.BaseRequest;
import com.google.gson.annotations.SerializedName;

/**
 * Subscribe to websocket server for updates regarding the specified account.
 * First action to take when connecting when app opens or reconnects, IF a wallet already exists
 */

public class SubscribeRequest extends BaseRequest {
    @SerializedName("action")
    private String action;

    @SerializedName("account")
    private String account;

    @SerializedName("currency")
    private String currency;

    @SerializedName("uuid")
    private String uuid;

    @SerializedName("fcm_token")
    private String fcmToken;

    public SubscribeRequest() {
        this.action = Actions.SUBSCRIBE.toString();
    }

    public SubscribeRequest(String account, String currency, String uuid, String fcmToken) {
        this.action = Actions.SUBSCRIBE.toString();
        this.currency = currency;
        if (uuid != null) {
            this.uuid = uuid;
            this.account = null;
        } else {
            this.account = account;
            this.uuid = null;
        }
        this.fcmToken = fcmToken;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
