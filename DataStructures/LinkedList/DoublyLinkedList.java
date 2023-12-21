import java.util.NoSuchElementException;

public class DoublyLinkedList<T> {

    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    /**
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(data);
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Cannot put data when index is less than 0 or greater than size");
        } else if (data == null) {
            throw new IllegalArgumentException("Cannot put null data in LinkedList");
        } else if (size == 0) {
            this.head = newNode;
            this.tail = newNode;
        } else if (index == 0) {
            head.setPrevious(newNode);
            newNode.setNext(head);
            newNode.setPrevious(null);
            head = newNode;
        } else if (index == size) {
            tail.setNext(newNode);
            newNode.setPrevious(tail);
            newNode.setNext(null);
            tail = newNode;
        } else {
            DoublyLinkedListNode<T> curNode = head;
            if (index >= size / 2) {
                curNode = tail;
                int curIdx = size - 1;
                while (curIdx != index) {
                    curNode = curNode.getPrevious();
                    curIdx--;
                }
            } else {
                int curIdx = 0;
                while (curIdx != index) {
                    curNode = curNode.getNext();
                    curIdx++;
                }
            }
            newNode.setNext(curNode);
            newNode.setPrevious(curNode.getPrevious());
            curNode.getPrevious().setNext(newNode);
            curNode.setPrevious(newNode);
        }
        size++;
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot put null data in LinkedList");
        } else {
            addAtIndex(0, data);
        }
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot put null data in LinkedList");
        } else {
            addAtIndex(size, data);
        }
    }

    /**
     * Removes and returns the element at the specified index. Don't forget to
     * consider whether traversing the list from the head or tail is more
     * efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        DoublyLinkedListNode<T> curNode;
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Cannot remove data when index is less than 0 or greater than size");
        } else if (index == 0) {
            curNode = head;
            DoublyLinkedListNode<T> nextNode = curNode.getNext();
            nextNode.setPrevious(null);
            curNode.setNext(null);
            head = nextNode;
        } else if (index == size - 1) {
            curNode = tail;
            DoublyLinkedListNode<T> prevNode = tail.getPrevious();
            curNode.setPrevious(null);
            prevNode.setNext(null);
            tail = prevNode;
        } else {
            curNode = head;
            if (index >= size / 2) {
                curNode = tail;
                int curIdx = size - 1;
                while (curIdx != index) {
                    curNode = curNode.getPrevious();
                    curIdx--;
                }
            } else {
                int curIdx = 0;
                while (curIdx != index) {
                    curNode = curNode.getNext();
                    curIdx++;
                }
            }
            DoublyLinkedListNode<T> prevNode = curNode.getPrevious();
            DoublyLinkedListNode<T> nextNode = curNode.getNext();
            curNode.setNext(null);
            curNode.setPrevious(null);
            prevNode.setNext(nextNode);
            nextNode.setPrevious(prevNode);
        }
        size--;

        return curNode.getData();
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        return removeAtIndex(0);
    }

    /**
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        return removeAtIndex(size - 1);
    }

    /**
     * Returns the element at the specified index. Don't forget to consider
     * whether traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Cannot remove data when index is less than 0 or greater than size");
        } else if (head == null) {
            return null;
        } else if (index == 0) {
            return head.getData();
        } else if (index == size - 1) {
            return tail.getData();
        } else {
            DoublyLinkedListNode<T> curNode = head;
            if (index >= size / 2) {
                curNode = tail;
                int curIdx = size - 1;
                while (curIdx != index) {
                    curNode = curNode.getPrevious();
                    curIdx--;
                }
            } else {
                int curIdx = 0;
                while (curIdx != index) {
                    curNode = curNode.getNext();
                    curIdx++;
                }
            }

            return curNode.getData();
        }
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        this.head = null;
        this.tail = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(1) if data is in the tail and O(n) for all other cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove null data from the list");
        }
        boolean found = false;
        DoublyLinkedListNode<T> curNode = tail;
        int curIdx = size - 1;
        while (curIdx >= 0) {
            if (curNode.getData().equals(data)) {
                found = true;
                break;
            }
            curNode = curNode.getPrevious();
            curIdx--;
        }
        if (!found) {
            throw new NoSuchElementException("Data is not found in the list");
        } else {
            return removeAtIndex(curIdx);
        }
    }

    /**
     * Returns an array representation of the linked list. If the list is
     * size 0, return an empty array.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        Object[] arr = new Object[size];
        if (size == 0) {
            return arr;
        } else {
            DoublyLinkedListNode<T> curNode = head;
            int idx = 0;
            while (curNode.getNext() != null) {
                arr[idx] = curNode.getData();
                idx++;
                curNode = curNode.getNext();
            }
            arr[idx] = curNode.getData();
        }

        return arr;
    }

    /**
     * Returns the head node of the list.
     *
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * @return the node at the tail of the list
     */
    public DoublyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
