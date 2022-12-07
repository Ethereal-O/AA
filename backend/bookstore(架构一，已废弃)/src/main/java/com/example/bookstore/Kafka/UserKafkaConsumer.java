package com.example.bookstore.Kafka;


import com.example.bookstore.Service.BookService;
import com.example.bookstore.WebSocket.UserWebSocket;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;


@Component
public class UserKafkaConsumer {

    @Autowired
    private BookService bookservice;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private UserWebSocket userWebSocket;

    @KafkaListener(topics = "usercleancartRequest", groupId = "group_topic_bookstore")
    public void usercleancartRequestListener(ConsumerRecord<String, String> record, Acknowledgment item) {
        Integer id = Integer.valueOf(record.key());
        String username = record.value();
//        System.out.println(username);
//        System.out.println(record);
        //手动提交
        item.acknowledge();
        Integer result=bookservice.usercleancartservice(username);
        kafkaTemplate.send("usercleancartSuccess", String.valueOf(id), String.valueOf(result));
    }

    @KafkaListener(topics = "usercleancartSuccess", groupId = "group_topic_bookstore")
    public void usercleancartSuccessListener(ConsumerRecord<String, String> record, Acknowledgment item) {
//        String value = record.value();
//        System.out.println(value);
//        System.out.println(record);
        //手动提交
        item.acknowledge();
        userWebSocket.sendMessage(record.key(),record.value());
    }
}
