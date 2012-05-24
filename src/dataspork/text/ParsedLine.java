package dataspork.text;
import java.io.PrintStream;

public interface ParsedLine {
  public int getNWords();
  public String getWord(int i);
  public void printToStream(PrintStream p);
}
