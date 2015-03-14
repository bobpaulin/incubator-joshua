package joshua.decoder.phrase;

import static org.junit.Assert.*;	
import org.junit.Test;

public class CoverageTest {

  @Test
  public void testSet() {
    Coverage cov = new Coverage();
    cov.set(1,2);
    cov.set(3,4);
    cov.set(2,3);
    cov.set(0,1);

    assertFalse(cov.compatible(0, 1));
    assertFalse(cov.compatible(0, 5));
    assertTrue(cov.compatible(4, 6));
  }
  
  @Test
  public void testCompatible() {
    Coverage a = new Coverage();
    a.set(10, 14);
    assertTrue(a.compatible(14, 16));
    assertTrue(a.compatible(6, 10));
    assertTrue(a.compatible(1, 10));
    assertTrue(a.compatible(1, 9));
    assertFalse(a.compatible(9, 11));
    assertFalse(a.compatible(13, 15));
    assertFalse(a.compatible(9, 15));
    assertFalse(a.compatible(9, 14));
    assertFalse(a.compatible(10, 15));
  }
   
  @Test
  public void testFirstZero() {
    Coverage cov = new Coverage();
    cov.set(2, 5);
    assertEquals(cov.firstZero(), 0);
    cov.set(8,10);
    assertEquals(cov.firstZero(), 0);
    cov.set(0, 2);
    assertEquals(cov.firstZero(), 5);
    cov.set(5, 7);
    assertEquals(cov.firstZero(), 7);
    cov.set(7,8);
    assertEquals(cov.firstZero(), 10);
  }
  
  @Test
  public void testPattern() {
    Coverage cov1 = new Coverage();
    System.err.println(cov1.pattern(5,8));
  }
  
  @Test
  public void testLeftOpen() {
    Coverage cov = new Coverage();
    cov.set(0, 2);
    cov.set(8, 10);
    
    for (int i = 2; i < 7; i++) {
      assertEquals(cov.leftOpening(i), 2);
      assertEquals(cov.rightOpening(i, 17), 8);
      assertEquals(cov.rightOpening(i, 7), 7);
    }
  }

  @Test
  public void testEquals() {
    Coverage cov = new Coverage();
    cov.set(9, 11);
    Coverage cov2 = new Coverage();
    cov2.set(9,10);
    cov2.set(10,11);
    assertEquals(cov, cov2);
  }
}