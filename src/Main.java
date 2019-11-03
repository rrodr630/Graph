/*
 * Author: Robert Rodriguez
 * Date: 11/03/2019
 */
import java.util.Stack;

public class Main
{
    public static void main(String[] args)
    {

        Graph myGraph = new Graph();

        myGraph.addNode("A",10);
        myGraph.addNode("B",15);
        myGraph.addNode("C",20);
        myGraph.addNode("D",1);
        myGraph.addNode("E",1);
        myGraph.addNode("F",1);
        myGraph.addNode("G",1);

        myGraph.connectNodes("A","B", 1);
        myGraph.connectNodes("A","C", 2);
        myGraph.connectNodes("B","G", 5);
        myGraph.connectNodes("B","E", 1);
        myGraph.connectNodes("E","F", 1);
        myGraph.connectNodes("F","G", 1);
        myGraph.connectNodes("C","D", 1);
        myGraph.connectNodes("D","F", 2);
        myGraph.connectNodes("D","G", 3);

        Stack<Graph.GraphNode> shortestPath = myGraph.Dijkstra(myGraph.graph.get("C"),myGraph.graph.get("A"));

        if(shortestPath.size() < 2)
        {
            System.out.println("No path found btw the nodes.");
        }
        else
        {
            while (!shortestPath.empty())
            {
                System.out.print(shortestPath.pop().name + " -> ");
            }
        }
    }
}
