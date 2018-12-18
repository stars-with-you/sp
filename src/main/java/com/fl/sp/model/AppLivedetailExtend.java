package com.fl.sp.model;

import java.util.Map;

public class AppLivedetailExtend {
    private AppLivedetail appLivedetail;
    private Map<String, String> base64;

    public AppLivedetailExtend() {
    }

    public AppLivedetailExtend(AppLivedetail appLivedetail, Map<String, String> base64) {
        this.appLivedetail = appLivedetail;
        this.base64 = base64;
    }

    public AppLivedetail getAppLivedetail() {
        return appLivedetail;
    }

    public void setAppLivedetail(AppLivedetail appLivedetail) {
        this.appLivedetail = appLivedetail;
    }

    public Map<String, String> getBase64() {
        return base64;
    }

    public void setBase64(Map<String, String> base64) {
        this.base64 = base64;
    }
}
