import java.io.*;
import java.util.*;

public class FordFulkerson {

 
 public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
  ArrayList<Integer> Stack = new ArrayList<Integer>();
   
  ArrayList<Edge> sorted_edges = graph.listOfEdgesSorted();
  Stack.add(source);
  int i = 0;
  while(i<sorted_edges.size()-1) {
    source = graph.getEdges().get(i).nodes[0];
    Integer adj_node = graph.getEdges().get(i).nodes[1];
    
    if(!Stack.contains(adj_node) && (adj_node != destination)) {
      Stack.add(adj_node);
    }
    i++;
  }
 
  return Stack;
 }
 
 
 
 public static void fordfulkerson(Integer source, Integer destination, WGraph graph, String filePath){
  String answer="";
  String myMcGillID = "260515648"; //Please initialize this variable with your McGill ID
  int maxFlow = 0;
  
  WGraph res_graph = new WGraph(graph);
  ArrayList<Integer> path = pathDFS(source,destination,res_graph);
  // there is augmenting path in residual graph
  while(!path.contains(source) && (path.size() != 1)) {
   
    //pathDFS returns path that contains source,...,destination
    if(path.contains(destination)) {
      
      for(int j=0; j< path.size(); j++) {
        
        int min_capacity = res_graph.getEdge(path.get(j),path.get(j+1)).weight;
        maxFlow = maxFlow + min_capacity;
        graph.getEdge(path.get(j),path.get(j+1)).weight += min_capacity;
        res_graph.getEdge(path.get(j),path.get(j+1)).weight -= min_capacity;
       
        }
      
      
    }
    if(!path.contains(destination)) {
      for(int j=0; j< path.size(); j++) {
        res_graph.getEdge(path.get(j),path.get(j+1)).weight = 0;
      }
    }
    
  }
  
                           
      
      
  
  
  answer += maxFlow + "\n" + graph.toString(); 
  writeAnswer(filePath+myMcGillID+".txt",answer);
  System.out.println(answer);
 }
  
 
 
 public static void writeAnswer(String path, String line){
  BufferedReader br = null;
  File file = new File(path);
  // if file doesnt exists, then create it
  try {
  if (!file.exists()) {
   file.createNewFile();
  }
  FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
  BufferedWriter bw = new BufferedWriter(fw);
  bw.write(line+"\n"); 
  bw.close();
  } catch (IOException e) {
   e.printStackTrace();
  } finally {
   try {
    if (br != null)br.close();
   } catch (IOException ex) {
    ex.printStackTrace();
   }
  }
 }
 
  public static void main(String[] args){
   String file = args[0];
   File f = new File(file);
   WGraph g = new WGraph(file);
   fordfulkerson(g.getSource(),g.getDestination(),g,f.getAbsolutePath().replace(".txt",""));
  }
}