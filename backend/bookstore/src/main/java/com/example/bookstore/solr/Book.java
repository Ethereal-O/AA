package com.example.bookstore.solr;

import com.example.bookstore.Entity.BookEntity;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.beans.Field;

import java.math.BigDecimal;

public class Book {
    @Field("id")
    public String id;
    @Field("type")
    public String type;
    @Field("name")
    public String name;
    @Field("author")
    public String author;
    @Field("price")
    public Double price;
    @Field("image")
    public String image;
    @Field("description")
    public String description;
    @Field("inventory")
    public Integer inventory;
    @Field("total_num")
    public Integer total_num;
    @Field("ISBN_num")
    public Integer ISBN_num;

    public Book(BookEntity bookEntity)
    {
        id=String.valueOf(bookEntity.getId());
        type=bookEntity.getType();
        name=bookEntity.getName();
        author=bookEntity.getAuthor();
        price=bookEntity.getPrice().doubleValue();
        image=bookEntity.getImage();
        description=bookEntity.getDescription();
        inventory=bookEntity.getInventory();
        total_num=bookEntity.getTotal_num();
        ISBN_num=bookEntity.getISBN_num();
    }

    public Book() {
    }

    BookEntity convert()
    {
        BookEntity bookEntity=new BookEntity();
        bookEntity.setId(Integer.valueOf(id));
        bookEntity.setType(type);
        bookEntity.setName(name);
        bookEntity.setAuthor(author);
        bookEntity.setPrice(new BigDecimal(price));
        bookEntity.setImage(image);
        bookEntity.setDescription(description);
        bookEntity.setInventory(inventory);
        bookEntity.setTotal_num(total_num);
        bookEntity.setISBN_num(ISBN_num);

        return bookEntity;
    }

    static SolrQuery getQuery(String str)
    {
        final SolrQuery query = new SolrQuery("*:*");
        query.addField("id");
        query.addField("type");
        query.addField("name");
        query.addField("author");
        query.addField("price");
        query.addField("image");
        query.addField("description");
        query.addField("inventory");
        query.addField("total_num");
        query.addField("ISBN_num");

        query.setSort("id", SolrQuery.ORDER.asc);

        query.addFilterQuery("description:*"+str+"*");

        return query;
    }


}
