package broker_architecture;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Consumer {
	public static void main(String[] args) throws IOException, TimeoutException{
		ConnectionFactory factory = new ConnectionFactory();
	  	
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare("hello-world", false, false, false, null);
		
		
		channel.basicConsume("hello-world", true, (consumerTag, message)->{
			String m = new String(message.getBody(), "UTF-8");
			System.out.println("Message received yehehe: " + m);
		}, consumerTag -> {});
		
	}
}
