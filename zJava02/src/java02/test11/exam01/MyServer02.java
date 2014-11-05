/*
 * ServerSocket 사용법
 * 
 */


package java02.test11.exam01;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MyServer02 {

	public static void main(String[] args) throws Exception {
		//----------------------------------- 자바 네트워킹 프로그램
		// 클라이언트와 통신을 담당할 객체 생성
		System.out.println("서버 소켓 생성");
		
		
		// 두 번째 backlog 값은 대기열 길이를 지정한다.
		ServerSocket ss = new ServerSocket(8888, 1); //
		
		// 대기열에 있는(연결을 대기하고 있는) 클라리언트들 중에서 하나 선택하기
		// => 선택할 클라이언트와의 통신을 담당할 Socket 객체를 리턴
		System.out.println("클라이언트의 연결을 기다리는 중.....");
		Socket socket = ss.accept();
		System.out.println("대기중에 있는 클라이언트와 연결됨.....");
		//------------------------------------------------------
		
		// 소켓을 통해 읽고 쓰기 위한 입/출력 스트림 얻기
		Scanner in = new Scanner(socket.getInputStream()); // 바이트로 받으려고하면 너무 힘들기 때문에, 문자열로 받는다. by Scanner를 사용하여.
		PrintStream out = new PrintStream(socket.getOutputStream());
		
		// 클라이언트가 보낸 문자열 읽기
		// 클라이언트에서 한 줄의 문자열을 보내기 전까지 리턴하지 않는다.
		// "실행이 완료될 때까지 리턴하지 않는다." == blocking // 하나의 라인을 보내야지만 비로서 리턴한다. 
		String line = in.nextLine();
		
		// 클라이언트가 보낸 메시지를 출력
		System.out.println(line);
		
		// 사용자에게서 키보드로 문자열을 한 줄 입력받는다.
		String message = prompt();
		
		
		// 클라이언트로 보낸다.
		// "클라이언트에서 문자열을 모두 받을 때까지 리턴하지 않는다. == blocking
		// 입/출력은 항상 블로킹으로 다룬다. ==> 단점해결 : Java non-bloking.api 등장
		out.println(message);
				
		
		in.close();
		out.close();
		socket.close();
		ss.close();
	}

	private static String prompt() {
		System.out.println(">");
		Scanner keyboard = new Scanner(System.in);
		String message = keyboard.nextLine();
		keyboard.close();
		return message;
	}
}
