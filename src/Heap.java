/*
 * Author: Robert Rodriguez
 * Date: 11/03/2019
 */
public class Heap
{
    private Graph.GraphNode[] priorityHeap;
    private int counter;

    public Heap(int maxSize)
    {
        priorityHeap = new Graph.GraphNode[maxSize];
        priorityHeap[0] = null;
        counter = 1;
    }

    /**
     * When you add, you add last in minHeap, so you percolate up
     * @param newNode new element to be inserted
     */
    public void add(Graph.GraphNode newNode)
    {
        priorityHeap[counter] = newNode;
        percolateUp();
        counter++;
    }

    /**
     * When you delete, you delete always the first element, switch last element with first and delete last. After, you
     * must percolate down.
     * @return GraphNode being deleted
     */
    public Graph.GraphNode deleteMin()
    {
        Graph.GraphNode temp = priorityHeap[1];

        priorityHeap[1] = priorityHeap[counter-1];

        counter--;

        percolateDown();

        return temp;
    }

    private void percolateDown()
    {
        // Heap is empty
        if (counter == 1)
            return;

        int index = 1;
        Graph.GraphNode temp;

        while (isLess(index, index*2) > 0 || isLess(index, (index*2)+1) > 0) // while 2i or 2i+1 have greater priority
        {
            int twoI = isLess(index, index*2);
            int twoIplusOne = isLess(index, (index*2)+1);

            // These if statements fix bug for OutOfBounds error and NullPointerException.
            if(twoI == -1)
            {
                swap(index,twoIplusOne);
                index = twoIplusOne;
            }
            else if(twoIplusOne == -1)
            {
                swap(index,twoI);
                index = twoI;
            }
            else
            {
                if (priorityHeap[twoI].weight < priorityHeap[twoIplusOne].weight)
                {
                    swap(index, twoI);
                    index = twoI;
                }

                else
                {
                    swap(index, twoIplusOne);
                    index = twoIplusOne;
                }
            }
        }
    }

    private void swap(int index, int child)
    {
        Graph.GraphNode temp = priorityHeap[index];
        priorityHeap[index] = priorityHeap[child];
        priorityHeap[child] = temp;
    }

    // Returns -1 if invalid index or value at index does have higher priority, otherwise, returns the new index where
    // the value needs to be percolated to
    private int isLess(int index, int child)
    {
        if (child >= counter) // sometimes 2i+1 tries to access not allocated memory in array
            return -1;
        else
            {
                // compares value at index with value at child
                if (priorityHeap[index].weight > priorityHeap[child].weight) // child can be 2i or 2i+1, check call line
                    return child;

                 return -1;
            }
    }

    private void percolateUp()
    {
        if (counter == 1)
            return;
        else
        {
            Graph.GraphNode temp;
            int index = counter;

            while (priorityHeap[index].weight < priorityHeap[index / 2].weight)
            {
                temp = priorityHeap[index/2];
                priorityHeap[index/2] = priorityHeap[index];
                priorityHeap[index] = temp;
                index = index/2;

                //Fixes a bug where tries to access priorityHeap[1/2] (index 0, which is null)
                if(index == 1)
                    break;
            }
        }
    }

    public boolean isEmpty()
    {
        if (counter == 1)
            return true;
        else
            return false;
    }

    // This is for testing purposes only, but I thought it would be better to leave it
    public void printHeap()
    {
        for(int i = 1; i < counter; i++)
            System.out.println(priorityHeap[i].weight);
    }
}
