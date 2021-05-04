package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Point;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    public void setLabels(ShortestPathData data) { 
        for (int i=0 ; i<data.getGraph().size() ; i++) {
        	labels[i] = new LabelStar(i, false, Double.POSITIVE_INFINITY, -1, Point.distance(data.getGraph().get(i).getPoint(), data.getDestination().getPoint())) ; 
        }
    }

}
