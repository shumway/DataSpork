package dataspork.view;
import java.awt.*;
import java.awt.geom.*;
import dataspork.plot.*;
import dataspork.stat.ScalarSeries;
import dataspork.stat.Histogram;
import dataspork.util.DoubleSource;
import dataspork.util.ConstXYDataSource;
import dataspork.util.XYDataSource;

/**
  */
public class TraceGraph {

  protected ScalarSeries dataset;
  protected Histogram histogram;
  protected CoordRange range;
  protected CoordRange histRange;
  protected double histMax;
  protected Plot plot;
  protected PlotBox traceBox;
  protected PlotBox histBox;
  protected XYPlot tracePlot;
  protected XYBarPlot histPlot;
  protected Line meanLine;
  protected XWindowMarks windowMarks;
  protected Axis xAxis;
  protected Axis yAxis;

  /**
    */
  public TraceGraph(ScalarSeries dataset, Histogram histogram) {
    this.dataset=dataset;
    this.histogram=histogram;
    ConstXYDataSource source = 
      new ConstXYDataSource(dataset.getData());
    //range = new CoordRange(new WindowRange(dataset.getWindow()),
    //                       new ConstRange(-5,5));
    //range = new CoordRange(new ConstRange(0,(double)(data.length-1)),
    //                     new ConstRange(-5,5));
    Range stepRange = new AutoXRange(source);
    //Range valueRange = new AutoYRange(source,0.1);
    Range valueRange = new AutoXRange(histogram,0.1);
    range = new CoordRange(stepRange,valueRange);
    
    histMax = 10;
    //
    //Lay out the plot
    //
    plot = new Plot();
    //
    //Lay out the trace part of the graph. 
    //
    range = new CoordRange(stepRange,valueRange);
    traceBox = new PlotBox(range, new CoordRange(0,0,0.85,1));
    plot.addElement(traceBox);
    traceBox.addElement(meanLine=new Line(new MeanSource(),Line.HORIZONTAL));
    meanLine.setPaint(Color.red);
    traceBox.addElement(windowMarks = new XWindowMarks(dataset.getWindow()));
    windowMarks.setPaint(Color.green);
    traceBox.addElement(tracePlot = new XYPlot(source));
    tracePlot.setPaint(Color.blue);
    //tracePlot.setStroke(new PlotStroke(2f,1));
    traceBox.addElement(new PlotFrame());
    traceBox.addElement(xAxis = new Axis(Axis.HORIZONTAL));
    traceBox.addElement(yAxis = new Axis(Axis.VERTICAL));
    //
    //Lay out the histogram part of the graph.
    //
    //histRange = (CoordRange)range.clone();
    //histRange.xmin=histMax; histRange.xmax=0;
    Range binRange = new HistYRange(histogram);
    histRange = new CoordRange(valueRange,binRange);
    histBox = new PlotBox(histRange, new CoordRange(0.85,0,1,1),true);
    histBox.addElement(histPlot = new XYBarPlot(histogram));
    //histBox.addElement(new Axis(Axis.HORIZONTAL));
    //histBox.addElement(new Axis(Axis.VERTICAL));
    histBox.addElement(new PlotFrame());
    plot.addElement(histBox);
  }


  /**
    */
  public Plot getPlot() {
    return plot;
  }

  public class MeanSource implements DoubleSource {
    public double getValue(){return dataset.getMean();}
  }

  public class HistYRange implements Range {
    protected XYDataSource source;
    public HistYRange(XYDataSource s) {source=s;}
    public double getLow() {return 0;}
    public double getHigh() {return source.getYMax()*1.1;}
    public double length() {return source.getYMax()*1.1;}
  }
}
