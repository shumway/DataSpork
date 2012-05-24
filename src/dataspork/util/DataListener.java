package dataspork.util;

import java.util.EventListener;

public interface DataListener extends EventListener {
  public void dataChanged(Object o);
}
