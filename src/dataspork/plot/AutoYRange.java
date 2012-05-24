package dataspork.plot;
import dataspork.util.XYDataSource;

/** Implementation of <code>Range</code> for reading the Y range off of
  * an <code>XYDataSource</code>. */
public class AutoYRange implements Range {
  
  /** The <code>XYDataSource</code> to query for current range. */
  protected XYDataSource source;
  /** The amount of padding. */
  protected double pad;

  /** Constructor. */
  public AutoYRange(XYDataSource source) {
    this.source = source;
  }

  /** Constructor that asks for additional padding. */
  public AutoYRange(XYDataSource source, double pad) {
    this.source = source;
    this.pad = pad;
  }

  /** Return the lower limit of the coordinate range. */
  public double getLow() {
    double hi=source.getYMax();
    double lo=source.getYMin();
    return lo+pad*(lo-hi);
  }

  /** Return the upper limit of the coordinate range. */
  public double getHigh() {
    double hi=source.getYMax();
    double lo=source.getYMin();
    return hi+pad*(hi-lo);
  }

  /** Return the extent of the coordinate range, high-low. */
  public double length() {
    double hi=source.getYMax();
    double lo=source.getYMin();
    return (1+2*pad)*(hi-lo);
  }
}
