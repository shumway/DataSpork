package dataspork.plot;
import java.awt.*;

/** Stroke with simpler constructor for plotting applicaitons.
  * Several dashed styles are supported. <ul>
  * <li> 0 = solid
  * <li> 1 = dashed
  * <li> 2 = dotted
  * <li> 3 = dot-dashed
  * </ul>
  */
public class PlotStroke extends BasicStroke {

  static final protected float[][] dashArray = { 
    {1f},
    {5.0f,3.0f},
    {1.0f,2.0f},
    {5.0f,3.0f,1.0f,3.0f}
  };

  /** Return a copy of dashArray[dash] scaled by width. */
  static protected float[] getDashArray(float width, int dash) {
    float[] dArray = (float[])dashArray[dash].clone();
    for (int i=0; i<dArray.length; i++) {
      dArray[i]*=width;
    }
    return dArray;
  }

  /** Construct a stroke style with a given width and dash style */
  public PlotStroke(float width, int dash) {
    super(width, CAP_SQUARE, JOIN_MITER, 10.0f,
          getDashArray(width,dash), 0.0f);  
  }

  /** Construct a solid line stroke style with a given width. */
  public PlotStroke(float width) {
    super(width);  
  }
}
