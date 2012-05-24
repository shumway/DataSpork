package dataspork.gui;
import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import dataspork.interp.ScalarDataset;
import dataspork.stat.*;
import dataspork.util.DataListener;

public class OldScalarAveragePanel extends JPanel implements DataListener {
  protected ScalarDataset dataset;
  protected DoubleWindowControl windowControl;

  protected JLabel meanField;
  protected JLabel errorField;
  protected JLabel sigmaField;
  protected JLabel corrField;
  protected JTextField startField;
  protected JTextField endField;
  protected JLabel meanLabel;
  protected JLabel errorLabel;
  protected JLabel sigmaLabel;
  protected JLabel corrLabel;
  protected JLabel startLabel;
  protected JLabel endLabel;

  protected DecimalFormat df;
  protected DecimalFormat errf;

  public OldScalarAveragePanel(ScalarDataset dataset) {
    this.dataset=dataset;
    setBackground(Color.white);
    setLayout(new GridLayout(0,2));
    df = new DecimalFormat(" 0.000000E00;-0.000000E00");
    errf = new DecimalFormat("0.0E00");

    add(meanLabel  = new JLabel("mean:",JLabel.RIGHT));
    add(meanField  = new JLabel(""));
    add(errorLabel = new JLabel("",JLabel.RIGHT));
    add(errorField = new JLabel(""));
    add(sigmaLabel = new JLabel("sigma:",JLabel.RIGHT));
    add(sigmaField = new JLabel(""));
    add(corrLabel  = new JLabel("autocorr. time:",JLabel.RIGHT));
    add(corrField  = new JLabel(""));
    add(startLabel = new JLabel("start cutoff:",JLabel.RIGHT));
    add(startField = new JTextField(10));
    add(endLabel   = new JLabel("end cutoff:",JLabel.RIGHT));
    add(endField   = new JTextField(10));
    
    Font font = new Font("Monospaced",Font.PLAIN,12);
    meanField.setFont(font);
    errorField.setFont(font);
    sigmaField.setFont(font);
    corrField.setFont(font);
    startField.setFont(font);
    endField.setFont(font);

    windowControl = new DoubleWindowControl(dataset.getWindow(),
                                            startField,endField);
    dataset.addListener(this);
    writeText();
  }

  protected void writeText() {
    meanField.setText(df.format(dataset.getMean()));
    errorField.setText("\u00b1"+errf.format(dataset.getErrorOfMean()));
    sigmaField.setText(df.format(dataset.getSigma()));
    corrField.setText(df.format(dataset.getAutoCorr()));
  }

  public void dataChanged(Object o) {
    writeText();
  }
}
