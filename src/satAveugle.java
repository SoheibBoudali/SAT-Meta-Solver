
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

public class satAveugle extends satSolver {
	
	cnfReader cnf;
	File f;

	public satAveugle(cnfReader cnf, File f) {
		super(cnf, f);
		// TODO Auto-generated constructor stub
	}
	/*
	public ArrayList<Integer> generateSolProfondeur(int litteral) {
		Set<Integer> litteraux = new HashSet<Integer>();
		Stack<Integer> open = new Stack<Integer>();
		List<Integer> closed = new LinkedList<Integer>();
		
		boolean found = false;
		int numVar = this.cnf.getNumVar();
		int lit=0;
		int actual =0;
		Solution solution = new Solution();
		do {
			if(solution.getActivatedLitterals().size()<numVar){
				int randNum = ThreadLocalRandom.current().nextInt(-numVar, numVar); //
				if (litteraux.contains(randNum) || litteraux.contains(-randNum)) {
					litteraux.remove(randNum);
					litteraux.remove(-randNum);
				}
				open.push(-randNum);
				
				solution.replaceLitteral(-randNum, Math.abs(randNum));
				actual = -randNum;
				
			}
			
			else {
				if(solution.verifySolution()) found=true;
				else {
					closed.add(actual);
					lit = open.pop();
					solution.replaceLitteral(-lit, Math.abs(lit));
					actual = open.peek();
					
					while (actual != closed.peek()) {
						
						a = closed.pop
						open.push(a);						
					}
					
				}
			}
		} while (!open.isEmpty() && !found);
		
		
		
	

		return solution;
	}

	public int extractSatClauses(int litteral, int[][] matClauses) {
		
		int[] clausesSatisfiables;
		for(int i=0; i<20;i++) {		
			if (satisfiable(matClauses[i],litteral)) {
				clausesSatisfiables.add(matClauses[i]);
			}
		return clausesSatisfiables;
	}
	*/
	public boolean satisfiable(int[] clause, int litteral) {
		return true;
	}
	
	public boolean verifySolution(int [] solution) {

		boolean cls = true;
		int i = 0, j = 0;
		int[][] matrice = cnf.getMatClauses();
		this.cnf.cnfRead(this.f);

		while (i < this.cnf.getNumClauses() && cls == true) {
			boolean c = true;

			while (j < this.cnf.getNumSat() && c == true) {
				int index = Math.abs(matrice[i][j]);
				if ((index < 0 && solution[index] == 0) || (index > 0 && solution[index] > 1))
					c = false;
				j++;
			}

			if (c)
				cls = false;
			i++;
		}
		if (cls)
			System.out.println("SAT");
		else
			System.out.println("UNSAT");
		return cls;
	}
	

	
	
	
	/*
	public int [] generateSolutionDfs(int numVar) {
		int[] solution = new int[numVar];
		boolean found=false;
		int prof=0;
		Set<Integer> values = new HashSet<Integer>();
		//ArrayList<Integer> open = new ArrayList<Integer>();
		//ArrayList<Integer> closed = new ArrayList<Integer>();
		
		Stack<ArrayList<Integer>> open = new Stack<ArrayList<Integer>>(); 
		Stack<ArrayList<Integer>> closed = new Stack<ArrayList<Integer>>(); 
		
		
		for (int i = -numVar; i <= numVar; i++)
			values.add(i);

		for (int t = -numVar; t <= numVar; t++) {
			values.remove(t);
			values.remove(-t);
			open.push(Arrays.asList[t]);
			while (!open.isEmpty() && !found ) {
				ArrayList<Integer> n = open.pop();
				if (!values.isEmpty()) {
					for (int suc : values)
						open.push(suc);
						if (verifySolution())

				}
					
			}
			
		}*/

}
