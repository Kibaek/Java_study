package java02.test12;

import java.util.Date;

public class Test03 {

	static class MyAlarm implements Runnable {

		@Override
		public void run() {

			int count = 0;
			while (true) {
				if(count++ > 10){
					break;
				}
				Date today = new Date();
				System.out.println("\n" + today);
				try {
					Thread.currentThread().sleep(1000); // 현재 스레드를 잠시 잠자게 한다. // 1초 쉬었다가 깨어나는데 하필이면 다른 애들이 실행하고 있어서 기다리고 있다가 다시 실행하고, 1초동안 잠들어있는다. 얘기하자면 1초동안 분명히 잠들어 있다. 그런데 대기를 한다. 대기시간은 ......쩜쩜쩜쩜 대기하다가 실행하고 또 1초동안 잠들어있다가 실행하고, 반복이다.
				} catch (Exception ex) {}
			}
		}
	}

	public static void main(String[] args) {

		new Thread(new MyAlarm()).start();

		for (int i = 0; i < 1000000000; i++) {
			double d1 = 3.14159;
			double d2 = 123.345;
			d1 = d1 / d2;
			if ((i % 10000) == 0)
				System.out.print(".");
		}

	}
}
