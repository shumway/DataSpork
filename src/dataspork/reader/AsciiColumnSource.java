package dataspork.reader;
import dataspork.tree.DataSource;
import dataspork.util.SciStreamTokenizer;
import java.io.*;

public class AsciiColumnSource implements DataSource {

  public String filename;
  public int col;
  public double[] data;

  public AsciiColumnSource(String filename, int col) {
    this.filename=filename;
    this.col=col;
  }

  public Object getData() {
    if (data==null) readData();
    return data;
  }

  public int[] getDims() {
    if (data==null) readData();
    return new int[] {data.length};
  }

  protected void readData() {
    if (data==null) data=new double[100];
    try { 
      BufferedReader reader = new BufferedReader(new FileReader(filename));
      StreamTokenizer st = new SciStreamTokenizer(reader);
      st.eolIsSignificant(true);
      st.commentChar('#');
      int line=-1;
      int token;
      while ( (token=st.nextToken())!=st.TT_EOF) {
        if (st.ttype==st.TT_EOL) continue;
        if (++line>=data.length) { //allocate larger array.
          double[] newData = new double[data.length+100];
          for (int i=0; i<data.length; ++i) newData[i]=data[i];
          data = newData;
        }
        for (int i=1; i<col; ++i) st.nextToken();
        data[line]=st.nval;
        while (st.nextToken()!=st.TT_EOL);
      }
      double[] resize = new double[line+1];
      for (int i=0; i<resize.length; ++i) resize[i]=data[i];
      data=resize;
    }
    catch (EOFException e) {}
    catch (IOException e) {data=null;}
  }

  public int getType() {
    return DOUBLE;
  }
}
