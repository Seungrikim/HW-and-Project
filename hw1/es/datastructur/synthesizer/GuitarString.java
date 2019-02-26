package es.datastructur.synthesizer;
import java.util.Random;
import java.util.HashSet;
import java.util.Set;

//Note: This file will not compile until you complete task 1 (BoundedQueue).
public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    private BoundedQueue<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        // TODO: Create a buffer with capacity = SR / frequency. You'll need to
        //       cast the result of this division operation into an int. For
        //       better accuracy, use the Math.round() function before casting.
        //       Your buffer should be initially filled with zeros.
        int size = (int) Math.round(SR / frequency);
        buffer = new ArrayRingBuffer<>(size);
        for (int i = 0; i < size; i++) {
            buffer.enqueue(0.0);
        }
    }

    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        // TODO: Dequeue everything in buffer, and replace with random numbers
        //       between -0.5 and 0.5. You can get such a number by using:
        //       double r = Math.random() - 0.5;
        //
        //       Make sure that your random numbers are different from each
        //       other.

        /** @source this check the duplication of random number method from
        stackoverflow */
        //Set<Double> checkSet = new HashSet<Double>();
        //Random rnd = new Random();
        //double nonDuplicated = Math.random() - 0.5;
        //double nonDuplicated = rnd.nextDouble() - 0.5;
        //nonDuplicated = (int) (Math.random() * 7);
        /*while (!checkSet.contains(nonDuplicated) && ) {
            checkSet.add(nonDuplicated);
        }*/
        for (int i = 0; i < buffer.capacity(); i++) {
            double nonDuplicated = Math.random() - 0.5;
            buffer.enqueue(nonDuplicated);
        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        // TODO: Dequeue the front sample and enqueue a new sample that is
        //       the average of the two multiplied by the DECAY factor.
        //       Do not call StdAudio.play().
        double newDouble = (buffer.dequeue() + buffer.peek()) / 2 * DECAY;
        buffer.enqueue(newDouble);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        return buffer.peek();
    }
}
    // TODO: Remove all comments that say TODO when you're done.
