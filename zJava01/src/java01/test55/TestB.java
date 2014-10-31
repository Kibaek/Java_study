package java01.test55;

public class TestB implements Gamer{

	 int count;
	  
	  @Override
	  public String who() {
	    return "박기남";
	  }

	  @Override
	  public void init() {}

	  @Override
	  public String play() {
	    count++;
	    if ((count % 2) == 0)
	      return Gamer.ROCK;
	    else if((count % 3) == 0)
	      return Gamer.SCISSORS;
	    else
	    	return Gamer.PAPER;
	  }

	  @Override
	  public void sendResult(int result) {}


}
