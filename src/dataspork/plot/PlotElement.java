package dataspork.plot;
import java.awt.*;
import java.awt.geom.*;

/** The base class of all plot elements.   The subclass PlotBox
  * serves as a container for PlotElements.
  */
public class PlotElement {

  /** The container that holds this plot element. */
  protected PlotBox parent;

  /** The <code>Paint</code> for this PlotElement.  Can be a color
    * or something more complicated.  Default is black. */
  protected Paint color;

  /** The <code>Stroke</code> for this PlotElement.  Defaults to a 
    * thin unbroken line. */
  protected Stroke stroke;

  public PlotElement() {
    color = Color.black;
    stroke = new BasicStroke(1);
  }

  /** Protected method for setting the parent, should be called by
    * the parent's addElement method.
    */
  protected void setParent(PlotBox parent) {
    this.parent=parent;
  }

  /** Draw the object and all children. */
  public void paint(Graphics2D g) {}

  /** Attempt to add a child PlotElement. */
  public void addElement(PlotElement child) {}

  /** Set the paint */
  public void setPaint(Paint p) {
    color = p;
  }

  /** Set the stroke */
  public void setStroke(Stroke s) {
    stroke = s;
  }
}
