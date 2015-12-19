

import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;

public class Exercise {
	/**
	 * Download http://coursera.cs.princeton.edu/algs4/Timing.class

Using the value 164767 for the seed, determine the order of growth of the running time
of the method call Timing.trial(N, seed) as a function of N. Assume that the running time obeys
a power law T(N) ~ a N^b. For your answer, enter the constant b with two digits after the decimal
separator, e.g., 2.34.
	 */
	@Test
	public void testQuestion1() {
		StopWatch st = new StopWatch();
		long prev = 0;
		for (int i = 10; i <= 10000; i*=2) {
			st.start();
//			Timing.trial(i, 193157);
			st.stop();
			System.out.print(i + ": ");
			System.out.println(st.getTime());
			if(prev!= 0){
				System.out.println("ratio: "+ ((double)st.getTime()/prev));
			}
			prev = st.getTime();
			st.reset();
		}
	}
}
