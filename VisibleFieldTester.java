import java.io.*;
public class VisibleFieldTester {
   public static void main(String[] args) {
      boolean[][] input1 = {
         {true, false, false},
         {false, false, false},
         {false, false, true}};
         
      MineField mineField1 = new MineField(input1);
      VisibleField myV = new VisibleField(mineField1);
      myV.cycleGuess(0,0);
      myV.cycleGuess(0,1);
      myV.cycleGuess(0,2);
      myV.cycleGuess(1,0);
      myV.cycleGuess(1,1);
      myV.cycleGuess(1,2);
      System.out.println(myV.numMinesLeft());
   }
   
   
}