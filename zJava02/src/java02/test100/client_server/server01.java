package java02.test100.client_server;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class server01 {

	static Scanner keyboard = new Scanner(System.in);

	public static void main(String[] args) throws Exception {

		ServerSocket ss = new ServerSocket(8888, 2);
		Socket socket = ss.accept();

		PrintStream out = new PrintStream(socket.getOutputStream());
		Scanner in = new Scanner(socket.getInputStream());

		String message = null, line = null;

		while (true) {

			line = in.nextLine();
			System.out.println(line);

			if (line.equalsIgnoreCase("exit")) {
				System.out.println("클라이언트와 연결이 끊겼습니다.");
				break;
				
			}
			
			message = prompt();
			System.out.println(message);
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
