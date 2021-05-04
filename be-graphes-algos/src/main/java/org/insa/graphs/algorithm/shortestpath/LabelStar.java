package org.insa.graphs.algorithm.shortestpath;

public class LabelStar extends Label {
	
	protected double costDest ; 
	
	// Constructor 
	public LabelStar(int nodeId, boolean marque, double cost, int father, double costDest) {
		super(nodeId, marque, cost, father) ; 
		this.costDest = costDest ; 
	}
	
	// Getters 
	public double getTotalCost() {
		return this.cost + this.costDest ; 
	}

}
