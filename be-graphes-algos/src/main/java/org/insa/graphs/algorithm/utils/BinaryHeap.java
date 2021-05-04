package org.insa.graphs.algorithm.utils;

import java.util.ArrayList;

/**
 * Implements a binary heap containing elements of type E.
 *
 * Note that all comparisons are based on the compareTo method, hence E must
 * implement Comparable
 * 
 * @author Mark Allen Weiss
 * @author DLB
 */
public class BinaryHeap<E extends Comparable<E>> implements PriorityQueue<E> {

    // Number of elements in heap.
    private int currentSize;

    // The heap array.
    protected final ArrayList<E> array;

    /**
     * Construct a new empty binary heap.
     */
    public BinaryHeap() {
        this.currentSize = 0;
        this.array = new ArrayList<E>();
    }

    /**
     * Construct a copy of the given heap.
     * 
     * @param heap Binary heap to copy.
     */
    public BinaryHeap(BinaryHeap<E> heap) {
        this.currentSize = heap.currentSize;
        this.array = new ArrayList<E>(heap.array);
    }

    /**
     * Set an element at the given index.
     * 
     * @param index Index at which the element should be set.
     * @param value Element to set.
     */
    private void arraySet(int index, E value) {
        if (index == this.array.size()) {
            this.array.add(value);
        }
        else {
            this.array.set(index, value);
        }
    }

    /**
     * @return Index of the parent of the given index.
     */
    protected int indexParent(int index) {
        return (index - 1) / 2;
    } 

    /**
     * @return Index of the left child of the given index.
     */
    protected int indexLeft(int index) {
        return index * 2 + 1;
    }

    /**
     * Internal method to percolate up in the heap.
     * 
     * @param index Index at which the percolate begins.
     */
    private void percolateUp(int index) {
        E x = this.array.get(index);

        for (; index > 0
                && x.compareTo(this.array.get(indexParent(index))) < 0; index = indexParent(
                        index)) {
            E moving_val = this.array.get(indexParent(index));
            this.arraySet(index, moving_val);
        }

        this.arraySet(index, x);
    }

    /**
     * Internal method to percolate down in the heap.
     * 
     * @param index Index at which the percolate begins.
     */
    private void percolateDown(int index) {
        int ileft = indexLeft(index);
        int iright = ileft + 1;

        if (ileft < this.currentSize) {
            E current = this.array.get(index);
            E left = this.array.get(ileft);
            boolean hasRight = iright < this.currentSize;
            E right = (hasRight) ? this.array.get(iright) : null;

            if (!hasRight || left.compareTo(right) < 0) {
                // Left is smaller
                if (left.compareTo(current) < 0) {
                    this.arraySet(index, left);
                    this.arraySet(ileft, current);
                    this.percolateDown(ileft);
                }
            }
            else {
                // Right is smaller
                if (right.compareTo(current) < 0) {
                    this.arraySet(index, right);
                    this.arraySet(iright, current);
                    this.percolateDown(iright);
                }
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return this.currentSize == 0;
    }

    @Override
    public int size() {
        return this.currentSize;
    }

    @Override
    public void insert(E x) {
        int index = this.currentSize++;
        this.arraySet(index, x);
        this.percolateUp(index);
    }
    
    // Fonction to find the index of an element x
    private int findIndexOf(E x, int currentIndex) {
    /* (1) On teste si l'élément est au currentIndex
     * (2) On teste si l'élément est au fils gauche du currentIndex
     * (3) On teste si l'élément est au fils droit du currentIndex
     * (4) On teste si l'élément peut être dans le sous arbre gauche
     * (5) On teste si l'élément peut être dans le sous arbre droit
     */
    	
    	int indLeft = indexLeft(currentIndex) ; 
    	int indRight = indLeft + 1 ; 
    	boolean hasLeft = indLeft < this.currentSize ; 
    	boolean hasRight = indRight < this.currentSize ; 
    	
    	if (x.compareTo(array.get(currentIndex))==0) {
    		return currentIndex ; 
    	} else if (hasLeft && x.compareTo(array.get(indLeft)) == 0) {
    		return indLeft ; 
    	} else if (hasRight && x.compareTo(array.get(indRight)) == 0) {
    		return indRight ; 
    	}
    	
    	int leftSearch = -1 ; 
    	int rightSearch = -1 ; 
    	
    	if (hasLeft && x.compareTo(array.get(indLeft))>0) {
    		leftSearch = findIndexOf(x, indLeft) ; 
    	}
    	
    	if (hasRight && x.compareTo(array.get(indRight))>0) {
    		rightSearch = findIndexOf(x, indRight) ; 
    	}
    	
    	if (rightSearch == -1 && leftSearch == -1) {
    		return -1 ; 
    	}
    	
    	if (rightSearch != -1) {
    		return rightSearch ; 
    	}
    	
    	return leftSearch ; 
    } 
    
    
    @Override
    public void remove(E x) throws ElementNotFoundException {
        // If there is no element in the heap
    	if (this.isEmpty()) {
        	throw new ElementNotFoundException(x) ; 
        }
        
    	// First step = find the index of the node to be removed
    	// Exception raised if element not found 
    	int index = findIndexOf(x, 0) ; 
    	if (index == -1) {
    		throw new ElementNotFoundException(x) ; 
    	}
    	
    	// Second step = delete node 
    	// The last element switched with the element to be removed 
    	E y = array.get(currentSize-1) ; 
    	arraySet(index, y) ; 
    	// The size is decreased 
    	this.currentSize-- ; 
        
    	// Percolate up or down 
    	if (index != 0 && y.compareTo(array.get(indexParent(index))) <0) {
    		percolateUp(index) ; 
    	} else {
    		percolateDown(index) ;
    	}
    }
    
    
    public boolean isValid() {
    	boolean valid = false ; 
    	
    	// Test if the heap is empty
    	if (this.isEmpty()) {
    		valid = true ; 
    	} 
    	
    	/* Heap is valid if : 
    	 * (1) the key of each node is less than or equal to the key of his child's node
    	 * (2) all levels of the tree, except the last one, are fully filled 
    	 */
    	else {
    		// (1) the key of each node is less than or equal to the key of his child's node //
    		valid = true ; 
    		for (int i=0; i<this.currentSize; i++) {
    			int iLeft = indexLeft(i) ; 
    			int iRight = indexLeft(i) + 1 ; 
    			boolean hasLeft = iLeft < this.currentSize ; 
    			boolean hasRight = iRight < this.currentSize ; 
    			
    			if (hasLeft) {
    				if (iLeft < i) {
    					valid = false ; 
    				}
    			}
    			else if (hasRight) {
    				if (iRight < i) {
    					valid = false ;
    				}
    			}
    			
    		}
    		
    		// (2) all levels of the tree, except the last one, are fully filled //
    		/* To verify that the the levels are all fully filled we have to check if :
    		 * (2.1) if a node has a right child, he has to have a left child
    		 * (2.2) if a right node has a left child, the left child of his parent node must have a right child
    		 */
    		/* for (int i=0 ; i<this.currentSize ; i++) {
    			int iLeft = indexLeft(i) ; 
    			int iRight = indexLeft(i) + 1 ; 
    			int iParent = indexParent(i) ; 
    			boolean hasLeft = iLeft < this.currentSize ; 
    			boolean hasRight = iRight < this.currentSize ; 
    			
    			// (2.1) //
    			if (hasRight && !hasLeft) {
    				valid = false ; 
    			}
    			
    			// (2.2) //  
    			boolean rightNode = (i%2==1) ; 
    			if (rightNode && hasLeft && !(indexLeft(indexLeft(iParent))+1<this.currentSize)) {
    				valid = false ; 
    			}  
    		} */ 
    		
    	}
    	
    	return valid ; 
    }
    
    

    @Override
    public E findMin() throws EmptyPriorityQueueException {
        if (isEmpty())
            throw new EmptyPriorityQueueException();
        return this.array.get(0);
    }

    @Override
    public E deleteMin() throws EmptyPriorityQueueException {
        E minItem = findMin();
        E lastItem = this.array.get(--this.currentSize);
        this.arraySet(0, lastItem);
        this.percolateDown(0);
        return minItem;
    }

    /**
     * Creates a multi-lines string representing a sorted view of this binary heap.
     * 
     * @return a string containing a sorted view this binary heap.
     */
    public String toStringSorted() {
        return BinaryHeapFormatter.toStringSorted(this, -1);
    }

    /**
     * Creates a multi-lines string representing a sorted view of this binary heap.
     * 
     * @param maxElement Maximum number of elements to display. or {@code -1} to
     *                   display all the elements.
     * 
     * @return a string containing a sorted view this binary heap.
     */
    public String toStringSorted(int maxElement) {
        return BinaryHeapFormatter.toStringSorted(this, maxElement);
    }

    /**
     * Creates a multi-lines string representing a tree view of this binary heap.
     * 
     * @return a string containing a tree view of this binary heap.
     */
    public String toStringTree() {
        return BinaryHeapFormatter.toStringTree(this, Integer.MAX_VALUE);
    }

    /**
     * Creates a multi-lines string representing a tree view of this binary heap.
     * 
     * @param maxDepth Maximum depth of the tree to display.
     * 
     * @return a string containing a tree view of this binary heap.
     */
    public String toStringTree(int maxDepth) {
        return BinaryHeapFormatter.toStringTree(this, maxDepth);
    }

    @Override
    public String toString() {
        return BinaryHeapFormatter.toStringTree(this, 8);
    }

}
