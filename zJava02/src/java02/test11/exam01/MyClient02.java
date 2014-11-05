package java02.test11.exam01;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class MyClient02 {

	
	public static void main(String[] args) throws Exception {
		System.out.println("서버와 연결 중...");
		
		// 서버와 연결할 소켓 생성
		// 서버와의 연결이 이루어지면 리턴한다.
		Socket socket = new Socket("192.168.0.173", 8888); // 상대방  
		System.out.println("서버와 연결 성공!");
		
		
		Scanner in = new Scanner(socket.getInputStream()); // 바이트로 받으려고하면 너무 힘들기 때문에, 문자열로 받는다. by Scanner를 사용하여.
		PrintStream out = new PrintStream(socket.getOutputStream());
		
		// 서버에 보낼 메시지를 사용자에게서 입력 받는다.
		String message = prompt(); 
		
		// 서버가 메세지를 보낸다.
		out.println(message);// 서버가 데이터를 모두 읽을때까지 리턴하지 않는다.
		
		// 서버가 보낸 메세지를 읽는다.
		String line = in.nextLine(); // 서버가 문자열 한 줄 보낼때 까지 리턴 안한다.
		
		System.out.println(line);
		
		// 서버에 메세지를 보낸다.
		in.close();
		out.close();
		socket.close();
		
	}
	
	private static String prompt() {
		System.out.println(">");
		Scanner keyboard = new Scanner(System.in);
		String message = keyboard.nextLine();
		keyboard.close();
		return message;
	}
}
