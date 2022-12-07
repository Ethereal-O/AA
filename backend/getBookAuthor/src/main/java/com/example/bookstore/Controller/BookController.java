package com.example.bookstore.Controller;
import com.example.bookstore.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@Controller
public class BookController {

    @Autowired
    private BookService bookservice;

    @RequestMapping("/")
    public List getbookauthorservice(@RequestParam(name = "bookname")String bookname){
        return bookservice.getbookauthorservice(bookname);
    }
}
