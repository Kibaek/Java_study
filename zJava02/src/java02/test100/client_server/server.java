package java02.test100.client_server;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class server {

	static Scanner keyboard = new Scanner(System.in);

	public static void main(String[] args) throws Exception {


		System.out.println("클라이의언트의 연결을 기다리는 중...");
		ServerSocket ss = new ServerSocket(8888, 1);
		Socket socket = ss.accept(); // 연결을 대기하고 있는 클라이언트중 하나와 연결을 한디.
		System.out.println("대기중에 있는 클라이언트와 연결되었습니다.");

		PrintStream out = new PrintStream(socket.getOutputStream()); // 보낼 값
		Scanner in = new Scanner(socket.getInputStream()); // 넘겨오는걸 읽을 값

		//---------------
		
		
		String message = null, line = null;

		
		while (true) {
			line = in.nextLine(); 		// 클라이언트가 보낸 문자열 읽기 (클라이언트에서 문자열을 보내기 전까지 리턴하지 않는다.)
			System.out.println(line);   // 화면에 출력한다.

			if (line.equalsIgnoreCase("quit")) {
				out.println("goodbye");
				break;

				
		// ------------------
				
			} // if

			message = prompt(); // 서버(자신) 입력한다.
			out.print(message); // 클라이언트에게 보낸다.

		} // while
		
		in.close();
		out.close();
		socket.close();
		ss.close();

	} // main

	private static String prompt() {
		System.out.println(">");
		String message = keyboard.next();
		return message;
	}
}
