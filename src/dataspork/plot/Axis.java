package dataspork.plot;
import java.awt.*;
import java.awt.geom.*;
import dataspork.util.DoubleSource;
import java.text.DecimalFormat;

/** Class for drawing a one-dimensional coordinate axis. Draws tick marks
  * and numerical lables.
  */
public class Axis extends PlotElement {
  protected int orientation;
  public static final int HORIZONTAL=0;
  public static final int VERTICAL=1;

  /** Constructor takes an orientation as its argument. */
  public Axis(int orientation) {
    this.orientation=orientation;
  }

  /** Paint method is called by parent <code>PlotElement</code>.*/
  public void paint(Graphics2D g) {
    CoordSys cs = parent.getCoordSys();
    double m11=cs.getScaleY();
    double m01=cs.getShearX();
    double m00=cs.getScaleX();
    double m10=cs.getShearY();
    double scaleX=1d/Math.sqrt(m00*m00+m10*m10);
    double scaleY=1d/Math.sqrt(m11*m11+m01*m01);
    if (Double.isNaN(scaleX)||Double.isInfinite(scaleX)||
        Double.isNaN(scaleY)||Double.isInfinite(scaleY)) return;
    double begin,end,other;
    if (orientation==HORIZONTAL) {
      begin=cs.limits.getXMin();
      end=cs.limits.getXMax();
      other=cs.limits.getYMin();
    } else {
      begin=cs.limits.getYMin();
      end=cs.limits.getYMax();
      other=cs.limits.getXMin();
    }
    double length=end-begin;
    double log10inv=1d/Math.log(10d);
    double bigTicks=Math.pow(10,Math.floor(Math.log(length)*log10inv)); 
    if (length/bigTicks<3d) bigTicks/=2d;
    if (length/bigTicks>6d) bigTicks*=2d;

    //Figure out the label format.
    DecimalFormat format;
    double logTicks = Math.log(bigTicks)*log10inv;
    if (logTicks>=6) {
      format = new DecimalFormat("0.000E0");
    } else if (logTicks>=0) {
      format = new DecimalFormat("0");
    } else if (logTicks>=-1) {
      format = new DecimalFormat("0.0");
    } else if (logTicks>=-2) {
      format = new DecimalFormat("0.00");
    } else if (logTicks>=-3) {
      format = new DecimalFormat("0.000");
    } else if (logTicks>=-4) {
      format = new DecimalFormat("0.0000");
    } else if (logTicks>=-5) {
      format = new DecimalFormat("0.00000");
    } else {
      format = new DecimalFormat("0.000E0");
    }
    //Save old Paint and Stroke settings.
    Paint oldPaint = g.getPaint();
    Stroke oldStroke = g.getStroke();
    g.setPaint(color); g.setStroke(stroke);

    //Loop over axis ticks.
    double s = Math.ceil(begin/bigTicks)*bigTicks;
    Point2D.Double[] tick = new Point2D.Double[3];
    Point2D.Double[] scaledTick = new Point2D.Double[3];
    while (s<end+0.0001*bigTicks) {
      String label = format.format(s);
      FontMetrics fm = g.getFontMetrics();
      double width=fm.stringWidth(label);
      double height=fm.getAscent();
      double labelDeltaX, labelDeltaY;
      //Determine coords for tick (tick[0],tick[1]) and label (tick[3]).
      if (orientation==HORIZONTAL){
        tick[0]	= new Point2D.Double(s,other);
        tick[1]	= new Point2D.Double(s,other+scaleY*10);
        tick[2]	= new Point2D.Double(s-scaleX*width*0.5,other-scaleY*height);
      } else {
        tick[0]	= new Point2D.Double(other,s);
        tick[1]	= new Point2D.Double(other+scaleX*10,s);
        tick[2]	= new Point2D.Double(other-scaleX*(5+width),
                                     s-scaleY*height*0.5);
      }
      cs.transform(tick,0,scaledTick,0,3);
      g.draw(new Line2D.Double(scaledTick[0],scaledTick[1]));
      g.drawString(label,(float)scaledTick[2].x,(float)scaledTick[2].y);
      s+=bigTicks;
    }
    g.setStroke(oldStroke); g.setPaint(oldPaint);
  }
}
