package dataspork.plot;
import dataspork.util.DoubleWindowModel;

/** Implementation of <code>Range</code> that attaches to 
  * a <code>DoubleWindowModel</code>.
  */
public class WindowRange implements Range {
  
  protected DoubleWindowModel window;

  public WindowRange(DoubleWindowModel window) {
    this.window = window;
  }

  /** Return the lower limit of the coordinate range. */
  public double getLow() {return window.getLow();}
  /** Return the upper limit of the coordinate range. */
  public double getHigh() {return window.getHigh();}
  /** Return the extent of the coordinate range, high-low. */
  public double length() {return window.getHigh()-window.getLow();}
}
