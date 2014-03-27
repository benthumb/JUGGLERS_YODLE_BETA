package org.benthumb.yodle.tests;

import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.benthumb.yodle.JugglerDataContainer;
import org.benthumb.yodle.Yodle;
import org.junit.BeforeClass;
import org.junit.Test;

public class YodleTestCase1 {
  
  // ** Logging **
  static Logger logMsg = Logger.getLogger("Yodle");
  
  static ArrayList<ArrayList<JugglerDataContainer>> fTestResult;
  
  @BeforeClass
  public static void setUpClass(){
    Yodle.initializeJugglerCircuitArrays();
    Yodle.calculateCircuitAssignmentCriteria();
    fTestResult = Yodle.assignCircuits();
  }
  
  @Test
  public void test1(){
    
    System.out.println("Size of our test result: " + fTestResult.size());
    ArrayList<JugglerDataContainer> insideTestRslt = fTestResult.get(0);
    ArrayList<JugglerDataContainer> insideTestRslt1 = fTestResult.get(1);
    ArrayList<JugglerDataContainer> insideTestRslt2 = fTestResult.get(2);
    System.out.println("First list of circuits: " + insideTestRslt.size());
    System.out
        .println("Second list of circuits: " + insideTestRslt1.size());
    int I1 = 0;
    for (JugglerDataContainer juggArr : insideTestRslt) {
      logMsg.log(java.util.logging.Level.INFO, "Result: ");
      System.out.println("Circuit number: " + juggArr.getCircuitNumber());
      System.out.println("Dot product score: " + juggArr.getDotProduct());
      System.out.println("Juggler number: " + juggArr.getJugglerNumber());
      System.out.println("Assigned circuit: " + juggArr.getAssignedCircuit());
      System.out.println("count: " + I1);
      I1++;
    }
    int I2 = 0;
    for (JugglerDataContainer juggArr1 : insideTestRslt1) {
      logMsg.log(java.util.logging.Level.INFO, "Result: ");
      System.out.println("Circuit number: " + juggArr1.getCircuitNumber());
      System.out.println("Dot product score: " + juggArr1.getDotProduct());
      System.out.println("Juggler number: " + juggArr1.getJugglerNumber());
      System.out.println("Assigned circuit: " + juggArr1.getAssignedCircuit());
      System.out.println("count: " + I2);
      I2++;
    }
    int I3 = 0;
    for (JugglerDataContainer juggArr2 : insideTestRslt2) {
      logMsg.log(java.util.logging.Level.INFO, "Result: ");
      System.out.println("Circuit number: " + juggArr2.getCircuitNumber());
      System.out.println("Dot product score: " + juggArr2.getDotProduct());
      System.out.println("Juggler number: " + juggArr2.getJugglerNumber());
      System.out.println("Assigned circuit: " + juggArr2.getAssignedCircuit());
      System.out.println("count: " + I3);
      I3++;
    }
    Yodle.testRecursive(13);
    // fTestResult = sortListOfLists(fTestResult);  
  }
}
