package dataspork.plot;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;

/** Container class for PlotElements.
  */
public class PlotBox extends PlotElement {

  /** Transformation for this local coordinate system.  Actual mapping
    * local coordinates to user coordinates is in the affine transformation
    * part of the coordSys.  Affine transformation is a product of the 
    * affine transforms from all parents and must be recalculated.
    */
  protected CoordSys coordSys;

  /** Container for plot elements. */
  Vector elements;

  /** Default constructor */
  public PlotBox() {
    coordSys = new CoordSys();
    elements = new Vector();
  }


  /** */
  public PlotBox(CoordRange limits, CoordRange location) {
    coordSys = new CoordSys(limits,location);
    elements = new Vector();
  }

  /** */
  public PlotBox(CoordRange limits, CoordRange location, boolean rotated) {
    coordSys = rotated ? new RotCoordSys(limits,location) :
                         new CoordSys(limits,location);
    elements = new Vector();
  }

  /** Add an element to be ploted in this box. */
  public void addElement(PlotElement e) {
    elements.addElement(e);
    e.setParent(this);
  }

  /** Plot the contents of this PlotBox.  First recalculate the affine
    * transformation for this box, then call paint for all elements.
    */
  public void paint(Graphics2D g) {
    //Recalculate the local affine transformation.
    AffineTransform affine 
      = (parent!=null) ? parent.getCoordSys() : new AffineTransform();
    coordSys.calcTransform(affine);
    //Paint the children.
    Enumeration e = elements.elements(); 
    while (e.hasMoreElements()) {
      ((PlotElement)e.nextElement()).paint(g);
    }
  } 

  /** Get the coordinate system. */
  public CoordSys getCoordSys() {
    return coordSys;
  }
}
