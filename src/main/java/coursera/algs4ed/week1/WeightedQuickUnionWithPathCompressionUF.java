package coursera.algs4ed.week1;

public class WeightedQuickUnionWithPathCompressionUF {
	int[] id; 
	private int[] sz;

	public WeightedQuickUnionWithPathCompressionUF(int size) {
		super();
		this.id = new int[size];
		this.sz = new int[size];
		for (int i = 0; i < id.length; i++) {
			id[i]=i;
			sz[i]=1;
		}
	}
	
	private int root(int n){
		while(n != id[n]){
			id[n]=id[id[n]];
			n=id[n];
		}
		return n;
	}
	
	public void union(int p, int q){
		int rp = root(p);
		int rq = root(q);
		if(sz[rp] < sz[rq]){
			id[rp]=rq;
			sz[rq]+=sz[rp];
		}else{
			id[rq]=rp;
			sz[rp]+=sz[rq];
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
