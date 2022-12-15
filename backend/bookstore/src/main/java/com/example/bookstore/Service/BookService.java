package com.example.bookstore.Service;

import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;
import java.util.List;

public interface BookService {
    public List getallbookdataservice();

    public List searchbookdataservice(String keyword) throws SolrServerException, IOException;

    void pushbookdataservice() throws SolrServerException, IOException;

    public Integer addbooktypedataservice();

    public List searchbooktypeservice(String type);

    public Integer adminchangedataservice(Integer index, Integer options, String content);

    public Integer admindeletebookservice(Integer bookid);

    public Integer adminaddbookservice(String booktype);

    public Integer addcartservice(Integer bookid,String username);

    public List getusercartdataservice(String username);

    public Integer userdeletecartdataservice(Integer bookid,String username);

    public Integer usercleancartservice(String username);

    public List getusershelfdataservice(String username);

    public List getallorderdataservice();

    public Integer adminaddorderservice(String username, Integer orderid, Integer bookid);

    public Integer admindeleteorderservice(Integer orderid,Integer bookid);

}
