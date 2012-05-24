package dataspork.reader;
import dataspork.tree.*;
import dataspork.reader.*;
import dataspork.interp.*;
import dataspork.util.SciStreamTokenizer;
import java.io.*;
import java.net.URL;

/// AsciiDataReader reads simple ascii text columns, where the columns
/// are separated by whitespace.  Columns can be labelled by making the
/// first line a comment line (using a '#' symbol), and multi-word
/// comments can be grouped using either single or double quotes.
public class AsciiDataReader implements DataReader {

  protected int ncols;
  final static public int MAX_COLS = 50;

  public AsciiDataReader() {}

  public DataTreeNode read(URL url) {

    ncols = 0;
    String filename = url.getFile();
    int k = filename.lastIndexOf('/');
    String shortFilename = filename.substring(++k);
    DataTreeNode parentnode = null;
    int nlabel = 0;
    String[] colLabel = new String[MAX_COLS];
    StreamTokenizer st;

    // figure out how many columns data file has
    try {
      Reader r = new BufferedReader(new FileReader(filename));
      st = new SciStreamTokenizer(r);          
      st.eolIsSignificant(true);
      int token;

      // check for column labels (indicated by comment character)
      while ( (token=st.nextToken())!=st.TT_EOF && st.lineno() < 3) {
        if (st.ttype==st.TT_EOL) { continue; }
        if (st.lineno()==1 && nlabel > 0) {
          colLabel[nlabel] = st.sval;
          nlabel++;
        }       
        if (token == '#') nlabel = 1;
        if (st.lineno() == 2) {
          ncols++;
        }
      }

      DataSource data;
      DataTreeNode node;
      InterpreterClass interp;
      parentnode = new DataTreeNode(shortFilename);
      String label;

      for (int i=1; i<=ncols; i++) {
        data = new AsciiColumnSource(filename,i);
        if (nlabel > 0 && colLabel[i] != null) { label = colLabel[i]; }
        else { label = "col " + i; }

        if (!label.equals("skip") && !label.equals("SKIP")) {

          node = new DataTreeNode(label,data);  
          node.setLongName(label+" ("+shortFilename+")");

          interp = new InterpreterClass(Class.forName("dataspork.interp.ScalarDataset"),node);
          node.addInterpreter(interp);
          if (node != null) parentnode.add(node);          
        }

      }
    } catch (Exception exc) {
      System.out.println("exception in AsciiDataReader: " + exc);
    }

    return parentnode;


  }
}
