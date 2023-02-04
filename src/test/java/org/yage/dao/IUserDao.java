package org.yage.dao;

import org.yage.po.User;

public interface IUserDao {
    User queryUserInfoById(Long id);
}
