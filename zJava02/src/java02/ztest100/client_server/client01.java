package java02.ztest100.client_server;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class client01 {

	static Scanner keyboard = new Scanner(System.in);

	public static void main(String[] args) throws Exception {

		Socket socket = new Socket("192.168.0.2", 8888);

		PrintStream out = new PrintStream(socket.getOutputStream());
		Scanner in = new Scanner(socket.getInputStream());

		String message = null, line = null;

		while (true) {

			message = prompt();
			out.println(message);

			line = in.nextLine();
			System.out.println(line);

			if (line.equalsIgnoreCase("goodbye")) {
				System.out.println("서버와 연결이 끊겼습니다.");
				break;
				
			}
		}

		in.close();
		out.close();
		socket.close();
		keyboard.close();

	}

	private static String prompt() {
		System.out.println(">");
		String message = keyboard.nextLine();
		return message;
		
	}
}
