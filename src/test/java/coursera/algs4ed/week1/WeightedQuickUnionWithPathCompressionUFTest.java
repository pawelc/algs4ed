package coursera.algs4ed.week1;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;


public class WeightedQuickUnionWithPathCompressionUFTest {

	/**
	 * (seed = 746012)
Give the id[] array that results from the following sequence of union operations
on a set of 10 items using the weighted quick-union algorithm.

    8-0 1-7 5-9 3-8 7-9 0-6 4-8 4-1 8-2 

Recall: when joining two trees of equal size, the algorithm makes the root of
the second tree point to the root of the first tree.
	 */
	@Test
	public void testQuestion2() {
		WeightedQuickUnionWithPathCompressionUF uf = new WeightedQuickUnionWithPathCompressionUF(10);
		uf.union(2,7);
		System.out.println(StringUtils.join( ArrayUtils.toObject(uf.id)," "));
		uf.union(9,0);
		System.out.println(StringUtils.join( ArrayUtils.toObject(uf.id)," "));
		uf.union(6,8);
		System.out.println(StringUtils.join( ArrayUtils.toObject(uf.id)," "));
		uf.union(0,3);
		System.out.println(StringUtils.join( ArrayUtils.toObject(uf.id)," "));
		uf.union(9,1);
		System.out.println(StringUtils.join( ArrayUtils.toObject(uf.id)," "));
		uf.union(2,6);
		System.out.println(StringUtils.join( ArrayUtils.toObject(uf.id)," "));
		uf.union(5,4);
		System.out.println(StringUtils.join( ArrayUtils.toObject(uf.id)," "));
		uf.union(0,5);
		System.out.println(StringUtils.join( ArrayUtils.toObject(uf.id)," "));
		uf.union(6,4);
		System.out.println(StringUtils.join( ArrayUtils.toObject(uf.id)," "));
	}

}
