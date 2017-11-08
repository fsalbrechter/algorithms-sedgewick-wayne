package chapter4.section3;

import chapter1.section3.Queue;
import chapter2.section4.PriorityQueueResize;

/**
 * Created by rene on 07/11/17.
 */
public class LazyPrimMST {

    private boolean[] marked; // minimum spanning tree vertices
    private Queue<Edge> minimumSpanningTree;
    private PriorityQueueResize<Edge> priorityQueue; // crossing (and ineligible) edges

    public LazyPrimMST(EdgeWeightedGraph edgeWeightedGraph) {
        priorityQueue = new PriorityQueueResize<>(PriorityQueueResize.Orientation.MIN);
        marked = new boolean[edgeWeightedGraph.vertices()];
        minimumSpanningTree = new Queue<>();

        visit(edgeWeightedGraph, 0); // assumes the graph is connected

        while (!priorityQueue.isEmpty()) {
            Edge edge = priorityQueue.deleteTop(); // Get lowest-weight edge from priority queue
            int vertex1 = edge.either();
            int vertex2 = edge.other(vertex1);

            // Skip if ineligible
            if(marked[vertex1] && marked[vertex2]) {
                continue;
            }

            // Add edge to the minimum spanning tree
            minimumSpanningTree.enqueue(edge);

            // Add vertex to the minimum spanning tree
            if(!marked[vertex1]) {
                visit(edgeWeightedGraph, vertex1);
            }
            if(!marked[vertex2]) {
                visit(edgeWeightedGraph, vertex2);
            }
        }
    }

    private void visit(EdgeWeightedGraph edgeWeightedGraph, int vertex) {
        // Mark vertex and add to priority queue all edges from vertex to unmarked vertices
        marked[vertex] = true;

        for(Edge edge : edgeWeightedGraph.adjacent(vertex)) {
            if(!marked[edge.other(vertex)]) {
                priorityQueue.insert(edge);
            }
        }
    }

    public Iterable<Edge> edges() {
        return minimumSpanningTree;
    }

    public double weight() {
        // Will be done in exercise 4.3.31
        return 0;
    }

}
