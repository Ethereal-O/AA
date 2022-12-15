package com.example.bookstore.ServiceImpl;

import com.example.bookstore.Dao.*;
import com.example.bookstore.DaoImpl.BookDaoImpl;
import com.example.bookstore.Entity.*;
import com.example.bookstore.Repository.BookTypeRepository;
import com.example.bookstore.Service.BookService;
import com.example.bookstore.solr.Book;
import com.example.bookstore.solr.BookIndex;
import com.example.bookstore.solr.SolrUtil;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

import static com.example.bookstore.solr.SolrUtil.addBook;
import static com.example.bookstore.solr.SolrUtil.addBookIndex;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookdao;
    @Autowired
    private UserDao userdao;
    @Autowired
    private CartDao cartdao;
    @Autowired
    private OrderDao orderdao;
    @Autowired
    private OrderitemDao orderitemdao;


    private List getgetusershelfdata(String username)
    {
        UserEntity user=userdao.findUserByName(username);
        List<Pair> bookdata=new ArrayList<>();
        Set<OrderEntity> allorders=user.getOrders();
        for (OrderEntity order:allorders)
        {
            Set<OrderitemEntity> allorderitems=order.getOrderitems();
            for (OrderitemEntity orderitem:allorderitems)
            {
                bookdata.add(Pair.of(orderitem.getBook(),order));
            }
        }
        return bookdata;
    }

    private CartEntity getgetuserdeletecart(Integer bookid,String username)
    {
        UserEntity user=userdao.findUserByName(username);
        Set<CartEntity> carts=user.getCarts();
        for (CartEntity cart:carts)
        {
            if (cart.getBook().getId()==bookid){
                return cart;
            }
        }
        return null;
    }

    public List getallbookdataservice(){
        List<BookEntity> bookdata=bookdao.findallBook();
        List<List> data=new ArrayList<>();
        for (int i=0;i<bookdata.size();i++)
        {
            List<String> tmpbook = new ArrayList<>();
            BookEntity tmpbookentity=bookdata.get(i);
            tmpbook.add(String.valueOf(tmpbookentity.getId()));
            tmpbook.add(tmpbookentity.getType());
            tmpbook.add(tmpbookentity.getName());
            tmpbook.add(tmpbookentity.getAuthor());
            tmpbook.add(String.valueOf(tmpbookentity.getPrice()));
            tmpbook.add(tmpbookentity.getImage());
            tmpbook.add(tmpbookentity.getDescription());
            tmpbook.add(String.valueOf(tmpbookentity.getInventory()));
            tmpbook.add(String.valueOf(tmpbookentity.getISBN_num()));
            data.add(tmpbook);
        }
        return data;
    }

    @Override
    public List searchbookdataservice(String keyword) throws SolrServerException, IOException {
//        List<BookEntity> bookdata=SolrUtil.queryBook(keyword);


        List<BookIndex> bookIndexList= SolrUtil.queryBookIndex(keyword);
        List<BookEntity> bookdata=new ArrayList<>();
        for (BookIndex bookIndex : bookIndexList)
        {
            bookdata.add(bookdao.findBookById(Integer.valueOf(bookIndex.id)));
        }

        List<List> data=new ArrayList<>();
        for (int i=0;i<bookdata.size();i++)
        {
            List<String> tmpbook = new ArrayList<>();
            BookEntity tmpbookentity=bookdata.get(i);
            tmpbook.add(String.valueOf(tmpbookentity.getId()));
            tmpbook.add(tmpbookentity.getType());
            tmpbook.add(tmpbookentity.getName());
            tmpbook.add(tmpbookentity.getAuthor());
            tmpbook.add(String.valueOf(tmpbookentity.getPrice()));
            tmpbook.add(tmpbookentity.getImage());
            tmpbook.add(tmpbookentity.getDescription());
            tmpbook.add(String.valueOf(tmpbookentity.getInventory()));
            tmpbook.add(String.valueOf(tmpbookentity.getISBN_num()));
            data.add(tmpbook);
        }
        return data;


    }

    @Override
    public void pushbookdataservice() throws SolrServerException, IOException {
        List<BookEntity> bookdata=bookdao.findallBook();
        for (BookEntity bookEntity:bookdata)
        {
//            addBook(bookEntity);
            addBookIndex(bookEntity);
        }
    }


    @Override
    public Integer addbooktypedataservice() {
        bookdao.addbooktypedata();
        return 1;
    }

    @Override
    public List searchbooktypeservice(String type) {
        List<BookTypeEntity> similars=bookdao.searchsimilars(type);

        List<BookEntity> bookdata=new ArrayList<>();
        for (BookTypeEntity bookType:similars)
        {
            List<BookEntity> similarbooks=bookdao.findBookByType(bookType.getType());
            bookdata.addAll(similarbooks);
        }

        List<List> data=new ArrayList<>();
        for (int i=0;i<bookdata.size();i++)
        {
            List<String> tmpbook = new ArrayList<>();
            BookEntity tmpbookentity=bookdata.get(i);
            tmpbook.add(String.valueOf(tmpbookentity.getId()));
            tmpbook.add(tmpbookentity.getType());
            tmpbook.add(tmpbookentity.getName());
            tmpbook.add(tmpbookentity.getAuthor());
            tmpbook.add(String.valueOf(tmpbookentity.getPrice()));
            tmpbook.add(tmpbookentity.getImage());
            tmpbook.add(tmpbookentity.getDescription());
            tmpbook.add(String.valueOf(tmpbookentity.getInventory()));
            tmpbook.add(String.valueOf(tmpbookentity.getISBN_num()));
            data.add(tmpbook);
        }
        return data;
    }


    public Integer adminchangedataservice(Integer index, Integer options, String content)
    {
        BookEntity newbook=bookdao.findBookById(index);
        if (options==2)
        {
            newbook.setName(content);
        }
        if (options==3)
        {
            newbook.setAuthor(content);
        }
        if (options==4)
        {
            newbook.setPrice(new BigDecimal(content));
        }
        if (options==5)
        {
            newbook.setImage(content);
        }
        if (options==6)
        {
            newbook.setDescription(content);
        }
        if (options==7)
        {
            newbook.setInventory(Integer.parseInt(content));
        }
        if (options==8)
        {
            newbook.setISBN_num(Integer.parseInt(content));
        }
        bookdao.updateBook(newbook);
        return 1;
    }

    @Override
    public Integer admindeletebookservice(Integer bookid) {
        // 删除订单记录
//        List<OrderitemEntity> allorderitems=orderitemdao.findallOrderitem();
//        List<OrderitemEntity> needdeleteorderitems=new ArrayList<>();
//        for (OrderitemEntity orderitem:allorderitems)
//        {
//            if(orderitem.getBook().getId()==bookid)
//            {
//                needdeleteorderitems.add(orderitem);
//            }
//        }
        List<OrderitemEntity> needdeleteorderitems=orderitemdao.findOrderitemsByBookId(bookid);
        System.out.println(needdeleteorderitems.size());

        // 遍历所有订单表项
        for(OrderitemEntity needdeleteorderitem:needdeleteorderitems)
        {
            OrderEntity order=needdeleteorderitem.getOrder();
//            orderitemdao.deleteOrderitem(needdeleteorderitem);
            order.getOrderitems().remove(needdeleteorderitem);
            orderdao.updateOrder(order);
            if(order.getOrderitems().size()==0){
                orderdao.deleteOrder(order);
            }
        }

        // 删除购物车记录
//        List<CartEntity> carts=cartdao.findallCart();
//        for(CartEntity cart:carts)
//        {
//            if(cart.getBook().getId()==bookid){
//                cartdao.deleteCart(cart);
//            }
//        }
        List<CartEntity> carts=cartdao.findCartsByBookId(bookid);
        for(CartEntity cart:carts)
        {
            cartdao.deleteCart(cart);
        }

        // 删除书籍
        bookdao.deleteBook(bookdao.findBookById(bookid));
        return 1;
    }

    @Override
    public Integer adminaddbookservice(String booktype) {
        BookEntity newbook=new BookEntity();
        newbook.setType(booktype);
        newbook.setName("Default name");
        newbook.setAuthor("Default author");
        newbook.setPrice(BigDecimal.valueOf(0));
        newbook.setImage("https://i-1-lanrentuku.52tup.com/2020/12/29/f3eab929-d990-42d9-b858-0270ae7528b6.jpg?imageView2/2/w/1024/");
        newbook.setDescription("Default description");
        newbook.setInventory(0);
        newbook.setTotal_num(0);
        newbook.setISBN_num(0);

        bookdao.addBook(newbook);
        return 1;
    }


    public Integer addcartservice(Integer bookid,String username)
    {
        List<Pair<BookEntity,Date>> bookdata=getgetusershelfdata(username);
        UserEntity user=userdao.findUserByName(username);
        Set<CartEntity> carts=user.getCarts();
        for (CartEntity cart:carts)
        {
            bookdata.add(Pair.of(cart.getBook(),new Date()));
        }
        for (int i=0;i<bookdata.size();i++)
        {
            if(bookdata.get(i).getFirst().getId()==bookid) return 0;
        }

        CartEntity newcart=new CartEntity();
        newcart.setBook(bookdao.findBookById(bookid));
        newcart.setUser(userdao.findUserByName(username));
//        cartdao.addCart(newcart);
        carts.add(newcart);
        userdao.updateUser(user);
        return 1;
    }


    public List getusercartdataservice(String username)
    {
        UserEntity user=userdao.findUserByName(username);
        Set<CartEntity> carts=user.getCarts();
        List<BookEntity> bookdata = new ArrayList<BookEntity>();
        for (CartEntity cart:carts)
        {
            bookdata.add(cart.getBook());
        }
        List<List> data=new ArrayList<>();
        for (int i=0;i<bookdata.size();i++)
        {
            List<String> tmpbook = new ArrayList<>();
            BookEntity tmpbookentity=bookdata.get(i);
            tmpbook.add(String.valueOf(tmpbookentity.getId()));
            tmpbook.add(tmpbookentity.getType());
            tmpbook.add(tmpbookentity.getName());
            tmpbook.add(tmpbookentity.getAuthor());
            tmpbook.add(String.valueOf(tmpbookentity.getPrice()));
            tmpbook.add(tmpbookentity.getImage());
            tmpbook.add(tmpbookentity.getDescription());
            tmpbook.add(String.valueOf(tmpbookentity.getInventory()));
            tmpbook.add(String.valueOf(tmpbookentity.getISBN_num()));
            data.add(tmpbook);
        }
        return data;
    }


    public Integer userdeletecartdataservice(Integer bookid,String username)
    {
        UserEntity user=userdao.findUserByName(username);
        Set<CartEntity> carts=user.getCarts();
        for (CartEntity cart:carts)
        {
            if (cart.getBook().getId()==bookid){
                carts.remove(cart);
                userdao.updateUser(user);
                return 1;
            }
        }
        return -1;
    }


//    public Integer usercleancartservice(String username)
//    {
//        UserEntity user=userdao.findUserByName(username);
//        OrderEntity neworder=new OrderEntity();
//        neworder.setUser(user);
//        neworder.setOrder_time(new Timestamp((new Date()).getTime()));
////        orderdao.addOrder(neworder);
//        Set<CartEntity> carts=user.getCarts();
//        for (CartEntity cart:carts)
//        {
//            BookEntity book=cart.getBook();
//            if(book.getInventory()<=0) return -1;
//            book.setInventory(book.getInventory()-1);
//            bookdao.updateBook(book);
//
//            OrderitemEntity neworderitem=new OrderitemEntity();
//            neworderitem.setOrder(neworder);
//            neworderitem.setBook(book);
////            orderitemdao.addOrderitem(neworderitem);
////            cartdao.deleteCart(cart);
//            if(neworder.getOrderitems()==null) neworder.setOrderitems(new HashSet<>());
//            neworder.getOrderitems().add(neworderitem);
//        }
//        user.getOrders().add(neworder);
//        carts.clear();
//        userdao.updateUser(user);
//        return 1;
//    }

    @Transactional
//    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public Integer usercleancartservice(String username)
    {
        UserEntity user=userdao.findUserByName(username);
        OrderEntity neworder=new OrderEntity();
        neworder.setUser(user);
        Timestamp newtimestamp=new Timestamp((new Date()).getTime());
        neworder.setOrder_time(newtimestamp);
        Set<CartEntity> carts=user.getCarts();
        for (CartEntity cart:carts)
        {
            BookEntity book=cart.getBook();
            if(book.getInventory()<=0) return -1;
        }

        Integer maxId=orderdao.findmaxOrderid();
        neworder.setId(maxId+1);
        orderdao.addOrderUseTX(neworder);
//        int result=10/0;
        for (CartEntity cart:carts)
        {
            BookEntity book=cart.getBook();
            book.setInventory(book.getInventory()-1);
            bookdao.updateBook(book);

            orderitemdao.addOrderitemUseTX(maxId+1,book.getId());
//            int result=10/0;
        }

        for (CartEntity cart:carts)
        {
            cartdao.deleteCartByIdUseTX(cart.getId());
        }

        return neworder.getId();
    }


    public List getusershelfdataservice(String username)
    {
        List<Pair<BookEntity,OrderEntity>> bookdata=getgetusershelfdata(username);
        List<List> data=new ArrayList<>();
        for (int i=0;i<bookdata.size();i++)
        {
            List<String> tmpbook = new ArrayList<>();
            BookEntity tmpbookentity=bookdata.get(i).getFirst();
            tmpbook.add(String.valueOf(tmpbookentity.getId()));
            tmpbook.add(tmpbookentity.getType());
            tmpbook.add(tmpbookentity.getName());
            tmpbook.add(tmpbookentity.getAuthor());
            tmpbook.add(String.valueOf(tmpbookentity.getPrice()));
            tmpbook.add(tmpbookentity.getImage());
            tmpbook.add(tmpbookentity.getDescription());
            tmpbook.add(String.valueOf(tmpbookentity.getInventory()));
            tmpbook.add(String.valueOf(tmpbookentity.getISBN_num()));
            tmpbook.add(String.valueOf(bookdata.get(i).getSecond().getOrder_time()));
            tmpbook.add(String.valueOf(bookdata.get(i).getSecond().getId()));
            data.add(tmpbook);
        }
        return data;
    }

    @Override
    public List getallorderdataservice() {
        List data=new ArrayList();
        List<UserEntity> users=userdao.findallUser();
        for (int i=0;i< users.size();i++)
        {
            UserEntity user=users.get(i);
            Set<OrderEntity> orders=user.getOrders();
            if(orders.size()==0) continue;
            for(OrderEntity order:orders)
            {
                Set<OrderitemEntity> orderitems= order.getOrderitems();
                for (OrderitemEntity orderitem:orderitems)
                {
                    List<String> tmpdata = new ArrayList();
                    tmpdata.add(user.getUsername());
                    tmpdata.add(String.valueOf(order.getId()));
                    tmpdata.add(orderitem.getBook().getName());
                    tmpdata.add(String.valueOf(order.getOrder_time()));
                    data.add(tmpdata);
                }
            }
        }
        return data;
    }

    @Override
    public Integer adminaddorderservice(String username, Integer orderid, Integer bookid) {
        UserEntity user=userdao.findUserByName(username);
        // user不存在
        if(user==null) return -1;
        Set<OrderEntity> orders=user.getOrders();
        OrderEntity order=null;
        for (OrderEntity torder:orders)
        {
            if (torder.getId()==orderid)
            {
                order=torder;
            }
            Set<OrderitemEntity> orderitems=torder.getOrderitems();
            for (OrderitemEntity orderitem:orderitems)
            {
                // 已经有此书
                if (orderitem.getBook().getId()==bookid) return -1;
            }
        }
        if (order==null)
        {
            List<OrderEntity> allorders=orderdao.findallOrder();
            for(OrderEntity torder:allorders)
            {
                // 已经有此orderid
                if(torder.getId()==orderid) return -1;
            }
            // 新order
            order=new OrderEntity();
            order.setUser(user);
            order.setOrder_time(new Timestamp((new Date()).getTime()));
            orderdao.addOrder(order);
        }

        // 旧order
        BookEntity book=bookdao.findBookById(bookid);
        if (book==null) return -1;
        book.setInventory(book.getInventory()-1);
        bookdao.updateBook(book);
        OrderitemEntity neworderitem=new OrderitemEntity();
        neworderitem.setOrder(order);
        neworderitem.setBook(book);
        orderitemdao.addOrderitem(neworderitem);
        return 1;
    }

    @Override
    public Integer admindeleteorderservice(Integer orderid, Integer bookid) {
        OrderEntity order=orderdao.findOrderById(orderid);
        if (order==null) return -1;
        Set<OrderitemEntity> orderitems=order.getOrderitems();
        for (OrderitemEntity orderitem:orderitems)
        {
            if(orderitem.getBook().getId()==bookid)
            {
                BookEntity book=orderitem.getBook();
                book.setInventory(book.getInventory()+1);
                bookdao.updateBook(book);
//                orderitemdao.deleteOrderitem(orderitem);
                orderitems.remove(orderitem);
                orderdao.updateOrder(order);
                if(order.getOrderitems().size()==0)
                {
                    orderdao.deleteOrder(order);
                }
                return 1;
            }
        }
        return -1;
    }
}
