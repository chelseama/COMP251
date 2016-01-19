import java.util.*;

public class BellmanFord{

    private int[] distances = null;
    private int[] predecessors = null;
    private int source;

    BellmanFord(WGraph g, int source) throws Exception{
        /* Constructor, input a graph and a source
         * Computes the Bellman Ford algorithm to populate the
         * attributes 
         *  distances - a t position "n" the distance of node "n" to the source is kept
         *  predecessors - at position "n" the predecessor of node "n" on the path
         *                 to the source is kept
         *  source - the source node
         *
         *  If the node is not reachable from the source, the
         *  distance value must be Integer.MAX_VALUE
         */
        int num_nodes = g.getNbNodes();
        distances = new int[num_nodes];
        predecessors = new int[num_nodes];
        distances[source] = 0;
        for(int i=0; i< num_nodes; i++) {
          distances[i] = Integer.MAX_VALUE;
        }
        for(int i=0; i< num_nodes; i++) {
          predecessors[i] = -Integer.MAX_VALUE;
          }
        for(int i=0; i< num_nodes; i++) {
          for(Edge e:g.getEdges()) {
            int n1 = e.nodes[0];
            int n2 = e.nodes[1];
            int w = e.weight;
            if(distances[n2] > distances[n1]+w) {
              distances[n2] = distances[n1]+w;
              predecessors[n2] = n1;
            }
        
          }
              
        }
    }

    public int[] shortestPath(int destination) throws Exception{
        /*Returns the list of nodes along the shortest path from 
         * the object source to the input destination
         * If not path exists an Error is thrown
         */
      int[] array = new int[10000];
      int j=0;
      while (destination!=-Integer.MAX_VALUE) {
        array[j] = destination;
        destination = predecessors[destination];
        j++;
      }
      int[] list_nodes = new int[j];
      for(int k=0; k<j; k++) {
        list_nodes[k] = array[j-k-1];
      }
      
        return list_nodes;
    }

    public void printPath(int destination){
        /*Print the path in the format s->n1->n2->destination
         *if the path exists, else catch the Error and 
         *prints it
         */
        try {
            int[] path = this.shortestPath(destination);
            for (int i = 0; i < path.length; i++){
                int next = path[i];
                if (next == destination){
                    System.out.println(destination);
                }
                else {
                    System.out.print(next + "-->");
                }
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public static void main(String[] args){

        String file = args[0];
        WGraph g = new WGraph(file);
        try{
            BellmanFord bf = new BellmanFord(g, g.getSource());
            bf.printPath(g.getDestination());
        }
        catch (Exception e){
            System.out.println(e);
        }

   } 
}