package com.example.bookstore.solr;

import com.example.bookstore.Entity.BookEntity;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.beans.Field;

import java.math.BigDecimal;

public class BookIndex {
    @Field("id")
    public String id;
    @Field("description")
    public String description;

    public BookIndex(BookEntity bookEntity)
    {
        id=String.valueOf(bookEntity.getId());
        description=bookEntity.getDescription();
    }

    public BookIndex() {
    }

    static SolrQuery getQuery(String str)
    {
        final SolrQuery query = new SolrQuery("*:*");
        query.addField("id");
        query.addField("description");


        query.setSort("id", SolrQuery.ORDER.asc);

        query.addFilterQuery("description:"+str);

        return query;
    }

}
