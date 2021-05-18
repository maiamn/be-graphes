package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.*;

public class Label implements Comparable<Label>{
	
	protected final Node node ; 
	protected boolean marque ; 
	protected double cost ; 
	protected int father ; 
	
	// Constructor
	public Label(Node node, boolean marque, double cost, int father) {
		this.node = node ; 
		this.marque = marque ; 
		this.cost = cost ; 
		this.father = father ;  
	}
	
	// getters 
	public Node getNode() {
		return this.node ; 
	}
	
	public boolean isMarked() {
		return this.marque ; 
	}
	
	public double getCost() {
		return this.cost ; 
	} 
	
	public double getTotalCost() {
		return this.cost ; 
	}
	
	public int getFather() {
		return this.father ; 
	}
	
	// Setters 
	public void setCost(double cost) {
		this.cost = cost ; 
	}
	
	public void setMarque(boolean marque) {
		this.marque = marque ; 
	}
	
	public void setFather(int fatherId) {
		this.father = fatherId ; 
	}
	
	// Information for A* Algorithm //
	public double getHeuristic() {
		return 0 ; 
	}
	
	// Comparable 
	@Override 
	public int compareTo(Label second) {
		return Double.compare(this.getTotalCost(), second.getTotalCost()) ; 
	}
	
}
