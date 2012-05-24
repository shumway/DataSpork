package dataspork.gui;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import dataspork.interp.ScalarDataset;
import dataspork.view.*;
import dataspork.plot.*;
import dataspork.stat.*;
import dataspork.util.DataListener;
import dataspork.util.JMenuUtil;

public class OldScalarDatasetGUI extends CommonFrame 
    implements InterpreterGUI, ActionListener, DataListener {
  protected ScalarDataset dataset;
  protected Histogram histogram;
  protected Blocker blocker;
  protected Autocorr autocorr;

  protected TraceGraph traceGraph;
  protected PlotPanel traceGraphPanel;
  protected AutocorGraph autocorGraph;
  protected PlotPanel autocorGraphPanel;
  protected BlockingGraph blockingGraph;
  protected PlotPanel blockingGraphPanel;
  protected OldScalarAveragePanel averagePane;

  public OldScalarDatasetGUI(ScalarDataset dataset) {
    this.dataset=dataset;
    histogram = new Histogram(dataset,16);
    blocker = new Blocker(dataset);
    autocorr = new Autocorr(dataset);

    //Set up the window.
    setTitle(dataset.getName());
    contentPane.setLayout(new GridBagLayout());
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.gridx=0;
    constraints.gridy=constraints.RELATIVE;
    constraints.fill=constraints.BOTH;
    constraints.anchor=constraints.CENTER;
    constraints.weightx=2.0; constraints.weighty=2.0;

    //Set up the menus.
    //JMenuBar menuBar = new JMenuBar();
    //setJMenuBar(menuBar);
    JMenuUtil.addMenu( menuBar, "File",
      new String[] {"Close"},
      new String[] {"close"},
      new int[]    {KeyEvent.VK_W},
      this
    );

    //Set up the trace graph.
    traceGraph = new TraceGraph(dataset,histogram);
    traceGraphPanel = new PlotPanel(traceGraph.getPlot());
    traceGraphPanel.setPreferredSize(new Dimension(500,300));
    traceGraphPanel.setBackground(Color.white);
    traceGraphPanel.setBorder(new TitledBorder( new EmptyBorder(10,70,20,10),
               "Trace of "+dataset.getName(),
               TitledBorder.CENTER,TitledBorder.BELOW_TOP));
    constraints.gridwidth=2;
    contentPane.add(traceGraphPanel,constraints);
    constraints.weightx=1.0; constraints.weighty=1.0;
    constraints.gridwidth=1;

    //Set up the autocorrelation graph.
    autocorGraph = new AutocorGraph(autocorr);
    autocorGraphPanel = new PlotPanel(autocorGraph.getPlot());
    autocorGraphPanel.setPreferredSize(new Dimension(250,180));
    autocorGraphPanel.setBackground(Color.white);
    autocorGraphPanel.setBorder(new TitledBorder( new EmptyBorder(10,70,20,10),
            "Autocorrelation",TitledBorder.CENTER,TitledBorder.BELOW_TOP));
    contentPane.add(autocorGraphPanel,constraints);
    
    //Set up the blocking graph.
    blockingGraph = new BlockingGraph(blocker);
    blockingGraphPanel = new PlotPanel(blockingGraph.getPlot());
    blockingGraphPanel.setPreferredSize(new Dimension(250,180));
    blockingGraphPanel.setBackground(Color.white);
    blockingGraphPanel.setBorder(new TitledBorder( new EmptyBorder(0,70,20,10),
            "Blocking Analysis",TitledBorder.CENTER,TitledBorder.BELOW_TOP));
    contentPane.add(blockingGraphPanel,constraints);

    //Set up the scalar average panel.
    averagePane = new OldScalarAveragePanel(dataset);
    constraints.gridx=1;
    constraints.fill=constraints.NONE;
    constraints.gridheight=constraints.REMAINDER;
    contentPane.add(averagePane,constraints);

    pack();
    show();

    dataset.addListener(this);
  }

  public void dataChanged(Object o) {
    traceGraphPanel.repaint();
    autocorGraphPanel.repaint();
    blockingGraphPanel.repaint();
  }

  public void actionPerformed(ActionEvent event) {
  }
}
