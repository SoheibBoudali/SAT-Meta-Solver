import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Meta {

	public static void main(String[] args) {
		
		cnfReader c = new cnfReader(0,0);
		int k = 01;
		File f = new File("C:\\Users\\ASUS X541U\\Desktop\\keh\\uf20-01.cnf");
		int [][] matClauses = c.cnfRead(f);
		int nbrLitteraux = 20;
		int taillePop = 10;
		int mutationRate = 10;
		int crossoverRate = 70;
		
		PopulationGenetic pop = new PopulationGenetic(nbrLitteraux, matClauses, taillePop, mutationRate);

		
		pop.geneticAlgorithm(matClauses, taillePop, crossoverRate, 60, nbrLitteraux);
		
		
		/*
		ArrayList<Integer> solution = new ArrayList<Integer>(Arrays.asList(1,2,3));
		int [][] matrice = new int [][] {{1,2},{-2,3}};
		Solution sol = new Solution(solution);
		System.out.println(sol.nbrSatisfiedClauses(matrice));
		sol.verifySolution(matrice);
		
		satSolver sat = new satSolver(c,f);
		System.out.println(sat.generateSolution(20));
		sat.verifySolution();
	*/
		
		
	}

}
