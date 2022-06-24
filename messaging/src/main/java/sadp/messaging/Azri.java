package sadp.messaging;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Azri {
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		Scanner input = new Scanner(System.in);
		channel.queueDeclare("USER2", false, false, false, null);

		channel.basicConsume("USER1", true, (consumerTag, message) -> {

			String m = new String(message.getBody(), "UTF-8");
			System.out.println("Azhan: " + m);
		}, consumerTag -> {
		});

		while (true) {
			String chat = input.nextLine(); // Read user inputh
			if (chat.equals("stop")) {
				break;
			}

			channel.basicPublish("", "USER2", false, null, chat.getBytes());

		}
		input.close();

	}
}
