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
	protected static ShortestPathSolution oneNodeDijkstra, oneNodeAStar, bigPathDijkstra, bigPathAStar, infeasibleDijkstra, infeasibleAStar ; 
	
	// Array of solutions 
	protected static ShortestPathSolution[] solutionsDijkstra ; 
	protected static ShortestPathSolution[] solutionsAStar ; 
	protected static ShortestPathSolution[] solutionsBellman ; 

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
		
		ShortestPathData data = new ShortestPathData(graphCarre, singleNode, singleNode, ArcInspectorFactory.getAllFilters().get(0));
		
		// DIJKSTRA ALGORITHM //
		DijkstraAlgorithm singleDijkstra = new DijkstraAlgorithm(data);
		oneNodeDijkstra = singleDijkstra.doRun();
		
		// ASTAR ALGORITHM // 
		AStarAlgorithm singleAStar = new AStarAlgorithm(data) ; 
		oneNodeAStar = singleAStar.doRun() ; 
		
		
		
		
		
		////////////////////////////////////////////////////////////////////////////////////////////////
	    ////////////////////// Test of the algorithms on 10 random pairs of nodes //////////////////////
	    ////////////////////////////////////////////////////////////////////////////////////////////////
		
		// Init of arrays to store the solutions 
		solutionsDijkstra = new ShortestPathSolution[10] ; 
		solutionsAStar = new ShortestPathSolution[10] ; 
		solutionsBellman = new ShortestPathSolution[10] ; 
		
		int nbIter = 0 ; 
		while (nbIter < 10) {
			
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
    			ShortestPathData dataRandom = new ShortestPathData(graphCarre, firstNode, secondNode, ArcInspectorFactory.getAllFilters().get(0)) ; 
    			
    			// Dijkstra algorithm
    			DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(dataRandom) ; 
    			solutionsDijkstra[nbIter] = dijkstra.doRun() ; 
    			
    			// AStar algorithm
    			AStarAlgorithm aStar = new AStarAlgorithm(dataRandom) ; 
    			solutionsAStar[nbIter] = aStar.doRun() ; 
    			
    			// Bellman Ford algorithm
    			BellmanFordAlgorithm bellman = new BellmanFordAlgorithm(dataRandom) ; 
    			solutionsBellman[nbIter] = bellman.doRun() ;
    			
    			nbIter++ ; 
    	    }
		}
		
		
		
		
		
		////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////// Test of the algorithms on an infeasible path /////////////////////////
		////////////////////////////////////////////////////////////////////////////////////////////////
		
		/*
		 * int indexFirst = -1 ; int indexSecond = -1 ;
		 * 
		 * boolean infeasible = false ;
		 * 
		 * while (!infeasible) {
		 * 
		 * infeasible = true ;
		 * 
		 * // Two random nodes in the graph Node firstNode =
		 * nodesHauteGaronne.get(rand.nextInt(nodesHauteGaronne.size())) ; Node
		 * secondNode = nodesHauteGaronne.get(rand.nextInt(nodesHauteGaronne.size())) ;
		 * 
		 * 
		 * // Weakly Connected Components to check if the two nodes are in the same
		 * component WeaklyConnectedComponentsData weakData = new
		 * WeaklyConnectedComponentsData(graphCarre) ;
		 * WeaklyConnectedComponentsAlgorithm algo = new
		 * WeaklyConnectedComponentsAlgorithm(weakData) ;
		 * WeaklyConnectedComponentsSolution solution = algo.run() ;
		 * ArrayList<ArrayList<Node>> components ; components = solution.getComponents()
		 * ;
		 * 
		 * 
		 * // Search indexes of firstNode and secondNode within the components for (int
		 * i=0; i<components.size(); i++) { if
		 * (components.get(i).get(0).equals(firstNode)) { indexFirst = i ; } else if
		 * (components.get(i).get(0).equals(secondNode)) { indexSecond = i ; } }
		 * 
		 * // Check if secondNode is in the same component as firstNode if (indexFirst
		 * != -1) { for (int j=0; j<components.get(indexFirst).size(); j++) { if
		 * (components.get(indexFirst).get(j).equals(secondNode)) { infeasible = false ;
		 * } } }
		 * 
		 * // Or if the firstNode is in the same component as secondNode else if
		 * (indexSecond != -1) { for (int j=0; j<components.get(indexSecond).size();
		 * j++) { if (components.get(indexSecond).get(j).equals(firstNode)) { infeasible
		 * = false ; } } } }
		 */

		
		ShortestPathData dataInfeasible = new ShortestPathData(graphHauteGaronne, graphHauteGaronne.get(120349), graphHauteGaronne.get(120351), ArcInspectorFactory.getAllFilters().get(0)) ; 
		
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
	public void testOneNodePathDijkstra() {
		assertEquals(0, oneNodePath.getOrigin().compareTo(oneNodeDijkstra.getPath().getOrigin()));
		assertTrue(Math.abs(oneNodePath.getLength() - oneNodeDijkstra.getPath().getLength()) < 0.01);
		assertTrue(oneNodeDijkstra.getPath().isValid());
	}
	
	
	@Test
	public void testOneNodePathAStar() {
		assertEquals(0, oneNodePath.getOrigin().compareTo(oneNodeAStar.getPath().getOrigin())) ; 
		assertTrue(Math.abs(oneNodePath.getLength() - oneNodeAStar.getPath().getLength()) < 0.01) ; 
		assertTrue(oneNodeAStar.getPath().isValid()) ; 
	}
	
	
	@Test
	public void testRandomDijkstra() {
		for (int k=0; k<10; k++) {
			assertTrue(Math.abs(solutionsDijkstra[k].getPath().getLength() - solutionsBellman[k].getPath().getLength()) < 0.01) ; 
			assertTrue(solutionsDijkstra[k].getPath().isValid()) ; 
		}
		
	}
	
	@Test
	public void testRandomAStar() {
		for (int k=0; k<10; k++) {
			assertTrue(Math.abs(solutionsAStar[k].getPath().getLength() - solutionsBellman[k].getPath().getLength()) < 0.01) ; 
			assertTrue(solutionsAStar[k].getPath().isValid()) ; 
		}
	}
	
	
	@Test 
	public void testBigPathDijkstra() {
		assertTrue(Math.abs(bigPathDijkstra.getPath().getLength() - pathHauteGaronne.getLength()) < 0.01) ; 
		assertTrue(bigPathDijkstra.getPath().isValid()) ; 
	}
	
	
	@Test 
	public void testBigPathAStar() {
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
