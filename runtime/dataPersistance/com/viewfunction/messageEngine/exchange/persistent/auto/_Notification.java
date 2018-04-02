package com.viewfunction.messageEngine.exchange.persistent.auto;

import java.util.Date;

import org.apache.cayenne.CayenneDataObject;

import com.viewfunction.messageEngine.exchange.persistent.ActivityTaskNotificationInfo;
import com.viewfunction.messageEngine.exchange.persistent.ExternalResourceNotificationInfo;

/**
 * Class _Notification was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _Notification extends CayenneDataObject {

    public static final String GROUP_ID_PROPERTY = "groupId";
    public static final String NOTIFICATION_CONTENT_PROPERTY = "notificationContent";
    public static final String NOTIFICATION_HANDLEABLE_PROPERTY = "notificationHandleable";
    public static final String NOTIFICATION_READ_STATUS_PROPERTY = "notificationReadStatus";
    public static final String NOTIFICATION_RECEIVER_ID_PROPERTY = "notificationReceiverId";
    public static final String NOTIFICATION_RECEIVERS_ID_PROPERTY = "notificationReceiversId";
    public static final String NOTIFICATION_RECEIVERS_NAME_PROPERTY = "notificationReceiversName";
    public static final String NOTIFICATION_RECEIVERS_TYPE_PROPERTY = "notificationReceiversType";
    public static final String NOTIFICATION_SCOPE_PROPERTY = "notificationScope";
    public static final String NOTIFICATION_SENDER_ID_PROPERTY = "notificationSenderId";
    public static final String NOTIFICATION_SENDER_NAME_PROPERTY = "notificationSenderName";
    public static final String NOTIFICATION_SENT_DATE_PROPERTY = "notificationSentDate";
    public static final String NOTIFICATION_STATUS_PROPERTY = "notificationStatus";
    public static final String NOTIFICATION_TITLE_PROPERTY = "notificationTitle";
    public static final String NOTIFICATION_TYPE_PROPERTY = "notificationType";
    public static final String TO_ACTIVITY_TASK_NOTIFICATION_INFO_PROPERTY = "toActivityTaskNotificationInfo";
    public static final String TO_EXTERNAL_RESOURCE_NOTIFICATION_INFO_PROPERTY = "toExternalResourceNotificationInfo";

    public static final String NOTIFICATION_ID_PK_COLUMN = "notificationId";

    public void setGroupId(String groupId) {
        writeProperty("groupId", groupId);
    }
    public String getGroupId() {
        return (String)readProperty("groupId");
    }

    public void setNotificationContent(String notificationContent) {
        writeProperty("notificationContent", notificationContent);
    }
    public String getNotificationContent() {
        return (String)readProperty("notificationContent");
    }

    public void setNotificationHandleable(Boolean notificationHandleable) {
        writeProperty("notificationHandleable", notificationHandleable);
    }
    public Boolean getNotificationHandleable() {
        return (Boolean)readProperty("notificationHandleable");
    }

    public void setNotificationReadStatus(Boolean notificationReadStatus) {
        writeProperty("notificationReadStatus", notificationReadStatus);
    }
    public Boolean getNotificationReadStatus() {
        return (Boolean)readProperty("notificationReadStatus");
    }

    public void setNotificationReceiverId(String notificationReceiverId) {
        writeProperty("notificationReceiverId", notificationReceiverId);
    }
    public String getNotificationReceiverId() {
        return (String)readProperty("notificationReceiverId");
    }

    public void setNotificationReceiversId(String notificationReceiversId) {
        writeProperty("notificationReceiversId", notificationReceiversId);
    }
    public String getNotificationReceiversId() {
        return (String)readProperty("notificationReceiversId");
    }

    public void setNotificationReceiversName(String notificationReceiversName) {
        writeProperty("notificationReceiversName", notificationReceiversName);
    }
    public String getNotificationReceiversName() {
        return (String)readProperty("notificationReceiversName");
    }

    public void setNotificationReceiversType(String notificationReceiversType) {
        writeProperty("notificationReceiversType", notificationReceiversType);
    }
    public String getNotificationReceiversType() {
        return (String)readProperty("notificationReceiversType");
    }

    public void setNotificationScope(String notificationScope) {
        writeProperty("notificationScope", notificationScope);
    }
    public String getNotificationScope() {
        return (String)readProperty("notificationScope");
    }

    public void setNotificationSenderId(String notificationSenderId) {
        writeProperty("notificationSenderId", notificationSenderId);
    }
    public String getNotificationSenderId() {
        return (String)readProperty("notificationSenderId");
    }

    public void setNotificationSenderName(String notificationSenderName) {
        writeProperty("notificationSenderName", notificationSenderName);
    }
    public String getNotificationSenderName() {
        return (String)readProperty("notificationSenderName");
    }

    public void setNotificationSentDate(Date notificationSentDate) {
        writeProperty("notificationSentDate", notificationSentDate);
    }
    public Date getNotificationSentDate() {
        return (Date)readProperty("notificationSentDate");
    }

    public void setNotificationStatus(String notificationStatus) {
        writeProperty("notificationStatus", notificationStatus);
    }
    public String getNotificationStatus() {
        return (String)readProperty("notificationStatus");
    }

    public void setNotificationTitle(String notificationTitle) {
        writeProperty("notificationTitle", notificationTitle);
    }
    public String getNotificationTitle() {
        return (String)readProperty("notificationTitle");
    }

    public void setNotificationType(String notificationType) {
        writeProperty("notificationType", notificationType);
    }
    public String getNotificationType() {
        return (String)readProperty("notificationType");
    }

    public void setToActivityTaskNotificationInfo(ActivityTaskNotificationInfo toActivityTaskNotificationInfo) {
        setToOneTarget("toActivityTaskNotificationInfo", toActivityTaskNotificationInfo, true);
    }

    public ActivityTaskNotificationInfo getToActivityTaskNotificationInfo() {
        return (ActivityTaskNotificationInfo)readProperty("toActivityTaskNotificationInfo");
    }


    public void setToExternalResourceNotificationInfo(ExternalResourceNotificationInfo toExternalResourceNotificationInfo) {
        setToOneTarget("toExternalResourceNotificationInfo", toExternalResourceNotificationInfo, true);
    }

    public ExternalResourceNotificationInfo getToExternalResourceNotificationInfo() {
        return (ExternalResourceNotificationInfo)readProperty("toExternalResourceNotificationInfo");
    }


}
