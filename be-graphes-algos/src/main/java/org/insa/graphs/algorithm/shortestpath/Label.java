package org.insa.graphs.algorithm.shortestpath;

public class Label implements Comparable<Label>{
	
	protected final int nodeId ; 
	protected boolean marque ; 
	protected double cost ; 
	protected int father ; 
	
	// Constructor
	public Label(int nodeId, boolean marque, double cost, int father) {
		this.nodeId = nodeId ; 
		this.marque = marque ; 
		this.cost = cost ; 
		this.father = father ;  
	}
	
	// getters 
	public int getNodeId() {
		return this.nodeId ; 
	}
	
	public boolean isMarked() {
		return this.marque ; 
	}
	
	public double getCost() {
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
	
	// Comparable 
	@Override 
	public int compareTo(Label second) {
		return Double.compare(this.cost, second.getCost()) ; 
	}
	
}
