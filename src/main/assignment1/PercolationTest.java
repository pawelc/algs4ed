
public class PercolationTest {
    public static void main(String[] args) {
        Percolation p = new Percolation(2);
        System.out.println(p.percolates());
        p.open(1, 1);
        System.out.println(p.percolates());
        p.open(2, 1);
        System.out.println(p.percolates());
    }
}
