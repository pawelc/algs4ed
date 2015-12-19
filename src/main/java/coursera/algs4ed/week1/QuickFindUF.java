package coursera.algs4ed.week1;

public class QuickFindUF {
	int[] id; 

	public QuickFindUF(int size) {
		super();
		this.id = new int[size];
		for (int i = 0; i < id.length; i++) {
			id[i]=i;
		}
	}
	
	public void union(int p, int q){
		int qComp = find(q);
		int pComp = find(p);
		if (pComp != qComp) { 
			for (int i = 0; i < id.length; i++) {
				if(id[i]==pComp){
					id[i]=qComp;
				}
			}
		}
	}
	
	public boolean connected(int p, int q){
		return find(p) == find(q);
	}
	
	private int find(int p){
		return id[p];
	}
	
	/**
	 * Number of components
	 * @return
	 */
	private int count(){
		return 0;
	}
}
