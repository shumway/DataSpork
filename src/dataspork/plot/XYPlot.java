package dataspork.plot;
import java.awt.*;
import java.awt.geom.*;
import dataspork.util.XYDataSource;

/** Make an x-y plot from array data, using lines. */
public class XYPlot extends PlotElement {

  protected double[] data;
  protected double[] ordinates;
  protected XYDataSource source;

  /**  */
  public XYPlot(double[] data) {
    this.data=data;
  }

  public XYPlot(XYDataSource source) {
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
    
    GeneralPath path = new GeneralPath(GeneralPath.WIND_EVEN_ODD,4);
    path.moveTo(temp[0],temp[1]);
    for (int i=1; i<data.length; i++) {
      path.lineTo(temp[2*i],temp[2*i+1]);
    }

    Paint oldPaint = g.getPaint();
    Shape oldClip = g.getClip();
    Stroke oldStroke = g.getStroke();
    g.setPaint(color);
    g.clip(cs.getLimitPath());
    //g.setStroke(new BasicStroke(1,BasicStroke.CAP_ROUND,
    //                BasicStroke.JOIN_BEVEL,1, new float[] {8,2}, 0) );
    g.setStroke(stroke);
    g.draw(path);
    g.setStroke(oldStroke);
    g.setClip(oldClip);
    g.setPaint(oldPaint);
  }
}
