package com.example.bookstore.solr;

import com.example.bookstore.Dao.BookDao;
import com.example.bookstore.DaoImpl.BookDaoImpl;
import com.example.bookstore.Entity.BookEntity;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.MapSolrParams;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SolrUtil {
    public static SolrClient getSolrClient() {
        final String solrUrl = "http://localhost:8983/solr";
        return new HttpSolrClient.Builder(solrUrl)
                .withConnectionTimeout(10000)
                .withSocketTimeout(60000)
                .build();
    }

    public static void addBook(BookEntity bookEntity) throws SolrServerException, IOException {
        final SolrClient client = getSolrClient();
        Book book=new Book(bookEntity);
        final UpdateResponse response = client.addBean("bookstore", book);
        client.commit("bookstore");
    }

    public static void addBookIndex(BookEntity bookEntity) throws SolrServerException, IOException {
        final SolrClient client = getSolrClient();
        BookIndex book=new BookIndex(bookEntity);
        final UpdateResponse response = client.addBean("bookstore", book);
        client.commit("bookstore");
    }

    public static List<BookEntity> queryBook(String str) throws SolrServerException, IOException {
        SolrQuery query=Book.getQuery(str);

        final SolrClient client = getSolrClient();

        final QueryResponse responseOne = client.query("bookstore", query);
        final List<Book> books = responseOne.getBeans(Book.class);
        List<BookEntity> bookEntities = new ArrayList<>();
        for (Book book : books) {
            bookEntities.add(book.convert());
        }

        return bookEntities;
    }

    public static List<BookIndex> queryBookIndex(String str) throws SolrServerException, IOException {
        SolrQuery query=BookIndex.getQuery(str);

        final SolrClient client = getSolrClient();

        final QueryResponse responseOne = client.query("bookstore", query);
        final List<BookIndex> books = responseOne.getBeans(BookIndex.class);

        return books;
    }


    public static void main(String[] args) throws SolrServerException, IOException {
        BookDao bookDao=new BookDaoImpl();
        List<BookEntity> bookdata=bookDao.findallBook();
        for (BookEntity bookEntity:bookdata)
        {
            addBook(bookEntity);
        }
    }
}


