package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.ArcInspectorFactory ;
import org.insa.graphs.algorithm.weakconnectivity.*;
import org.insa.graphs.algorithm.weakconnectivity.WeaklyConnectedComponentsData ; 
import org.insa.graphs.algorithm.weakconnectivity.WeaklyConnectedComponentsSolution ;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random ; 
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path ; 
import org.insa.graphs.model.io.BinaryGraphReader; 
import org.insa.graphs.model.io.GraphReader; 
import org.insa.graphs.model.io.BinaryPathReader;
import org.insa.graphs.model.io.PathReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.insa.graphs.algorithm.AbstractSolution ; 


public class AlgoTest {
	
	// Graph to do tests
	private static Graph graphCarre, graphHauteGaronne ;
	
	// List of nodes
	private static ArrayList<Node> nodesCarre, nodesHauteGaronne ; 
	
	// Solutions of algorithms when origin equals destination
	protected static ShortestPathSolution oneNodeDijkstra0, oneNodeDijkstra2, oneNodeAStar0, oneNodeAStar2, bigPathDijkstra, bigPathAStar, infeasibleDijkstra, infeasibleAStar ; 
	
	// Array of solutions 
	protected static ShortestPathSolution[] solutionsDijkstra0 ; 
	protected static ShortestPathSolution[] solutionsAStar0 ; 
	protected static ShortestPathSolution[] solutionsBellman0 ; 
	protected static ShortestPathSolution[] solutionsDijkstra2 ; 
	protected static ShortestPathSolution[] solutionsAStar2 ; 
	protected static ShortestPathSolution[] solutionsBellman2 ; 

	// Some paths to realize tests
	protected static Path oneNodePath ; 
	private static Path pathHauteGaronne ; 
	
	
	
	@BeforeClass
	public static void initAll() throws IOException {
		
		// Get graph of map Carre
		try {
			String mapCarre = "D:/INSA/3A/S6/BE_Graphes/be-graphes/Maps/carre.mapgr" ; 
			GraphReader readerCarre = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapCarre))));
			graphCarre = readerCarre.read() ; 
		} catch (Exception e) {}
		
		// Get graph of map Haute-Garonne
		try {
			String mapHauteGaronne = "D:/INSA/3A/S6/BE_Graphes/be-graphes/Maps/haute-garonne.mapgr" ;
			GraphReader readerHauteGaronne = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapHauteGaronne))));
			graphHauteGaronne = readerHauteGaronne.read() ; 
			
		} catch (Exception e) {}
		
		
		// Get path on map Haute-Garonne
		try {
			String testPath = "D:/INSA/3A/S6/BE_Graphes/be-graphes/Maps/path_fr31_insa_bikini_canal.path" ; 
			PathReader readerPath = new BinaryPathReader(new DataInputStream(new BufferedInputStream(new FileInputStream(testPath)))) ;
			pathHauteGaronne = readerPath.readPath(graphHauteGaronne) ; 
		} catch (Exception e) {} 
		
		
		// Nodes of the graph
		// Get nodes of the graph Carre
		nodesCarre = new ArrayList<>(graphCarre.getNodes()) ; 
		// Get nodes of the graph CarreDense 
		nodesHauteGaronne = new ArrayList<>(graphHauteGaronne.getNodes()) ; 
		
		
		
		
		
	    ////////////////////////////////////////////////////////////////////////////////////////////////
	    ////// Test of the algorithms on a situation where the origin is equal to the destination //////
	    ////////////////////////////////////////////////////////////////////////////////////////////////

		
		// Init of a one node path
		Random rand = new Random(); 
		Node singleNode = nodesCarre.get(rand.nextInt(nodesCarre.size()));
		oneNodePath = new Path(graphCarre, singleNode);
		
		ShortestPathData data0 = new ShortestPathData(graphCarre, singleNode, singleNode, ArcInspectorFactory.getAllFilters().get(0));
		ShortestPathData data2 = new ShortestPathData(graphCarre, singleNode, singleNode, ArcInspectorFactory.getAllFilters().get(2));
		
		
		// DIJKSTRA ALGORITHM //
		// Mode 0 //
		DijkstraAlgorithm singleDijkstra0 = new DijkstraAlgorithm(data0);
		oneNodeDijkstra0 = singleDijkstra0.doRun();
		// Mode 2 //
		DijkstraAlgorithm singleDijkstra2 = new DijkstraAlgorithm(data2);
		oneNodeDijkstra2 = singleDijkstra2.doRun();
		
		
		// ASTAR ALGORITHM // 
		// Mode 0 //
		AStarAlgorithm singleAStar0 = new AStarAlgorithm(data0) ; 
		oneNodeAStar0 = singleAStar0.doRun() ; 
		// Mode 2 //
		AStarAlgorithm singleAStar2 = new AStarAlgorithm(data2) ; 
		oneNodeAStar2 = singleAStar2.doRun() ;
		
		
		
		
		////////////////////////////////////////////////////////////////////////////////////////////////
	    ////////////////////// Test of the algorithms on 50 random pairs of nodes //////////////////////
	    ////////////////////////////////////////////////////////////////////////////////////////////////
		
		// Init of arrays to store the solutions 
		solutionsDijkstra0 = new ShortestPathSolution[50] ; 
		solutionsAStar0 = new ShortestPathSolution[50] ; 
		solutionsBellman0 = new ShortestPathSolution[50] ; 
		solutionsDijkstra2 = new ShortestPathSolution[50] ; 
		solutionsAStar2 = new ShortestPathSolution[50] ; 
		solutionsBellman2 = new ShortestPathSolution[50] ;
		
		int nbIter = 0 ; 
		while (nbIter < 50) {
			
			// Boolean variable to test if the origin and the destination are different and in the same component
			boolean valid = true ; 
			
			// Two random nodes in the graph 
			Node firstNode = nodesCarre.get(rand.nextInt(nodesCarre.size())) ; 
			Node secondNode = nodesCarre.get(rand.nextInt(nodesCarre.size())) ; 
			
			// If firstNode equals secondNode => new iteration 
			if (firstNode.equals(secondNode)) {
				valid = false ; 	
			} 
    	    
    	    // Algorithms on random pairs of nodes 
    	    if (valid) {
    			ShortestPathData dataRandom0 = new ShortestPathData(graphCarre, firstNode, secondNode, ArcInspectorFactory.getAllFilters().get(0)) ; 
    			ShortestPathData dataRandom2 = new ShortestPathData(graphCarre, firstNode, secondNode, ArcInspectorFactory.getAllFilters().get(2)) ; 
    			
    			// Dijkstra algorithm
    			// Mode 0 //
    			DijkstraAlgorithm dijkstra0 = new DijkstraAlgorithm(dataRandom0) ; 
    			solutionsDijkstra0[nbIter] = dijkstra0.doRun() ; 
    			// Mode 2 //
    			DijkstraAlgorithm dijkstra2 = new DijkstraAlgorithm(dataRandom2) ; 
    			solutionsDijkstra2[nbIter] = dijkstra2.doRun() ; 
    			
    			
    			// AStar algorithm
    			// Mode 0 //
    			AStarAlgorithm aStar0 = new AStarAlgorithm(dataRandom0) ; 
    			solutionsAStar0[nbIter] = aStar0.doRun() ; 
    			// Mode 2 //
    			AStarAlgorithm aStar2 = new AStarAlgorithm(dataRandom2) ; 
    			solutionsAStar2[nbIter] = aStar2.doRun() ; 
    			
    			
    			// Bellman Ford algorithm
    			// Mode 0 //
    			BellmanFordAlgorithm bellman0 = new BellmanFordAlgorithm(dataRandom0) ; 
    			solutionsBellman0[nbIter] = bellman0.doRun() ;
    			// Mode 2 //
    			BellmanFordAlgorithm bellman2 = new BellmanFordAlgorithm(dataRandom2) ; 
    			solutionsBellman2[nbIter] = bellman2.doRun() ;
    			
    			nbIter++ ; 
    	    }
		}
		
		
		
		
		
		////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////// Test of the algorithms on an infeasible path /////////////////////////
		////////////////////////////////////////////////////////////////////////////////////////////////
		
		Node origin ; 
		Node destination ; 
		
		int componentFirst = -1 ; 
		int originId = -1 ; 
		int destinationId = -1 ; 
		boolean originFound = false ;  
		boolean infeasible = false ; 
		
		while (!infeasible) {
			
			infeasible = true; 
			
			// Two random nodes in the graph 
			origin = nodesHauteGaronne.get(rand.nextInt(nodesHauteGaronne.size())) ; 
			originId = origin.getId() ; 
			destination = nodesHauteGaronne.get(rand.nextInt(nodesHauteGaronne.size())) ;
			destinationId = destination.getId() ; 
			   
			// Weakly Connected Components to check if the two nodes are in the same component 
			WeaklyConnectedComponentsData weakData = new WeaklyConnectedComponentsData(graphHauteGaronne) ;
			WeaklyConnectedComponentsAlgorithm algo = new WeaklyConnectedComponentsAlgorithm(weakData) ;
			WeaklyConnectedComponentsSolution solution = algo.run() ;
			ArrayList<ArrayList<Node>> components ; 
			components = solution.getComponents() ;
	
			for (int i=0; i<components.size(); i++) {
				for (int j=0; j<components.get(i).size(); j++) {
					if (origin.equals(components.get(i).get(j))) {
						originFound = true ; 
						componentFirst = i ; 
					}
				}
			}
			
			if (originFound) {
				for (int k=0; k<components.get(componentFirst).size(); k++) {
					if (destination.equals(components.get(componentFirst).get(k))) {
						infeasible = false ; 
					} 
				}
			}
		}
			
		
		
		ShortestPathData dataInfeasible = new ShortestPathData(graphHauteGaronne, graphHauteGaronne.get(originId), graphHauteGaronne.get(destinationId), ArcInspectorFactory.getAllFilters().get(0)) ; 
		
		// DIJKSTRA ALGORITHM //
		DijkstraAlgorithm infeasibleDijkstraAlgo = new DijkstraAlgorithm(dataInfeasible) ; 
		infeasibleDijkstra = infeasibleDijkstraAlgo.doRun() ; 
		
		// ASTAR ALGORITHM //
		AStarAlgorithm infeasibleAStarAlgo = new AStarAlgorithm(dataInfeasible) ; 
		infeasibleAStar = infeasibleAStarAlgo.doRun() ; 
		

		
		
		
		////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////// Test with a big path on Dijkstra and A Star //////////////////////////
		////////////////////////////////////////////////////////////////////////////////////////////////
		
		ShortestPathData dataHauteGaronne = new ShortestPathData(graphHauteGaronne, pathHauteGaronne.getOrigin(), pathHauteGaronne.getDestination(), ArcInspectorFactory.getAllFilters().get(0));

		// DIJKSTRA ALGORITHM //
		DijkstraAlgorithm bigDijkstra = new DijkstraAlgorithm(dataHauteGaronne);
		bigPathDijkstra = bigDijkstra.doRun();

		// ASTAR ALGORITHM //
		AStarAlgorithm bigAStar = new AStarAlgorithm(dataHauteGaronne) ; 
		bigPathAStar = bigAStar.doRun() ; 
		
	}
    
	
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////// TESTS /////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	@Test
	public void OneNodePathDijkstra_LENGTH() {
		assertEquals(0, oneNodePath.getOrigin().compareTo(oneNodeDijkstra0.getPath().getOrigin()));
		assertTrue(Math.abs(oneNodePath.getLength() - oneNodeDijkstra0.getPath().getLength()) < 0.01);
		assertTrue(oneNodeDijkstra0.getPath().isValid());
	}
	
	
	@Test
	public void OneNodePathDijkstra_TIME() {
		assertEquals(0, oneNodePath.getOrigin().compareTo(oneNodeDijkstra2.getPath().getOrigin()));
		assertTrue(Math.abs(oneNodePath.getMinimumTravelTime() - oneNodeDijkstra2.getPath().getMinimumTravelTime()) < 0.01);
		assertTrue(oneNodeDijkstra2.getPath().isValid());
	}
	
	
	@Test
	public void OneNodePathAStar_LENGTH() {
		assertEquals(0, oneNodePath.getOrigin().compareTo(oneNodeAStar0.getPath().getOrigin())) ; 
		assertTrue(Math.abs(oneNodePath.getLength() - oneNodeAStar0.getPath().getLength()) < 0.01) ; 
		assertTrue(oneNodeAStar0.getPath().isValid()) ; 
	}
	
	
	@Test
	public void OneNodePathAStar_TIME() {
		assertEquals(0, oneNodePath.getOrigin().compareTo(oneNodeAStar2.getPath().getOrigin())) ; 
		assertTrue(Math.abs(oneNodePath.getMinimumTravelTime()- oneNodeAStar2.getPath().getMinimumTravelTime()) < 0.01) ; 
		assertTrue(oneNodeAStar2.getPath().isValid()) ; 
	}
	
	
	@Test
	public void RandomDijkstra_LENGTH() {
		for (int k=0; k<10; k++) {
			assertTrue(Math.abs(solutionsDijkstra0[k].getPath().getLength() - solutionsBellman0[k].getPath().getLength()) < 0.01) ; 
			assertTrue(solutionsDijkstra0[k].getPath().isValid()) ; 
		}	
	}
	
	
	@Test
	public void RandomDijkstra_TIME() {
		for (int k=0; k<10; k++) {
			assertTrue(Math.abs(solutionsDijkstra2[k].getPath().getMinimumTravelTime() - solutionsBellman2[k].getPath().getMinimumTravelTime()) < 0.01) ; 
			assertTrue(solutionsDijkstra2[k].getPath().isValid()) ; 
		}	
	}
	
	
	@Test
	public void RandomAStar_LENGTH() {
		for (int k=0; k<10; k++) {
			assertTrue(Math.abs(solutionsAStar0[k].getPath().getLength() - solutionsBellman0[k].getPath().getLength()) < 0.01) ; 
			assertTrue(solutionsAStar0[k].getPath().isValid()) ; 
		}
	}
	
	
	@Test
	public void RandomAStar_TIME() {
		for (int k=0; k<10; k++) {
			assertTrue(Math.abs(solutionsAStar2[k].getPath().getMinimumTravelTime() - solutionsBellman2[k].getPath().getMinimumTravelTime()) < 0.01) ; 
			assertTrue(solutionsAStar2[k].getPath().isValid()) ; 
		}
	}
	
	
	@Test 
	public void BigPathDijkstra_LENGTH() {
		assertTrue(Math.abs(bigPathDijkstra.getPath().getLength() - pathHauteGaronne.getLength()) < 0.01) ; 
		assertTrue(bigPathDijkstra.getPath().isValid()) ; 
	}
	
	
	@Test 
	public void BigPathAStar_LENGTH() {
		assertTrue(Math.abs(bigPathAStar.getPath().getLength() - pathHauteGaronne.getLength()) < 0.01) ; 
		assertTrue(bigPathAStar.getPath().isValid()) ; 
	}
	
	
	@Test public void testInfeasibleDijkstra() {
		assertEquals(infeasibleDijkstra.getStatus(), AbstractSolution.Status.INFEASIBLE);
	}
	  
	
	@Test public void testInfeasibleAStar() {
		assertEquals(infeasibleAStar.getStatus(), AbstractSolution.Status.INFEASIBLE) ; 
	}
	 
	
}
