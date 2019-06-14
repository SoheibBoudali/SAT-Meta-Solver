import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class satSolver {

	cnfReader cnf;
	File f;

	public satSolver(cnfReader cnf, File f) {
		super();
		this.cnf = cnf;
		this.f = f;
	}

	public cnfReader getCnf() {
		return cnf;
	}

	public void setCnf(cnfReader cnf) {
		this.cnf = cnf;
	}

	public File getF() {
		return f;
	}

	public void setF(File f) {
		this.f = f;
	}
    // Genere une solution Aleatoire
	public int[] generateSolution(int numVar) {

		int[] solution = new int[numVar];
		Set<Integer> values = new HashSet<Integer>();
		for (int i = -numVar; i <= numVar; i++)
			values.add(i);

		while (solution.length != numVar) {

			int randNum = ThreadLocalRandom.current().nextInt(-numVar, numVar);
			if (values.contains(randNum) || values.contains(-randNum)) {
				values.remove(randNum);
				values.remove(-randNum);
				solution[Math.abs(randNum)] = randNum;
			}

		}
		return solution;

	}
	
	public boolean verifySolution() {

		boolean cls = true;
		int i = 0, j = 0;
		int[][] matrice = cnf.getMatClauses();
		int[] solution = generateSolution(this.cnf.getNumVar());
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

}
