import java.util.*;

public class BST<T extends Comparable<? super T>> {

    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null || data.contains(null)) {
            throw new IllegalArgumentException("Cannot add null data");
        } else {
            for (T element : data) {
                add(element);
            }
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data");
        } else {
            root = addHelper(root, data);
        }
    }

    /**
     * Helper method for add
     * @param curNode current BSTNode for traverse
     * @param data the data to add
     * @return the node that was added
     */
    private BSTNode<T> addHelper(BSTNode<T> curNode, T data) {
        if (curNode == null) {
            size++;
            return new BSTNode<>(data);
        } else if (data.compareTo(curNode.getData()) < 0) {
            curNode.setLeft(addHelper(curNode.getLeft(), data));
        } else if (data.compareTo(curNode.getData()) > 0) {
            curNode.setRight(addHelper(curNode.getRight(), data));
        }
        return curNode;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        BSTNode<T> dummy = new BSTNode<>(null);
        root = removeHelper(root, data, dummy);
        return dummy.getData();
    }

    /**
     * First helper method for remove
     * @param curNode current BSTNode for traverse
     * @param data the data to remove
     * @param dummy dummy place for removed data
     * @return removed node with data
     */
    private BSTNode<T> removeHelper(BSTNode<T> curNode, T data, BSTNode<T> dummy) {
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
                BSTNode<T> dummy2 = new BSTNode<>(null);
                curNode.setRight(findSuccessor(curNode.getRight(), dummy2));
                curNode.setData(dummy2.getData());
            }
        }
        return curNode;
    }

    /**
     * Second helper method for remove to find successor
     * @param curNode current BSTNode for traverse
     * @param dummy2 dummy place for successor
     * @return successor node
     */
    private BSTNode<T> findSuccessor(BSTNode<T> curNode, BSTNode<T> dummy2) {
        if (curNode.getLeft() == null) {
            dummy2.setData(curNode.getData());
            return curNode.getRight();
        } else {
            curNode.setLeft(findSuccessor(curNode.getLeft(), dummy2));
        }
        return curNode;
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot find null data");
        }
        return getHelper(root, data).getData();
    }

    /**
     * Helper method for get to get target node
     * @param curNode current BSTNode for traverse
     * @param data the data to find
     * @return target node
     */
    private BSTNode<T> getHelper(BSTNode<T> curNode, T data) {
        if (curNode == null) {
            throw new NoSuchElementException("The data is not in the tree");
        } else if (data.compareTo(curNode.getData()) < 0) {
            return getHelper(curNode.getLeft(), data);
        } else if (data.compareTo(curNode.getData()) > 0) {
            return getHelper(curNode.getRight(), data);
        }
        return curNode;
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        if (root == null) {
            return false;
        } else {
            return containsHelper(root, data);
        }
    }

    /**
     * Helper method for contains to check
     * @param curNode current BSTNode for traverse
     * @param data the data to check
     * @return true or false depends on the presence of target node
     */
    private boolean containsHelper(BSTNode<T> curNode, T data) {
        if (curNode == null) {
            return false;
        } else if (data.compareTo(curNode.getData()) < 0) {
            return containsHelper(curNode.getLeft(), data);
        } else if (data.compareTo(curNode.getData()) > 0) {
            return containsHelper(curNode.getRight(), data);
        } else {
            return true;
        }
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> orderList = new ArrayList<>();
        preorderHelper(root, orderList);
        return orderList;
    }

    /**
     * Helper method for preorder
     * @param curNode current BSTNode for traverse
     * @param list list to put data
     */
    private void preorderHelper(BSTNode<T> curNode, List<T> list) {
        if (curNode != null) {
            list.add(curNode.getData());
            preorderHelper(curNode.getLeft(), list);
            preorderHelper(curNode.getRight(), list);
        }
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> orderList = new ArrayList<>();
        inorderHelper(root, orderList);
        return orderList;
    }

    /**
     * Helper method for inorder
     * @param curNode current BSTNode for traverse
     * @param list list to put data
     */
    private void inorderHelper(BSTNode<T> curNode, List<T> list) {
        if (curNode != null) {
            inorderHelper(curNode.getLeft(), list);
            list.add(curNode.getData());
            inorderHelper(curNode.getRight(), list);
        }
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> orderList = new ArrayList<>();
        postorderHelper(root, orderList);
        return orderList;
    }

    /**
     * Helper method for postorder
     * @param curNode current BSTNode for traverse
     * @param list list to put data
     */
    private void postorderHelper(BSTNode<T> curNode, List<T> list) {
        if (curNode != null) {
            postorderHelper(curNode.getLeft(), list);
            postorderHelper(curNode.getRight(), list);
            list.add(curNode.getData());
        }
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        List<T> orderList = new ArrayList<>();
        Queue<BSTNode<T>> queue = new LinkedList<>();
        if (root == null) {
            return orderList;
        } else {
            queue.add(root);
            BSTNode<T> curNode;
            while (!queue.isEmpty()) {
                curNode = queue.poll();
                orderList.add(curNode.getData());
                if (curNode.getLeft() != null) {
                    queue.add(curNode.getLeft());
                }
                if (curNode.getRight() != null) {
                    queue.add(curNode.getRight());
                }
            }
            return orderList;
        }
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (size == 0) {
            return -1;
        }
        return heightHelper(root);
    }

    /**
     * Helper method for height
     * @param curNode current BSTNode for traverse
     * @return Node's height
     */
    private int heightHelper(BSTNode<T> curNode) {
        if (curNode == null) {
            return -1;
        } else {
            return Math.max(heightHelper(curNode.getLeft()), heightHelper(curNode.getRight())) + 1;
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Finds the path between two elements in the tree, specifically the path
     *      * from data1 to data2, inclusive of both.
     *      *
     *      * This must be done recursively.
     *      *
     *      * A good way to start is by finding the deepest common ancestor (DCA) of both data
     *      * and add it to the list. You will most likely have to split off and
     *      * traverse the tree for each piece of data adding to the list in such a
     *      * way that it will return the path in the correct order without requiring any
     *      * list manipulation later. One way to accomplish this (after adding the DCA
     *      * to the list) is to then traverse to data1 while adding its ancestors
     *      * to the front of the list. Finally, traverse to data2 while adding its
     *      * ancestors to the back of the list.
     *      *
     *      * Please note that there is no relationship between the data parameters
     *      * in that they may not belong to the same branch.
     *      *
     *      * You may only use 1 list instance to complete this method. Think about
     *      * what type of list to use considering the Big-O efficiency of the list
     *      * operations.
     *      *
     *      * This method only needs to traverse to the deepest common ancestor once.
     *      * From that node, go to each data in one traversal each. Failure to do
     *      * so will result in a penalty.
     *      *
     *      * If both data1 and data2 are equal and in the tree, the list should be
     *      * of size 1 and contain the element from the tree equal to data1 and data2.
     *      *
     *      * Ex:
     *      * Given the following BST composed of Integers
     *      *              50
     *      *          /        \
     *      *        25         75
     *      *      /    \
     *      *     12    37
     *      *    /  \    \
     *      *   11   15   40
     *      *  /
     *      * 10
     *      * findPathBetween(10, 40) should return the list [10, 11, 12, 25, 37, 40]
     *      * findPathBetween(50, 37) should return the list [50, 25, 37]
     *      * findPathBetween(75, 75) should return the list [75]
     *      *
     *      * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data1 the data to start the path from
     * @param data2 the data to end the path on
     * @return the unique path between the two elements
     * @throws java.lang.IllegalArgumentException if either data1 or data2 is
     *                                            null
     * @throws java.util.NoSuchElementException   if data1 or data2 is not in
     *                                            the tree
     */
    public List<T> findPathBetween(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("Either data1 or data2 is null");
        }
        BSTNode<T> dca = findDCA(root, data1, data2);
        if (dca == null) {
            throw new NoSuchElementException("Data1 or data2 is not in the tree");
        }
        List<T> pathList = new ArrayList<>();
        pathList.add(dca.getData());
        traverseHelper1(dca, null, dca, pathList, data1);
        traverseHelper2(dca, null, dca, pathList, data2);

        return pathList;
    }

    /**
     * Helper method for findPathBetween to find DCA
     * @param curNode current BSTNode for traverse
     * @param data1 start point in path
     * @param data2 end point in path
     * @return DCA
     */
    private BSTNode<T> findDCA(BSTNode<T> curNode, T data1, T data2) {
        if (curNode == null) {
            return null;
        }
        if (data1.compareTo(curNode.getData()) < 0 && data2.compareTo(curNode.getData()) < 0) {
            return findDCA(curNode.getLeft(), data1, data2);
        } else if (data1.compareTo(curNode.getData()) > 0 && data2.compareTo(curNode.getData()) > 0) {
            return findDCA(curNode.getRight(), data1, data2);
        }
        return curNode;
    }

    /**
     * Helper method for findPathBetween to add node at 0
     * @param curNode current BSTNode for traverse
     * @param ancestor prior node
     * @param dca deepest common ancestor
     * @param pathList list to add node's data
     * @param data target data
     */
    private void traverseHelper1(BSTNode<T> curNode, BSTNode<T> ancestor, BSTNode<T> dca, List<T> pathList, T data) {
        if (curNode == null) {
            throw new NoSuchElementException("Data1 is not in the tree");
        } else if (ancestor == null) {
            if (data.compareTo(curNode.getData()) < 0) {
                traverseHelper1(curNode.getLeft(), curNode, dca, pathList, data);
            } else if (data.compareTo(curNode.getData()) > 0) {
                traverseHelper1(curNode.getRight(), curNode, dca, pathList, data);
            }
        } else if (data.compareTo(curNode.getData()) == 0) {
            if (ancestor.getData().compareTo(dca.getData()) != 0) {
                pathList.add(0, ancestor.getData());
            }
            pathList.add(0, curNode.getData());
        } else {
            if (ancestor.getData().compareTo(dca.getData()) != 0) {
                pathList.add(0, ancestor.getData());
            }
            if (data.compareTo(curNode.getData()) > 0) {
                traverseHelper1(curNode.getRight(), curNode, dca, pathList, data);
            } else {
                traverseHelper1(curNode.getLeft(), curNode, dca, pathList, data);
            }
        }
    }

    /**
     * Helper method for findPathBetween to add node at pathList's size
     * @param curNode current BSTNode for traverse
     * @param ancestor prior node
     * @param dca deepest common ancestor
     * @param pathList list to add node's data
     * @param data target data
     */
    private void traverseHelper2(BSTNode<T> curNode, BSTNode<T> ancestor, BSTNode<T> dca, List<T> pathList, T data) {
        if (curNode == null) {
            throw new NoSuchElementException("Data2 is not in the tree");
        } else if (ancestor == null) {
            if (data.compareTo(curNode.getData()) < 0) {
                traverseHelper2(curNode.getLeft(), curNode, dca, pathList, data);
            } else if (data.compareTo(curNode.getData()) > 0) {
                traverseHelper2(curNode.getRight(), curNode, dca, pathList, data);
            }
        } else if (data.compareTo(curNode.getData()) == 0) {
            if (ancestor.getData().compareTo(dca.getData()) != 0) {
                pathList.add(pathList.size(), ancestor.getData());
            }
            pathList.add(pathList.size(), curNode.getData());
        } else {
            if (ancestor.getData().compareTo(dca.getData()) != 0) {
                pathList.add(pathList.size(), ancestor.getData());
            }
            if (data.compareTo(curNode.getData()) > 0) {
                traverseHelper2(curNode.getRight(), curNode, dca, pathList, data);
            } else {
                traverseHelper2(curNode.getLeft(), curNode, dca, pathList, data);
            }
        }
    }

    /**
     * Returns the root of the tree.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
