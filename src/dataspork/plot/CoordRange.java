package dataspork.plot;

/** Class for storing a two-dimensional coordinate range, given by two 
  * <code>Range</code> objects.
  */
public class CoordRange implements Cloneable {

  /** The x coordinate range */
  protected Range xRange;
  /** The y coordinate range */
  protected Range yRange;

  public Object clone() {
    Object obj=null;
    try {obj=super.clone();} catch (Exception e) {}
    return obj;
  } 

  /** Constructor defaults to (0,0) to (1,1). */
  public CoordRange() {
    xRange = new ConstRange(0,1);
    yRange = new ConstRange(0,1);
  }

  /** Constructor.*/
  public CoordRange(Range xRange, Range yRange) {
    this.xRange = xRange;
    this.yRange = yRange;
  }

  /** Constructor.*/
  public CoordRange(double xmin, double ymin, double xmax, double ymax) {
    xRange = new ConstRange(xmin,xmax);
    yRange = new ConstRange(ymin,ymax);
  }

  /** Return the width of the coordinate range. */
  public double width() {
    return xRange.length();
  }

  /** Return the height of the coordinate range. */
  public double height() {
    return yRange.length();
  }

  /** Return the minimum x value. */
  public double getXMin() {return xRange.getLow();}
  /** Return the minimum y value. */
  public double getYMin() {return yRange.getLow();}
  /** Return the maximum x value. */
  public double getXMax() {return xRange.getHigh();}
  /** Return the maximum y value. */
  public double getYMax() {return yRange.getHigh();}
}
