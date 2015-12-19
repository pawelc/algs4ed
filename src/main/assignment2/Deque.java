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
public class Deque<Item> implements Iterable<Item> {
    /**
     * Number of elements.
     */
    private int size;

    /**
     * first node.
     */
    private Node<Item> first;

    /**
     * last node.
     */
    private Node<Item> last;

    /**
     * construct an empty deque.
     */
    public Deque() {
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
     * insert the item at the front.
     * @param item to add
     */
    public void addFirst(final Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        Node<Item> prevFirst = first;
        first = new Node<Item>(item, prevFirst, null);
        if (prevFirst != null) {
            prevFirst.previous = first;
        }
        if (last == null) {
           last = first;
        }
        size++;
    }

    /**
     * insert the item at the end.
     *
     * @param item to add
     */
    public void addLast(final Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        Node<Item> prevLast = last;
        last = new Node<Item>(item, null, prevLast);
        if (prevLast != null) {
            prevLast.next = last;
        }
        if (first == null) {
            first = last;
        }
        size++;
    }

    /**
     * delete and return the item at the front.
     *
     * @return first element of deck
     */
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = first.item;
        if (first.next != null) {
            first = first.next;
            first.previous = null;
        } else {
            first = null;
            last = null;
        }
        size--;
        return item;
    }

    /**
     * delete and return the item at the end.
     *
     * @return last element of deck
     */
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = last.item;
        if (last.previous != null) {
            last = last.previous;
            last.next = null;
        } else {
            first = null;
            last = null;
        }
        size--;
        return item;
    }

    /**
     * return an iterator over items in order from front to end.
     * @return iterator
     */
    public Iterator<Item> iterator() {
        return new FrontToEndIterator();
    }

    /**
     * Iterator for the deck.
     * @author pawelc
     *
     */
    private class FrontToEndIterator implements Iterator<Item> {
        /**
         * Current node.
         */
        private Node<Item> current = first;

        /**
         * has next element?
         * @return if has next element.
         */
        public boolean hasNext() {
            return current != null;
        }

        /**
         * Gets next item.
         * @return next item.
         */
        public Item next() {
            if (current == null) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        /**
         * Removed current item.
         */
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Node of the deck.
     * @author pawelc
     *
     * @param <NI>
     */
    private class Node<NI> {

        /**
         * Next element.
         */
        private Node<NI> next;
        /**
         * previous node.
         */
        private Node<NI> previous;
        /**
         * item.
         */
        private NI item;

        /**
         * Create node.
         * @param i item
         * @param n next node
         * @param p previous node
         */
        public Node(final NI i, final Node<NI> n, final Node<NI> p) {
            this.item = i;
            this.next = n;
            this.previous = p;
        }
    }

    /**
     * Test method.
     * @param args command line arguments.
     */
    public static void main(final String[] args) {
        Deque<Integer> d = new Deque<Integer>();
        d.addFirst(1);
        d.addFirst(2);
    }
}
