package org.insa.graphs.algorithm.shortestpath;

//Import of classes
import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.algorithm.AbstractSolution.Status ;
import org.insa.graphs.algorithm.utils.BinaryHeap;
// Import of exceptions
import org.insa.graphs.algorithm.utils.ElementNotFoundException;
import org.insa.graphs.algorithm.utils.EmptyPriorityQueueException;

import org.insa.graphs.model.*;

// Import java.util 
import java.util.ArrayList;
import java.util.Collections;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        Graph graph = data.getGraph() ; 
        
        ///// INITIALISATION /////
        // Association of label for each node 
        final int nbNodes = graph.size(); 
        Label[] labels = new Label[nbNodes] ; 
        for (int i=0 ; i<nbNodes ; i++) {
        	labels[i] = new Label(i, false, Double.POSITIVE_INFINITY, -1) ; 
        }
        
        int origin = data.getOrigin().getId(); 
        double initCost = 0 ; 
        labels[origin].setCost(initCost) ; 
        
        ////// CONSTRUCTION BINARY HEAP /////
        BinaryHeap<Label> heap = new BinaryHeap<>(); 
        heap.insert(labels[origin]);
        
        // Notify observers about the first event (origin processed).
        notifyOriginProcessed(data.getOrigin());
        
        ///// ITERATIONS /////
        
        /*	Tant qu'il existe des sommets non marqués {
         * 		x <- ExtractMin(Tas)
         * 		x.marque = true 
         * 		Pour tous les successeurs y de x {
         * 			Si y n'est pas marqué {
         * 				cost(y) = min[cost(y), cost(x)+W(x,y)]
         * 				Si cost(y) a été mis à jour {
         * 					Placer(y, tas)
         * 					y.father = x 
         *				}
         *			}
         *		}
         *	}
         */		
        
        while (!labels[data.getDestination().getId()].isMarked()) {
        	// Extraction of minimal element of the heap
        	Label currentNode ; 
        	// Find the minimal element in the heap
        	try {
        		currentNode = heap.findMin() ; 
        	} catch (EmptyPriorityQueueException e) {
        		break ; 
        	}

        	// Remove the element from the heap 
        	try {
        		heap.remove(currentNode) ; 
        	} catch (ElementNotFoundException e) {
        	}
        	
        	// Mark the element 
        	labels[currentNode.getNodeId()].setMarque(true) ; 
        	
        	// Go through the successors of the element 
        	for (Arc successor : graph.get(currentNode.getNodeId()).getSuccessors()) {
        		if(!data.isAllowed(successor)) {
        			continue ; 
        		}
        		
        		int nextNodeId = successor.getDestination().getId() ; 
        		if (!labels[nextNodeId].isMarked()) {
        			
        			// Actual and new distance
        			double currentDistance = labels[nextNodeId].getCost() ; 
        			double weightArc = data.getCost(successor) ; 
        			double newDistance = labels[currentNode.getNodeId()].getCost() + weightArc ; 
        			
        			// Update of cost of successor 
        			// Which cost is the best ? 
        			if (newDistance < currentDistance) {
        				labels[nextNodeId].setCost(newDistance) ; 
        				labels[nextNodeId].setFather(currentNode.getNodeId()) ; 
        				
        				// Update the heap 
        				try {
        					heap.remove(labels[nextNodeId]) ; 
        					heap.insert(labels[nextNodeId]) ; 
        				} catch (ElementNotFoundException e) {
        					heap.insert(labels[nextNodeId]) ; 
        				}
        			}
        		}
        	}

        }
        
        
        ///// SOLUTION OF DJIKSTRA ALGORITHM /////
        ShortestPathSolution solution = null;
        
        // INFEASIBLE // 
        if (!labels[data.getDestination().getId()].isMarked()) {
        	solution = new ShortestPathSolution(data, Status.INFEASIBLE) ; 
        } else {
        	// The destination has been found, notify the observers.
            notifyDestinationReached(data.getDestination());
            
        	// Find the list of nodes 
        	ArrayList<Node> nodes = new ArrayList<>() ; 
        	nodes.add(data.getDestination()) ; 
        	Node node = data.getDestination() ; 
        	
        	while(!node.equals(data.getOrigin())) {
        		Node fatherNode = graph.getNodes().get(labels[node.getId()].getFather()) ; 
        		nodes.add(fatherNode) ; 
        		node = fatherNode ; 
        	}
        	
        	// Reverse 
        	Collections.reverse(nodes) ; 
        	
        	// Create the final solution
        	Path finalPath ; 
       
        	if (data.getMode().equals(AbstractInputData.Mode.LENGTH)) {
        		// Final solution according to length of the path
        		finalPath = Path.createShortestPathFromNodes(graph, nodes) ; 
        	} else {
        		// Final solution according to time of the path 
        		finalPath = Path.createFastestPathFromNodes(graph, nodes) ; 
        	}
        	
            solution = new ShortestPathSolution(data, Status.OPTIMAL, finalPath) ; 
        }
        
        return solution;
    }

}
