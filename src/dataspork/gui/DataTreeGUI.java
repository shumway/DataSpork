package dataspork.gui;

import dataspork.interp.InterpreterClass;
import dataspork.interp.Interpreter;
import dataspork.tree.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;
import java.util.EventObject;
import java.util.Enumeration;

public class DataTreeGUI extends JTree {
  protected JPopupMenu jpop;

  public DataTreeGUI(DataTreeNode dataTree) {
    super(dataTree);
    jpop = new JPopupMenu();
    setCellEditor(new DefaultTreeCellEditor(
                          (JTree)this,new DefaultTreeCellRenderer()) {
      public boolean isCellEditable(EventObject anEvent)  {
        MouseEvent e=(MouseEvent)anEvent;
        if (e!=null) {
          {
//          if(e.isPopupTrigger()  //this may not work on NT, next line is a hack
//                 || SwingUtilities.isLeftMouseButton(e) ) {
            jpop.removeAll();
            DataTreeNode node = (DataTreeNode)getClosestPathForLocation(
                                  e.getX(),e.getY()).getLastPathComponent();
            Enumeration en = node.getInterpreters();
            while (en.hasMoreElements()) {
              jpop.add(new InterpreterRequest(
                              (InterpreterClass)en.nextElement()));
            }
          jpop.show((Component)e.getSource(),e.getX(),e.getY());
          }
        }
        return false;
      }
    });
    setEditable(true);
  }

  public class InterpreterRequest extends AbstractAction {
    protected InterpreterClass interp;
    public InterpreterRequest(InterpreterClass interp) {
      super(interp.toString()); this.interp=interp;}
    public void setEnabled(boolean b) {}
    public void actionPerformed(ActionEvent event) {
      interp.getInstance().getGUI();}
  }

}
