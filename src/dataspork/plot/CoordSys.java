package dataspork.plot;
import java.awt.geom.*;

/** Class for representing a local coordinate system.  This is defined
  * in terms of an <code>AffineTransform</code> and a 
  * coordinate range.
  */
public class CoordSys extends AffineTransform {
 
  /** Store the coordinate range */
  CoordRange limits;

  /** Store the location */
  CoordRange location;
  
  public CoordSys(){
    super();
    limits = new CoordRange();
  };

  public CoordSys(CoordRange limits) {
    super(); //Default to identity transformation.
    this.limits = (CoordRange)limits.clone();
  }

  /** Create a coordinate system relative to another coordinate system.
    * @param limits   The limits on the new coordinate system.
    * @param location The location of the new coordinate system, in the
    *                 old coordinate system.
    */
  public CoordSys(CoordRange limits, CoordRange location) {
    super();
    this.limits = limits;
    this.location = location;
  }

  /** Calculate Transform */
  public void calcTransform(AffineTransform preTransform) {
    //First set the scaling.
    super.setToScale(location.width()/limits.width(),
                     location.height()/limits.height());
    //Then determine the offset by looking at how corner transforms.
    Point2D.Double cornerPt =
       new Point2D.Double(limits.getXMin(),limits.getYMin());
    super.transform(cornerPt,cornerPt);
    super.preConcatenate( AffineTransform.getTranslateInstance
         (location.getXMin()-cornerPt.x,location.getYMin()-cornerPt.y) );
    super.preConcatenate(preTransform);
  }

  /** Return a GeneralPath that borders the coordinate system. */
  public GeneralPath getLimitPath() {
    Point2D.Double ptMin =
      new Point2D.Double(limits.getXMin(),limits.getYMin());
    Point2D.Double ptMax = 
      new Point2D.Double(limits.getXMax(),limits.getYMax());
    transform(ptMin,ptMin);
    transform(ptMax,ptMax);
    GeneralPath path = new GeneralPath(GeneralPath.WIND_EVEN_ODD,4);
    path.moveTo((float)ptMin.x,(float)ptMin.y);
    path.lineTo((float)ptMin.x,(float)ptMax.y);
    path.lineTo((float)ptMax.x,(float)ptMax.y);
    path.lineTo((float)ptMax.x,(float)ptMin.y);
    path.closePath();
    return path;
  }
}
