package java02.test10.command;

import java.util.ArrayList;
import java.util.Map;
import java02.test10.Score;
import java02.test10.ScoreDao;
import java02.test10.annotation.Command;
import java02.test10.annotation.Component;

@Component
public class AddCommand {
  ScoreDao scoreDao;
  
  public void setScoreDao(ScoreDao scoreDao) {
    this.scoreDao = scoreDao;
  }
  
  @Command("add")
  public void ohora(Map<String, Object> params) throws Exception {
    @SuppressWarnings("unchecked")
    ArrayList<String> options = (ArrayList<String>)params.get("options");
    
    Score score = new Score(options.get(0), Integer.parseInt(options.get(1)), Integer.parseInt(options.get(2)), Integer.parseInt(options.get(3)));
    
    scoreDao.add(score);
    System.out.println("저장하였습니다.");
  }

}

























