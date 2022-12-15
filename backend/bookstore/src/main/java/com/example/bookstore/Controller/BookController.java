package com.example.bookstore.Controller;
import com.example.bookstore.Service.BookService;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@Controller
//@CrossOrigin
public class BookController {

    @Autowired
    private BookService bookservice;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @RequestMapping("/getallbookdataservice")
    public List getallbookdataservice(){
        return bookservice.getallbookdataservice();
    }

    @RequestMapping("/searchbookdataservice")
    public List searchbookdataservice(@RequestParam(name = "keyword")String keyword) throws SolrServerException, IOException {
        return bookservice.searchbookdataservice(keyword);
    }

    @RequestMapping("/pushbookdataservice")
    public void pushbookdataservice() throws SolrServerException, IOException {
        bookservice.pushbookdataservice();
    }

    @RequestMapping("/addbooktypedataservice")
    public void addbooktypedataservice() throws SolrServerException, IOException {
        bookservice.addbooktypedataservice();
    }

    @RequestMapping("/searchbooktypeservice")
    public List searchbooktypeservice(@RequestParam(name = "type")String type)
    {
        return bookservice.searchbooktypeservice(type);
    }

    @RequestMapping("/adminchangedataservice")
    public Integer adminchangedataservice(@RequestParam(name = "index")Integer index, @RequestParam(name = "options")Integer options,@RequestParam(name = "content")String content)
    {
        return bookservice.adminchangedataservice(index,options,content);
    }

    @RequestMapping("admindeletebookservice")
    public Integer admindeletebookservice(@RequestParam(name="bookid")Integer bookid)
    {
        return bookservice.admindeletebookservice(bookid);
    }

    @RequestMapping("adminaddbookservice")
    public Integer adminaddbookservice(@RequestParam(name="booktype")String booktype)
    {
        return bookservice.adminaddbookservice(booktype);
    }

    @RequestMapping("/addcartservice")
    public Integer addcartservice(@RequestParam(name = "bookid")Integer bookid,@RequestParam(name = "username")String username)
    {
        return bookservice.addcartservice(bookid,username);
    }

    @RequestMapping("/getusercartdataservice")
    public List getusercartdataservice(@RequestParam(name = "username")String username)
    {
        return bookservice.getusercartdataservice(username);
    }

    @RequestMapping("/userdeletecartdataservice")
    public Integer userdeletecartdataservice(@RequestParam(name = "bookid")Integer bookid,@RequestParam(name = "username")String username)
    {
        return  bookservice.userdeletecartdataservice(bookid,username);
    }

//    @RequestMapping("/usercleancartservice")
//    public Integer usercleancartservice(@RequestParam(name = "username")String username)
//    {
//        return  bookservice.usercleancartservice(username);
//    }

    @RequestMapping("/usercleancartservice")
    public Integer usercleancartservice(@RequestParam(name = "username")String username)
    {
        kafkaTemplate.send("usercleancartRequest",  "username", username);
        return 1;
    }

    @RequestMapping("/getusershelfdataservice")
    public List getusershelfdataservice(@RequestParam(name = "username")String username)
    {
        return bookservice.getusershelfdataservice(username);
    }

    @RequestMapping("getallorderdataservice")
    public List getallorderdataservice(){return bookservice.getallorderdataservice();}

    @RequestMapping("adminaddorderservice")
    public Integer adminaddorderservice(@RequestParam(name="username")String username,@RequestParam(name="orderid")Integer orderid,@RequestParam(name="bookid")Integer bookid)
    {
        return bookservice.adminaddorderservice(username,orderid,bookid);
    }

    @RequestMapping("admindeleteorderservice")
    public Integer admindeleteorderservice(@RequestParam(name="orderid")Integer orderid,@RequestParam(name="bookid")Integer bookid)
    {
        return bookservice.admindeleteorderservice(orderid,bookid);
    }
}
