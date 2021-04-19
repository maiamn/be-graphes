package org.insa.graphs.algorithm.shortestpath;

// Import of classes
import org.insa.graphs.algorithm.AbstractSolution;
import org.insa.graphs.algorithm.AbstractInputData;
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
        
        // Association of label for each node 
        int nbNodes = graph.size(); 
        Label[] labels = new Label[nbNodes] ; 
        for (int i=0 ; i< nbNodes ; i++) {
        	labels[i]= new Label(i, false, Double.POSITIVE_INFINITY, -1) ; 
        }
        
        ShortestPathSolution solution = null;
        // TODO:
        return solution;
    }

}
