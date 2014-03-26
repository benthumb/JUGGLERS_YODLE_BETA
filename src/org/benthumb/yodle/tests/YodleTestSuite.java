package org.benthumb.yodle.tests;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ YodleTestCase1.class })
public class YodleTestSuite {

  @BeforeClass
  public static void setUpClass() {

  }

  @AfterClass
  public static void tearDownClass() {

  }
}
