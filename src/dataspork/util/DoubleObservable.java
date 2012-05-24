package dataspork.util;
import java.util.*;

/** Represent a double precision parameter.
  */
public class DoubleObservable extends Observable {

  protected double value;

  public DoubleObservable(double value) {
    this.value = value;
  }

  /** Get value. */
  public double getValue() {
    return value;
  }

  /** Attempt to change value. Subclasses may not accept all changes.
    * Return value is the value after the attempted change. 
    */
  public double setValue(double v) {
    value=v;
    return value;
  }
}

