import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern; 

public class cnfReader {
	private int numClauses;
	private int numVar;
	private int numSat;
	private int matClauses [][];
	

	public cnfReader(int numClauses, int numVar) {
		super();
		this.numClauses = numClauses;
		this.numVar = numVar;
	}
	// Retourne le nombre de clauses total
	public int getNumClauses() {
		return numClauses;
	}

	public void setNumClauses(int numClauses) {
		this.numClauses = numClauses;
	}

	public int getNumVar() {
		return numVar;
	}

	public void setNumVar(int numVar) {
		this.numVar = numVar;
	}
	
	public int getNumSat() {
		return numSat;
	}

	public void setNumSat(int numSat) {
		this.numSat = numSat;
	}

	public int [][] cnfRead(File f) {
		int numSat=0;
		int iter=-1;
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(f.getPath()));
            String line = bufferedReader.readLine();
            Pattern pattern = Pattern.compile("clause length = ([0-9]+)");
            Pattern instances=Pattern.compile("cnf ([0-9]+)[\\s]+([0-9]+)");
            Pattern clause=Pattern.compile("(-?\\d+);(-?\\d+);(-?\\d+);0");
			while (line.startsWith("c") ) {
				Matcher matcher = pattern.matcher(line);
				if(matcher.find()) numSat=Integer.parseInt(matcher.group(1));
				
				line = bufferedReader.readLine();
			}
			
			while (!line.matches("%")) {
				
				if(line.startsWith("p")) {
					
					Matcher m = instances.matcher(line);
					if(m.find()) {
						this.numVar=Integer.parseInt(m.group(1)); // 20
						this.numClauses=Integer.parseInt(m.group(2)); //91
						this.matClauses = new int [this.numClauses][numSat];
					}	
				}
				
				if(line.startsWith(" ")) line=line.replaceFirst(" ", "");
				
				line=line.replaceAll(" ", ";");
				line.split(";");
				Matcher cl = clause.matcher(line);
				
				if (cl.find()) {
					iter++;
					for (int i = 0; i < 3; i++) {
						int literal = Integer.parseInt(cl.group(i + 1));
						this.matClauses[iter][i] = literal;
					}

				}
				line = bufferedReader.readLine();
				
				
			}

			
				for (int cls=0; cls<this.numClauses;cls++) {
					for (int i=0; i<3; i++) {
				//System.out.print(this.matClauses[cls][i]+";");
			}
			//System.out.println("\n");
			
			}
			
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.matClauses;
	}

	public int[][] getMatClauses() {
		return matClauses;
	}

	public void setMatClauses(int[][] clauses) {
		this.matClauses = clauses;
	}
}
