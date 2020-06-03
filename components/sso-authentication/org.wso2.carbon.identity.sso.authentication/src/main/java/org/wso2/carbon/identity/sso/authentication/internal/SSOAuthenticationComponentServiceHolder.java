package org.wso2.carbon.identity.sso.authentication.internal;

import org.osgi.service.http.HttpService;

public class SSOAuthenticationComponentServiceHolder {

    private static HttpService httpService;

    private SSOAuthenticationComponentServiceHolder() {

    }

    public static HttpService getHttpService() {
        return httpService;
    }

    public static void setHttpService(HttpService httpService) {
        SSOAuthenticationComponentServiceHolder.httpService = httpService;
    }
}
