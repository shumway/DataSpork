package dataspork.util;
import java.util.*;

/** Represent a integer parameter.
  */
public class IntegerObservable extends Observable {

  protected int value;

  public IntegerObservable(int value) {
    this.value = value;
  }

  /** Get value. */
  public int getValue() {
    return value;
  }

  /** Attempt to change value. Subclasses may not accept all changes.
    * Return value is the value after the attempted change. 
    */
  public int setValue(int v) {
    value=v;
    return value;
  }
}

