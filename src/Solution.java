import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class Solution {
	
	ArrayList<Integer> solution;
	
	public Solution() {
		this.solution = new ArrayList<Integer>();
	}
	
	public Solution(int numVar) {
		this.solution = new ArrayList<Integer>();
		for (int i = 0; i < numVar; i++) {
			this.solution.add(0);
		}
	}
	
	public Solution(ArrayList<Integer> solution) {
		super();
		this.solution = solution;

	}
	
	public void replaceLitteral(int value, int litteralPosition) {
		this.solution.set(litteralPosition, value);
	}
	
	public ArrayList<Integer> getActivatedLitterals(){
		ArrayList<Integer> activated = new ArrayList<Integer>();
		for(int i : this.getSolution()){
			if (i!=0) activated.add(i);
		}
		return activated;
	}
	
	public ArrayList<Integer> getSolution() {
		return solution;
	}

	public void setSolution(ArrayList<Integer> solution) {
		this.solution = solution;
	}

	public int[] generateSolution(int numVar) {

		int[] solution = new int[numVar];
		Set<Integer> values = new HashSet<Integer>();
		for (int i = -numVar; i <= numVar; i++)
			values.add(i);
		int i=0;
		while (i < numVar) {

			int randNum = ThreadLocalRandom.current().nextInt(-numVar, numVar);
			if ((values.contains(randNum) || values.contains(-randNum)) && randNum!=0) {
				values.remove(randNum);
				values.remove(-randNum);
				solution[Math.abs(randNum)-1] = randNum;
				i++;
			}
			
			

		}
		return solution;

	}

	public boolean verifySolution(int [][] matClauses) {
		
		int [] solution = convertSol(this.solution);
		boolean cls = true;
		int i = 0, j = 0;
		int[][] matrice = matClauses;
		int numSat = matClauses.length;
		int numClauses = matClauses[0].length;
		
		while (i < numSat-1 && cls) {
			boolean satisfiable = false;
			
			while (j < numClauses-1 && !satisfiable) {
				int index = Math.abs(matrice[i][j])-1;
				if ( matrice[i][j] == solution[index])
					satisfiable = true;
				else j++;
			}

			if (satisfiable)
				i++;
			else cls = false;
		}
		
		if (cls)
			System.out.println("SAT");
		else
			System.out.println("UNSAT");
		return cls;
	}
	
	// Compute number of satisifed clauses for the solution
		public int nbrSatisfiedClauses(int [][] matClauses) {
			
			int [] solution = convertSol(this.solution);
			int nbrSatisfied=0;
			int[][] matrice = matClauses;
			int numSat = matClauses.length;
			int numClauses = matClauses[0].length;
			
			
			for(int i=0; i<=numSat-1;i++) {
			 for (int j=0; j<=numClauses-1;j++) {
					
					int index = Math.abs(matrice[i][j])-1;
					if (matrice[i][j] == solution[index]) {
						nbrSatisfied++;
						break;
					}
				}
			}
			return nbrSatisfied;
		}

	public int [] convertSol(ArrayList<Integer> solution) {
		int[] sol = new int[solution.size()];
		for(int i = 0; i < solution.size(); i++) {
		    sol[i] = solution.get(i);
		}
		return sol;
	}
	
	public ArrayList<Integer> convertSol(int [] solution) {
		ArrayList<Integer> sol = new ArrayList<Integer>();
		for(int i = 0; i < solution.length; i++) {
		    sol.add(solution[i]);
		}
		return sol;
	}

	@Override
	public String toString() {
		return "Solution [solution=" + solution + "]";
	}



}
