package dataspork.plot;
import dataspork.util.XYDataSource;

/** Implementation of <code>Range</code> for reading the X range off of
  * an <code>XYDataSource</code>. 
  */
public class AutoXRange implements Range {
  
  /** The <code>XYDataSource</code> to query for current range. */
  protected XYDataSource source;
  /** The amount of padding. */
  protected double pad;

  /** Constructor. */
  public AutoXRange(XYDataSource source) {
    this.source = source;
  }

  /** Constructor that asks for additional padding. */
  public AutoXRange(XYDataSource source, double pad) {
    this.source = source;
    this.pad = pad;
  }

  /** Return the lower limit of the coordinate range. */
  public double getLow() {
    double hi=source.getXMax();
    double lo=source.getXMin();
    return lo+pad*(lo-hi);
  }

  /** Return the upper limit of the coordinate range. */
  public double getHigh() {
    double hi=source.getXMax();
    double lo=source.getXMin();
    return hi+pad*(hi-lo);
  }

  /** Return the extent of the coordinate range, high-low. */
  public double length() {
    double hi=source.getXMax();
    double lo=source.getXMin();
    return (1+2*pad)*(hi-lo);
  }

}
