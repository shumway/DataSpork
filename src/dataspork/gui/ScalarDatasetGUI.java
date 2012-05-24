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

public class ScalarDatasetGUI extends CommonFrame 
    implements InterpreterGUI, ActionListener, DataListener {
  protected ScalarDataset dataset;
  protected Histogram histogram;
  protected Blocker blocker;
  protected Autocorr autocorr;

  protected JTabbedPane tabbedPane;

  protected TraceGraph traceGraph;
  protected PlotPanel traceGraphPanel;
  protected AutocorGraph autocorGraph;
  protected PlotPanel autocorGraphPanel;
  protected BlockingGraph blockingGraph;
  protected PlotPanel blockingGraphPanel;
  protected ScalarAveragePanel averagePane;

  public ScalarDatasetGUI(ScalarDataset dataset) {
    this.dataset=dataset;
    histogram = new Histogram(dataset,16);
    blocker = new Blocker(dataset);
    autocorr = new Autocorr(dataset);

    //Set up the window.
    setTitle(dataset.getName());
    contentPane.setLayout(new BoxLayout(contentPane,BoxLayout.X_AXIS));
    tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
    tabbedPane.setBorder(CommonFrame.BORDER);
    contentPane.add(tabbedPane);
    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,
                                        "close"));
      }
    });


    //Set up the menus.
    JMenuUtil.addMenu( menuBar, "File",
      new String[] {"Close"},
      new String[] {"close"},
      new int[]    {KeyEvent.VK_W},
      this
    );

    //Set up the trace graph.
    traceGraph = new TraceGraph(dataset,histogram);
    traceGraphPanel = new PlotPanel(traceGraph.getPlot());
    traceGraphPanel.setPreferredSize(new Dimension(400,250));
    traceGraphPanel.setBackground(Color.white);
    traceGraphPanel.setBorder(new TitledBorder( new EmptyBorder(0,70,20,5),
               "Trace of "+dataset.getName(),
               TitledBorder.CENTER,TitledBorder.BELOW_TOP));
    //contentPane.add(traceGraphPanel);
    tabbedPane.add("Trace",traceGraphPanel);

    //Set up the autocorrelation graph.
    autocorGraph = new AutocorGraph(autocorr);
    autocorGraphPanel = new PlotPanel(autocorGraph.getPlot());
    autocorGraphPanel.setBackground(Color.white);
    autocorGraphPanel.setBorder(new TitledBorder( new EmptyBorder(0,70,20,5),
            "Autocorrelation",TitledBorder.CENTER,TitledBorder.BELOW_TOP));
    //contentPane.add(autocorGraphPanel);
    tabbedPane.add("Autocorrelation",autocorGraphPanel);
    
    //Set up the blocking graph.
    blockingGraph = new BlockingGraph(blocker);
    blockingGraphPanel = new PlotPanel(blockingGraph.getPlot());
    blockingGraphPanel.setBackground(Color.white);
    blockingGraphPanel.setBorder(new TitledBorder( new EmptyBorder(0,70,20,5),
            "Blocking Analysis",TitledBorder.CENTER,TitledBorder.BELOW_TOP));
    //contentPane.add(blockingGraphPanel);
    tabbedPane.add("Blocking",blockingGraphPanel);

    //Set up the scalar average panel.
    averagePane = new ScalarAveragePanel(dataset);
    averagePane.setBorder(CommonFrame.BORDER);
    contentPane.add(averagePane);

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
    String command = event.getActionCommand();
    if (command.equals("close")) {
      Runnable hideFrame = new Runnable() {
        public void run() {setVisible(false);}
      };
      SwingUtilities.invokeLater(hideFrame);
    }
  }
}
