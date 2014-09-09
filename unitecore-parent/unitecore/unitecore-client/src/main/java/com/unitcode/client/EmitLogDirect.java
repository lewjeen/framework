package com.unitcode.client;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * <strong>Title : EmitLogDirect </strong>. <br>
 * <strong>Description : 类的描述信息.</strong> <br>
 * <strong>Create on : 2014年8月6日 下午7:41:44 </strong>. <br>
 * <p>
 * <strong>Copyright (C) Mocha Software Co.,Ltd.</strong> <br>
 * </p>
 * @author 刘军  liujun1@mochasoft.com.cn <br>
 * @version <strong>Mocha JavaOA v7.0.0</strong> <br>
 * <br>
 * <strong>修改历史: .</strong> <br>
 * 修改人 修改日期 修改描述<br>
 * -------------------------------------------<br>
 * <br>
 * <br>
 */
public class EmitLogDirect { 
  
    private static final String EXCHANGE_NAME = "direct_logs";  
  
    public static void main(String[] argv)  
                  throws java.io.IOException {  
  
        ConnectionFactory factory = new ConnectionFactory();  
        factory.setHost("localhost");  
        Connection connection = factory.newConnection();  
        Channel channel = connection.createChannel();  
  
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");  
  
        String severity = getSeverity(argv);  
        String message = getMessage(argv);  
  
        channel.basicPublish(EXCHANGE_NAME, severity, null, message.getBytes());  
        System.out.println(" [x] Sent '" + severity + "':'" + message + "'");  
  
        channel.close();  
        connection.close();  
    }  
      
    private static String getMessage(String[] strings){  
        if (strings.length < 1)  
            return "Hello World!";  
        return joinStrings(strings, " ");  
    }  
      
    private static String joinStrings(String[] strings, String delimiter) {  
        int length = strings.length;  
        if (length == 0) return "";  
        StringBuilder words = new StringBuilder(strings[0]);  
        for (int i = 1; i < length; i++) {  
            words.append(delimiter).append(strings[i]);  
        }  
        return words.toString();  
    }  
      
    private static String getSeverity(String[] strings){  
        if (strings.length < 1)  
                    return "info";  
        return strings[0];  
      }  }
