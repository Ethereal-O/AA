package com.example.bookstore.Dao;

import com.example.bookstore.Entity.UserEntity;

import java.util.List;

public interface UserDao {
    //增删改
    public boolean addUser(UserEntity user);
    public boolean updateUser(UserEntity user);
    public boolean deleteUser(UserEntity user);
    //查询
    //根据唯一标识查询单个实体
    public UserEntity findUserById(Integer id);
    public UserEntity findUserByName(String name);
    //根据实体的条件查询多个实体
    public List<UserEntity> findUserByExample(UserEntity user);
    //得到所有实体
    public List<UserEntity> findallUser();
}
