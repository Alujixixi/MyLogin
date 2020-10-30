package com.aluji.mylogin.service;

import com.aluji.mylogin.entity.AutoId;
import com.aluji.mylogin.entity.User;
import com.aluji.mylogin.mapper.LoginMapper;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import static org.apache.ibatis.io.Resources.getResourceAsStream;

@Service
public class LoginService {

    public int addUser(User user) throws IOException {
        InputStream in = getResourceAsStream("SqlMapConfig.xml");

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);

        SqlSession session = sqlSessionFactory.openSession();
        LoginMapper loginMapper = session.getMapper(LoginMapper.class);
        int res = loginMapper.addAccount(user);
        System.out.println("add_user res: " + res);
        if(res != 1) {
            return -1;
        }
        session.commit();
        session.close();
        in.close();
        return 0;
    }

    public int createUserId() throws IOException {
//        InputStream in = getResourceAsStream("SqlMapConfig.xml");
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
//        SqlSession session = sqlSessionFactory.openSession();
        Random random =new Random();
        int dlt = random.nextInt(5) + 1;
        int res = insertAutoUserId(dlt);
//        LoginMapper loginMapper = session.getMapper(LoginMapper.class);
//        int res = loginMapper.selectAutoUserId();
//        System.out.println(res);
//        session.close();
//        in.close();
        return res;
    }

    public int insertAutoUserId(int n) throws IOException {
        InputStream in = getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
        SqlSession session = sqlSessionFactory.openSession();
        LoginMapper loginMapper = session.getMapper(LoginMapper.class);
        int userId = 0;
        AutoId autoId = new AutoId();
        for(int i = 0; i < n; i++) {
            loginMapper.insertAutoUserId(autoId);
        }
        userId = autoId.getId();
        session.commit();
        session.close();
        in.close();
        return userId;
    }


    public User tryLogin(String accountName) throws IOException {
        InputStream in = getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
        SqlSession session = sqlSessionFactory.openSession();
        LoginMapper loginMapper = session.getMapper(LoginMapper.class);
        User user = loginMapper.queryUserByAccountName(accountName);
        session.commit();
        session.close();
        in.close();
        return user;
    }
}
