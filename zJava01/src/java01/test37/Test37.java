package java01.test37;

public class Test37 {

  public static void main(String[] args) {
    System.out.println("----------------");
    Score s = new Score();
    s.name = "홍길동";
    s.kor = 100;
    s.eng = 100;
    s.math = 90;
    s.compute();
    
    System.out.println("이름:" + s.name);
    System.out.println("국어:" + s.kor);
    System.out.println("영어:" + s.eng);
    System.out.println("수학:" + s.math);
    System.out.println("합계:" + s.sum);
    System.out.println("평균:" + s.average);

  }

}












