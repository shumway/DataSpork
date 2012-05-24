package dataspork.plot;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/** A <code>JPanel</code> for holding and displaying a plot. */
public class PlotPanel extends JPanel {

  /** The plot information. */
  protected Plot p;

  /** Constructor. */
  public PlotPanel(Plot p) {
    this.p = p;
    p.setPanel(this);
  }

  /** <code>JPanel</code> paint method, which calls the paint methods
    * for the plot.
     */
  public void paint(Graphics g) {
    super.paint(g);
    p.paint((Graphics2D)g);
  }

}
