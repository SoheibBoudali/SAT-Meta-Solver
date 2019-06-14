import java.util.ArrayList;
import java.util.Collections;

public class PopulationPso {

	ArrayList<SolutionPso> population;
	int taillePopulation;
	int w;
	int c1;
	int c2;
	int pBest;
	SolutionPso gBest;
	
	
	public PopulationPso() {
		super();
	}



	public PopulationPso(int nbrLitteraux, int maxVelocity, int pBest, int w, int c1, int c2, int taillePop) {
		super();
		
		this.population = new ArrayList<SolutionPso>();
		for (int i=0; i<taillePop; i++) {
			SolutionPso gensol = new SolutionPso(nbrLitteraux, maxVelocity); // Fitness values are computed here
			this.population.add(gensol);
		}
		this.taillePopulation=taillePop;
		this.w = w;
		this.c1 = c1;
		this.c2 = c2;
		this.pBest=pBest;
	}



	public SolutionPso psoAlgorithm(int maxVelocity, int numVar) {
		
		PopulationPso randomPopulation = this;
		
		do {
		// We determine pBest & gBest
		/*for (SolutionPso slg : randomPopulation.getPopulation()) 
			if(slg.getFitnessValue() > pBest) {
				pBest=slg.getFitnessValue();
				gBest = slg;
			}*/
		Collections.sort(randomPopulation.getPopulation());
		int best = randomPopulation.getPopulation().size()-1;
		gBest = randomPopulation.getPopulation().get(best);
		pBest = randomPopulation.getPopulation().get(best).getFitnessValue();
		
		for (SolutionPso slg : randomPopulation.getPopulation()) {
				slg.updateVelocities(w,c1,c2, numVar);

			}
		
		
		}while(true);
	}



	public ArrayList<SolutionPso> getPopulation() {
		return population;
	}



	public void setPopulation(ArrayList<SolutionPso> population) {
		this.population = population;
	}



	public int getTaillePopulation() {
		return taillePopulation;
	}



	public void setTaillePopulation(int taillePopulation) {
		this.taillePopulation = taillePopulation;
	}



	public int getW() {
		return w;
	}



	public void setW(int w) {
		this.w = w;
	}



	public int getC1() {
		return c1;
	}



	public void setC1(int c1) {
		this.c1 = c1;
	}



	public int getC2() {
		return c2;
	}



	public void setC2(int c2) {
		this.c2 = c2;
	}
	
	
}
