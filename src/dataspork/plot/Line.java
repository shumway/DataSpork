package dataspork.plot;
import java.awt.*;
import java.awt.geom.*;
import dataspork.util.DoubleSource;

/** Draw a straight line, either horizontal or vertical.*/
public class Line extends PlotElement {
  /** The object thap rrovides the coordinate of the line. */
  protected DoubleSource coordSource;
  /** The orientation of the line. */
  protected int orientation;
  /** Horizontal orientation. */
  public static final int HORIZONTAL=0;
  /** Vertical orientation. */
  public static final int VERTICAL=1;

  /** Constructor */
  public Line(DoubleSource coordSource, int orientation) {
    this.coordSource=coordSource;
    this.orientation=orientation;
  }

  /** Paint */
  public void paint(Graphics2D g) {
    float[] temp = new float[4];
    CoordSys cs = parent.getCoordSys();
    if (orientation==HORIZONTAL) {
      temp[1]=temp[3]=(float)coordSource.getValue();
      temp[0]=(float)cs.limits.getXMin();
      temp[2]=(float)cs.limits.getXMax();
    } else {
      temp[0]=temp[2]=(float)coordSource.getValue();
      temp[1]=(float)cs.limits.getYMin();
      temp[3]=(float)cs.limits.getYMax();
    }
    cs.transform(temp,0,temp,0,2);

    GeneralPath path = new GeneralPath(GeneralPath.WIND_EVEN_ODD,4);
    path.moveTo(temp[0],temp[1]);
    path.lineTo(temp[2],temp[3]);

    Paint oldPaint = g.getPaint();
    Stroke oldStroke = g.getStroke();
    Shape oldClip = g.getClip();
    g.setPaint(color); g.setStroke(stroke); g.clip(cs.getLimitPath());
    g.draw(path);
    g.setClip(oldClip); g.setStroke(oldStroke); g.setPaint(oldPaint);
  }

}
