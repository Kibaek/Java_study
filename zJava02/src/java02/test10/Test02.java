/* commandMap에 명령어를 처리하는 객체를 저장할 때,
 * 명령어 처리 객체뿐만 아니라 메서드 객체도 함께 저장한다.
 
  1. 새로운 타입 정의 => CommandInfo
 
 */
package java02.test10;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

import java02.test10.ScoreDao;
import java02.test10.annotation.Command;
import java02.test10.annotation.Component;

import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
//import static org.reflections.Reflections.*;

@SuppressWarnings({"unchecked", "rawtypes"})
public class Test02 {
  static class CommandInfo {	/// ??????????
    public Object instance;
    public Method method;
  }
  
  Scanner scanner; 
  ScoreDao scoreDao;
  HashMap<String,CommandInfo> commandMap;
  
  // -------------------------------------------------------- // 3
  
  public void init() throws Exception {
    scoreDao = new ScoreDao();								  // 3-1
    try {
      scoreDao.load();
    } catch (Exception e) {
      System.out.println("데이터 로딩 중 오류가 발생하였습니다.");
    }
    
    scanner = new Scanner(System.in);									// 명령을 받는 것 생성
    
    commandMap = new HashMap</*귀찮다. 컴파일러 너가 알아서 추측해서 적어라*/>();   // 해쉬값맵을 커맨드맵을 지정
    
    Reflections reflections = new Reflections("java02.test10");			// 이 아랫것을 다 찾아라, 이유 : 애노테이션 붙은 타입을 결국찾기 위함이다.
    Set<Class<?>> clazzList = reflections.getTypesAnnotatedWith(Component.class);
    
    Object command = null; // ???
    Component component = null;			// 애노테이션 타입 클래스들을 찾아서 쓰기 위한??? 초기화?
    Method method = null;
    CommandInfo commandInfo = null;
    Command commandAnno = null;
    
    for (Class clazz : clazzList) {
      component = (Component) clazz.getAnnotation(Component.class);
      command = clazz.newInstance();

      // @Component 애노테이션이 붙은 클래스에서
      // @Command가 붙은 메서드를 모두 찾는다.
      // 그 메서드와 인스턴스를 CommandInfo에 담아서
      // CommandMap에 등록한다.
      Set<Method> methods = ReflectionUtils.getMethods(clazz, ReflectionUtils.withAnnotation(Command.class));	// 두가지로 지정안해주면 다 찾아옴.
      
      for (Method m :methods) {
        commandAnno = m.getAnnotation(Command.class);
        commandInfo = new CommandInfo();
        commandInfo.instance = command;
        commandInfo.method = m;
        commandMap.put(commandAnno.value(), commandInfo);
      }
      
      try { 
        method = clazz.getMethod("setScoreDao", ScoreDao.class);
        //System.out.println(
        //    clazz.getName() + "." + method.getName());
        method.invoke(command, scoreDao);
      } catch (Exception e) {}
      
      try { 
        method = clazz.getMethod("setScanner", Scanner.class);
        //System.out.println(
        //    clazz.getName() + "." + method.getName());
        method.invoke(command, scanner);
      } catch (Exception e) {}
    }
    
    
  } // init()
  
  // -------------------------------------------------------- // 4
  
  public void service() {
    CommandInfo commandInfo = null;
    loop: 
    while (true) {
      try {
        String[] token = promptCommand();
        commandInfo = commandMap.get(token[0]);
        
        if (commandInfo == null) {
          System.out.println("해당 명령을 지원하지 않습니다.");
          continue;
        }
        
        HashMap<String,Object> params = new HashMap<String,Object>();
        ArrayList<String> options = new ArrayList<String>();
        
        for (int i = 1; i < token.length; i++) {
          options.add(token[i]);
        }
        
        params.put("options", options);
        
        commandInfo.method.invoke(commandInfo.instance, params);
        
        if (token[0].equals("exit"))
          break loop;
        
      } catch (Exception e) {
        e.printStackTrace();
        System.out.println("명령어 처리 중 오류 발생. 다시 시도해 주세요.");
      }
    }
  }
  
  // -------------------------------------------------------- // 5
  
  public void destroy() { 
    scanner.close();
  }

  // -------------------------------------------------------- // 2
  
  private String[] promptCommand() {
    System.out.print("명령>");
    String[] token = scanner.nextLine().split(" ");
    return token;
  }
  
  // -------------------------------------------------------- // 1

  public static void main(String[] args) throws Exception {
    Test02 app = new Test02();
    app.init();
    app.service();
    app.destroy();
  }

}







