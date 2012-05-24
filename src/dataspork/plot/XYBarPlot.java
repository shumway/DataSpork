package dataspork.plot;
import java.awt.*;
import java.awt.geom.*;
import dataspork.util.XYDataSource;

/** Make an x-y plot from array data. */
public class XYBarPlot extends PlotElement {

  protected double[] data;
  protected double[] ordinates;
  protected XYDataSource source;

  /**  */
  public XYBarPlot(double[] data) {
    this.data=data;
  }

  public XYBarPlot(XYDataSource source) {
    this.source=source;
  }


  public void paint(Graphics2D g) {
    if (source!=null) {
      ordinates=source.getXData();
      data=source.getYData();
    }
    float[] temp = new float[4*(data.length+1)];
    float x=(ordinates!=null)?(float)(1.5*ordinates[0]-0.5*ordinates[1]):-.5f;
    temp[0]=x;
    temp[1]=0;
    for (int i=0; i<data.length-1; i++) {
      temp[4*i+2]=x;
      temp[4*i+3]=(float)data[i];
      x=(ordinates!=null)?(float)(ordinates[i]+ordinates[i+1])*0.5f : i+0.5f;
      temp[4*i+4]=x;
      temp[4*i+5]=(float)data[i];
    }
    int imax=data.length;
    temp[4*imax-2]=x;
    temp[4*imax-1]=(float)data[imax-1];
    x=(ordinates!=null) ? (float)(1.5*ordinates[imax-1]-0.5*ordinates[imax-2])
                        : imax+0.5f;
    temp[4*imax]=x;
    temp[4*imax+1]=(float)data[imax-1];
    temp[4*imax+2]=x;
    temp[4*imax+3]=0;

    CoordSys cs = parent.getCoordSys();
    cs.transform(temp,0,temp,0,temp.length/2);
    
    GeneralPath path = new GeneralPath(GeneralPath.WIND_EVEN_ODD,4);
    path.moveTo(temp[0],temp[1]);
    for (int i=1; i<temp.length/2; i++) {
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
