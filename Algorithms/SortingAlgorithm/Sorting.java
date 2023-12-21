import java.util.Comparator;
import java.util.Random;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class Sorting {

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Cannot sort null array");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Cannot sort with null comparator");
        }

        for (int i = 1; i < arr.length; i++) {
            int n = i;
            while ((n > 0) && comparator.compare(arr[n - 1], arr[n]) > 0) {
                T temp = arr[n - 1];
                arr[n - 1] = arr[n];
                arr[n] = temp;
                n--;
            }
        }
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Cannot sort null array");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Cannot sort with null comparator");
        }
        int start = 0;
        int end = arr.length - 1;
        int swappedIndex;

        while (start < end) {
            swappedIndex = start;
            for (int i = start; i < end; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    T temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    swappedIndex = i;
                }
            }
            end = swappedIndex;
            for (int i = end; i > start; i--) {
                if (comparator.compare(arr[i - 1], arr[i]) > 0) {
                    T temp = arr[i - 1];
                    arr[i - 1] = arr[i];
                    arr[i] = temp;
                    swappedIndex = i;
                }
            }
            start = swappedIndex;
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Cannot sort null array");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Cannot sort with null comparator");
        }

        int length = arr.length;
        int mid = arr.length / 2;
        T[] leftArr = (T[]) new Object[mid];
        T[] rightArr = (T[]) new Object[arr.length - mid];

        for (int i = 0; i < leftArr.length; i++) {
            leftArr[i] = arr[i];
        }
        for (int i = 0; i < rightArr.length; i++) {
            rightArr[i] = arr[i + mid];
        }
        if (arr.length > 1) {
            mergeSort(leftArr, comparator);
            mergeSort(rightArr, comparator);
        }

        int leftIdx = 0;
        int rightIdx = 0;
        int curIdx = 0;
        while (leftIdx < mid && rightIdx < length - mid) {
            if (comparator.compare(leftArr[leftIdx], rightArr[rightIdx]) <= 0) {
                arr[curIdx] = leftArr[leftIdx];
                leftIdx++;
            } else {
                arr[curIdx] = rightArr[rightIdx];
                rightIdx++;
            }
            curIdx++;
        }
        while (leftIdx < mid) {
            arr[curIdx] = leftArr[leftIdx];
            curIdx++;
            leftIdx++;
        }
        while (rightIdx < length - mid) {
            arr[curIdx] = rightArr[rightIdx];
            curIdx++;
            rightIdx++;
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null) {
            throw new IllegalArgumentException("Cannot sort null array");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Cannot sort with null comparator");
        }
        if (rand == null) {
            throw new IllegalArgumentException("Cannot sort with null rand");
        }
        quickSortHelper(arr, 0, arr.length - 1, comparator, rand);
    }

    /**
     *
     * @param arr the array that is sorted after the method runs
     * @param left the left index
     * @param right the right index
     * @param comparator the Comparator that is used to compare the data
     * @param rand the Random object that is used to select pivot
     * @param <T> data type to be sorted
     */
    private static <T> void quickSortHelper(T[] arr, int left, int right, Comparator<T> comparator, Random rand) {
        if (right - left < 1) {
            return;
        }

        int pivotIdx = rand.nextInt(right - left + 1) + left;
        T pivot = arr[pivotIdx];
        T temp = arr[left];
        arr[left] = arr[pivotIdx];
        arr[pivotIdx] = temp;

        int i = left + 1;
        int j = right;
        while (i <= j) {
            while (i <= j && comparator.compare(pivot, arr[i]) >= 0) {
                i++;
            }
            while (i <= j && comparator.compare(pivot, arr[j]) <= 0) {
                j--;
            }
            if (i <= j) {
                T temp2 = arr[i];
                arr[i] = arr[j];
                arr[j] = temp2;
                i++;
                j--;
            }
        }
        T temp3 = arr[left];
        arr[left] = arr[j];
        arr[j] = temp3;
        quickSortHelper(arr, left, j - 1, comparator, rand);
        quickSortHelper(arr, j + 1, right, comparator, rand);
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("The array is null");
        }

        LinkedList<Integer>[] bucket = (LinkedList<Integer>[]) new LinkedList[19];

        for (int i = 0; i < 19; i++) {
            bucket[i] = new LinkedList<>();
        }

        int mod = 10;
        int divNum = 1;
        boolean cnt = true;
        while (cnt) {
            cnt = false;
            for (int integer: arr) {
                int num = integer / divNum;
                if (num / 10 != 0) {
                    cnt = true;
                }
                if (bucket[num % mod + 9] == null) {
                    bucket[num % mod + 9] = new LinkedList<>();
                }
                bucket[num % mod + 9].add(integer);
            }
            int idx = 0;
            for (int i = 0; i < bucket.length; i++) {
                if (!bucket[i].isEmpty()) {
                    for (int j : bucket[i]) {
                        arr[idx] = j;
                        idx++;
                    }
                    bucket[i].clear();
                }
            }
            divNum *= 10;
        }
    }

    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot sort with null data");
        }
        PriorityQueue<Integer> pq = new PriorityQueue<>(data);
        int[] sorted = new int[data.size()];
        for (int i = 0; i < data.size(); i++) {
            sorted[i] = pq.remove();
        }

        return sorted;
    }
}
