package dataspork.view;
import java.awt.*;
import java.awt.geom.*;
import dataspork.plot.*;
import dataspork.interp.StructureFactor;
import dataspork.util.DoubleSource;

/**
  */
public class StructureFactorGraph {

  protected StructureFactor structureFactor;
  protected CoordRange range;
  protected Plot plot;
  protected PlotBox plotBox;
  protected XYPlot plotLine;
  protected XYPoints plotPoints;
  protected Axis xAxis;
  protected Axis yAxis;

  /**
    */
  public StructureFactorGraph(StructureFactor structureFactor) {
    this.structureFactor=structureFactor;
    range = new CoordRange( new AutoXRange(structureFactor,0.05), 
                            new AutoYRange(structureFactor,0.1));
    //
    //Lay out the plot
    //
    plot = new Plot();
    plotBox = new PlotBox(range, new CoordRange(0,0,1,1));
    plot.addElement(plotBox);
    plotBox.addElement(new Line(new DoubleSource.Const(0),Line.HORIZONTAL));
    plotBox.addElement(new Line(new DoubleSource.Const(0),Line.VERTICAL));
    plotBox.addElement( plotLine = new XYPlot(structureFactor) );
    plotLine.setPaint(Color.blue);
    plotLine.setStroke(new PlotStroke(1f,2));
    plotBox.addElement( plotPoints = new XYPoints(structureFactor) );
    plotPoints.setPaint(Color.blue);
    plotBox.addElement(xAxis = new Axis(Axis.HORIZONTAL));
    plotBox.addElement(yAxis = new Axis(Axis.VERTICAL));
    plotBox.addElement( new PlotFrame() );
  }


  /**
    */
  public Plot getPlot() {
    return plot;
  }
}
