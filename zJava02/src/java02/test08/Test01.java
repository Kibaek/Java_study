/* 목표1. 오픈 소스 Reflections를 사용하여 클래스 찾기
 * 목표2. 의존 객체 자동 주입
 * 
 Dependency Injection (의존 객체 주입)
 => 클래스가 사용하는 의존 객체를 애플리케이션을 시작할 때 자동으로 주입하는 것.
 
 Inversion of Control(역제어)
 1) 사례1 => 이벤트 처리
 2) 사례2 => 의존 객체 주입
 
 할 일.
 1. ListCommand 클래스에 의존 객체를 주입할 수 있도록 소스 변경
    1) 의존 객체를 저장하기 위해 ScoreDao 인스턴스 변수 추가 
    2) 의존 객체를 주입하기 위해 setScoreDao() 메서드를 추가
 2. 나머지 Command 클래스들도 소스 변경하라!
 3. Command 클래스를 로딩하여 객체를 생성한 후 commandMap에 등록하기 전에
    의존 객체를 먼저 주입한다.    
 
 
 */
package java02.test08;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

import java02.test08.annotation.Component;

import org.reflections.Reflections;

public class Test01 {
  Scanner scanner; 
  ScoreDao scoreDao;
  HashMap<String,Command> commandMap;
  
  public void init() throws Exception {
    scoreDao = new ScoreDao();
    try {
      scoreDao.load();
    } catch (Exception e) {
      System.out.println("데이터 로딩 중 오류가 발생하였습니다.");
    }
    
    scanner = new Scanner(System.in);
    
    commandMap = new HashMap<String,Command>();						// 리스트, 맵, 해쉬, 
    
    Reflections reflections = new Reflections("java02.test08");		// 리플렉션스를 지정한다.
    Set<Class<?>> clazzList = 										// 클래스정보를 담고있는 목록
        reflections.getTypesAnnotatedWith(Component.class);			// 그 하위패키지까지 다 찾는다. 애노테이션이 붙은 클래스를 리턴한다.
    
    Command command = null;
    Component component = null;
    Method method = null;
    
    for (Class clazz : clazzList) {									
      component = (Component) clazz.getAnnotation(Component.class); // 컴포넌트가 붙은리스트만 리턴한다. 애노테이션 정보를 꺼내고 애노테이션 정보뽑아내고				
      command = (Command)clazz.newInstance();						// 해당 클래스에서 인스턴스 생성하고
      commandMap.put(component.value(), command);					// 벨류값을 커맨드 맵에 추가한다.
        
        // 만약 setScoreDao가 있다면 호출하여 ScoreDao객체를 주입한다.
        // Class 관리자로부터 해당 클래스의 Method 객체를 얻는다.
        // invoke()를 사용하여 메서드를 소출한다.
        try { 
          method = clazz.getMethod("setScoreDao", ScoreDao.class);
          //System.out.println(
          //    clazz.getName() + "." + method.getName());
          method.invoke(command, scoreDao);							// 있다면 호출하고 첫번째 파라미터에는 인스턴스 주소를 리턴한다. 두번째에는 그 메소드를 줄때 파라미터값을 주면 된다.
        // ==> command.setScoreDao(scoreDao);						// 위에랑 같은 방식이다. 메소드를 호출해다오 = invoke
        } catch (Exception e) {}
        
        // Scanner 의존 객체 주입
        try { 
          method = clazz.getMethod("setScanner", Scanner.class);	// 셋 스캐너라는 객체가 있다면
          //System.out.println(
          //    clazz.getName() + "." + method.getName());
          method.invoke(command, scanner);							// 찾아서 의존객체를 주입해주겠다.
        } catch (Exception e) {}
       // 드린거 기억나는데.. 그냥 다 낸걸로 기억하니까.. 기분 왕찝집 그냥 돈 2만원 더 드릴까 생각중이요. 
      
    }
    
    
  }
  
  public void service() {
    Command command = null;
    loop: 
    while (true) {
      try {
        String[] token = promptCommand();
        command = commandMap.get(token[0]);
        
        if (command == null) {
          System.out.println("해당 명령을 지원하지 않습니다.");
          continue;
        }
        
        HashMap<String,Object> params = 
            new HashMap<String,Object>();
        
        ArrayList<String> options = new ArrayList<String>();
        for (int i = 1; i < token.length; i++) {
          options.add(token[i]);
        }
        params.put("options", options);
        
        command.service(params);
        
        if (token[0].equals("exit"))
          break loop;
        
      } catch (Exception e) {
        e.printStackTrace();
        System.out.println("명령어 처리 중 오류 발생. 다시 시도해 주세요.");
      }
    }
  }
  
  public void destroy() {
    scanner.close();
  }

  private String[] promptCommand() {
    System.out.print("명령>");
    String[] token = scanner.nextLine().split(" ");
    return token;
  }

  public static void main(String[] args) throws Exception {
    Test01 app = new Test01();
    app.init();
    app.service();
    app.destroy();
  }

}







