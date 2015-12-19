package coursera.algs4ed.week1;

import static org.junit.Assert.*;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;


public class QuickFindUFTest {

	/*
	 * (seed = 249270)
Give the id[] array that results from the following sequence of union operations
on a set of 10 items using the quick-find algorithm.

    0-8 9-0 4-0 5-1 9-7 9-6 

Recall: our quick-find convention for the union operation p-q is to change id[p]
(and perhaps some other entries) but not id[q].
	 */
	@Test
	public void testQuestion1() {
		QuickFindUF uf = new QuickFindUF(10);
		uf.union(4, 7);
		uf.union(0, 3);
		uf.union(2, 9);
		uf.union(0, 2);
		uf.union(2, 7);
		uf.union(9, 1);
		
		System.out.println(StringUtils.join( ArrayUtils.toObject(uf.id)," "));
	}
}
