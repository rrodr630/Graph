/*
* Author: Robert Rodriguez
* Date: 11/03/2019
*/

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Stack;

public class Graph
{
    Hashtable<String,GraphNode> graph;

    public Graph()
    {
        graph = new Hashtable<>();
    }

    public void addNode(String nodeName, int nodeWeight)
    {
        GraphNode newGraphNode = new GraphNode(nodeName,nodeWeight);
        graph.put(nodeName,newGraphNode);
    }

    /**
     * The idea is when you add a node in adjacent nodes list, the distance from current node (this) to adjacent node
     * you just added will be at the same index position in the distance list.
     * Example: if you have 2 adjacent nodes already and you add a 3rd node, you will also add the distance at the same
     * index in the distances list.
     * @param from Name of the node to connect to.
     * @param end Name of the node to be connected with.
     * @param distance distance btw "this" node to adjacent node
     */
    public void connectNodes(String from, String end, int distance)
    {
        // Gets node "from" to access its adjacentNodes list and add node "end" to list
        if (graph.get(from) != null && graph.get(end) != null)
        {
            graph.get(from).adjacentNodes.add(graph.get(end));
            graph.get(from).distances.add(distance);
        }
        else
            return;
        // IMPORTANT: if this is an undirected graph the only change required for this method will be to call itself
        // with the parameters reversed, example: connectNodes(end,from);
    }

    /**
     * Dijkstra algorithm for shortest path possible
     * @param start starting point
     * @param end ending point
     */
    public Stack<GraphNode> Dijkstra(GraphNode start, GraphNode end)
    {
        // First step is to set all weights to a very large number and start.weight to 0
        graph.forEach( (k,v) -> v.weight += 10000 );
        start.weight = 0;

        // Use heap to store all adjacent nodes to vertex and always delete min of all of them, this improves complexity
        // from O(|V|^2) to O(|V|log|V|)
        Heap heap = new Heap(graph.size());

        heap.add(start);// add starting point

        while (!heap.isEmpty())
        {
            GraphNode current = heap.deleteMin();

            if (current.visited)
                continue;

            // iterates thru the list of adjacent nodes of current vertex
            for(int i = 0; i < current.adjacentNodes.size(); i++)
            {
                // if new shortest distance found
                if (current.adjacentNodes.get(i).weight > current.weight + current.distances.get(i))
                {
                    current.adjacentNodes.get(i).weight = current.weight + current.distances.get(i);
                    current.adjacentNodes.get(i).previous = current;
                    heap.add(current.adjacentNodes.get(i));
                }
            }
            current.visited = true;
        }
        // Linking the path in a stack
        Stack<GraphNode> stack = new Stack<>();
        GraphNode iterator = end;
        while(iterator.previous != null)
        {
            stack.push(iterator);
            iterator = iterator.previous;
        }
        // Including last item in path (start point)
        stack.push(iterator);

        // returns stack containing the path, if stack has only one element, no path was found, otherwise the shortest
        // path from "from" to "end" was found.
        return stack;
    }

    /**
     * Prints adjacent vertices to the vertex passed in as an argument.
     * @param adjacentTo Vertex from which to know its adjacent vertices
     */
    public void printAdjacentNodes(GraphNode adjacentTo)
    {
        System.out.println("Printing all adjacent nodes to " + adjacentTo.name + ":");
        for (int i = 0; i < adjacentTo.distances.size(); i++)
        {
            System.out.println(adjacentTo.adjacentNodes.get(i).name + ", "
                    + adjacentTo.distances.get(i));
        }
    }

    class GraphNode
    {
                          //Nodes at indexes: 0  1  2  3
        ArrayList<GraphNode> adjacentNodes;// A  B  C  D
        ArrayList<Integer> distances;     //  2  3  1  4
        int weight;
        String name;
        boolean visited;

        //Exclusively for Dijkstra algorithm
        GraphNode previous;

        GraphNode(String name, int weight)
        {
            this.visited = false;
            this.name = name;
            this.weight = weight;
            adjacentNodes = new ArrayList<>();
            distances = new ArrayList<>();
        }
    }
}
