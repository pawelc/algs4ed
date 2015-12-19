import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A double-ended queue or deque (pronounced "deck") is a generalization of a
 * stack and a queue that supports inserting and removing items from either the
 * front or the back of the data structure.
 *
 * @author pawelc
 *
 * @param <Item>
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    /**
     * Initial size.
     */
    private static final int INITIAL_SIZE = 100;

    /**
     * Too big.
     */
    private static final int FRACTION_TO_BIG = 4;

    /**
     * Number of elements.
     */
    private int size;

    /**
     * items.
     */
    private Item[] items = (Item[]) new Object[INITIAL_SIZE];

    /**
     * construct an empty deque.
     */
    public RandomizedQueue() {
    }

    /**
     * is the deque empty?
     *
     * @return true if deck is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * @return size of the deck
     */
    public int size() {
        return size;
    }

    /**
     * add the item.
     * @param item to add
     */
    public void enqueue(final Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        ensureSpace(size + 1);
        items[size++] = item;
    }

    /**
     * resized items if necessary.
     * @param newSize new size.
     */
    private void ensureSpace(final int newSize) {
        if (newSize > items.length / 2) {
            Item[] newItems = (Item[]) new Object[items.length * 2];
            System.arraycopy(items, 0, newItems, 0, size);
            items = newItems;
        } else if (newSize < items.length / FRACTION_TO_BIG + 1) {
            Item[] newItems = (Item[]) new Object[(int) (items.length / 2)];
            System.arraycopy(items, 0, newItems, 0, size);
            items = newItems;
        }
    }

    /**
     * delete and return a random item.
     *
     * @return delete and return a random item
     */
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        final int index = StdRandom.uniform(size);
        final Item item = items[index];
        if (index + 1 < size) {
            System.arraycopy(items, index + 1, items, index, size - index - 1);
        }
        size--;
        ensureSpace(size);
        return item;
    }

    /**
     * @return (but do not delete) a random item.
     */
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return items[StdRandom.uniform(size)];
    }

    /**
     * return an iterator over items in order from front to end.
     * @return iterator
     */
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    /**
     * Iterator for the deck.
     * @author pawelc
     *
     */
    private class RandomIterator implements Iterator<Item> {
        /**
         * Items to iterate.
         */
        private Item[] iterItems = (Item[]) new Object[size];

        /**
         * Iterator index.
         */
        private int currentIndex = -1;

        /**
         * Randomly iterates.
         */
        public RandomIterator() {
            System.arraycopy(items, 0, iterItems, 0, size);
            StdRandom.shuffle(iterItems);
        }

        /**
         * has next element?
         * @return if has next element.
         */
        public boolean hasNext() {
            return currentIndex + 1 < iterItems.length;
        }

        /**
         * Gets next item.
         * @return next item.
         */
        public Item next() {
            if (currentIndex + 1 >= iterItems.length) {
                throw new NoSuchElementException();
            }
            return iterItems[++currentIndex];
        }

        /**
         * Removed current item.
         */
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Test method.
     * @param args command line arguments.
     */
    public static void main(final String[] args) {
        int size = Integer.parseInt(args[0]);
        RandomizedQueue<Integer> d = new RandomizedQueue<Integer>();
        for (int i = 0; i < size; i++) {
            d.enqueue(i);
        }
        long sum = 0;
        for (int i = 0; i < size; i++) {
            sum += d.dequeue();
        }
        StdOut.println(sum);
    }
}
