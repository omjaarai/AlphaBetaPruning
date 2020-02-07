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
        
        for(int i = 2; i < x; i++) {
            if (x % i != 0) return true;
        }
        return false;
    }

    /**
    * This method is used to get the largest prime factor 
    * @param x A positive integer number
    * @return int The largest prime factor of x
    */
    public static int getLargestPrimeFactor(int x) {

        for(int i = x-1; i >=1; i--) {
            if (isPrime(i) && (x % i) == 0 ) return i;            
        }
        return -1;

    }
}