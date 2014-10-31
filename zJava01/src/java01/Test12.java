/* 문자 표현
 - 문자 상수 표현: 'A', '\u0041'
 - 데이터 타입: char(2)
 - 자바는 유니코드에 정의된 값을 사용한다.
 */
package java01;

public class Test12 {

  public static void main(String[] args) {
    char c = 0x41; // A
    char c2 = 0xAC00; // 가
    char c3 = 0xACE0; //44256  고
    char c4 = '간'; // 따옴표는 주언 글자의 유니코드 값을 리턴한다.
    
    System.out.println(c);
    System.out.println(c2);
    System.out.println(c3);
    System.out.println(c4);

  }

}











