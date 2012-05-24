package dataspork.plot;
import java.awt.*;
import java.awt.geom.*;
import dataspork.util.DoubleWindowModel;

/** A rectangular frame for PlotBox. */
public class XWindowMarks extends PlotElement {
  public DoubleWindowModel xWindowModel;

  /** Constructor */
  public XWindowMarks(DoubleWindowModel xWindowModel) {
    this.xWindowModel=xWindowModel;
  }

  /** Paint */
  public void paint(Graphics2D g) {
    float[] temp = new float[8];
    temp[0]=temp[2]=(float)xWindowModel.getLow();
    temp[4]=temp[6]=(float)xWindowModel.getHigh();
    temp[1]=temp[7]=100;
    temp[3]=temp[5]=-100;
    CoordSys cs = parent.getCoordSys();
    cs.transform(temp,0,temp,0,4);

    GeneralPath path = new GeneralPath(GeneralPath.WIND_EVEN_ODD,4);
    path.moveTo(temp[0],temp[1]);
    path.lineTo(temp[2],temp[3]);
    path.moveTo(temp[4],temp[5]);
    path.lineTo(temp[6],temp[7]);

    Paint oldPaint = g.getPaint();
    Stroke oldStroke = g.getStroke();
    Shape oldClip = g.getClip();
    g.setPaint(color);
    g.setStroke(stroke);
    g.clip(cs.getLimitPath());
    g.draw(path);
    g.setClip(oldClip);
    g.setStroke(oldStroke);
    g.setPaint(oldPaint);
  }

}
