package sadp.messaging;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class SecondUser {
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		Scanner input = new Scanner(System.in);

		String[] user = { "Ahmad", "Abu" };
		int receiver = 99;

		while (true) {
			do {
				System.out.println("Ali, select the person you want to chat by entering the number:");
				for (int i = 0; i < user.length; i++) {
					System.out.println(i + ": " + user[i]);
				}

				receiver = input.nextInt();

			} while (receiver >= user.length || receiver < 0);

			System.out.println("You are now chatting with " + user[receiver] + "\n Enter '/e' to stop chatting");
			Chat("Ali", user[receiver], channel, input);
			receiver = 99;
		}
	}

	static void Chat(String sender, String receiver, Channel channel, Scanner input)
			throws IOException, TimeoutException {
		channel.queueDeclare(receiver, false, false, false, null);

		channel.basicConsume(sender, true, (consumerTag, message) -> {

			String m = new String(message.getBody(), "UTF-8");
			System.out.println(receiver + ": " + m);
		}, consumerTag -> {
		});

		while (true) {
			String chat = input.nextLine(); // Read user input
			if (chat.equals("/e")) {
				break;
			}

			channel.basicPublish("", receiver, false, null, chat.getBytes());
		}
	}
}
