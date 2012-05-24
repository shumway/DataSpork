package dataspork.plot;
import java.awt.geom.*;

/** Class for representing a local coordinate system.  This is defined
  * in terms of an <code>AffineTransform</code> and a 
  * coordinate range.
  */
public class RotCoordSys extends CoordSys {
 
  public RotCoordSys(){super();};

  public RotCoordSys(CoordRange limits) {
    super(limits); //Default to identity transformation.
  }

  /** Create a coordinate system relative to another coordinate system.
    * @param limits   The limits on the new coordinate system.
    * @param location The location of the new coordinate system, in the
    *                 old coordinate system.
    */
  public RotCoordSys(CoordRange limits, CoordRange location) {
    super(limits,location);
  }

  /** Calculate Transform */
  public void calcTransform(AffineTransform preTransform) {
    //First set the scaling.
    setToScale(location.width()/limits.height(),
               location.height()/limits.width());
    //And rotate.
    concatenate(AffineTransform.getRotateInstance(Math.PI*0.5));
    //Then determine the offset by looking at how corner transforms.
    Point2D.Double cornerPt =
       new Point2D.Double(limits.getXMin(),limits.getYMin());
    transform(cornerPt,cornerPt);
    preConcatenate( AffineTransform.getTranslateInstance
         (location.getXMax()-cornerPt.x,location.getYMin()-cornerPt.y) );
    preConcatenate(preTransform);
  }

}
