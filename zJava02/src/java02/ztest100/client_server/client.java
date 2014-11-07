package java02.ztest100.client_server;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class client {

	static Scanner keyboard = new Scanner(System.in); // --

	public static void main(String[] args) throws Exception {
		
		System.out.println("서버와 연결 중...");
		Socket socket = new Socket("123.23.23.23", 8888); // --
		System.out.println("서버와 연결 성공!");
		
		PrintStream out = new PrintStream(socket.getOutputStream()); // --
		Scanner in = new Scanner(socket.getInputStream()); // --

		String message = null, line = null;
		// message = 사용자가 보낼메세지, line = 서버에게 받을 메세지.

		while (true) {
			message = prompt();   // 클라이언트(자신) 입력한다.
			out.println(message); // 서버에게 메세지 보낸다.
			
			//------ 서버에게 받을 메세지를 기다리는중

			line = in.nextLine(); // 서버에게 메세지를 받는다.
			System.out.println(line); // 받은 메세지를 출력한다.

			if (line.equalsIgnoreCase("goodbye")) {
				System.out.println("서버와 연결을 끊었습니다.");
				break;
				
			} // if
		} // while

		in.close();
		out.close();
		socket.close();
		keyboard.close();

	} // main

	private static String prompt() {
		System.out.println(">");
		String message = keyboard.nextLine();
		return message;
	}
}
