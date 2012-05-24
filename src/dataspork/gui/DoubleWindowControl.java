package dataspork.gui;

import dataspork.util.DoubleWindowModel;
import dataspork.util.DataListener;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DoubleWindowControl extends KeyAdapter
              implements ActionListener, FocusListener, DataListener {
  protected DoubleWindowModel model;
  protected JTextField low;
  protected JTextField high;

  public DoubleWindowControl(DoubleWindowModel model, 
       JTextField low, JTextField high) {
    this.model=model;
    this.low=low;
    this.high=high;
    setTextValues();
    low.addActionListener(this);
    low.addFocusListener(this);
    low.addKeyListener(this);
    high.addActionListener(this);
    high.addFocusListener(this);
    high.addKeyListener(this);
    model.addListener(this);
  }

  protected void setTextValues() {
    low.setText(Double.toString(model.getLow()));
    high.setText(Double.toString(model.getHigh()));
  }

  public void actionPerformed(ActionEvent event) {
    if (event.getSource()==low) {
      //Try to send the string value to dataset.setIMin as an integer
      try{model.setLow( Double.valueOf(low.getText()).doubleValue() );}
      catch(NumberFormatException e) {}
    }
    if (event.getSource()==high) {
      //Try to send the string value to dataset.setIMin as an integer
      try{model.setHigh( Double.valueOf(high.getText()).doubleValue() );}
      catch(NumberFormatException e) {}
    }
    setTextValues();
  }

  public void focusLost(FocusEvent e) {
    //Convert a non-temporary loss of focus to a text action.
    Object source=e.getSource();
    if (!e.isTemporary()) {  
      actionPerformed( new 
           ActionEvent(source,ActionEvent.ACTION_PERFORMED,null) );
    }
  }

  public void focusGained(FocusEvent e) {}

  public void keyTyped(KeyEvent k) {
    int code = k.getKeyCode();
    if (code==k.VK_ENTER) {  
System.out.println("Return key pressed");
      actionPerformed( new 
           ActionEvent(k.getSource(),ActionEvent.ACTION_PERFORMED,null) );
    }
    
  }

  public void dataChanged(Object o) {
    setTextValues();
  }

}
