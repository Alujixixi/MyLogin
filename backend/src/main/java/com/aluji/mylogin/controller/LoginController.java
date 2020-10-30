package com.aluji.mylogin.controller;

import com.aluji.mylogin.entity.User;

import com.aluji.mylogin.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping("/login")
    public Map<String, Object> handleLogin(@RequestBody Map<String, Object> body) throws IOException {
        String username = (String) body.get("username");
        String password = (String) body.get("password");
        int ret = -1;
        Map<String, Object> res = new HashMap<>();

        if(username == null || password == null) {
            res.put("ret", -1);
            res.put("msg", "invalid data format");
            return res;
        }
        User user = loginService.tryLogin(username);
        if(!password.equals(user.getPassword())) {
            res.put("ret", -1);
            res.put("msg", "wrong password");
            return res;
        }

        res.put("ret", ret);
        res.put("data", "welcome");
        return res;
    }

    @PostMapping("/register")
    public Map<String, Object> handleRegister(@RequestBody Map<String, Object> body) throws IOException {
        String username = (String) body.get("username");
        String password = (String) body.get("password");

        int user_id = loginService.createUserId();
        User newUser = new User(user_id, username, password);
        System.out.println(newUser);
        int ret = loginService.addUser(newUser);

        Map<String, Object> res = new HashMap<>();
        res.put("ret", ret);
        return res;
    }
}
