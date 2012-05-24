package dataspork.plot;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
/**
 * A class that implements PlotBox and attaches to a PlotPanel.  Use
 * this class as the base of a PlotElement tree to paint the graphics
 * defined by the tree onto a PlotPanel.
 *
 * To create a plot, create a Plot object, and add it to  a PlotPanel. 
 * <pre>
 *   Plot plot = new Plot();
 *   plotPanel.add(plot);
 * </pre>
 *
 * <p>A Plot object stores a reference to the PlotPanel.  The base class 
 * of Plot, PlotBox, has a CoordSys object that is used to map the PlotPanel
 * pixel coordinates (measured in pts, 72 pts per inch) to the local 
 * coordinate system.
 *
 * @see dataspork.plot.PlotPanel
 * @see dataspork.plot.PlotElement
 * @see dataspork.plot.PlotBox 
 * @author John Shumway
 */
public class Plot extends PlotBox {

  /** The Panel on which to paint the plot graphics.  */
  protected PlotPanel panel;

  /** Construct the base of the plot tree, with coordinates (0,0)-(1,1).
  public Plot() {
    super();
  }

  /** Set the panel for the plot - should only be called by the 
    * add method of PlotPanel. 
    * @param panel The panel on which to paint the plot.
    */
  public void setPanel(PlotPanel panel) {
    this.panel = panel;
  }

  public void paint(Graphics2D g) {
    if (panel!=null) {
      //Update the coordinate system to map the panel to the local coordinates.
      Dimension dim = panel.getSize();
      Insets insets = panel.getInsets();
      //Note: ymin and ymax are switched here to get to math convention
      //      from the panels computer graphics convention.
      CoordRange location = new CoordRange(insets.left,
        dim.height-insets.bottom,dim.width-insets.right,insets.top);
      coordSys = new CoordSys(coordSys.limits, location);
      super.paint(g);
    }
  }

}
