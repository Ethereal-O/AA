package com.example.bookstore.WebSocket;


import com.example.bookstore.Utils.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.relational.core.sql.In;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

//import com.alibaba.fastjson.JSONObject;

import javax.websocket.server.ServerEndpoint;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/websocket/{username}/{service}")
public class UserWebSocket {

    public static Map<Integer, Session> websocketClients = new ConcurrentHashMap<Integer, Session>();

    public static List<Integer> availIds;

    public static Integer MAXID=1000;

    private static KafkaTemplate<String, String> kafkaTemplate;

    private Integer id;

    private String username;

    private String service;


    /**
     * 发送消息到指定连接
     * @param id 连接名
     * @param jsonString 消息
     */
    public static void sendMessage(String id,String jsonString){
        Session nowsession = websocketClients.get(Integer.valueOf(id));
        if(nowsession!=null){
            try {
                nowsession.getBasicRemote().sendText(jsonString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    synchronized void initializeAvailIds()
    {
        if (availIds==null)
        {
            availIds=new Vector<>();
            for (int i=1;i<=MAXID;i++)
            {
                availIds.add(i);
            }
        }
    }

    synchronized Boolean getAndDeleteId()
    {
        if (availIds.size()==0)
        {
            return false;
        }
        Integer index=new Random().nextInt(availIds.size());
        System.out.println(index);
        System.out.println(availIds.size());
        this.id=availIds.get(index);
        availIds.remove(index);
        return true;
    }

    @OnOpen
    public void onOpen(@PathParam("username") String username, @PathParam("service") String service,Session session) throws IOException {
        initializeAvailIds();
        if (!getAndDeleteId())
        {
            session.getBasicRemote().sendText("0");
            session.close();
        }
        this.username = username;
        this.service = service;
        this.kafkaTemplate= SpringContextUtil.getApplicationContext().getBean(KafkaTemplate.class);
        if(websocketClients.get(username)==null){
            websocketClients.put(id, session);
            System.out.println("当前socket通道"+username+"已加入连接，id为"+id);
        }
    }

//    @OnError
//    public void onError(Session session, Throwable error) {
////        System.out.println("服务端发生了错误"+error.getMessage());
//        System.out.println("服务端发生了错误"+error.getMessage());
//    }
    /**
     * 连接关闭
     */
    @OnClose
    public void onClose()
    {
        if (id>0)
        {
            websocketClients.remove(id);
            availIds.add(id);
        }
        System.out.println("当前socket通道"+username+"已退出连接");

    }

    /**
     * 收到客户端的消息
     *
     * @param message 消息
     * @param session 会话
     */
    @OnMessage
    public void onMessage(String message, Session session) throws InterruptedException {
        System.out.println("当前收到了消息："+message);
        if (service.equals("usercleancartservice")&&message.equals("confirm"))
        {
            System.out.println("已确认"+username+"usercleancartservice");
//            Thread.sleep(5000);
            if (id>0)
            {
                kafkaTemplate.send("usercleancartRequest", String.valueOf(id), username);
            }
//            session.getAsyncRemote().sendText("来自服务器："+this.socketname+"已完成提交");
        }
    };

//    /**
//     * 向所有连接主动推送消息
//     * @param jsonObject 消息体
//     * @throws IOException
//     */
//    public void sendMessageAll(JSONObject jsonObject) throws IOException {
//        for (Session item : websocketClients.values()) {
//            item.getAsyncRemote().sendText(jsonObject.toJSONString());
//        }
//    }

}
