package org.insa.graphs.algorithm.shortestpath;

public class Label {
	
	int currentNode ; 
	boolean marque ; 
	double cost ; 
	int father ; 
	
	// Constructor
	public Label(int currentNode, boolean marque, double cost, int father) {
		this.currentNode = currentNode ; 
		this.marque = marque ; 
		this.cost = cost ; 
		this.father = father ;  
	}
	
	public double getCost() {
		return this.cost ; 
	} 
	
	
}
