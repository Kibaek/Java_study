package java02.test10.command;

import java.util.Map;
import java02.test10.ScoreDao;
import java02.test10.annotation.Command;
import java02.test10.annotation.Component;

@Component
public class ExitCommand {
  ScoreDao scoreDao;
  
  public void setScoreDao(ScoreDao scoreDao) {
    this.scoreDao = scoreDao;
  }

  @Command("exit")
  public void doExit(Map<String, Object> params) throws Exception {
    try {
      scoreDao.save();
    } catch (Exception e) {
      System.out.println("데이터 저장 중 오류가 발생했습니다.");
    }
  }
}










