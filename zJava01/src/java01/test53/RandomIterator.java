package java01.test53;

import java.util.ArrayList;

import java01.test53.step05.Iterator;

public class RandomIterator extends Iterator {
  ArrayList<String> randomList = new ArrayList<String>();
  
  public RandomIterator() {}
  
  @Override
  public void setList(String[] list) {
    super.setList(list);
    for (String value : list) {
      randomList.add(value);
    }
  }
  
  public boolean hasNext() {
    if (randomList.size() > 0)
      return true;
    else 
      return false;
  }
  
  public String next() {
    return randomList.remove(
        (int)(Math.random() * randomList.size()));
  }
}










