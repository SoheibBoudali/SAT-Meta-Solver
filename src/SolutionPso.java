import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class SolutionPso extends Solution implements Comparable <SolutionPso> {
	
	double velocity;
	int fitnessValue;
	double [] velocityValues;
	double [] moveProbability;
	
	public SolutionPso(int numVar, int maxVelocity) {
		super();
		this.solution = this.convertSol(this.generateSolution(numVar));
		this.velocity = ThreadLocalRandom.current().nextInt(0,maxVelocity);
		initVelocities(numVar);
	}

	
	public void updateVelocities(int w, int c1, int c2, int numVar) {
		double rand1 = ThreadLocalRandom.current().nextDouble(0,1);
		double rand2 = ThreadLocalRandom.current().nextDouble(0,1);
		
		
		for (int i=0; i<numVar; i++) {
			velocityValues[i] = w*velocityValues[i];
		}
		
		this.velocity = w*this.velocity + rand1*c1 + rand2*c2; 
	}
	
	public void updatePosition(double movep) {
		
		for (int i=0; i<this.getSolution().size();i++) {
			if (movep < this.moveProbability[i] ) this.getSolution().set(i, i);
			else this.getSolution().set(i, -i);
		}
	}
	// INITS
	public void initVelocities(int numVar) {
		velocityValues = new double [numVar];
		for (int i=0; i<numVar; i++) {
			velocityValues[i]=ThreadLocalRandom.current().nextDouble(-4,4);
		}
	}
	
	public void initMoves(int numVar) {
		moveProbability = new double [numVar];
		for (int i=0; i<numVar; i++) {
			moveProbability[i]=sigmoid(velocityValues[i]);
		}
	}
	
	public double getVelocity() {
		return velocity;
	}

	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}

	public int getFitnessValue() {
		return fitnessValue;
	}

	public void setFitnessValue(int fitnessValue) {
		this.fitnessValue = fitnessValue;
	}
	
	@Override
	public int compareTo(SolutionPso slg) {
		return Double.compare(this.getFitnessValue(), slg.getFitnessValue());	
	}
	
	public double sigmoid(double x) {
		return 1/1+Math.exp(-x);
	}
	
}
