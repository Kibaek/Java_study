package java02.test10.command;

import java.util.ArrayList;
import java.util.Map;
import java02.test10.Score;
import java02.test10.ScoreDao;
import java02.test10.annotation.Command;
import java02.test10.annotation.Component;

@Component
public class ViewCommand {
  ScoreDao scoreDao;
  
  public void setScoreDao(ScoreDao scoreDao) {
    this.scoreDao = scoreDao;
  }

  @Command("view")
  public void doView(Map<String, Object> params) throws Exception {
    @SuppressWarnings("unchecked")
    ArrayList<String> options = 
        (ArrayList<String>)params.get("options");
    
    int index = Integer.parseInt(options.get(0));
    
    Score score = scoreDao.getData(index);
    if (score == null) {
      System.out.println("해당 인덱스의 성적 정보를 찾을 수 없습니다.");
      return;
    }
    
    System.out.println("인덱스: " + index);
    System.out.println("이름: " + score.getName());
    System.out.println("국어: " + score.getKor());
    System.out.println("영어: " + score.getEng());
    System.out.println("수학: " + score.getMath());
    System.out.println("총점: " + score.getSum());
    System.out.println("평균: " + score.getAverage());
  }
}










