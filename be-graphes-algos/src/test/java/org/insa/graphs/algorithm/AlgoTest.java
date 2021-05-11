package org.insa.graphs.algorithm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List ; 
import java.util.Arrays;
import java.util.Random ; 

import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;
import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.algorithm.* ; 
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.RoadInformation;
import org.insa.graphs.model.RoadInformation.RoadType;
import org.insa.graphs.model.io.BinaryGraphReader; 
import org.insa.graphs.model.io.GraphReader; 
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.File ; 
import org.junit.BeforeClass;
import org.junit.Test;

/* Une fois que vous avez récupéré un graphe : 
 * - testez des cas spécifiques (comme invalide, infeasible ou 1 seul point)
 * - Choisir un mode (distance, temps, arcs autorisés, .....)
 * - Pour chaque type de mode faire une boucle pour générer X (par exemple 50) paires origine/destination
 * --> si la paire => infeasible : ne pas compter
 * --> si la paire correspond à un chemin réalisable : comparer avec Bellman-Ford et incrémenter le nb de tests de 1 (X++) arrêt quand X = 50
- recommencer pour un autre mode
 */
public class AlgoTest {
	
	// Graph to do tests
	private static Graph graph ;
	
	// List of nodes
	private static ArrayList<Node> nodes ; 

	
	@BeforeClass
	public static void initAll() throws IOException {
		// Small graph use for tests
		try {
			String mapCarreDense = "D:/INSA/3A/S6/BE_Graphes/be-graphes/Maps/carredense.mapgr" ;
			GraphReader readerCarreDense = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapCarreDense))));
			graph = readerCarreDense.read() ; 
			
		} catch (Exception e) {}
		
		// Nodes of the graph
		nodes = new ArrayList<>(graph.getNodes()) ; 
        
	}
    
	
    @Test
    public void testRandom() {
    	// Test on 50 random origin-destination 
    	int nbIter = 0 ; 
    	while (nbIter<5) {
    		Random rand = new Random();
    	    Node firstNode = nodes.get(rand.nextInt(nodes.size()));
    	    Node secondNode= nodes.get(rand.nextInt(nodes.size())) ; 
    	    ArrayList<Node> originDestination = new ArrayList<>() ; 
    	    
    	    
    	    //Bellman
    	    // ShortestPathSolution solutionBellman = BellmanFordAlgorithm(ShortestPathData(graph, firstNode, secondNode)) ; 
    	    //weaklycomponents 
    		/*if () {
    			
    		} else {
    			nbIter++ ; 
    		}*/  
    	    
    	 // Test when origin = destination
    	    
    	    
    	 
    	 
    	}    	
    }
    
    
    @Test 
    public void originEqualsDestination() {
    	
    }
    

}
