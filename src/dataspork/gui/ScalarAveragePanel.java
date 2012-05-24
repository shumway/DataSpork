package dataspork.gui;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.text.DecimalFormat;
import dataspork.interp.ScalarDataset;
import dataspork.stat.*;
import dataspork.util.DataListener;

public class ScalarAveragePanel extends JPanel implements DataListener {
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

  public ScalarAveragePanel(ScalarDataset dataset) {
    this.dataset=dataset;
    //setBackground(Color.white);
    setLayout(new GridLayout(6,1));
    df = new DecimalFormat(" 0.0000000E00;-0.0000000E00");
    errf = new DecimalFormat("0.0E00");

    Border blackline = BorderFactory.createLineBorder(Color.black);
    //add(meanLabel  = new JLabel("mean:",JLabel.RIGHT));
    add(meanField  = new JLabel(""));
    meanField.setBorder(BorderFactory.createTitledBorder(blackline,"mean"));
    //add(errorLabel = new JLabel("",JLabel.RIGHT));
    add(errorField = new JLabel(""));
    errorField.setBorder(
          BorderFactory.createTitledBorder(blackline,"error of mean"));
    //add(sigmaLabel = new JLabel("sigma:",JLabel.RIGHT));
    add(sigmaField = new JLabel(""));
    sigmaField.setBorder(BorderFactory.createTitledBorder(blackline,"sigma"));
    //add(corrLabel  = new JLabel("autocorr. time:",JLabel.RIGHT));
    add(corrField  = new JLabel(""));
    corrField.setBorder(
        BorderFactory.createTitledBorder(blackline,"autocor. time"));
    //add(startLabel = new JLabel("start cutoff:",JLabel.RIGHT));
    add(startField = new JTextField(10));
    startField.setBorder(
        BorderFactory.createTitledBorder(blackline,"start cutoff"));
    //add(endLabel   = new JLabel("end cutoff:",JLabel.RIGHT));
    add(endField   = new JTextField(10));
    endField.setBorder(
        BorderFactory.createTitledBorder(blackline,"end cutoff"));
    
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
    setMaximumSize(getPreferredSize());
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
