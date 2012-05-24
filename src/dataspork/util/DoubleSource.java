package dataspork.util;

public interface DoubleSource {
  public double getValue();

  public class Const implements DoubleSource {
    public static final Const ZERO = new Const(0);
    protected double value;
    public Const(double v) {value=v;}
    public double getValue() {return value;}
  }
}
