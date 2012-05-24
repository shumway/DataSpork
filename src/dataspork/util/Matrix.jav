package analyzer.util;

public class Matrix{
  public int n;
  protected int n2;
  public double [] data;

  public Matrix(double[] data) {
    n=(int)Math.sqrt(data.length);
    if (data.length!=n*n) throw new Err(Err.DIM_MISMATCH);
    this.data = data;
    n2=n*n;
  } 

  public Matrix(int n) {
    data = new double[n*n];
    this.n = n;
    n2 = n*n;
  }

  public double trace() {
    double tr=0;
    int np1=n+1;
    for (int i=0; i<n2; i+=np1) tr+=data[i];
    return tr;
  }

  public void plusEq(double scalar) {
    for (int i=0; i<n2; i++) data[i]+=scalar;
  }

  public void plusEq(Matrix mat) {
    if (n!=mat.n) throw new Err(Err.DIM_MISMATCH);
    for (int i=0; i<n2; i++) data[i]+=mat.data[i];
  }

  public void minusEq(double scalar) {
    for (int i=0; i<n2; i++) data[i]-=scalar;
  }

  public void minusEq(Matrix mat) {
    if (n!=mat.n) throw new Err(Err.DIM_MISMATCH);
    for (int i=0; i<n2; i++) data[i]-=mat.data[i];
  }

  public void timesEq(double scalar) {
    for (int i=0; i<n2; i++) data[i]*=scalar;
  }

  public void divideEq(double scale) {
    for (int i=0; i<n2; i++) data[i]/=scale;
  }

  public Matrix square() {
    Matrix r = new Matrix(n);
    for (int i=0; i<n; i++) {
      int in=i*n;
      for (int j=0; j<n; j++) {
        for (int k=0; k<n; k++) {
          r.data[in+j] += data[k*n+j]*data[in+k];
        }
      }
    }
    return r;
  }

  public void print() {
    String nstr = String.valueOf(n);
    System.out.println("Matrix "+nstr+"x"+nstr);
  }

}
