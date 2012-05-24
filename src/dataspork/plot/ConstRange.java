package dataspork.plot;

/** Implementation of <code>Range</code> for reading the X range off of
  * an <code>XYDataSource</code>. */
public class ConstRange implements Range {

  /** The lower limit of the coordinate range. */
  protected double low;
  /** The upper limit of the coordinate range. */
  protected double high;

  /** Constructor. */
  public ConstRange(double low, double high) {
    this.low = low;
    this.high = high;
  }

  /** Return the lower limit of the coordinate range. */
  public double getLow() {return low;}
  /** Return the upper limit of the coordinate range. */
  public double getHigh() {return high;}
  /** Return the extent of the coordinate range, high-low. */
  public double length() {return high-low;}
}
