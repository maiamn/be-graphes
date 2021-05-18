package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.AbstractInputData.Mode;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    public void setLabels(ShortestPathData data) { 
    	boolean lengthMode = data.getMode() == Mode.LENGTH ; 
        for (int i=0 ; i<data.getGraph().size() ; i++) {
        	labels[i] = new LabelStar(data.getGraph().get(i), false, Double.POSITIVE_INFINITY, -1, data.getDestination(), lengthMode) ; 
        }
    }

}
