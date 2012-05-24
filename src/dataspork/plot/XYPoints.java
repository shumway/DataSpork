package dataspork.plot;
import java.awt.*;
import java.awt.geom.*;
import dataspork.util.XYDataSource;

/** Make an x-y plot from array data, using points. */
public class XYPoints extends PlotElement {

  protected double[] data;
  protected double[] ordinates;
  protected XYDataSource source;
  protected float pointSize=5;

  /**  */
  public XYPoints(double[] data) {
    this.data=data;
  }

  public XYPoints(XYDataSource source) {
    this.source=source;
  }


  public void paint(Graphics2D g) {
    if (source!=null) {
      ordinates=source.getXData();
      data=source.getYData();
    }
    float[] temp = new float[2*data.length];
    for (int i=0; i<data.length; i++) {
      if (ordinates!=null) {
        temp[2*i]=(float)ordinates[i];
      } else {
        temp[2*i]=i;
      }
      temp[2*i+1]=(float)data[i];
    }
    CoordSys cs = parent.getCoordSys();
    cs.transform(temp,0,temp,0,data.length);
    
    Paint oldPaint = g.getPaint();
    Shape oldClip = g.getClip();
    Stroke oldStroke = g.getStroke();
    g.setPaint(color);
    g.clip(cs.getLimitPath());
    g.setStroke(stroke);
    for (int i=0; i<data.length; i++) {
      g.draw(new Ellipse2D.Float(temp[2*i]-0.5f*pointSize,
                                 temp[2*i+1]-0.5f*pointSize,
                                 pointSize,pointSize) );
    }
    g.setStroke(oldStroke);
    g.setClip(oldClip);
    g.setPaint(oldPaint);
  }
}
