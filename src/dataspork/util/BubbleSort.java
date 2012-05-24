package dataspork.util;

public class BubbleSort{

  public static double[] revSort(double[] a) {
    return sort(a,false);
  }

  public static double[] sort(double[] a) {
    return sort(a,true);
  } 

  public static double[] sort(double[] a, boolean forward) {
    int n=a.length;
    double temp;
    for (int i=0; i<n; i++) {
      for (int j=n-1; j>i; j--) {
        if (forward ^ a[j-1]<a[j]) {
          temp=a[j];
          a[j]=a[j-1];
          a[j-1]=temp;
        }
      }
    }
    return a;
  }

}
