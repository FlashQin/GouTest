package net.goutalk.fowit.Bean;

public class VersionBean {
    /**
     * code : 0
     * msg : 成功
     * data : {"id":"4","appId":"1","appName":"勾转","systemType":"android","versionNumber":1.1,"channel":null,"downloadUrl":"http://www.51wit.cn/files/apk/51wit.apk","upgradeDate":"2020-07-10 00:00:00","upgradeLog":null,"forceInstall":1}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 4
         * appId : 1
         * appName : 勾转
         * systemType : android
         * versionNumber : 1.1
         * channel : null
         * downloadUrl : http://www.51wit.cn/files/apk/51wit.apk
         * upgradeDate : 2020-07-10 00:00:00
         * upgradeLog : null
         * forceInstall : 1
         */

        private String id;
        private String appId;
        private String appName;
        private String systemType;
        private double versionNumber;
        private Object channel;
        private String downloadUrl;
        private String upgradeDate;
        private Object upgradeLog;
        private int forceInstall;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getSystemType() {
            return systemType;
        }

        public void setSystemType(String systemType) {
            this.systemType = systemType;
        }

        public double getVersionNumber() {
            return versionNumber;
        }

        public void setVersionNumber(double versionNumber) {
            this.versionNumber = versionNumber;
        }

        public Object getChannel() {
            return channel;
        }

        public void setChannel(Object channel) {
            this.channel = channel;
        }

        public String getDownloadUrl() {
            return downloadUrl;
        }

        public void setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }

        public String getUpgradeDate() {
            return upgradeDate;
        }

        public void setUpgradeDate(String upgradeDate) {
            this.upgradeDate = upgradeDate;
        }

        public Object getUpgradeLog() {
            return upgradeLog;
        }

        public void setUpgradeLog(Object upgradeLog) {
            this.upgradeLog = upgradeLog;
        }

        public int getForceInstall() {
            return forceInstall;
        }

        public void setForceInstall(int forceInstall) {
            this.forceInstall = forceInstall;
        }
    }
}
