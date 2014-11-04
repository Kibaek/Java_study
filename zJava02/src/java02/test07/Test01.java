/* 애노테이션이 붙은 클래스를 찾아 자동 로딩하기
 - 클래스를 찾을 때 ClassFinder를 사용한다.
 */
package java02.test07;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java02.test07.annotation.Component;
import java02.test07.util.ClassFinder;

public class Test01 {
  Scanner scanner; 
  ScoreDao scoreDao;
  HashMap<String,Command> commandMap;
  
  public void init() throws Exception {
    commandMap = new HashMap<String,Command>();							// 1. 커맨드 맵을 준비하라 ( 키를 준비하고, 커맨드와 인터페이스를 만들어 창고를 준비한다. )
    
    // 클래스가 들어 있는 폴더를 뒤져서
    // @Component 애노테이션이 붙은 클래스를 로딩한다.
    // 그 로딩된 클래스의 인스턴스를 생성하여
    // commandMap에 저장한다.
    
    // 1) 폴더를 뒤져서 클래스 이름(패키지명 + 클래스명)을 알아낸다.
    ClassFinder classFinder = new ClassFinder("java02.test07"); 		// 2. 클래스를 찾는 객체이다. 자바02아래있는 하위 패키지들을 찾아라.
    classFinder.find("./bin");											// 3. 2번 패키지가 어디있는지 알려준다. (찾을 폴더를 지정해준다.)
    List<String> classNames = classFinder.getClassList(); // classNames // 4. 찾아서 보관된 클래스의 이름을 classNames으로 리턴하라.
    
    // 2) 클래스를 로딩한다.
    Class /* 타입정보이다. : Type정보를 다루는 객체이다. */clazz = null;			// 5. 로컬변수들 지정 (그냥 밖으로 꺼낸거임)
    Command command = null;		// Type정보란 그 정보를 다루는객체 : 데이터 크기 = 그 정보를 다루는것들이 클래스 객체 // 모든 타입은 클래스 객체이다.
    Component component = null;
    
    for (String className : classNames) { 								// 6. 클래스를 로딩하고 로딩한 객체를 다루는것을 리턴한다.
      clazz = Class.forName(className);									// 해당되는 클래스는 메소드 에어리어에 로딩된다. 하드 코딩을 해버리면 매번 바꿔야되기때문에 하드 코딩으로 지정안한것이다.
      																	
      // 3) 로딩된 클래스 중에서 @Component 애노테이션이 붙은 클래스만
      //   인스턴스를 생성한다.
      component = (Component) clazz.getAnnotation(Component.class);		// 7. 관리자를 뽑는다. 에노테이션(정보) 클래스관리에게 애노테이션 정보좀 달라. 파라미터값을 타입정보를 넘긴다. 애노테이션이 여러개일 수도 있으니깐, 타입으로 넘겨야 한다.	 String (타입) / "Hello" (인스턴스)
      																	//(xxxx.class) : 클래스변수로 접근가능한 static 변수이다. 자바 버추얼머신이 로딩할때 자동으로 만들어준다. 그 클래스가 누구인지 알려주는 객체이다. 타입의 종류는 : 클래스, 애노테이션. 인터페이스가 있다. // 애노테이션 인스턴스를 리턴한다. // 생성이 아니라 타입 값을 넘겨주는 것이다. 타입을 다루는 클래스를 넘겨준다. //
      																	// 부가설명 : 저 회색이름을 추출해서 에노테이션 메소드에게 넘겨준다. 그러면 애노테이션이 미리 로딩이되있기 떄문에 추출해 처리해준다.
      																	// 관리자 객체주소가 들어있다. 클래스안에 관리하는 컴포넌트 애노테이션 값을 달라.		꺼낸이유 : 애노테이션 설정된 명령어를 사용하기위해.
      																	// 컴포넌트 타입정보를 다루는 클래스라는 객체주소(인스턴스주소) 클래스 객체 주소가 있다. Component
      																	// 클래스라는 히든변수에 자기 자신을 다루는 객체주소가 .class안에 들어있다.(타입정보를 다루는것=타입) 그 애노테이션을 찾아서 리턴합니다.
      
      if (component != null) {											// 8. null이 아니라면 클래스를 생성하고 생성된 클래스를, 애노테이션 벨류값을 넣어라. 로딩된 객체의 키값을 사용하기 위해. 그 꺼내 커맨드 벨류값을 그 커맨드로 저장을 한다.						
        command = (Command)clazz.newInstance();							// 9. 클래스객체에 대해 인스턴스를 생성해달라 한 것이다. // 클래즈에 담겨있는 도구 주소를 넘겨주는 것이다. 
        commandMap.put(component.value(), command);						// 10. 컴포넌트라는 키값으로 해당되는 해당되는 인스턴스를(9번) 저장할 것이고 그 변수의 값을 커맨드 맵에 넣는다.
        																
      }		//  = commandMap.put(component.value(),(Command)clazz.newInstance())	9번 10번 동일코드이다.														
    }
    
    scoreDao = new ScoreDao();											// 11. 준비한다.
    try {
      scoreDao.load();
    } catch (Exception e) {
      System.out.println("데이터 로딩 중 오류가 발생하였습니다.");
    }
    
    scanner = new Scanner(System.in);									
  }
  
  public void service() {
    Command command = null;
    loop: 
    while (true) {
      try {
        String[] token = promptCommand(); // 사용자로부터 명령어를 입력받고
        command = commandMap.get(token[0]); // 그 명령어에 해당되는것을 커맨드맵 객체를 찾아서
        
        if (command == null) {				// 없다면 경고문 뜨게하고
          System.out.println("해당 명령을 지원하지 않습니다.");
          continue;
        }
        
        HashMap<String,Object> params = 
            new HashMap<String,Object>();
        params.put("scoreDao", scoreDao); // 있다면 저장하고
        params.put("scanner", scanner); // 있다면 스캐너에 저장하고
        
        ArrayList<String> options = new ArrayList<String>();	// 0번 말고 나머지를 여기에 저장하고
        for (int i = 1; i < token.length; i++) {
          options.add(token[i]);
        }
        params.put("options", options);
        
        command.service(params); // 이 정보를 가지고 일을해라
        
        if (token[0].equals("exit")) // 토큰이 EXIT라면 서비스를 종료하라.
        	
          break loop;
        
      } catch (Exception e) {
        e.printStackTrace();
        System.out.println("명령어 처리 중 오류 발생. 다시 시도해 주세요.");
      }
    }
  }
  
  public void destroy() { // 없다면 종료하라.
    scanner.close();
  }

  private String[] promptCommand() {
    System.out.print("명령>");
    String[] token = scanner.nextLine().split(" ");
    return token;
  }

  public static void main(String[] args) throws Exception {
    Test01 app = new Test01(); // 서비스준비하라
    app.init();		// 서비스 초기화하라 (서비스를 위한 자원을 준비한다.)
    app.service();	// 서비스 하라
    app.destroy();	// 준비한 자원 해제하라
  }

}



/* 7번 부가설명 
 * 항상 타입에는 도구가 따라다닌다. // 오브젝트라는 클래스, 파일 클래스, 파일리더 클래서, 컴포넌트라는 애노테이션, 모든 클래스는  : 항상 클래스가 로딩된면 구지 만들지않아도 클래스라는 변수가 존재한다. 
 * 이 클래스라는 변수는 static이기때문에 바로 저장된다.  모든 타입에는 클래스가 존재한다. 그 타입의 정보를 추출하는 클래스라는 new 객체가 있다. 자기의 소속되있는 생성자 정보나, 퍼플릭인스턴스나.. 모든정보를 추출한다.
 * 모든 타입에는 : 클래스라는 변수가있고 타입은 : 클래스 , 인터페이스, 애노테이션을 지칭해서 하는 말이다. 그 
 * 
 * 메소드 = 오퍼레이터
 * 변수 = 프로퍼티
 * 
 * 
 * 
 * // 리스트, 맵, 해쉬, 셋 (찾아보자) // 중복허용의 차이
 */





