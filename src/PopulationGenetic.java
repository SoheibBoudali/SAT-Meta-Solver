import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PopulationGenetic  {
	
	ArrayList<SolutionGenetic> population;
	int taillePop;
	int mutationRate;
	int generation=0;
	
	public PopulationGenetic() {
		this.population = new ArrayList<SolutionGenetic>();
	}
	
	// Generate a set of solution randomly 
	// with random mutationRate & 
	public PopulationGenetic(int nbrLitteraux, int[][] matClauses, int taillePop, int mutationRate) {
		//  des solutions
		generation=0;
		this.population = new ArrayList<SolutionGenetic>();
		for (int i=0; i<taillePop; i++) {
			SolutionGenetic gensol = new SolutionGenetic(nbrLitteraux, matClauses); // Fitness values are computed here
			this.population.add(gensol);
		}
		
		this.taillePop=taillePop;
		this.mutationRate=mutationRate;

	}
	
	// Generate a population from elite solutions of older generation
	public PopulationGenetic(int nbrLitteraux, int[][] matClauses, int taillePop, int mutationRate, ArrayList<SolutionGenetic> elite) {
		
		generation=0;
		this.population = elite;
		this.taillePop=taillePop;
		this.mutationRate=mutationRate;

	}
	
	
	// Generate a new solution with literals equals to father from  to crossover point & to mother from crossover+1 to length of solution
	public SolutionGenetic offspring(SolutionGenetic pere, SolutionGenetic mere, int crossover, int [][] matClauses, String crossOverType) {
		SolutionGenetic croisement = new SolutionGenetic(pere.getSolution().size(), matClauses);
		for (int i=0; i<pere.getSolution().size();i++) {
			if (crossOverType.equals("One Point")) {
				if (i<=crossover) croisement.getSolution().set(i,pere.getSolution().get(i));
				else croisement.getSolution().set(i,mere.getSolution().get(i));
			}
			if (crossOverType.equals("Uniform")) {
				int chosen = ThreadLocalRandom.current().nextInt(0, 2);
				if (chosen==1) croisement.getSolution().set(i,pere.getSolution().get(i));
				else croisement.getSolution().set(i,mere.getSolution().get(i));
			}
			
		}
		croisement.initSolutionFitnessValue();
		return croisement;
	}
	
	// Genetic Algorithm 
	public SolutionGenetic geneticAlgorithm(int [][] matClauses,int populationNumber, int crossoverRate, long execTime, int nbrLitteraux) {
		
		generation=0;
		boolean found = false;
		long startTime = System.currentTimeMillis();
		int last_fitness=0, last_generation=0;
		
		//PopulationGenetic randomPopulation = new PopulationGenetic(nbrLitteraux,matClauses,populationNumber,this.mutationRate);
		PopulationGenetic randomPopulation = this;
		long execTimeMillis = (long) (execTime*1000F);
		//System.out.println("taille"+randomPopulation.getPopulation().size());
		
		// We check if the solution is in the random pop 
		for (SolutionGenetic slg : randomPopulation.getPopulation()) if(slg.verifySolution(matClauses)) return slg;
		SolutionGenetic bestSolution = new SolutionGenetic();
		
		while(!found) {
			
			Collections.sort(randomPopulation.getPopulation()); // Sort Solutions by fitness values
		
			int half = randomPopulation.getPopulation().size()/2;
			int end =  randomPopulation.getPopulation().size();
			bestSolution = randomPopulation.getPopulation().get(end-1);

			
			System.out.println("Best : "+bestSolution);
			System.out.println("Satisf :"+bestSolution.getSolutionFitnessValue());
			System.out.println("GENERATION "+generation);
			System.out.println("---------------------------------");
			
			if((System.currentTimeMillis() - startTime) >= execTimeMillis )
				break;
			
			
			SolutionGenetic father = randomPopulation.getPopulation().get(ThreadLocalRandom.current().nextInt(0,end));	
			SolutionGenetic mother = randomPopulation.getPopulation().get(ThreadLocalRandom.current().nextInt(0,end));
			
			//System.out.println("fitness "+bestSolution.getSolutionFitnessValue());
			if (bestSolution.getSolutionFitnessValue()==matClauses.length) found=true;
			
			
			int randomMutationRate = ThreadLocalRandom.current().nextInt(0, 100);
			int randomMutationGene = ThreadLocalRandom.current().nextInt(0, nbrLitteraux);
			
			int randomCrossoverRate = ThreadLocalRandom.current().nextInt(0, 100);
			int crossoverPoint = ThreadLocalRandom.current().nextInt(0, nbrLitteraux);
			String crossoverType = "Uniform";
			
			
			SolutionGenetic child = new SolutionGenetic(nbrLitteraux,matClauses);
			
			
			if(randomCrossoverRate <= crossoverRate) {			
				child = offspring(father,mother,10,matClauses, crossoverType);
				
			}
			
			
			if(randomMutationRate <= mutationRate) {
				
				child.mutate(randomMutationGene);
			}
			
			
			
			
			// We re-compute fitness values for the child
			child.initSolutionFitnessValue();
			
			
			
			if (child.compareTo(father) > 0)  {father.copy(child); }
			else if (child.compareTo(mother) > 0) {mother.copy(child);}
			
			
			// Deal with stagnation
			int pas = 500;
			if (generation == pas) {
				last_fitness = bestSolution.getSolutionFitnessValue();
				last_generation = pas ;
			}else if (generation-last_generation == pas) {
				
				if(last_fitness==bestSolution.getSolutionFitnessValue()) {
					System.out.println("---------*******--------");
					
					int mutationGene = ThreadLocalRandom.current().nextInt(0, nbrLitteraux);
					
					SolutionGenetic mutant =  new SolutionGenetic(nbrLitteraux,matClauses);
					mutant.getSolution().clear();
					mutant.getSolution().addAll(bestSolution.getSolution());
					
					
					mutant.mutate(mutationGene);
					
					System.out.println("Satisf M:"+mutant.getSolutionFitnessValue());
					System.out.println("Satisf B:"+bestSolution.getSolutionFitnessValue());
					
					
					if (mutant.getSolutionFitnessValue()>=bestSolution.getSolutionFitnessValue()) {
						System.out.println("*********M*********"+mutant.getSolutionFitnessValue());
						bestSolution=mutant;
					}
						
				}
				last_fitness = bestSolution.getSolutionFitnessValue();
				last_generation = generation;
			}
			
			
				
			generation++;
			
			
			
			
			
		}
		System.out.println(bestSolution);
		return bestSolution;
	}
	
	
	// Genetic Paper Algorithm 
		public SolutionGenetic geneticPaperAlgorithm(int [][] matClauses,int populationNumber, int crossoverRate, int nbrLitteraux, int elitismRate, long execTime) {
			generation=0;
			boolean found = false;
			long startTime = System.currentTimeMillis();
			int maxFitness = 0;
			

			PopulationGenetic randomPopulation = new PopulationGenetic(nbrLitteraux,matClauses,populationNumber,this.mutationRate);
			long execTimeMillis = (long) (execTime*1000F);

			int half = randomPopulation.getPopulation().size()/2;
			int end =  randomPopulation.getPopulation().size();
			
			// We check if the solution is in the random pop 
			for (SolutionGenetic slg : randomPopulation.getPopulation()) {
				if(slg.verifySolution(matClauses)) return slg;
				else if(slg.getSolutionFitnessValue()>maxFitness) {
					maxFitness = slg.getSolutionFitnessValue();
				}
			}
			
			SolutionGenetic bestSolution = new SolutionGenetic();
			
			Collections.sort(randomPopulation.getPopulation()); // Sort Solutions by fitness values
			
			// Extract best top best solutions
			ArrayList<SolutionGenetic> eliteSolutions = new ArrayList<SolutionGenetic> ();
			for (int i=end-1; i>=elitismRate*populationNumber; i--) {
				eliteSolutions.add(randomPopulation.getPopulation().get(i));
			}
			PopulationGenetic eliteGenerationPopulation = new PopulationGenetic(nbrLitteraux,matClauses,populationNumber,this.mutationRate,eliteSolutions);
			
			while(!found) {
				
				
				
				bestSolution = randomPopulation.getPopulation().get(end-1);

				
				System.out.println("Best : "+bestSolution);
				System.out.println("Satisf :"+bestSolution.getSolutionFitnessValue());
				System.out.println("GENERATION "+generation);
				System.out.println("---------------------------------");
				
				if((System.currentTimeMillis() - startTime) >= execTimeMillis)
					break;
				
				
				SolutionGenetic father = randomPopulation.getPopulation().get(0);	
				SolutionGenetic mother = randomPopulation.getPopulation().get(ThreadLocalRandom.current().nextInt(0,end));
				

				bestSolution = randomPopulation.getPopulation().get(end-1);
				System.out.println("fitness "+bestSolution.getSolutionFitnessValue());
				
				if (bestSolution.getSolutionFitnessValue()==matClauses.length) found=true;
				
				int randomCrossoverRate = ThreadLocalRandom.current().nextInt(0, 100);
				int randomMutationRate = ThreadLocalRandom.current().nextInt(0, 100);
				
				int randomMutationGene = ThreadLocalRandom.current().nextInt(0, nbrLitteraux);
				String crossoverType = "Uniform";
				int crossoverPoint = ThreadLocalRandom.current().nextInt(0, nbrLitteraux-1);
				
				//System.out.println("crossRate"+randomCrossoverRate);
				//System.out.println("mutRate"+randomMutationGene);
				
				SolutionGenetic child = new SolutionGenetic(nbrLitteraux,matClauses);
				//System.out.println("pere"+father);
				//System.out.println("mom"+mother);
				
				ArrayList<SolutionGenetic> childs = new ArrayList<SolutionGenetic>();
				
				if(randomCrossoverRate <= crossoverRate) {
					childs.add(offspring(father,mother,crossoverPoint,matClauses,crossoverType));
					childs.add(offspring(mother,father,crossoverPoint,matClauses,crossoverType));
				}
				
				if(randomMutationRate <= mutationRate) {
					for(SolutionGenetic child1 : childs) child1.mutate(randomMutationGene);
				}
				
				
				
				// We re-compute fitness values for the childs
				for(SolutionGenetic child1 : childs) child1.initSolutionFitnessValue();
				
				//System.out.println("fils"+child);
				for(SolutionGenetic child1 : childs) {
					if (child1.compareTo(father) > 0)  {this.getPopulation().set(this.getPopulation().indexOf(father), child1); }
					else if (child1.compareTo(mother) > 0) {this.getPopulation().set(this.getPopulation().indexOf(mother), child1);}
				}
				
				generation++;
				
			}
			System.out.println(bestSolution);
			return bestSolution;
		}
	

	public ArrayList<SolutionGenetic> getPopulation() {
		return population;
	}

	public void setPopulation(ArrayList<SolutionGenetic> population) {
		this.population = population;
	}

	public int getMutationRate() {
		return mutationRate;
	}

	public void setMutationRate(int mutationRate) {
		this.mutationRate = mutationRate;
	}

	public int getGeneration() {
		return generation;
	}

	public void setGeneration(int generation) {
		this.generation = generation;
	}
	
	public ArrayList<Integer> inexistantLiterals(int nbrLitterals, int [][] matClauses){
		ArrayList<Integer> inexistants = new ArrayList<Integer>();
		for (int i = -nbrLitterals; i< 0; i++) {
			for(SolutionGenetic slg : this.getPopulation()) {
				
				for (int j=0; j<this.getPopulation().size()-1; j++) {
					int k = slg.getSolution().get(Math.abs(j));
					if(k==i) {
						if (slg.nbrSatisfiedClauses(-i, matClauses)!=0) inexistants.add(-i);
						
					}
					else if (inexistants.contains(k)) inexistants.remove(k);	
					}
				
			}
		}
		return inexistants;
	}
	
	
}
