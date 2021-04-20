package org.insa.graphs.algorithm.shortestpath;

public class Label implements Comparable<Label>{
	
	protected final int currentNode ; 
	protected boolean marque ; 
	protected double cost ; 
	protected int father ; 
	
	// Constructor
	public Label(int currentNode, boolean marque, double cost, int father) {
		this.currentNode = currentNode ; 
		this.marque = marque ; 
		this.cost = cost ; 
		this.father = father ;  
	}
	
	// getters 
	public int getcurrentNode() {
		return this.currentNode ; 
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
	
	// Comparable 
	@Override 
	public int compareTo(Label second) {
		return Double.compare(this.cost, second.getCost()) ; 
	}
	
}
