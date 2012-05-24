package dataspork.reader;
import java.util.*;

public interface ReaderIndex {

  public String getReaderClass(String key);
  public String getReaderName(String key);
  public boolean isReaderGroupable(String key);
  public String getReaderFilterKey(String key);
  public String getFilterClass(String key);
  public String getFilterName(String key);
  public String getReaderClassKey(String name);
  public String getReaderNameKey(String name);
  public String[] getReaderKeyList();
  public String[] getFilterKeyList();
  public int getNReaders();
  public int getNFilters();
}
