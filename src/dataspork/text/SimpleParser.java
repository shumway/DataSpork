package dataspork.text;
import java.io.*;
import java.util.*;

public class SimpleParser implements Parser, ParsedLine {
  /** The current source of input. */
  protected BufferedReader input;
  protected PrintStream output;
  protected String prompt;

  protected String[] words;
  protected int nwords;

  public SimpleParser() {
    this(System.in,System.out,">");
  }

  public SimpleParser(String prompt) {
    this(System.in,System.out,prompt);
  }

  public SimpleParser(InputStream in, PrintStream out, String prompt) {
    input = new BufferedReader(new InputStreamReader(in));
    output = out;
    this.prompt=prompt;
    nwords=0;
  }

  public ParsedLine readNextLine() {
    StringTokenizer tokenizer = null;
    do {
      if (prompt!=null) output.print(prompt);
      String line=null;
      try {line=input.readLine();} catch (IOException e) {return null;}
      if (line==null) {
        if (prompt!=null) output.println();
        return null;
      }
      tokenizer = new StringTokenizer(line);
      nwords=tokenizer.countTokens();
    } while (nwords==0);
    words = new String[nwords];
    for (int i=0; i<nwords; ++i) {
      words[i]=tokenizer.nextToken();
    }
    return this;
  }

  public int getNWords() {return nwords;}

  public String getWord(int i) {
    return (i>=0 && i<nwords) ? words[i] : null;
  }

  public void printToStream(PrintStream p) {
    for (int i=0; i<nwords-1; ++i) {
      p.print(words[i]+" ");
    }
    if (nwords>0) p.println(words[nwords-1]);
  }

  public static void main(String[] args) {
    Parser p = new SimpleParser("test>");
    ParsedLine line;
    while ((line=p.readNextLine())!=null) {
      System.out.println("Next line has "+line.getNWords()+" words.");
      line.printToStream(System.out);
    }
    System.exit(0);
  }
}
