/*
 * 여러 클라이언트와 대화 나누기?
 * => 동시에 대화 나누는 것이 불가능
 */

package java02.test11.exam01;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MyServer {

	static Scanner keyboard = new Scanner(System.in);

	public static void main(String[] args) throws Exception {
		System.out.println("서버 소켓 생성");

		ServerSocket ss = new ServerSocket(8888, 1); // 대기열 갯수 정할 수 있다.
		// 클라이언트와 연결이 종료되면 대기열의 다른 클라이언트와 연결한다.
		// 다만, 코딩한 바와 같이 순차적으로 실행한다.

		while (true) {

			Socket socket = ss.accept();
			System.out.println("클라이언트의 연결을 기다리는 중.....");
			System.out.println("대기중에 있는 클라이언트와 연결됨.....");

			Scanner in = new Scanner(socket.getInputStream());
			PrintStream out = new PrintStream(socket.getOutputStream());

			String message = null, line = null; // 임시변수는 반복문에서 만들게 하지마라 스택메모리가
												// 끊임없이 생긴다.
			while (true) {

				line = in.nextLine();
				System.out.println(line); // 화면에 뿌리고

				if (line.equalsIgnoreCase("quit")) {
					out.println("goodbye");
					break;
				}
				message = prompt(); // 입력하고
				out.println(message); // 내가 입력할걸 클라이언트에 보내고

			}

			in.close();
			out.close();
			socket.close(); // 다음클라이언트 와 연결 승인 시켜
		}
		// ss.close();
		// keyboard.close();
	}

	private static String prompt() {
		System.out.println(">");
		String message = keyboard.nextLine();
		return message;
	}
}
