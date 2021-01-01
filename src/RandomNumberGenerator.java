import java.util.Random;

public final class RandomNumberGenerator {

    static synchronized int randomNumberGenerator(int low, int high){
        Random r = new Random();
        return (r.nextInt(high-low) + low);
    }
}
