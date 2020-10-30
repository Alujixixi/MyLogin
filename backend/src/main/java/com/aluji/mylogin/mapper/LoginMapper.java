package com.aluji.mylogin.mapper;

import com.aluji.mylogin.entity.AutoId;
import com.aluji.mylogin.entity.User;

public interface LoginMapper {

    int addAccount(User user);

    int insertAutoUserId(AutoId autoId);

    User queryUserByAccountName(String accountName);
}
