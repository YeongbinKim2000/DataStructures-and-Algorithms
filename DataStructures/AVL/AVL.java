import java.util.Collection;
import java.util.NoSuchElementException;

public class AVL<T extends Comparable<? super T>> {

    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {}

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null || data.contains(null)) {
            throw new IllegalArgumentException("data or any element in data cannot be null");
        } else {
            for (T element : data) {
                add(element);
            }
        }
    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        } else {
            root = addHelper(root, data);
        }
    }

    /**
     * Helper method for add
     * @param curNode current node
     * @param data data to add
     * @return the node that was added
     */
    private AVLNode<T> addHelper(AVLNode<T> curNode, T data) {
        if (curNode == null) {
            size++;
            return new AVLNode<>(data);
        } else if (data.compareTo(curNode.getData()) < 0) {
            curNode.setLeft(addHelper(curNode.getLeft(), data));
        } else if (data.compareTo(curNode.getData()) > 0) {
            curNode.setRight(addHelper(curNode.getRight(), data));
        }
        update(curNode);
        return balancedTree(curNode);
    }

    /**
     * method for the update
     * @param curNode current node
     */
    private void update(AVLNode<T> curNode) {
        int lHeight = -1;
        int rHeight = -1;
        if (curNode.getLeft() != null) {
            lHeight = curNode.getLeft().getHeight();
        }
        if (curNode.getRight() != null) {
            rHeight = curNode.getRight().getHeight();
        }
        curNode.setHeight(Math.max(lHeight, rHeight) + 1);
        curNode.setBalanceFactor(lHeight - rHeight);
    }

    /**
     * method for the left rotation
     * @param curNode current node
     * @return rotated node
     */
    private AVLNode<T> leftRotation(AVLNode<T> curNode) {
        AVLNode<T> target = curNode.getRight();
        curNode.setRight(target.getLeft());
        target.setLeft(curNode);
        update(curNode);
        update(target);

        return target;
    }

    /**
     * method for the right rotation
     * @param curNode current node
     * @return rotated node
     */
    private AVLNode<T> rightRotation(AVLNode<T> curNode) {
        AVLNode<T> target = curNode.getLeft();
        curNode.setLeft(target.getRight());
        target.setRight(curNode);
        update(curNode);
        update(target);

        return target;
    }

    /**
     * Make the tree balanced
     * @param curNode current node
     * @return balanced current node
     */
    private AVLNode<T> balancedTree(AVLNode<T> curNode) {
        if (curNode.getBalanceFactor() <= -2) {
            if (curNode.getRight().getBalanceFactor() <= 0) {
                curNode = leftRotation(curNode);
            } else {
                curNode.setRight(rightRotation(curNode.getRight()));
                curNode = leftRotation(curNode);
            }
        } else if (curNode.getBalanceFactor() >= 2) {
            if (curNode.getLeft().getBalanceFactor() >= 0) {
                curNode = rightRotation(curNode);
            } else {
                curNode.setLeft(leftRotation(curNode.getLeft()));
                curNode = rightRotation(curNode);
            }
        }
        return curNode;
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data, NOT successor. As a reminder, rotations can occur
     * after removing the predecessor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        AVLNode<T> dummy = new AVLNode<>(null);
        root = removeHelper(root, data, dummy);
        return dummy.getData();
    }

    /**
     * helper method for remove
     * @param curNode current node
     * @param data the data to remove
     * @param dummy dummy place for removed data
     * @return removed node with data
     */
    private AVLNode<T> removeHelper(AVLNode<T> curNode, T data, AVLNode<T> dummy) {
        if (curNode == null) {
            throw new NoSuchElementException("The data is not in the tree");
        } else if (data.compareTo(curNode.getData()) < 0) {
            curNode.setLeft(removeHelper(curNode.getLeft(), data, dummy));
        } else if (data.compareTo(curNode.getData()) > 0) {
            curNode.setRight(removeHelper(curNode.getRight(), data, dummy));
        } else {
            dummy.setData(curNode.getData());
            size--;
            if (curNode.getLeft() == null && curNode.getRight() == null) {
                return null;
            } else if (curNode.getLeft() != null && curNode.getRight() == null) {
                return curNode.getLeft();
            } else if (curNode.getLeft() == null && curNode.getRight() != null) {
                return curNode.getRight();
            } else {
                AVLNode<T> dummy2 = new AVLNode<>(null);
                curNode.setLeft(removePredecessor(curNode.getLeft(), dummy2));
                curNode.setData(dummy2.getData());
            }
        }
        update(curNode);
        return balancedTree(curNode);
    }

    /**
     * Method for removing predecessor
     * @param curNode current node
     * @param dummy2 dummy place for predecessor
     * @return predecessor node
     */
    private AVLNode<T> removePredecessor(AVLNode<T> curNode, AVLNode<T> dummy2) {
        if (curNode.getRight() == null) {
            dummy2.setData(curNode.getData());
            return curNode.getLeft();
        } else {
            curNode.setRight(removePredecessor(curNode.getRight(), dummy2));
            update(curNode);
            return balancedTree(curNode);
        }
    }

    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        T result = getHelper(root, data);
        if (result == null) {
            throw new NoSuchElementException("Data is not in the tree");
        }
        return result;
    }

    /**
     * Helper method for get
     * @param curNode current node
     * @param data returned data
     * @return the data in the tree equal to the parameter
     */
    private T getHelper(AVLNode<T> curNode, T data) {
        if (curNode == null) {
            return null;
        } else {
            T marked = curNode.getData();
            if (marked.compareTo(data) < 0) {
                return getHelper(curNode.getRight(), data);
            } else if (marked.compareTo(data) > 0) {
                return getHelper(curNode.getLeft(), data);
            } else {
                return marked;
            }
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        if (size == 0) {
            return false;
        }
        return containsHelper(root, data);
    }

    /**
     * helper method for contains
     * @param curNode current node
     * @param data the data to search in the tree
     * @return true if the data is contained in the tree
     */
    private boolean containsHelper(AVLNode<T> curNode, T data) {
        if (curNode == null) {
            return false;
        }
        if (curNode.getData().compareTo(data) > 0) {
            return containsHelper(curNode.getLeft(), data);
        } else if (curNode.getData().compareTo(data) < 0) {
            return containsHelper(curNode.getRight(), data);
        }
        return true;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * Should be O(1).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (size == 0) {
            return -1;
        }
        return root.getHeight();
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * The predecessor is the largest node that is smaller than the current data.
     *
     * Should be recursive.
     *
     * This method should retrieve (but not remove) the predecessor of the data
     * passed in. There are 2 cases to consider:
     * 1: The left subtree is non-empty. In this case, the predecessor is the
     * rightmost node of the left subtree.
     * 2: The left subtree is empty. In this case, the predecessor is the lowest
     * ancestor of the node containing data whose right child is also
     * an ancestor of data.
     *
     * This should NOT be used in the remove method.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *     76
     *   /    \
     * 34      90
     *  \    /
     *  40  81
     * predecessor(76) should return 40
     * predecessor(81) should return 76
     *
     * @param data the data to find the predecessor of
     * @return the predecessor of data. If there is no smaller data than the
     * one given, return null.
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T predecessor(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot find predecessor with null data");
        }
        AVLNode<T> curNode = new AVLNode<>(data);
        findPredecessor(root, curNode);

        if (curNode.getLeft() == null) {
            throw new NoSuchElementException("The data is not in the tree");
        }
        if (curNode.getRight() == null) {
            return null;
        } else {
            return curNode.getRight().getData();
        }
    }

    /**
     * method for finding predecessor
     * @param curNode current node
     * @param dummy node that is used to find predecessor
     */
    private void findPredecessor(AVLNode<T> curNode, AVLNode<T> dummy) {
        if (curNode == null) {
            return;
        }
        findPredecessor(curNode.getRight(), dummy);
        if (dummy.getRight() != null) {
            return;
        }
        if (curNode.getData().equals(dummy.getData())) {
            dummy.setLeft(curNode);
        } else if (curNode.getData().compareTo(dummy.getData()) < 0) {
            if (dummy.getLeft() == null) {
                throw new NoSuchElementException("data is not in the tree");
            }
            dummy.setRight(curNode);
            return;
        }
        findPredecessor(curNode.getLeft(), dummy);
    }

    /**
     * Returns the data in the deepest node. If there is more than one node
     * with the same deepest depth, return the rightmost (i.e. largest) node with
     * the deepest depth.
     *
     * Should be recursive.
     *
     * Must run in O(log n) for all cases.
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      3
     *        \
     *         1
     * Max Deepest Node:
     * 1 because it is the deepest node
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      4
     *        \    /
     *         1  3
     * Max Deepest Node:
     * 3 because it is the maximum deepest node (1 has the same depth but 3 > 1)
     *
     * @return the data in the maximum deepest node or null if the tree is empty
     */
    public T maxDeepestNode() {
        return maxDeepestHelper(root);
    }

    /**
     * Helper method for finding maxDeepestNode
     * @param curNode current node
     * @return the maxDeepestNode
     */
    private T maxDeepestHelper(AVLNode<T> curNode) {
        if (curNode == null) {
            return null;
        } else if (curNode.getBalanceFactor() > 0) {
            return maxDeepestHelper(curNode.getLeft());
        } else if (curNode.getBalanceFactor() < 0) {
            return maxDeepestHelper(curNode.getRight());
        } else {
            if (curNode.getHeight() == 0) {
                return curNode.getData();
            }
            return maxDeepestHelper(curNode.getRight());
        }
    }

    /**
     * Returns the root of the tree.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * @return the size of the tree
     */
    public int size() {
        return size;
    }
}
