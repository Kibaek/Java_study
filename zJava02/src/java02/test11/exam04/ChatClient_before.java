/*
 * setSize : 너비, 높이
 * setVisible : 출력
 */

package java02.test11.exam04;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame; // minimize, maximize, close [title bar] 만들 수 있다.
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ChatClient_before extends Frame {
	TextField serverAddr = new TextField(10); // 인스턴스 변수는 접근하기 위해서 만든것이다.
	TextField name = new TextField(10);
	Button connectBtn = new Button("연결");
	TextArea content = new TextArea();
	TextField input = new TextField(30);
	Button sendBtn = new Button("보내기");
	
String Username;
String serverAddress;
	
	public ChatClient_before() {

		Panel toolbar = new Panel(new FlowLayout(FlowLayout.LEFT));
		;
		toolbar.add(new Label("이름 :"));
		toolbar.add(name);
		toolbar.add(new Label("서버 : ")); // 라벨은 출력만 하면 끝이기때문에 인스턴스를 안놓음.
		toolbar.add(serverAddr);
		toolbar.add(connectBtn);

		this.add(toolbar, BorderLayout.NORTH);
		this.add(content, BorderLayout.CENTER);

		Panel bottom = new Panel(new FlowLayout(FlowLayout.LEFT));
		bottom.add(input);
		bottom.add(sendBtn);

		this.add(bottom, BorderLayout.SOUTH);

		// 리스너 등록
		// 1) 윈도우 이벤트를 처리할 리스너 객체 등록
		// WindowListener 인터페이스를 구현한 객체여야 한다.
		this.addWindowListener(new MyWindowListener()); // 아래 등록한 것들이 여기에 등록된다.

		// ActionEvent는 버튼을 눌렀을 때 발생하는 이벤트이다.
		// connectBtn.addActionListener(new MyConnectListner());
		// 실무에서는 한번 밖에 안 쓸 객체라면 익명 이너 클래스로 정의한다.
		// 익명이너클래스 사용
		connectBtn.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("연결 버튼 눌렀네...");
			}

		});
		// 보내기 버튼을 눌렀을 때 발생하는 이벤트이다.
		sendBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("보내기 버튼 눌렀네...");

			}
		});

	}

	public static void main(String[] args) {

		ChatClient_before wnd = new ChatClient_before();
		wnd.setSize(400, 600); // 너비, 높이
		wnd.setVisible(true);
	}

	// WindowListener를 직접 구현하지 말고,
	// 미리 구현한 윈도우 어댑터를 상속 받아
	class MyWindowListener extends WindowAdapter {
		// 다음 메서드는 윈도우 close 버튼을 눌렀을 때 호출된다.

		@Override
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
		
		

	}
	
	Object obj = new Object(){
		public void m(){
			System.out.println("okok");
		}
	};
	


}

/*
 * 서브클래스에게 상속해주기위해 만든것이 추상클래스이다.
 */
