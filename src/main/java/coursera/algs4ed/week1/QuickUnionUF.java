package coursera.algs4ed.week1;

public class QuickUnionUF {
	private int[] id; 

	public QuickUnionUF(int size) {
		super();
		this.id = new int[size];
		for (int i = 0; i < id.length; i++) {
			id[i]=i;
		}
	}
	
	private int root(int n){
		while(n != id[n]){
			n=id[n];
		}
		return n;
	}
	
	public void union(int p, int q){
		int rp = root(p);
		int rq = root(q);
		if(rp != rq){
			id[rp]=rq;
		}
	}
	
	public boolean connected(int p, int q){
		return root(p) == root(q);
	}
	
	/**
	 * Number of components
	 * @return
	 */
	private int count(){
		return 0;
	}
}
