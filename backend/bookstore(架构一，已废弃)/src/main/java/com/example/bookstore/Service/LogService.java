package com.example.bookstore.Service;

import java.util.List;

public interface LogService {

    public Integer tryloginservice(String username,String password);
    public boolean trylogoutservice();

    public Integer checkloginservice(String username,String password);

    public Integer registerservice(String username,String password,String email);

    public List getalluserdataservice();

    public Integer adminchangeuserdataservice(Integer id,Integer index,String content);

    public Integer admindeleteuserdataservice(Integer id);
}
