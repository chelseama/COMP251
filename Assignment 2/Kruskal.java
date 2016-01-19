import java.util.*;

public class Kruskal{

    public static WGraph kruskal(WGraph g){
      WGraph MSTgraph = new WGraph();
      ArrayList<Edge> sorted_edges = g.listOfEdgesSorted();
      int num_nodes = g.getNbNodes();
      DisjointSets disj_nodes = new DisjointSets(num_nodes);
      int i = 0;
      while (i<sorted_edges.size()) {
        if(IsSafe(disj_nodes, sorted_edges.get(i))) {
          MSTgraph.addEdge(sorted_edges.get(i));
          int n0 = sorted_edges.get(i).nodes[0];
          int n1 = sorted_edges.get(i).nodes[1];
          disj_nodes.union(n0,n1);
        }
        i++;
      }
      return MSTgraph;
            
    
    }

    public static Boolean IsSafe(DisjointSets p, Edge e){
      boolean IsSafe = false;
      int n0 = e.nodes[0];
      int n1 = e.nodes[1];
      if (p.find(n0) != p.find(n1)) {
        IsSafe = true;
      }
      
      
        return IsSafe;
    
    }

    public static void main(String[] args){

        String file = args[0];
        WGraph g = new WGraph(file);
        WGraph t = kruskal(g);
        System.out.println(t);

   } 
}