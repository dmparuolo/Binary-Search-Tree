/* CS 314 STUDENTS: FILL IN THIS HEADER.
 *
 * Student information for assignment:
 *
 *  On my honor, Dominic Paruolo, this programming assignment is my own work
 *  and I have not provided this code to any other student.
 *
 *  UTEID: dmp3588
 *  email address: dominicparuolo78@gmail.com
 *  TA name: Nidhi
 *  Number of slip days I am using: 1
 */

import java.util.ArrayList;
import java.util.List;

/**
 * Shell for a binary search tree class.
 * @author scottm
 * @param <E> The data type of the elements of this BinarySearchTree.
 * Must implement Comparable or inherit from a class that implements
 * Comparable.
 *
 */
public class BinarySearchTree<E extends Comparable<? super E>> {

    private BSTNode<E> root;
    // CS314 students. Add any other instance variables you want here
    private int size;
    
    // CS314 students. Add a default constructor here if you feel it is necessary.
    
    /**
     *  Add the specified item to this Binary Search Tree if it is not already present.
     *  <br>
     *  pre: <tt>value</tt> != null<br>
     *  post: Add value to this tree if not already present. Return true if this tree
     *  changed as a result of this method call, false otherwise.
     *  @param value the value to add to the tree
     *  @return false if an item equivalent to value is already present
     *  in the tree, return true if value is added to the tree and size() = old size() + 1
     */
    public boolean add(E value) {
    	if (value == null) {
    		throw new IllegalArgumentException("Violation of precondition: add."
    				+ " Parameter must not be null.");
    	}
    	//this code was from the lecture
    	int oldSize = size;
    	//adds new node if not already present
    	root = addHelp(root, value);
    	//finds if anything was added
    	return oldSize != size;
    }
    
    
    /**
     * Helper method for add that adds the given value to this tree.
     * @param node, the current node.
     * @param value, the value to be added.
     * @return the new node to be added if not already present.
     */
    private BSTNode<E> addHelp(BSTNode<E> node, E value) {
    	//this code was from the lecture
    	//if the spot for the value to be added has been found.
    	if (node == null) {
    		size++;
    		//the new node to be added to this tree
    		return new BSTNode<E> (value);
    	}
    	int direction = value.compareTo(node.data);
    	//choose left or right path
    	if (direction < 0) { //left path
    		node.left = addHelp(node.left, value);
    	} else if (direction > 0) { //right path
    		node.right = addHelp(node.right, value);
    	}
    	//the next node in the path to not break the tree
    	return node;
    }

    /**
     *  Remove a specified item from this Binary Search Tree if it is present.
     *  <br>
     *  pre: <tt>value</tt> != null<br>
     *  post: Remove value from the tree if present, return true if this tree
     *  changed as a result of this method call, false otherwise.
     *  @param value the value to remove from the tree if present
     *  @return false if value was not present
     *  returns true if value was present and size() = old size() - 1
     */
    public boolean remove(E value) {
    	//this code was from the lecture
    	int oldSize = size;
    	//removes the given value from the tree if present
    	root = removeHelp(root, value);
    	//finds if anything was removed
        return oldSize != size;
    }


    /**
     * Helper method for remove that removes the given value from the tree if it is present.
     * @param node, the node currently at in the tree.
     * @param value, the value to remove.
     * @return
     */
    private BSTNode<E> removeHelp(BSTNode<E> node, E value){
    	//this code was from the lecture
    	//if current node is null then nothing happens
    	if (node != null) {
    		int direction = value.compareTo(node.data);
    		if (direction < 0) {
    			//if the value is in the left subtree of this node
    			node.left = removeHelp(node.left, value);
    		} else if (direction > 0) {
    			//if the value is in the right subtree of this node
    			node.right = removeHelp(node.right, value);
    		} else {
    			//this node contains the data of value
    			size--;
    			if (node.left == null && node.right == null) {
    				//this node is a leaf
    				node = null;
    			} else if (node.right == null) {
    				//nodes left child cannot be null
    				node = node.left;
    			} else if (node.left == null) {
    				//nodes right child cannot be null
    				node = node.right;
    			} else {
    				//node has two children
    				//replace this node with min of right subtree
    				//remove the min of the right subtree
    				node.data = minHelp(node.right);
    				node.right = removeHelp(node.right, node.data);
    				//re increment size to avoid logic error
    				size++;
    			}
    		}
    	}
    	return node;
    }
    
    
    /**
     * Finds and returns the smallest data in this
     * tree from the point of the given node.
     * @param node, the starting place in this tree.
     * @return the smallest data in this tree from the point
     * of the given node.
     */
    private E minHelp(BSTNode<E> node) {
    	//finds the left most node or smallest data
    	while (node.left != null) {
    		node = node.left;
    	}
    	//the min value
        return node.data;
    }
    
    
    /**
     *  Check to see if the specified element is in this Binary Search Tree.
     *  <br>
     *  pre: <tt>value</tt> != null<br>
     *  post: return true if value is present in tree, false otherwise
     *  @param value the value to look for in the tree
     *  @return true if value is present in this tree, false otherwise
     */
    public boolean isPresent(E value) {
    	if (value == null) {
    		throw new IllegalArgumentException("Violation of precondition: isPresent."
    				+ " Parameter must not be null.");
    	}
    	//finds if target value is present
    	return presentHelper(root, value);
    }
    
    
    /**
     * Helper method for isPresent that finds if the given value is present in this tree.
     * @param node, the current node the traversal is at.
     * @param value, the target value to look for.
     * @return true if the given value is in the tree and false otherwise.
     */
    private boolean presentHelper(BSTNode<E> node, E value) {
    	//if no other nodes in this path exist
    	if (node == null) {
    		return false;
    	}
    	boolean isFound = false;
    	int comparison = value.compareTo(node.data); 
    	//decides if the traversal should go down the left or right path
    	if (comparison < 0) { //if the path should continue left
    		isFound = presentHelper(node.left, value);
    	} else if (comparison > 0) { //if the path should continue right
    		isFound = presentHelper(node.right, value);
    	} else { //if the target value is found
    		isFound = true;
    	}
    	//finds if target value was found
    	return isFound;
    }


    /**
     *  Return how many elements are in this Binary Search Tree.
     *  <br>
     *  pre: none<br>
     *  post: return the number of items in this tree
     *  @return the number of items in this Binary Search Tree
     */
    public int size() {
    	//gets the size
        return size;
    }

    /**
     *  return the height of this Binary Search Tree.
     *  <br>
     *  pre: none<br>
     *  post: return the height of this tree.
     *  If the tree is empty return -1, otherwise return the
     *  height of the tree
     *  @return the height of this tree or -1 if the tree is empty
     */
    public int height() {
    	//this code was from the lecture
    	//gets height of tree
    	return heightHelp(root);
    }
    
    /**
     * Helper method for height that returns the height of the tree.
     * @param node 
     * @return the height of the tree.
     */
    private int heightHelp(BSTNode<E> node) {
    	//this code was from the lecture
    	//at max height of this path
    	if (node == null) {
    		return -1;
    	}
    	//finds the max height of both paths
    	return 1 + Math.max(heightHelp(node.left), heightHelp(node.right));
    }

    /**
     *  Return a list of all the elements in this Binary Search Tree.
     *  <br>
     *  pre: none<br>
     *  post: return a List object with all data from the tree in ascending order.
     *  If the tree is empty return an empty List
     *  @return a List object with all data from the tree in sorted order
     *  if the tree is empty return an empty List
     */
    public List<E> getAll() {
    	List<E> allData = new ArrayList<>();
    	//adds every element from this tree to the allData list
    	getAllHelp(allData, root);
        return allData;
    }
    
    
    /**
     * Helper method for getAll that adds all values of this tree to a list in ascending order.
     * @param allData, the list that will contain every element of this tree at the end
     * of this method.
     * @param current, the node in this tree to start at.
     */
    private void getAllHelp(List<E> allData, BSTNode<E> current) {
    	//no operation if null, otherwise add the rest of the tree from the current node to list
    	if (current != null) {
    		//adds the left side of node
    		getAllHelp(allData, current.left);
    		//adds the current node
    		allData.add(current.data);
    		//adds the right side of node
    		getAllHelp(allData, current.right);
    	}
    }


    /**
     * return the maximum value in this binary search tree.
     * <br>
     * pre: <tt>size()</tt> > 0<br>
     * post: return the largest value in this Binary Search Tree
     * @return the maximum value in this tree
     */
    public E max() {
    	if (size == 0) {
    		throw new IllegalArgumentException("Violation of precondtion: max."
    				+ " Size of tree must not be zero.");
    	}
    	BSTNode<E> current = root;
    	//goes as far down the right side as possible to get max value
    	while (current.right != null) {
    		current = current.right;
    	}
    	//the max value
        return current.data;
    }
    

    /**
     * return the minimum value in this binary search tree.
     * <br>
     * pre: <tt>size()</tt> > 0<br>
     * post: return the smallest value in this Binary Search Tree
     * @return the minimum value in this tree
     */
    public E min() {
    	if (size == 0) {
    		throw new IllegalArgumentException("Violation of precondtion: min."
    				+ " Size of tree must not be zero.");
    	}
    	BSTNode<E> current = root;
    	//goes as far down the left side as possible to get min value
    	while (current.left != null) {
    		current = current.left;
    	}
    	//the min value
        return current.data;
    }

    /**
     * An add method that implements the add algorithm iteratively 
     * instead of recursively.
     * <br>pre: data != null
     * <br>post: if data is not present add it to the tree, 
     * otherwise do nothing.
     * @param data the item to be added to this tree
     * @return true if data was not present before this call to add, 
     * false otherwise.
     */
    public boolean iterativeAdd(E data) {
    	if (data == null) {
    		throw new IllegalArgumentException("Violation of precondtion: iterativeAdd."
    				+ " Parameter must not be null.");
    	}
    	//if its an empty tree
    	if (root == null) {
    		root = new BSTNode<>(data);
    	}
       	BSTNode<E> current = root;
    	//traverses tree to find if value is already present or to find spot to place new value
    	while (!current.data.equals(data)) {
    		int comparison = data.compareTo(current.data);
    		if (comparison < 0) { //left path
    			//finds if next spot is target location
    			if (current.left == null) {
    				size++;
    				current.left = new BSTNode<>(data);
    				return true;
    			}
    			//if not target location
    			current = current.left;
    		} else if (comparison > 0) { //right path
    			//finds if next spot is target location
    			if (current.right == null) {
    				size++;
    				current.right = new BSTNode<>(data);
    				return true;
    			}
    			//if not target location
    			current = current.right;
    		}
    	}
    	//if data is already in tree
        return false;
    }


    /**
     * Return the "kth" element in this Binary Search Tree. If kth = 0 the
     * smallest value (minimum) is returned.
     * If kth = 1 the second smallest value is returned, and so forth.
     * <br>pre: 0 <= kth < size()
     * @param kth indicates the rank of the element to get
     * @return the kth value in this Binary Search Tree
     */
    public E get(int kth) {
    	if (kth < 0 || kth >= size) {
    		throw new IllegalArgumentException("Violation of precondition: get."
    				+ " Parameter must be greater than zero and less than the"
    				+ " size of this tree.");
    	}
    	//represents the kth value currently at
    	int[] num = {-1};
    	//gets the data of the kth element
    	E data = getHelp(kth, num, root);
        return data;
    }
    
    
    /**
     * Helper method for get that finds and returns the kth element
     * in this tree.
     * @param kth, the rank of the element to get.
     * @param num, the current rank of the current element.
     * @param node, the current node.
     * @return the kth element of this tree.
     */
    private E getHelp(int kth, int[] num, BSTNode<E> node) {
    	//will hold the kth element of the tree
    	E val = null;
    	//if current node is null then no operation
    	if (node != null) {
    		//if the kth element is to the left of the current node
    	    val = getHelp(kth, num, node.left);
    	    //ensures the kth element has not been found yet
    		if (val == null ) {
    			//updates current rank of element in tree
    			num[0] += 1;
    			//checks if current element is kth element
    			if (num[0] == kth) {
    				val = node.data;
    			}
    		}
    		//ensures the kth element wasn't already found
    		if (val == null) {
    			//if the kth element is to the right of the current node
    			val = getHelp(kth, num, node.right);
    		}
    	}
    	return val;
    }
    
    
    /**
     * Return a List with all values in this Binary Search Tree 
     * that are less than the parameter <tt>value</tt>.
     * <tt>value</tt> != null<br>
     * @param value the cutoff value
     * @return a List with all values in this tree that are less than 
     * the parameter value. If there are no values in this tree less 
     * than value return an empty list. The elements of the list are 
     * in ascending order.
     */
    public List<E> getAllLessThan(E value) {
    	if (value == null) {
    		throw new IllegalArgumentException("Violation of precondition: getAllLessThan."
    				+ " Parameter must not be null");
    	}
    	List<E> allData = new ArrayList<>();
    	//adds all values less than the given value to allData list
    	allLessHelper(allData, root, value);
        return allData;
    }
    
    
    /**
     * Helper method for getAllLessThan that adds all elements less than the given value
     * to the allData list.
     * @param allData, the list that the values are being added to.
     * @param current, the node in the tree currently at.
     * @param value, the value given to be less than.
     */
    private void allLessHelper(List<E> allData, BSTNode<E> current, E value) {
    	//no operation if null, otherwise add all data in this tree from this node less than
    	//value to the list
    	if (current != null) {
    		//checks all values left of this node
    		allLessHelper(allData, current.left, value);
    		//if the current value is greater than the target then every value to the right
    		//will also be greater
    		if (current.data.compareTo(value) < 0) {
    			//adds the current value
    			allData.add(current.data);
    			//checks all values right of this node
    			allLessHelper(allData, current.right, value);
    		}
    	}
    }


    /**
     * Return a List with all values in this Binary Search Tree 
     * that are greater than the parameter <tt>value</tt>.
     * <tt>value</tt> != null<br>
     * @param value the cutoff value
     * @return a List with all values in this tree that are greater
     *  than the parameter value. If there are no values in this tree
     * greater than value return an empty list. 
     * The elements of the list are in ascending order.
     */
    public List<E> getAllGreaterThan(E value) {
    	if (value == null) {
    		throw new IllegalArgumentException("Violation of precondition: getAllGreaterThan."
    				+ " Parameter must not be null.");
    	}
    	List<E> allData = new ArrayList<>();
    	//adds all values greater than the given value to allData list
    	allGreaterHelper(allData, root, value);
        return allData;
    }


    /**
     * Helper method for getAllGreaterThan that adds all elements greater than the given value
     * to the allData list.
     * @param allData, the list that the values are being added to.
     * @param current, the node in the tree currently at.
     * @param value, the value given to be greater than.
     */
    private void allGreaterHelper(List<E> allData, BSTNode<E> current, E value) {
    	//no operation if null, otherwise add all data in this tree from this node greater than
    	//value to the list
    	if (current != null) {
    		//if current value is less than target than everything to the left cannot be greater
    		if (current.data.compareTo(value) > 0) {
    		    //checks all values left of this node
    			allGreaterHelper(allData, current.left, value);
    			//adds the current value
    			allData.add(current.data);
    		}
    		//checks all values right of this node
    		allGreaterHelper(allData, current.right, value);
    	}
    }

    /**
     * Find the number of nodes in this tree at the specified depth.
     * <br>pre: none
     * @param d The target depth.
     * @return The number of nodes in this tree at a depth equal to
     * the parameter d.
     */
    public int numNodesAtDepth(int d) {
    	//gets number of nodes at target depth
        return numDepthHelper(root, d, 0);
    }
    
    
    /**
     * Helper method for numNodesAtDepth that finds the number of nodes in this tree at
     * the specified depth.
     * @param current, the node of this tree currently at.
     * @param tgtDepth, the target depth to look for nodes at.
     * @param currentDepth, the current depth of the current node.
     * @return the number of nodes at the given depth in this tree.
     */
    private int numDepthHelper(BSTNode<E> current, int tgtDepth, int currentDepth) {
    	//stop going down tree if at a null element
    	if (current == null) {
    		return 0;
    	}
    	//stop going down tree but increment depth counter if at target depth
    	if (currentDepth == tgtDepth) {
    		return 1;
    	}
    	int numAtDepth = 0;
    	//check the left side of this node for nodes at correct depth
        numAtDepth += numDepthHelper(current.left, tgtDepth, currentDepth + 1);
        //check the right side of this node for nodes at the correct depth
        numAtDepth += numDepthHelper(current.right, tgtDepth, currentDepth + 1);
        //number of nodes at correct depth
        return numAtDepth;
    }
    

    /**
     * Prints a vertical representation of this tree.
     * The tree has been rotated counter clockwise 90
     * degrees. The root is on the left. Each node is printed
     * out on its own row. A node's children will not necessarily
     * be at the rows directly above and below a row. They will
     * be indented three spaces from the parent. Nodes indented the
     * same amount are at the same depth.
     * <br>pre: none
     */
    public void printTree() {
        printTree(root, "");
    }

    private void printTree(BSTNode<E> n, String spaces) {
        if(n != null){
            printTree(n.getRight(), spaces + "  ");
            System.out.println(spaces + n.getData());
            printTree(n.getLeft(), spaces + "  ");
        }
    }

    private static class BSTNode<E extends Comparable<? super E>> {
        private E data;
        private BSTNode<E> left;
        private BSTNode<E> right;

        public BSTNode() {
            this(null);
        }

        public BSTNode(E initValue) {
            this(null, initValue, null);
        }

        public BSTNode(BSTNode<E> initLeft,
                E initValue,
                BSTNode<E> initRight) {
            data = initValue;
            left = initLeft;
            right = initRight;
        }

        public E getData() {
            return data;
        }

        public BSTNode<E> getLeft() {
            return left;
        }

        public BSTNode<E> getRight() {
            return right;
        }

        public void setData(E theNewValue) {
            data = theNewValue;
        }

        public void setLeft(BSTNode<E> theNewLeft) {
            left = theNewLeft;
        }

        public void setRight(BSTNode<E> theNewRight) {
            right = theNewRight;
        }
    }
}