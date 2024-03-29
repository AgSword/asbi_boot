package com.agsword.asbi.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户更新请求   管理员操作
 *
 * @author as
 */
@Data
public class UserUpdateRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户角色：user/admin
     */
    private String userRole;

    /**
     * 性别 男 女
     */
    private String gender;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态 0 - 正常 1-注销 2-封号
     */
    private Integer userStatus;

    /**
     * 用户编号
     */
    private String userCode;

    private static final long serialVersionUID = 1L;
}