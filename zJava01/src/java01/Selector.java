package java01;

/* 10/22 과제 대상: 김종서, 반기백, 김다은, 신경민 */
public class Selector {

  public static void main(String[] args) throws Exception {
    String[] students = new String[]{
        "권영근", "배연정", "송민준", "지용근", "정해창", "장대혁",
        "조민석", "김지영", "박기남", "이용호", "염정우", "김종서",
        "김해경", "한진수", "양기욱", "이주형", "허정아", "김광철",
        "반기백", "윤용식", "김원기", "나정담", "현나래", "김기현",
        "조현권", "강다현", "김다은", "신경민", "윤종익", "정세라"
    };
    
    int no = -1;
    
    for (int i = 10; i > 0; i--) {
      Thread.currentThread().sleep(500);
      System.out.println(i);
      no = (int)(Math.random() * 30);
      System.out.println(students[no] + "일까요?");
    }
    System.out.print("당첨 번호: ");
    Thread.currentThread().sleep(3000);
    no = (int)(Math.random() * 30);
    System.out.println(students[no]);

  }

}











