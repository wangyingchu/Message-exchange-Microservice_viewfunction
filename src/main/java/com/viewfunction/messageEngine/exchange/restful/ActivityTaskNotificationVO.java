package com.viewfunction.messageEngine.exchange.restful;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ActivityTaskNotificationVO")
public class ActivityTaskNotificationVO {
	private String activityId;
	private String activityName;
	private String notificationId;
	private String taskDescription;
	private long taskDueDate;
	private String taskDueStatus;
	private String taskName;
	private String taskRole;
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getNotificationId() {
		return notificationId;
	}
	public void setNotificationId(String notificationId) {
		this.notificationId = notificationId;
	}
	public String getTaskDescription() {
		return taskDescription;
	}
	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}
	public long getTaskDueDate() {
		return taskDueDate;
	}
	public void setTaskDueDate(long taskDueDate) {
		this.taskDueDate = taskDueDate;
	}
	public String getTaskDueStatus() {
		return taskDueStatus;
	}
	public void setTaskDueStatus(String taskDueStatus) {
		this.taskDueStatus = taskDueStatus;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskRole() {
		return taskRole;
	}
	public void setTaskRole(String taskRole) {
		this.taskRole = taskRole;
	}
}