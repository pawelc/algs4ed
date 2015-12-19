
/**
 * takes a command-line integer k, reads in a sequence of N strings from standard
 * input using StdIn.readString(), and prints out exactly k of them, uniformly at random.
 * @author pawelc
 *
 */
public class Subset {

    /**
     * Utility class.
     */
    public Subset() {
    }

    /**
     *
     * @param args arguments
     */
    public static void main(final String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        String[] strings = StdIn.readStrings();
        for (int i = 0; i < strings.length; i++) {
            q.enqueue(strings[i]);
        }
        for (int i = 0; i < k; i++) {
            StdOut.println(q.dequeue());
        }
    }
}
