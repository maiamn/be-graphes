package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.*;

public class LabelStar extends Label {
	
	private final double heuristic ; 
	
	// Constructor 
	public LabelStar(Node node, boolean marque, double cost, int father, Node destination, boolean lengthMode) {
		super(node, marque, cost, father) ; 
		
		if (lengthMode) {
			this.heuristic = Point.distance(node.getPoint(), destination.getPoint()) ; 
		} else {
			this.heuristic = Point.distance(node.getPoint(), destination.getPoint()) / 25d ; 
		}
	}
	
	// Getters 
	public double getHeuristic() {
		return heuristic ; 
	}
	
	public double getTotalCost() {
		return this.cost + heuristic ; 
	}

}
