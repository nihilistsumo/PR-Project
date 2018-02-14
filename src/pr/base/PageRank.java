package pr.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class PageRank {
	String graphFilePath;
	String seedFilePath;
	double alpha;
	HashMap<String, ArrayList<String>> graphSparseAdjMatrix;
	ArrayList<String> seedNodes;
	int n, ns, edges;
	static double CONVERGED_MIN = 0.0000000000000000000001;
	
	public PageRank(Properties prop){
		this.graphFilePath = prop.getProperty("graph-file");
		this.seedFilePath = prop.getProperty("seed-file");
		this.alpha = Float.parseFloat(prop.getProperty("alpha"));
		try {
			this.graphSparseAdjMatrix = initMatrix();
			this.seedNodes = initSeedList();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.n = this.graphSparseAdjMatrix.size();
		this.ns = this.seedNodes.size();
	}
	
	private HashMap<String, ArrayList<String>> initMatrix() throws IOException{
		HashMap<String, ArrayList<String>> matrix = new HashMap<String, ArrayList<String>>();
		this.edges = 0;
		BufferedReader br = new BufferedReader(new FileReader(new File(this.graphFilePath)));
		String line = br.readLine();
		while(line!=null){
			String[] values = line.split("\\s+");
			ArrayList<String> targets = new ArrayList<String>();
			for(int i=1; i<values.length; i++){
				targets.add(values[i]);
				this.edges++;
			}
			matrix.put(values[0], targets);
			line = br.readLine();
		}
		return matrix;
	}
	
	private ArrayList<String> initSeedList() throws IOException{
		ArrayList<String> seeds = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(new File(this.seedFilePath)));
		String line = br.readLine();
		while(line!=null){
			seeds.add(line.trim());
			line = br.readLine();
		}
		return seeds;
	}
	
	public HashMap<String, Double> calculatePersonalizedPageRank(){
		System.out.println("Calculating personalized page rank...");
		HashMap<String, Double> prValues = new HashMap<String, Double>();
		HashMap<String, Double> prValuesOld = new HashMap<String, Double>();
		for(String node:this.graphSparseAdjMatrix.keySet()){
			/*
			if(this.seedNodes.contains(node))
				prValues.put(node, (double)1/ns);
			else
				prValues.put(node, 0.0);
			*/
			prValues.put(node, (double)1/n);
			prValuesOld.put(node, prValues.get(node));
		}
		boolean isConverged = false;
		int it = 0;
		while(!isConverged){
			it++;
			for(String node:prValues.keySet()){
				double sumSinkScore = 0.0;
				for(String other:prValues.keySet()){
					if(other.equalsIgnoreCase(node))
						continue;
					else if(this.graphSparseAdjMatrix.get(other).contains(node))
						sumSinkScore+=prValues.get(other)/this.graphSparseAdjMatrix.get(other).size();
				}
				if(this.seedNodes.contains(node))
					prValues.put(node, this.alpha/ns+(1-this.alpha)*sumSinkScore);
				else
					prValues.put(node, (1-this.alpha)*sumSinkScore);
			}
			isConverged = checkConverged(prValues, prValuesOld);
			for(String node:prValues.keySet())
				prValuesOld.put(node, prValues.get(node));
		}
		//Normalizing
		double sumsc = 0;
		for(String n:prValues.keySet())
			sumsc+=prValues.get(n);
		for(String n:prValues.keySet())
			prValues.put(n, prValues.get(n)/sumsc);
		//----
		System.out.println("Converged after "+it+" iterations.");
		System.out.println("Number of edges: "+this.edges);
		System.out.println("Number of nodes: "+this.n);
		System.out.println("Random jump factor: "+this.alpha);
		return prValues;
	}
	
	public HashMap<String, Double> calculatePageRank(){
		System.out.println("Calculating page rank...");
		HashMap<String, Double> prValues = new HashMap<String, Double>();
		HashMap<String, Double> prValuesOld = new HashMap<String, Double>();
		for(String node:this.graphSparseAdjMatrix.keySet()){
			//prValues.put(node, this.alpha/n);
			prValues.put(node, (double)1/n);
			prValuesOld.put(node, prValues.get(node));
		}
		boolean isConverged = false;
		int it = 0;
		while(!isConverged){
			it++;
			for(String node:prValues.keySet()){
				double sumSinkScore = 0.0;
				for(String other:prValues.keySet()){
					if(other.equalsIgnoreCase(node))
						continue;
					else if(this.graphSparseAdjMatrix.get(other).contains(node))
						sumSinkScore+=prValues.get(other)/this.graphSparseAdjMatrix.get(other).size();
				}
				prValues.put(node, this.alpha/n+(1-this.alpha)*sumSinkScore);
			}
			isConverged = checkConverged(prValues, prValuesOld);
			for(String node:prValues.keySet())
				prValuesOld.put(node, prValues.get(node));
		}
		//Normalizing
		double sumsc = 0;
		for(String n:prValues.keySet())
			sumsc+=prValues.get(n);
		for(String n:prValues.keySet())
			prValues.put(n, prValues.get(n)/sumsc);
		//----
		System.out.println("Converged after "+it+" iterations.");
		System.out.println("Number of edges: "+this.edges);
		System.out.println("Number of nodes: "+this.n);
		System.out.println("Random jump factor: "+this.alpha);
		return prValues;
	}
	
	public HashMap<String, Double> calculatePageRankMat(){
		System.out.println("Calculating page rank...");
		HashMap<String, Double> prValues = new HashMap<String, Double>();
		HashMap<String, Double> prValuesOld = new HashMap<String, Double>();
		for(String node:this.graphSparseAdjMatrix.keySet()){
			//prValues.put(node, this.alpha/n);
			prValues.put(node, (double)1/n);
			prValuesOld.put(node, prValues.get(node));
		}
		boolean isConverged = false;
		int it = 0;
		while(!isConverged){
			it++;
			for(String node:prValues.keySet()){
				double sumSinkScore = 0.0;
				for(String other:prValues.keySet()){
					if(other.equalsIgnoreCase(node))
						continue;
					else if(this.graphSparseAdjMatrix.get(other).contains(node))
						sumSinkScore+=prValues.get(other)/this.graphSparseAdjMatrix.get(other).size();
				}
				prValues.put(node, this.alpha/n+(1-this.alpha)*sumSinkScore);
			}
			isConverged = checkConverged(prValues, prValuesOld);
			for(String node:prValues.keySet())
				prValuesOld.put(node, prValues.get(node));
		}
		//Normalizing
		double sumsc = 0;
		for(String n:prValues.keySet())
			sumsc+=prValues.get(n);
		for(String n:prValues.keySet())
			prValues.put(n, prValues.get(n)/sumsc);
		//----
		System.out.println("Converged after "+it+" iterations.");
		System.out.println("Number of edges: "+this.edges);
		System.out.println("Number of nodes: "+this.n);
		System.out.println("Random jump factor: "+this.alpha);
		return prValues;
	}
	
	private boolean checkConverged(HashMap<String, Double> newPr, HashMap<String, Double> oldPr){
		for(String node:newPr.keySet()){
			if(Math.abs(newPr.get(node)-oldPr.get(node))>PageRank.CONVERGED_MIN)
				return false;
		}
		return true;
	}

}
