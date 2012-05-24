package dataspork.plot;
import java.awt.*;
import java.awt.geom.*;

/** A rectangular frame for PlotBox. */
public class PlotFrame extends PlotElement {

  /** Constructor */
  public PlotFrame() {

  }

  /** Paint */
  public void paint(Graphics2D g) {
    GeneralPath path = parent.getCoordSys().getLimitPath();   
    Paint oldPaint = g.getPaint();
    Stroke oldStroke = g.getStroke();
    g.setPaint(color);
    g.setStroke(stroke);
    g.draw(path);
    g.setPaint(oldPaint);
    g.setStroke(oldStroke);
  }

}
