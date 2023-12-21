import java.util.ArrayList;
import java.util.NoSuchElementException;

public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap when created with the default
     * constructor.
     *
     */
    public static final int INITIAL_CAPACITY = 13;

    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MinHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     * To initialize the backing array, create a Comparable array and then cast
     * it to a T array.
     */
    public MinHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * size of the passed in ArrayList (not INITIAL_CAPACITY). Index 0 should
     * remain empty, indices 1 to n should contain the data in proper order, and
     * the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        backingArray = (T[]) new Comparable[2 * data.size() + 1];
        for (int i = 0; i < data.size(); i++) {
            T curData = data.get(i);
            if (curData == null) {
                throw new IllegalArgumentException("The element in data is null");
            }
            backingArray[i + 1] = curData;
            size++;
        }
        for (int i = size / 2; i > 0; i--) {
            downHeap(i);
        }
    }

    /**
     * A method running downHeap algorithm
     * @param curIdx current index
     */
    private void downHeap(int curIdx) {
        while (curIdx * 2 <= size) {
            int leftIdx = curIdx * 2;
            int rightIdx = leftIdx + 1;
            int compareIdx = 0;
            if (rightIdx <= size) {
                if (backingArray[leftIdx].compareTo(backingArray[rightIdx]) < 0) {
                    compareIdx = leftIdx;
                } else {
                    compareIdx = rightIdx;
                }
            } else {
                compareIdx = leftIdx;
            }
            if (backingArray[curIdx].compareTo(backingArray[compareIdx])  > 0) {
                T temp = backingArray[curIdx];
                backingArray[curIdx] = backingArray[compareIdx];
                backingArray[compareIdx] = temp;
            } else {
                break;
            }
            curIdx = compareIdx;
        }
    }

    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     * The order property of the heap must be maintained after adding. You can
     * assume that no duplicate data will be passed in.
     * 
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null");
        }
        if (size == backingArray.length - 1) {
            T[] tempArr = this.backingArray;
            backingArray = (T[]) new Comparable[backingArray.length * 2];
            for (int i = 1; i < tempArr.length; i++) {
                backingArray[i] = tempArr[i];
            }
        }
        size++;
        backingArray[size] = data;
        upHeap(size);
    }

    /**
     * A method running upHeap algorithm
     * @param curIdx current index
     */
    private void upHeap(int curIdx) {
        while (curIdx != 1 && backingArray[curIdx].compareTo(backingArray[curIdx / 2]) < 0) {
            int parentIdx = curIdx / 2;
            T temp = backingArray[curIdx];
            backingArray[curIdx] = backingArray[parentIdx];
            backingArray[parentIdx] = temp;
            curIdx = parentIdx;
        }
    }

    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     * The order property of the heap must be maintained after removing.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (isEmpty()) {
            throw new NoSuchElementException("The heap is empty");
        }
        T removed = backingArray[1];
        if (size == 1) {
            backingArray[size] = null;
            size--;
        } else {
            backingArray[1] = backingArray[size];
            backingArray[size] = null;
            size--;
            downHeap(1);
        }
        return removed;
    }

    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMin() {
        if (size == 0) {
            throw new NoSuchElementException("The heap is empty");
        } else {
            return backingArray[1];
        }
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * @return the size of the list
     */
    public int size() {
        return size;
    }
}
