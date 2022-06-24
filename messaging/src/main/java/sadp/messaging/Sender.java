package sadp.messaging;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Sender {
    public static void main(String[] args) throws IOException, TimeoutException{
    ConnectionFactory factory = new ConnectionFactory();

    try(
    Connection connection = factory.newConnection())
    {
        Channel channel = connection.createChannel();
        channel.queueDeclare("hello-world", false, false, false, null);

        String message = "What time is it?" + LocalDateTime.now();

        channel.basicPublish("", "hello-world", false, null, message.getBytes());

        System.out.println("! ! ! message has been sent");
    }

    // Connection connection = factory.newConnection();
    // Channel channel = connection.createChannel();
    // channel.queueDeclare("hello-world", false, false, false, null);

    // channel.basicConsume("hello-world", true, (consumerTag, message)->{
    // String m = new String(message.getBody(), "UTF-8");
    // System.out.println("Message received: " + m);
    // }, consumerTag -> {});
    }
}
