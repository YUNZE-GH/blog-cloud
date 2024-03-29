package com.gh.consumer.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * @author gaohan
 * @version 1.0
 * @date 2021/2/28 22:53
 */
// todo:尝试将WebSocket持久化
@Component
@ServerEndpoint("/websocket/{username}")
public class WebSocket {

    /**
     * 在线人数
     */
    public static int onlineNumber = 0;

    /**
     * 会话
     */
    private Session session;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 建立连接
     *
     * @param session
     */
    @OnOpen
    public void onOpen(@PathParam("username") String username, Session session) {
        onlineNumber++;
        System.err.println("现在来连接的客户id：" + session.getId() + "用户名：" + username);
        this.username = username;
        this.session = session;
        System.err.println("有新连接加入！ 当前在线人数" + onlineNumber);
        try {
            //messageType 1代表上线 2代表下线 3代表在线名单 4代表普通消息
            //先给所有人发送通知，说我上线了
            Map<String, Object> map1 = Maps.newHashMap();
            map1.put("messageType", 1);
            map1.put("username", username);
            sendMessageAll(JSON.toJSONString(map1));

            //把自己的信息加入到map当中去
            WebSocketTools.getClients().put(username, this);
            //给自己发一条消息：告诉自己现在都有谁在线
            Map<String, Object> map2 = Maps.newHashMap();
            map2.put("messageType", 3);
            //移除掉自己
            Set<String> set = WebSocketTools.getClients().keySet();
            map2.put("onlineUsers", set);
            sendMessageTo(JSON.toJSONString(map2), username);
        } catch (IOException e) {
            System.err.println(username + "上线的时候通知所有人发生了错误");
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.err.println("服务端发生了错误" + error.getMessage());
    }

    /**
     * 连接关闭
     */
    @OnClose
    public void onClose() {
        onlineNumber--;
        // 删除下线用户
        WebSocketTools.getClients().remove(username);
        try {
            //messageType 1代表上线 2代表下线 3代表在线名单  4代表普通消息
            Map<String, Object> map1 = Maps.newHashMap();
            map1.put("messageType", 2);
            map1.put("onlineUsers", WebSocketTools.getClients().keySet());
            map1.put("username", username);
            sendMessageAll(JSON.toJSONString(map1));
        } catch (IOException e) {
            System.err.println(username + "下线的时候通知所有人发生了错误");
        }
        System.err.println("有连接关闭！ 当前在线人数" + onlineNumber);
    }

    /**
     * 收到客户端的消息
     *
     * @param message 消息
     * @param session 会话
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        try {
            System.err.println("来自客户端消息：" + message + "客户端的id是：" + session.getId());
            JSONObject jsonObject = JSON.parseObject(message);
            String textMessage = jsonObject.getString("message");
            String fromusername = jsonObject.getString("username");
            String tousername = jsonObject.getString("to");
            //如果不是发给所有，那么就发给某一个人
            //messageType 1代表上线、2代表下线、3代表在线名单、4代表普通消息、5代表系统消息
            Map<String, Object> map1 = Maps.newHashMap();
            map1.put("messageType", 4);
            map1.put("textMessage", textMessage);
            map1.put("fromusername", fromusername);
            if (tousername.equals("All")) {
                map1.put("tousername", "所有人");
                sendMessageAll(JSON.toJSONString(map1));
            } else {
                map1.put("tousername", tousername);
                sendMessageTo(JSON.toJSONString(map1), tousername);
            }
        } catch (Exception e) {
            System.err.println("发生了错误了");
        }

    }

    public void sendMessageTo(String message, String ToUserName) throws IOException {
        for (WebSocket item : WebSocketTools.getClients().values()) {
            if (item.username.equals(ToUserName)) {
                item.session.getAsyncRemote().sendText(message);
                break;
            }
        }
    }

    public void sendMessageAll(String message) throws IOException {
        for (WebSocket item : WebSocketTools.getClients().values()) {
            item.session.getAsyncRemote().sendText(message);
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineNumber;
    }

    public static Map<String, WebSocket> getClients(){
        return WebSocketTools.getClients();
    }

    public Session getSession() {
        return session;
    }

}
