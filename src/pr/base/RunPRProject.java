package pr.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.TreeMap;

public class RunPRProject {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Properties p = new Properties();
		try {
			InputStream is = new FileInputStream(new File("project.properties"));
			p.load(is);
			PageRank pr = new PageRank(p);
			HashMap<String, Double> prScores;
			if(p.getProperty("mode").equalsIgnoreCase("ppr"))
				prScores = pr.calculatePersonalizedPageRank();
			else
				prScores = pr.calculatePageRank();
			for(String node:prScores.keySet())
				System.out.println(node+"\t"+prScores.get(node));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
