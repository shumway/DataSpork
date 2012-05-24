package dataspork.util;
import java.util.*;
import javax.swing.*;

public class ListProps {
  public static void main (String args[]) {
    Hashtable defaultProps = UIManager.getDefaults();
    Enumeration enum = defaultProps.keys();
    while (enum.hasMoreElements()) {
    Object key = enum.nextElement();
    System.out.println(key + "\t" + defaultProps.get(key));
    }
    System.exit(0);
  }
}
