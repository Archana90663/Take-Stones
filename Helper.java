public class Helper {

  /**
   * Class constructor.
   */
  private Helper () {}

  /**
   * This method is used to check if a number is prime or not
   * @param x A positive integer number
   * @return boolean True if x is prime; Otherwise, false
   */
  public static boolean isPrime(int x) {
    if(x%2==0) {
      return false;
    }

    return true;
  }

  /**
   * This method is used to get the largest prime factor
   * @param x A positive integer number
   * @return int The largest prime factor of x
   */
  public static int getLargestPrimeFactor(int x) {
    int max=0;
    while(x%2==0) {
      max = 2;
      x/=2;
    }
    for(int i=3; i<=Math.sqrt(x); i+=2) {
      while(x%i==0) {
        max=i;
        x=x/i;
      }
    }
    if(x>2) {
      max = x;
      return max;
    }


    return -1;

  }
}