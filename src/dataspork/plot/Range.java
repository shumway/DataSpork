package dataspork.plot;

/** Interface for objects that provide a range of one-dimensional coordinates.
  */
public interface Range {
  /** Return the upper limit of the coordinate range. */
  public double getHigh();
  /** Return the lower limit of the coordinate range. */
  public double getLow();
  /** Return the extent of the coordinate range, high-low. */
  public double length();
}
