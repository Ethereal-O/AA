package com.example.bookstore.Repository;

import com.example.bookstore.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer> {

    @Query(value = "from UserEntity where id=:id")
    public UserEntity findUserById(Integer id);

    @Query(value = "from UserEntity where username=:name")
    public List<UserEntity> findUserByName(String name);

    @Query(value = "from UserEntity ")
    public List<UserEntity> findallUser();
}
