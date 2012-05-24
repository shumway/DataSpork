package dataspork.reader;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;

public class AsciiFilter extends FileFilter {

  final static public String dat = "dat";
    
    // Accept all directories and all .dat files.
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = getExtension(f);
        if (extension == null) {return false;}
        if (extension.equals(dat)) { return true; }
        else { return false; }
    }
    
    // The description of this filter
    public String getDescription() {
      DefaultReaderIndex ri = new DefaultReaderIndex();
      return ri.getFilterName("dat");
    }
    private static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
}

