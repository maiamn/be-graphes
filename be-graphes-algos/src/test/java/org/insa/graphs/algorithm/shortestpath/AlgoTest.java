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
	protected static ShortestPathSolution 	oneNodeDijkstra0, oneNodeDijkstra2, 
											oneNodeAStar0, oneNodeAStar2, 
											bigPathDijkstra, bigPathAStar, 
											infeasibleDijkstra, infeasibleAStar, 
											inequalityDijkstraAC, inequalityDijkstraAB, inequalityDijkstraBC, inequalityDijkstraABC,
											inequalityAStarAC, inequalityAStarAB, inequalityAStarBC, inequalityAStarABC ; 
	
	// Array of solutions 
	// MODE : Shortest path, all roads allowed
	protected static ShortestPathSolution[] solutionsDijkstra0 ; 
	protected static ShortestPathSolution[] solutionsAStar0 ; 
	protected static ShortestPathSolution[] solutionsBellman0 ; 
	
	// MODE : Shortest path, only road for cars
	protected static ShortestPathSolution[] solutionsDijkstra1 ; 
	protected static ShortestPathSolution[] solutionsAStar1 ; 
	protected static ShortestPathSolution[] solutionsBellman1 ; 
	
	// MODE : Fastest path, all roads allowed
	protected static ShortestPathSolution[] solutionsDijkstra2 ; 
	protected static ShortestPathSolution[] solutionsAStar2 ; 
	protected static ShortestPathSolution[] solutionsBellman2 ; 

	// MODE : Fastest path, pedestrian
	protected static ShortestPathSolution[] solutionsDijkstra4 ; 
	protected static ShortestPathSolution[] solutionsAStar4 ; 
	protected static ShortestPathSolution[] solutionsBellman4 ; 

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
		
		solutionsDijkstra1 = new ShortestPathSolution[50] ; 
		solutionsAStar1 = new ShortestPathSolution[50] ; 
		solutionsBellman1 = new ShortestPathSolution[50] ;
		
		solutionsDijkstra2 = new ShortestPathSolution[50] ; 
		solutionsAStar2 = new ShortestPathSolution[50] ; 
		solutionsBellman2 = new ShortestPathSolution[50] ;
		
		solutionsDijkstra4 = new ShortestPathSolution[50] ; 
		solutionsAStar4 = new ShortestPathSolution[50] ; 
		solutionsBellman4 = new ShortestPathSolution[50] ;
		
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
    			ShortestPathData dataRandom1 = new ShortestPathData(graphCarre, firstNode, secondNode, ArcInspectorFactory.getAllFilters().get(1)) ; 
    			ShortestPathData dataRandom2 = new ShortestPathData(graphCarre, firstNode, secondNode, ArcInspectorFactory.getAllFilters().get(2)) ; 
    			ShortestPathData dataRandom4 = new ShortestPathData(graphCarre, firstNode, secondNode, ArcInspectorFactory.getAllFilters().get(4)) ; 
    			
    			// Dijkstra algorithm
    			// Mode 0 //
    			DijkstraAlgorithm dijkstra0 = new DijkstraAlgorithm(dataRandom0) ; 
    			solutionsDijkstra0[nbIter] = dijkstra0.doRun() ; 
    			// Mode 1 //
    			DijkstraAlgorithm dijkstra1 = new DijkstraAlgorithm(dataRandom1) ; 
    			solutionsDijkstra1[nbIter] = dijkstra1.doRun() ; 
    			// Mode 2 //
    			DijkstraAlgorithm dijkstra2 = new DijkstraAlgorithm(dataRandom2) ; 
    			solutionsDijkstra2[nbIter] = dijkstra2.doRun() ; 
    			// Mode 4 //
    			DijkstraAlgorithm dijkstra4 = new DijkstraAlgorithm(dataRandom4) ; 
    			solutionsDijkstra4[nbIter] = dijkstra4.doRun() ; 
    			
    			
    			// AStar algorithm
    			// Mode 0 //
    			AStarAlgorithm aStar0 = new AStarAlgorithm(dataRandom0) ; 
    			solutionsAStar0[nbIter] = aStar0.doRun() ; 
    			// Mode 1 //
    			AStarAlgorithm aStar1 = new AStarAlgorithm(dataRandom1) ; 
    			solutionsAStar1[nbIter] = aStar1.doRun() ; 
    			// Mode 2 //
    			AStarAlgorithm aStar2 = new AStarAlgorithm(dataRandom2) ; 
    			solutionsAStar2[nbIter] = aStar2.doRun() ; 
    			// Mode 4 //
    			AStarAlgorithm aStar4 = new AStarAlgorithm(dataRandom4) ; 
    			solutionsAStar4[nbIter] = aStar4.doRun() ; 
    			
    			
    			// Bellman Ford algorithm
    			// Mode 0 //
    			BellmanFordAlgorithm bellman0 = new BellmanFordAlgorithm(dataRandom0) ; 
    			solutionsBellman0[nbIter] = bellman0.doRun() ;
    			// Mode 1 //
    			BellmanFordAlgorithm bellman1 = new BellmanFordAlgorithm(dataRandom1) ; 
    			solutionsBellman1[nbIter] = bellman1.doRun() ;
    			// Mode 2 //
    			BellmanFordAlgorithm bellman2 = new BellmanFordAlgorithm(dataRandom2) ; 
    			solutionsBellman2[nbIter] = bellman2.doRun() ;
    			// Mode 4 //
    			BellmanFordAlgorithm bellman4 = new BellmanFordAlgorithm(dataRandom4) ; 
    			solutionsBellman4[nbIter] = bellman4.doRun() ;
    			
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
		
		
		
		
		
		////////////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////// Test triangular inequality Dijkstra and A Star ////////////////////////
		////////////////////////////////////////////////////////////////////////////////////////////////
		
		Node pointA = nodesCarre.get(rand.nextInt(nodesCarre.size())) ; 
		Node pointC = nodesCarre.get(rand.nextInt(nodesCarre.size())) ; 
		Node pointB = nodesCarre.get(rand.nextInt(nodesCarre.size())) ; 
		
		ShortestPathData dataAB = new ShortestPathData(graphHauteGaronne, pointA, pointB, ArcInspectorFactory.getAllFilters().get(0));
		ShortestPathData dataBC = new ShortestPathData(graphHauteGaronne,pointB, pointC, ArcInspectorFactory.getAllFilters().get(0));
		ShortestPathData dataAC = new ShortestPathData(graphHauteGaronne, pointA, pointC, ArcInspectorFactory.getAllFilters().get(0));

		// DIJKSTRA ALGORITHM //
		DijkstraAlgorithm dijkstraAB = new DijkstraAlgorithm(dataAB) ; 
		inequalityDijkstraAB = dijkstraAB.doRun() ; 
		
		DijkstraAlgorithm dijkstraBC = new DijkstraAlgorithm(dataBC) ;
		inequalityDijkstraBC = dijkstraBC.doRun() ; 
		
		DijkstraAlgorithm dijkstraAC = new DijkstraAlgorithm(dataAC) ; 
		inequalityDijkstraAC = dijkstraAC.doRun() ; 
		
		// ASTAR ALGORITHM //
		AStarAlgorithm aStarAB = new AStarAlgorithm(dataAB) ; 
		inequalityAStarAB = aStarAB.doRun() ; 
		
		AStarAlgorithm aStarBC = new AStarAlgorithm(dataBC) ; 
		inequalityAStarBC = aStarBC.doRun() ; 
		
		AStarAlgorithm aStarAC = new AStarAlgorithm(dataAC) ; 
		inequalityAStarAC = aStarAC.doRun() ; 
		
		
	}
    
	
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////// TESTS /////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	// Test Dijkstra - Origin=Destination - shortest path, all roads allowed //
	@Test
	public void OneNodePathDijkstra_LENGTH_ALL() {
		assertEquals(0, oneNodePath.getOrigin().compareTo(oneNodeDijkstra0.getPath().getOrigin()));
		assertTrue(Math.abs(oneNodePath.getLength() - oneNodeDijkstra0.getPath().getLength()) < 0.01);
		assertTrue(oneNodeDijkstra0.getPath().isValid());
	}
	

	// Test Dijkstra - Origin=Destination - fastest path, all roads allowed //
	@Test
	public void OneNodePathDijkstra_TIME_ALL() {
		assertEquals(0, oneNodePath.getOrigin().compareTo(oneNodeDijkstra2.getPath().getOrigin()));
		assertTrue(Math.abs(oneNodePath.getMinimumTravelTime() - oneNodeDijkstra2.getPath().getMinimumTravelTime()) < 0.01);
		assertTrue(oneNodeDijkstra2.getPath().isValid());
	}
	
	
	// Test A* - Origin=Destination - shortest path, all roads allowed //
	@Test
	public void OneNodePathAStar_LENGTH_ALL() {
		assertEquals(0, oneNodePath.getOrigin().compareTo(oneNodeAStar0.getPath().getOrigin())) ; 
		assertTrue(Math.abs(oneNodePath.getLength() - oneNodeAStar0.getPath().getLength()) < 0.01) ; 
		assertTrue(oneNodeAStar0.getPath().isValid()) ; 
	}
	
	
	// Test A* - Origin=Destination - fastest path, all roads allowed //
	@Test
	public void OneNodePathAStar_TIME_ALL() {
		assertEquals(0, oneNodePath.getOrigin().compareTo(oneNodeAStar2.getPath().getOrigin())) ; 
		assertTrue(Math.abs(oneNodePath.getMinimumTravelTime()- oneNodeAStar2.getPath().getMinimumTravelTime()) < 0.01) ; 
		assertTrue(oneNodeAStar2.getPath().isValid()) ; 
	}
	
	
	// Test Dijkstra - Random - shortest path, all roads allowed //
	@Test
	public void RandomDijkstra_LENGTH_ALL() {
		for (int k=0; k<50; k++) {
			assertTrue(Math.abs(solutionsDijkstra0[k].getPath().getLength() - solutionsBellman0[k].getPath().getLength()) < 0.01) ; 
			assertTrue(solutionsDijkstra0[k].getPath().isValid()) ; 
		}	
	}
	
	
	// Test Dijkstra - Random - fastest path, all roads allowed //
	@Test
	public void RandomDijkstra_TIME_ALL() {
		for (int k=0; k<50; k++) {
			assertTrue(Math.abs(solutionsDijkstra2[k].getPath().getMinimumTravelTime() - solutionsBellman2[k].getPath().getMinimumTravelTime()) < 0.01) ; 
			assertTrue(solutionsDijkstra2[k].getPath().isValid()) ; 
		}	
	}
	
	
	// Test Dijkstra - Random - shortest path, only roads for cars //
	@Test
	public void RandomDijkstra_LENGTH_CARS() {
		for (int k=0; k<50; k++) {
			assertTrue(Math.abs(solutionsDijkstra1[k].getPath().getLength() - solutionsBellman1[k].getPath().getLength()) < 0.01) ; 
			assertTrue(solutionsDijkstra1[k].getPath().isValid()) ; 
		}	
	}
	
	
	// Test Dijkstra - Random - fastest path, pedestrian //
	@Test
	public void RandomDijkstra_TIME_PEDESTRIAN() {
		for (int k=0; k<50; k++) {
			assertTrue(Math.abs(solutionsDijkstra4[k].getPath().getMinimumTravelTime() - solutionsBellman4[k].getPath().getMinimumTravelTime()) < 0.01) ; 
			assertTrue(solutionsDijkstra4[k].getPath().isValid()) ; 
		}	
	}
	
	
	// Test A* - Random - shortest path, all roads allowed //
	@Test
	public void RandomAStar_LENGTH_ALL() {
		for (int k=0; k<50; k++) {
			assertTrue(Math.abs(solutionsAStar0[k].getPath().getLength() - solutionsBellman0[k].getPath().getLength()) < 0.01) ; 
			assertTrue(solutionsAStar0[k].getPath().isValid()) ; 
		}
	}
	
	
	// Test A* - Random - fastest path, all roads allowed //
	@Test
	public void RandomAStar_TIME_ALL() {
		for (int k=0; k<50; k++) {
			assertTrue(Math.abs(solutionsAStar2[k].getPath().getMinimumTravelTime() - solutionsBellman2[k].getPath().getMinimumTravelTime()) < 0.01) ; 
			assertTrue(solutionsAStar2[k].getPath().isValid()) ; 
		}
	}
	
	
	// Test A* - Random - shortest path, only roads for cars //
	@Test
	public void RandomAStar_LENGTH_CARS() {
		for (int k=0; k<50; k++) {
			assertTrue(Math.abs(solutionsAStar1[k].getPath().getLength() - solutionsBellman1[k].getPath().getLength()) < 0.01) ; 
			assertTrue(solutionsAStar1[k].getPath().isValid()) ; 
		}
	}
	
	
	// Test A* - Random - fastest path, pedestrian //
	@Test
	public void RandomAStar_TIME_PEDESTRIAN() {
		for (int k=0; k<50; k++) {
			assertTrue(Math.abs(solutionsAStar4[k].getPath().getMinimumTravelTime() - solutionsBellman4[k].getPath().getMinimumTravelTime()) < 0.01) ; 
			assertTrue(solutionsAStar4[k].getPath().isValid()) ; 
		}
	}
	
	
	// Test Dijkstra - Comparison between mode //
	@Test 
	public void comparisonModeDijkstra() {
		for (int k=0; k<50; k++) {
			assertTrue(solutionsDijkstra0[k].getPath().getLength() <= solutionsDijkstra2[k].getPath().getLength()) ;
			assertTrue(solutionsDijkstra2[k].getPath().getMinimumTravelTime() <= solutionsDijkstra0[k].getPath().getMinimumTravelTime()) ;
		}	
	}
	
	
	// Test A* - Comparison between mode //
	@Test 
	public void comparisonModeAStar() {
		for (int k=0; k<50; k++) {
			assertTrue(solutionsAStar0[k].getPath().getLength() <= solutionsAStar2[k].getPath().getLength()) ;
			assertTrue(solutionsAStar2[k].getPath().getMinimumTravelTime() <= solutionsAStar0[k].getPath().getMinimumTravelTime()) ;
		}	
	}
	
	
	
	// Test Dijkstra - Big Path - shortest path, all roads allowed //
	@Test 
	public void BigPathDijkstra_LENGTH_ALL() {
		assertTrue(Math.abs(bigPathDijkstra.getPath().getLength() - pathHauteGaronne.getLength()) < 0.01) ; 
		assertTrue(bigPathDijkstra.getPath().isValid()) ; 
	}
	
	
	// Test A* - Big Path - shortest path, all roads allowed //
	@Test 
	public void BigPathAStar_LENGTH_ALL() {
		assertTrue(Math.abs(bigPathAStar.getPath().getLength() - pathHauteGaronne.getLength()) < 0.01) ; 
		assertTrue(bigPathAStar.getPath().isValid()) ; 
	}
	
	
	// Test Dijkstra - Infeasible - shortest path, all roads allowed //
	@Test 
	public void testInfeasibleDijkstra() {
		assertEquals(infeasibleDijkstra.getStatus(), AbstractSolution.Status.INFEASIBLE);
	}
	  
	
	// Test A* - Infeasible - shortest path, all roads allowed //
	@Test 
	public void testInfeasibleAStar() {
		assertEquals(infeasibleAStar.getStatus(), AbstractSolution.Status.INFEASIBLE) ; 
	}
	
	
	// Test Dijkstra - Triangular inequality - shortest path, all roads allowed //
	@Test
	public void testTriangleDijkstra() {
		assertTrue((inequalityDijkstraAB.getPath().getLength() + inequalityDijkstraBC.getPath().getLength()) >= inequalityDijkstraAC.getPath().getLength()) ; 
	}
	
	
	// Test A* - Triangular inequality - shortest path, all roads allowed //
	@Test 
	public void testTriangleAStar() {
		assertTrue((inequalityAStarAB.getPath().getLength() + inequalityAStarBC.getPath().getLength()) >= inequalityAStarAC.getPath().getLength()) ; 
	}

}
