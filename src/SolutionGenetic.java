import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SolutionGenetic extends Solution implements Comparable <SolutionGenetic> {
	
	int [][] matClauses;
	int [] fitnessValues;
	int [] mutationValues;
	int solutionFitnessValue;
	int averageMutationValue;
	
	
	public SolutionGenetic() {
		
		this.solution = new ArrayList<Integer>();
		
	}
	
	public SolutionGenetic(int numVar, int [][] matClauses) {
		this.solution = new ArrayList<Integer>();
		this.matClauses=matClauses;
		this.solution = this.convertSol(this.generateSolution(numVar));
		this.solutionFitnessValue = this.getSolutionFitnessValue();
	}
	
	public SolutionGenetic(ArrayList<Integer> solution) {
		this.solution = solution;
	}
	
	public SolutionGenetic(ArrayList<Integer> solution, int [][] matClauses) {
		
		this.solution = solution;
		this.matClauses=matClauses;
		initFitnessValues();
		this.solutionFitnessValue = this.getSolutionFitnessValue();

	}
	
	public SolutionGenetic(int [][] matClauses) {
		
		this.matClauses = matClauses;
		this.solution = new ArrayList<Integer>();
		
	}
	
	public SolutionGenetic(int[] fitnessValues, int mutationRateValue, int [][] matClauses) {
		
		this.fitnessValues = fitnessValues;
		this.matClauses = matClauses;
		this.solutionFitnessValue = this.getSolutionFitnessValue();
	}

	public int getSumFitnessValue() {
		return Arrays.stream(this.fitnessValues).sum();
	}
	
	public int getSolutionFitnessValue() {
		return nbrSatisfiedClauses(this.matClauses);
	}
	
	// Initialize fitness value of the solution
		public void initSolutionFitnessValue() {
			this.solutionFitnessValue = this.getSolutionFitnessValue();
		}
		
	// Initialize fitness values for each gene "Calculated according to the number of satisfied clauses"
	public void initFitnessValues() {
		for (int i = 0; i < this.fitnessValues.length; i++) {
			this.fitnessValues[i] = nbrSatisfiedClauses(i, this.matClauses);
		}
	}
	
	// Compute number of satisifed clauses for a single gene of the solution
	public int nbrSatisfiedClauses(int element, int [][] matClauses) {
		return 0;
	}
	
	
	
	/* Perform mutation at random position to solution (ie change to -value) */
	public void mutate(int gene) {
		
		if(gene==0) {int mutation = (-1)*this.solution.get(gene); this.solution.set(Math.abs(gene), mutation);}
		else {int mut = (-1)*this.solution.get(gene-1); this.solution.set(Math.abs(gene-1), mut);}
		
		this.solutionFitnessValue = this.getSolutionFitnessValue();
	}
	
	
	public int[] getFitnessValues() {
		return fitnessValues;
	}

	public void setFitnessValues(int[] fitnessValues) {
		this.fitnessValues = fitnessValues;
	}

	public int[] getMutationValues() {
		return mutationValues;
	}

	public void setMutationValues(int[] mutationValues) {
		this.mutationValues = mutationValues;
	}

	public int getAverageMutationValue() {
		return averageMutationValue;
	}

	public void setAverageMutationValue(int averageMutationValue) {
		this.averageMutationValue = averageMutationValue;
	}

	public void setSolutionFitnessValue(int solutionFitnessValue) {
		this.solutionFitnessValue = solutionFitnessValue;
	}
	

	@Override
	public int compareTo(SolutionGenetic slg) {
		return Double.compare(this.getSolutionFitnessValue(), slg.getSolutionFitnessValue());	
	}

	@Override
	public String toString() {
		return "SolutionGenetic [" + solution +"solutionFitnessValue=" + solutionFitnessValue 
				+ "]\n";
	}
	
	public void setSolution(ArrayList<Integer> solution) {
		this.solution = solution;
		this.solutionFitnessValue = this.getSolutionFitnessValue();
	}
	
	public void copy(SolutionGenetic sol) {
		this.getSolution().clear();
		this.getSolution().addAll(sol.getSolution());
		this.initSolutionFitnessValue();
	}
	
}
