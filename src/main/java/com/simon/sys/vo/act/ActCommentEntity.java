package com.simon.sys.vo.act;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author Simon
 * @date 2019/9/2
 * @time 15:51
 * @description 自定义批注信息实体类
 */
public class ActCommentEntity {

    private String userId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date time;

    private String message;

    private String fullMessage;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFullMessage() {
        return fullMessage;
    }

    public void setFullMessage(String fullMessage) {
        this.fullMessage = fullMessage;
    }
}
