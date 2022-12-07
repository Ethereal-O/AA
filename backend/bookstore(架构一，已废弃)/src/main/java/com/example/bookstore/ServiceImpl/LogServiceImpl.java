package com.example.bookstore.ServiceImpl;

import com.example.bookstore.Dao.UserDao;
import com.example.bookstore.Entity.*;
import com.example.bookstore.Service.LogService;
import com.example.bookstore.Utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.Integer.parseInt;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private UserDao userdao;

    public Integer tryloginservice(String username, String password){
        UserEntity user=userdao.findUserByName(username);
        if (user==null) return -1;
        if (Objects.equals(user.getPassword(), password))
        {
            SessionUtil.SetSession(user.getId(),user.getType());
            return user.getType();
        }
        return 0;
    }

    @Override
    public boolean trylogoutservice() {
        return SessionUtil.RemoveSession();
    }

    public Integer checkloginservice(String username, String password){
        UserEntity user=userdao.findUserByName(username);
        if (user==null) return -1;
        if (Objects.equals(user.getPassword(), password))
        {
            SessionUtil.SetSession(user.getId(),user.getType());
            return user.getType();
        }
        return 0;
    }

    public Integer registerservice(String username, String password,String email)
    {
        UserEntity user=userdao.findUserByName(username);
        if (user!=null) return 0;
        UserEntity newuser=new UserEntity();
        newuser.setUsername(username);
        newuser.setPassword(password);
        newuser.setType(1);
        newuser.setEmail(email);
        userdao.addUser(newuser);
        return 1;
    }

    @Override
    public List getalluserdataservice() {
        List<UserEntity> userdata=userdao.findallUser();
        List<List> data=new ArrayList<>();
        for (int i=0;i<userdata.size();i++)
        {
            List<String> tmpuser = new ArrayList<>();
            UserEntity tmpuserentity=userdata.get(i);
            tmpuser.add(String.valueOf(tmpuserentity.getId()));
            tmpuser.add(tmpuserentity.getUsername());
            tmpuser.add(tmpuserentity.getPassword());
            tmpuser.add(String.valueOf(tmpuserentity.getType()));
            data.add(tmpuser);
        }
        return data;
    }

    @Override
    public Integer adminchangeuserdataservice(Integer id, Integer index, String content) {
        UserEntity newuser=userdao.findUserById(id);
        if (index==1)
        {
            newuser.setUsername(content);
        }
        if (index==2)
        {
            newuser.setPassword(content);
        }
        if (index==3)
        {
            newuser.setType(parseInt(content));
        }
        userdao.updateUser(newuser);
        return 1;
    }

    @Override
    public Integer admindeleteuserdataservice(Integer id) {
        UserEntity olduser=userdao.findUserById(id);
        userdao.deleteUser(olduser);
        return 1;
    }
}
