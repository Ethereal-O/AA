package com.example.bookstore.Repository;

import com.example.bookstore.Entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartEntity,Integer> {

    @Query(value = "from CartEntity where id=:id")
    public CartEntity findCartById(Integer id);

    @Query(value = "from CartEntity")
    public List<CartEntity> findallCart();

    @Query(value = "from CartEntity where book.id=:bookid")
    public List<CartEntity> findCartsByBookId(Integer bookid);

    @Transactional
    @Modifying
    @Query(value="delete from cart where cart.id=:bookid",nativeQuery = true)
    public void deleteCartById(Integer bookid);
}
