package edu.iastate.cs228.hw3;

import java.util.*;

/**
 * Implementation of the list interface based on linked nodes
 * that store multiple items per node.  Rules for adding and removing
 * elements ensure that each node (except possibly the last one)
 * is at least half full.
 *
 * @author Max Mayer-Mader
 */
public class StoutList<E extends Comparable<? super E>> extends AbstractSequentialList<E>
{
  /**
   * Default number of elements that may be stored in each node.
   */
  private static final int DEFAULT_NODESIZE = 4;
  
  /**
   * Number of elements that can be stored in each node.
   */
  private final int nodeSize;
  
  /**
   * Dummy node for head.  It should be private but set to public here only  
   * for grading purpose.  In practice, you should always make the head of a 
   * linked list a private instance variable.  
   */
  public Node head;
  
  /**
   * Dummy node for tail.
   */
  private Node tail;
  
  /**
   * Number of elements in the list.
   */
  private int size;
  
  /**
   * Constructs an empty list with the default node size.
   */
  public StoutList()
  {
    this(DEFAULT_NODESIZE);
  }

  /**
   * Constructs an empty list with the given node size.
   * @param nodeSize number of elements that may be stored in each node, must be 
   *   an even number
   */
  public StoutList(int nodeSize)
  {
    if (nodeSize <= 0 || nodeSize % 2 != 0) throw new IllegalArgumentException();
    
    // dummy nodes
    head = new Node();
    tail = new Node();
    head.next = tail;
    tail.previous = head;
    this.nodeSize = nodeSize;
  }
  
  /**
   * Constructor for grading only.  Fully implemented. 
   * @param head
   * @param tail
   * @param nodeSize
   * @param size
   */
  public StoutList(Node head, Node tail, int nodeSize, int size)
  {
	  this.head = head; 
	  this.tail = tail; 
	  this.nodeSize = nodeSize; 
	  this.size = size; 
  }

  @Override
  public int size()
  {
    return size;
  }
  
  @Override
  public boolean add(E item)
  {
      if (item == null){
          throw new NullPointerException();
      }
      //if there is nothing in the list create a new node
      if (tail.previous == head || size() == 0) {
          //temp node
          Node temp = new Node();
          temp.addItem(item);

          //book keeping
          head.next = temp;
          temp.previous = head;
          tail.previous = temp;
          temp.next = tail;


          size++;
          return true;
      }

      //if the node is full create a new node
      else if (tail.previous.count >= nodeSize) {
          Node temp = new Node();
          temp.addItem(item);

          //book keeping
          tail.previous.next = temp;
          temp.previous = tail.previous;
          temp.next = tail;
          tail.previous = temp;

          size++;
          return true;
      } else {
          tail.previous.addItem(item);

          size++;
          return true;
      }


  }

  @Override
  public void add(int pos, E item)
  {
      if (item == null){
          throw new NullPointerException();
      }
      if (pos < 0 || pos > size){
          throw new IndexOutOfBoundsException();
      }
      NodeInfo nf = find(pos);
      Node n = nf.node;
      int off = nf.offset;

      //if the list is empty, create a new node and put X at offset 0
      if (head.next == tail || pos == size) {
        this.add(item);
      }
      // otherwise if off = 0 and one of the following two cases occurs,
      else if (off == 0 && ( n.previous.count < this.nodeSize && n.previous != head  ||  n == tail )) {
          //if n has a predecessor which has fewer than M elements (and is not the head), put X in n’s predecessor
          if (n.previous.count < this.nodeSize && n.previous != head){
              n.previous.addItem(item);
          }
          //if n is the tail node and n’s predecessor has M elements, create a new node and put X at offset 0
          else if (n == tail ){
              add(item);

          }
      }
      //otherwise if there is space in node n, put X in node n at offset off, shifting array elements as necessary
      else if(n.count < nodeSize){
          n.addItem(off, item);
      }
      //otherwise, perform a split operation: move the last M/2 elements of node n into a new successor node n', and then
      else {
          int half = nodeSize / 2;
          Node nPrime = new Node();

          nPrime.previous = n;
          nPrime.next = n.next;
          n.next.previous = nPrime;
          n.next = nPrime;

          for (int i = nodeSize - 1; i >= nodeSize - half; i--)
          {
              nPrime.addItem(0, n.data[i]);
              n.removeItem(i);
          }

          //if  off <= M/2,  put X in node n at offset off
          if (off <= nodeSize / 2)
          {
              n.addItem(off, item);

          }

          //if  off > M/2 put X in node n' at offset (off−M/2)
          else
          {
              nPrime.addItem(off - half, item);
          }



      }
      size++;
  }

  @Override
  public E remove(int pos)
  {
      if (pos < 0 || pos > size){
          throw new IndexOutOfBoundsException();
      }
      NodeInfo nf = find(pos);
      Node n = nf.node;
      int off = nf.offset;
      E removed = n.data[off];

      //if the node n containing X is the last node and has only one element, delete it;
      if (n.next == tail && n.count == 1){
            unlink(n);
      }

      //otherwise, if n is the last node (thus with two or more elements) , or if n has more than M/2
      //elements, remove X from n, shifting elements as necessary;
      else if (n.next == tail || n.count > nodeSize/2 ) {
        n.removeItem(off);
      }

      //otherwise (the node n must have at most M/2 elements), look at its successor n' (note that we don’t look at the predecessor of n)
      // and perform a merge operation as follows:
      else {
          Node nPrime = n.next;
          n.removeItem(off);

          //if the successor node n' has more than M/2 elements, move the first element from n' to n.
          //(mini-merge)
          if (nPrime.count > nodeSize/2){
              n.addItem(nPrime.data[0]);
              nPrime.removeItem(0);
          }
          //if the successor node n' has M/2 or fewer elements, then move all elements from n' to n and
          //delete n' (full merge)
          else if (nPrime.count <= nodeSize/2) {
              for (int i = 0; i < nPrime.count; i++){
                  n.addItem(nPrime.data[i]);
              }
              unlink(nPrime);
          }
      }

      size--;
      return removed;
  }

  /**
   * Sort all elements in the stout list in the NON-DECREASING order. You may do the following. 
   * Traverse the list and copy its elements into an array, deleting every visited node along 
   * the way.  Then, sort the array by calling the insertionSort() method.  (Note that sorting 
   * efficiency is not a concern for this project.)  Finally, copy all elements from the array 
   * back to the stout list, creating new nodes for storage. After sorting, all nodes but 
   * (possibly) the last one must be full of elements.  
   *  
   * Comparator<E> must have been implemented for calling insertionSort().    
   */
  public void sort()
  {
	  E[] newList = toList();

      insertionSort(newList, new EComparator());

      //delete linkedList
      head.next.previous = null;
      tail.previous.next = null;
      head.next = tail;
      tail.previous = head;

      size = 0;

      for (int i = 0; i < newList.length; i++){
          StoutList.this.add(newList[i]);
      }
  }
  
  /**
   * Sort all elements in the stout list in the NON-INCREASING order. Call the bubbleSort()
   * method.  After sorting, all but (possibly) the last nodes must be filled with elements.  
   *  
   * Comparable<? super E> must be implemented for calling bubbleSort(). 
   */
  public void sortReverse() 
  {
      E[] newList = toList();

      bubbleSort(newList);

      //delete linkedList
      head.next.previous = null;
      tail.previous.next = null;
      head.next = tail;
      tail.previous = head;

      size = 0;

      for (int i = 0; i < newList.length; i++){
          StoutList.this.add(newList[i]);
      }
  }
  
  @Override
  public Iterator<E> iterator()
  {
      return new StoutIterator();
  }

  @Override
  public ListIterator<E> listIterator()
  {
      return new StoutListIterator();
  }

  @Override
  public ListIterator<E> listIterator(int index)
  {
      return new StoutListIterator(index);
  }

    /**
     * links a new node to the linked list
     * @param n node that you're linking to
     * @param newNode node you want to link to
     */
  public void link(Node n, Node newNode){
      newNode.previous = n;
      newNode.next = n.next;
      n.next.previous = newNode;
      n.next = newNode;
  }

    /**
     * unlinks a node from the linked list
     * @param n node that you waant to unlink
     */
  public void unlink(Node n){
      n.previous.next = n.next;
      n.next.previous = n.previous;

  }
  
  /**
   * Returns a string representation of this list showing
   * the internal structure of the nodes.
   */
  public String toStringInternal()
  {
    return toStringInternal(null);
  }

  /**
   * Returns a string representation of this list showing the internal
   * structure of the nodes and the position of the iterator.
   *
   * @param iter
   *            an iterator for this list
   */
  public String toStringInternal(ListIterator<E> iter) 
  {
      int count = 0;
      int position = -1;
      if (iter != null) {
          position = iter.nextIndex();
      }

      StringBuilder sb = new StringBuilder();
      sb.append('[');
      Node current = head.next;
      while (current != tail) {
          sb.append('(');
          E data = current.data[0];
          if (data == null) {
              sb.append("-");
          } else {
              if (position == count) {
                  sb.append("| ");
                  position = -1;
              }
              sb.append(data.toString());
              ++count;
          }

          for (int i = 1; i < nodeSize; ++i) {
             sb.append(", ");
              data = current.data[i];
              if (data == null) {
                  sb.append("-");
              } else {
                  if (position == count) {
                      sb.append("| ");
                      position = -1;
                  }
                  sb.append(data.toString());
                  ++count;

                  // iterator at end
                  if (position == size && count == size) {
                      sb.append(" |");
                      position = -1;
                  }
             }
          }
          sb.append(')');
          current = current.next;
          if (current != tail)
              sb.append(", ");
      }
      sb.append("]");
      return sb.toString();
  }


  /**
   * Node type for this list.  Each node holds a maximum
   * of nodeSize elements in an array.  Empty slots
   * are null.
   */
  private class Node
  {
    /**
     * Array of actual data elements.
     */
    // Unchecked warning unavoidable.
    public E[] data = (E[]) new Comparable[nodeSize];
    
    /**
     * Link to next node.
     */
    public Node next;
    
    /**
     * Link to previous node;
     */
    public Node previous;
    
    /**
     * Index of the next available offset in this node, also 
     * equal to the number of elements in this node.
     */
    public int count;

    /**
     * Adds an item to this node at the first available offset.
     * Precondition: count < nodeSize
     * @param item element to be added
     */
    void addItem(E item)
    {
      if (count >= nodeSize)
      {
        return;
      }
      data[count++] = item;
      //useful for debugging
      //      System.out.println("Added " + item.toString() + " at index " + count + " to node "  + Arrays.toString(data));
    }
  
    /**
     * Adds an item to this node at the indicated offset, shifting
     * elements to the right as necessary.
     * 
     * Precondition: count < nodeSize
     * @param offset array index at which to put the new element
     * @param item element to be added
     */
    void addItem(int offset, E item)
    {
      if (count >= nodeSize)
      {
    	  return;
      }
      for (int i = count - 1; i >= offset; --i)
      {
        data[i + 1] = data[i];
      }
      ++count;
      data[offset] = item;
      //useful for debugging 
      //System.out.println("Added " + item.toString() + " at index " + offset + " to node: "  + Arrays.toString(data));
    }

    /**
     * Deletes an element from this node at the indicated offset, 
     * shifting elements left as necessary.
     * Precondition: 0 <= offset < count
     * @param offset
     */
    void removeItem(int offset)
    {
      E item = data[offset];
      for (int i = offset + 1; i < nodeSize; ++i)
      {
        data[i - 1] = data[i];
      }
      data[count - 1] = null;
      --count;
    }    
  }

    /**
     * Stout Iterator class implements Iterator and its methods
     */
  private class StoutIterator implements  Iterator<E>{
        ListIterator<E> si;

        public StoutIterator(){
            si = listIterator();
        }

        @Override
        public boolean hasNext() {
            return si.hasNext();
        }

        @Override
        public E next() {
            return si.next();
        }

        @Override
        public void remove() {
            si.remove();
        }

    }

    /**
     * StoutListIterator class implements ListIterator and its methods
     */
    private class StoutListIterator implements ListIterator<E>
  {
	// constants you possibly use ...
     private final static int RIGHT = 1;
     private final static int LEFT = -1;
     private final static int NONE = 0;

	// instance variables
    private Node curNode;
    private int pos;
    private int curN;
    private int curOffset;
    private int direc = NONE;

    /**
     * Default constructor 
     */
    public StoutListIterator()
    {
        this(0);

    }

    /**
     * Constructor finds node at a given position.
     * @param pos
     */
    public StoutListIterator(int pos)
    {
        if (pos < 0 || pos > size){
            throw new IndexOutOfBoundsException();
        }
        NodeInfo nf = find(pos);

        this.pos = pos;
        curNode = nf.node;
        curOffset = nf.offset;
        size = size();
    }

    @Override
    public boolean hasNext()
    {
        return pos < size;

    }

    @Override
    public E next()
    {
        //make sure there is a next
        if (hasNext()) {
            direc = RIGHT;

            //get new offset at pos
            NodeInfo nf = find(pos++);
            curOffset = nf.offset;
            curNode = nf.node;

            return curNode.data[curOffset];
        }

        else {
            throw new NoSuchElementException();
        }

    }

      @Override
      public boolean hasPrevious() {
         return pos > 0;
      }

      @Override
      public E previous() {
          if (hasPrevious()){
              direc = LEFT;

              //dec pos and then get new node and pos
              NodeInfo nf = find(--pos);
              curNode = nf.node;
              curOffset = nf.offset;

              return curNode.data[curOffset];


          } else {
              throw new NoSuchElementException();
          }
      }

      @Override
      public int nextIndex() {
          return pos;
      }

      @Override
      public int previousIndex() {
          return pos-1;
      }

      @Override
      public void remove()
      {
    	if (direc == NONE){
          throw new IllegalStateException();
        }
        if (direc == RIGHT){
            pos-=1;
            StoutList.this.remove(pos);

            NodeInfo nf = find(pos);
            curNode = nf.node;
            curOffset = nf.offset;

        } else {
            StoutList.this.remove(pos);

            NodeInfo nf = find(pos);
            curNode = nf.node;
            curOffset = nf.offset;
        }
        direc = NONE;

        //update pos and offset
        //pos--;

      }

      @Override
      public void set(E e) {
        if (direc == NONE){
            throw new IllegalStateException();
        }
        if (direc == RIGHT ) {
            if (curOffset >= 0) {
                curNode.data[curOffset] = e;
            } else {
                curNode.previous.data[curNode.previous.count - 1] = e;
            }

        } else if (direc == LEFT){
            curNode.data[curOffset] = e;


        }
      }

      @Override
      public void add(E e) {
        StoutList.this.add(pos, e);
        pos++;

        NodeInfo nf = find(pos);
        curOffset = nf.offset;
        curNode = nf.node;
        direc = NONE;



      }


  }

    /**
     * NodeInfo class
     * gives you the node and the offset and a certain pos
     */
    private class NodeInfo
    {
        public Node node;
        public int offset;
        public NodeInfo(Node node, int offset)
        {
            this.node = node;
            this.offset = offset;
        }

    }


    /**
     * returns the node and offset for the given logical index
     * @param pos of element, you want to find
     * @return the element at pos
     */
    private NodeInfo find(int pos){

        if (pos < 0 || pos > size()) {
            throw new NoSuchElementException();
        }

        Node cur = head.next;
        int index = 0;
        //run the loop as long as the current node is not the tail.
        while(cur != tail) {
            //
            if((index + cur.count) <= pos) {
                index += cur.count;
                cur = cur.next;
                continue;
            }
            return new NodeInfo( cur, pos - index);

        }
        return new NodeInfo(tail.previous, tail.previous.count);
    }

    /**
   * Sort an array arr[] using the insertion sort algorithm in the NON-DECREASING order. 
   * @param arr   array storing elements from the list 
   * @param comp  comparator used in sorting 
   */
  private void insertionSort(E[] arr, Comparator<? super E> comp)
  {
      for (int i = 1; i < arr.length; i++)
      {
          E key = arr[i];
          int j = i - 1;

          while(j > -1 && comp.compare(arr[j], key) > 0)
          {
              arr[j + 1] = arr[j];
              j--;
          }

          arr[j + 1] = key;
      }


  }
  
  /**
   * Sort arr[] using the bubble sort algorithm in the NON-INCREASING order. For a 
   * description of bubble sort please refer to Section 6.1 in the project description. 
   * You must use the compareTo() method from an implementation of the Comparable 
   * interface by the class E or ? super E. 
   * @param arr  array holding elements from the list
   */
  private void bubbleSort(E[] arr)
  {
      int n = arr.length;
      for (int i = 0; i < n - 1; i++) {
          for (int j = 0; j < n - i - 1; j++) {
              if (arr[j].compareTo(arr[j + 1]) < 0) {
                  E temp = arr[j];
                  arr[j] = arr[j + 1];
                  arr[j + 1] = temp;
              }
          }
      }
  }

    /**
     * creates new list for sorting
     * @return new list
     */
  private E[] toList()
    {
        E[] list = (E[]) new Comparable[size];
        int cnt = 0;
        ListIterator<E> li = listIterator(0);

        while (cnt < size){
            list[cnt] = li.next();
            cnt++;
        }

        return  list;
    }

    /**
     * comapartor class for comparing to objects
     * @param <E> takes in generic
     */
    class EComparator<E extends Comparable<E>> implements Comparator<E> {

        @Override
        public int compare(E o1, E o2) {
            return  o1.compareTo(o2);
        }
    }

}