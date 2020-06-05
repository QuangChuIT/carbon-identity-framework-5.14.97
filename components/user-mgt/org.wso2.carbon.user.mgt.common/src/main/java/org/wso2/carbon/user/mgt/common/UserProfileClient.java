package org.wso2.carbon.user.mgt.common;

public class UserProfileClient {
    public UserProfileClient() {

    }

    String[] userPropertiesValue;

    public UserProfileClient(String[] values) {
        userPropertiesValue = values;
    }

    public String[] getUserPropertiesValue() {
        return userPropertiesValue;
    }

    public void setUserPropertiesValue(String[] userPropertiesValue) {
        this.userPropertiesValue = userPropertiesValue;
    }
}

