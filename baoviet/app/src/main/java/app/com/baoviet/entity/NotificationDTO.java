package app.com.baoviet.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 6/21/2018.
 */

public class NotificationDTO implements Serializable {
    private String notifyId;
    private String notifySendType;
    private String notifyUserCode;
    private String notifyTitle;
    private String notifyContent;
    private String notifyType;
    private String notifyTypeDescription;
    private String notifyStatus;
    private Long createdDate;
    private Long updatedDate;

    public String getNotifyId() {
        return notifyId;
    }

    public void setNotifyId(String notifyId) {
        this.notifyId = notifyId;
    }

    public String getNotifySendType() {
        return notifySendType;
    }

    public void setNotifySendType(String notifySendType) {
        this.notifySendType = notifySendType;
    }

    public String getNotifyUserCode() {
        return notifyUserCode;
    }

    public void setNotifyUserCode(String notifyUserCode) {
        this.notifyUserCode = notifyUserCode;
    }

    public String getNotifyTitle() {
        return notifyTitle;
    }

    public void setNotifyTitle(String notifyTitle) {
        this.notifyTitle = notifyTitle;
    }

    public String getNotifyContent() {
        return notifyContent;
    }

    public void setNotifyContent(String notifyContent) {
        this.notifyContent = notifyContent;
    }

    public String getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(String notifyType) {
        this.notifyType = notifyType;
    }

    public String getNotifyTypeDescription() {
        return notifyTypeDescription;
    }

    public void setNotifyTypeDescription(String notifyTypeDescription) {
        this.notifyTypeDescription = notifyTypeDescription;
    }

    public String getNotifyStatus() {
        return notifyStatus;
    }

    public void setNotifyStatus(String notifyStatus) {
        this.notifyStatus = notifyStatus;
    }

    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    public Long getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Long updatedDate) {
        this.updatedDate = updatedDate;
    }
}
