package com.ansgar.mylib.api.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by kirila on 19.2.17.
 */
public class ServerResponse implements Serializable {

    @SerializedName("message")
    private String mMessage;

    public ServerResponse() {

    }

    public ServerResponse(String message) {
        mMessage = message;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }
}
