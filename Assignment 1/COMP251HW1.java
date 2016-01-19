import java.io.*;
import java.util.*;

/**
 * 
 * ATTENTION: ANY CHANGES IN INITIAL CODE (INCLUDING FILE NAME, METHODS, CONSTRUCTORS etc) WILL CAUSE NOT POSITIVE MARK! 
 * HOWEVER YOU CAN CREATE YOUR OWN METHODS WITH CORRECT NAME. ONLY THIS FILE WILL BE GRADED, that is NO EXTERNAL CLASSES are allowed.
 *
 * TO STUDENT: ALL IMPORTANT PARTS ARE SELECTED "TO STUDENT" AND WRITTEN IN HEADERS OF METHODS. * 
 * @author AlexanderButyaev
 *
 */
public class COMP251HW1 {
 //Example for 10%, 30%, 50%, 70%, 80%, 90%, 100%, 120%, 150%, 200% --> This has to be updated
 public double[] ns = {0.1, 0.3, 0.5, 0.7, 0.8, 0.9, 1, 1.2, 1.5, 2.0};
 /*Fields / methods for grading BEGIN*/
 private HashMap<Integer,String> pathMap;
 
 public HashMap<Integer, String> getPaths() {
  return pathMap;
 }

 public void setPaths(HashMap<Integer, String> pathMap) {
  this.pathMap = pathMap;
 }
 /*Fields / methods for grading END*/
 
 /**
  * method generateRandomNumbers generates array of random numbers (double primitive) of size = "size" and w, which limits generated random number by 2^w-1 
  * @param w - integer number, which limits generated random number by 2^w-1
  * @param size - size of the resulting array
  * @return double[]
  */
 public double[] generateRandomNumbers(int w, int size) {
  double[] resultArray = new double[size];
  if (getPaths() != null) { //THIS PART WILL BE USED FOR GRADING
   String path = getPaths().get(size);
   File file = new File (path);
   Scanner scanner;
   try {
    scanner = new Scanner(file);
    int i = 0;
    while (scanner.hasNextLine() && i < resultArray.length) {
     resultArray[i] = Double.parseDouble(scanner.nextLine());
    }
   } catch (FileNotFoundException e) {
    e.printStackTrace();
   }
  } else {
   
   for (int i = 0; i < size; i++) {
    resultArray[i] = Math.floor(Math.random()*(Math.pow(2, w)-1)); //cast to int to make it Integer
   } 
  }
  return resultArray;
 }

 public double generateRandomNumberiInRange(double min, double max) {
  double res = min;
  while (res == min) {
   res = Math.floor(min + Math.random() * (max - min));
  }
  return res;
 }
 
 /**
  * method generateCSVOutputFile generates CSV File which contains row of x (first element is identificator "X"), 
  * and one row for every experiment (ys - id with set of values)
  * Looks like this:
  * ================
  *  X,1,2,3
  *  E1,15,66,34
  *  E2,16,15,14
  *  E3,99,88,77
  * ================
  *  
  * @param filePathName - absolute path to the file with name (it will be rewritten or created)
  * @param x - values along X axis, eg 1,2,3,4,5,6,7,8
  * @param ys - values for Y axis with the name of the experiment for different plots.
  */
 public void generateCSVOutputFile(String filePathName, double[] x, HashMap<String, double[]> ys) {
  File file = new File(filePathName);
  FileWriter fw;
  try {
   fw = new FileWriter(file);
   fw.append("X");
   for (double d: x) {
    fw.append("," + d);
   }
   fw.append("\n");
   for (Map.Entry<String, double[]> entry: ys.entrySet()) {
    fw.append(entry.getKey());
    double[] dTemp = entry.getValue();
    for (int i = 0, len = dTemp.length;i < len; i++) {
     fw.append(","+dTemp[i]);
    }
    fw.append("\n");
   }
   fw.flush();
   fw.close();
  } catch (IOException e) {
   e.printStackTrace();
  }
 }
 
 
 
 /**
  * divisionMethod is the main method for division method in hashing problem. It creates specific array ys, iterates over the set of n (defined by ns field) and for every n adds in ys array particular number of collisions
  * 
  * It requires arguments:
  * d - division factor, m - number of slots, w - integer number (required for random number generator)  
  * 
  * It returns an array of number of collisions (for all n in range from 10% to 200% of m) - later in plotting phase - it is Y values for divisionMethod
  * @param d
  * @param m
  * @param w
  * @return ys for division method {double[]}
  */
 public double[] divisionMethod (int d, int m, int w) {
  double[] ys = new double[ns.length];
  for (int it = 0, len = ns.length; it < len; it++) {
   int n = (int)(ns[it]*m);
   ys[it] = divisionMethodImpl (d, n, w);
  }
  return ys;
 }
 
 /**
  * divisionMethodImpl is the particular implementation of the division method.
  * 
  * It requires arguments:
  * d - division factor, n - number of key to insert, w - integer number (required for random number generator)
  * @param d
  * @param n
  * @param w
  * @return number of collision for particular configuration {double}
  */
 public double divisionMethodImpl (int d, int n, int w) {
   //initialize number of collision as 0
   double num_collision=0;
   //creating a hash table for division method
   int array[] = new int[(int)Math.pow(2,d)];
   //initially all arrays are 0 because there is no collision
   for (int j=0; j<=(int)Math.pow(2,d); j++) {
     array[j] = 0;
   }
   //generates a random number for key and hash value with each key
   for (int i=0; i<n; i++) {
     int key = (int)generateRandomNumberiInRange(0, Math.pow(2,w));
     int function = key % (int)Math.pow(2, d);
     array[function]++;
   }
   /*if the array has value greater than 1, more than 2 elements have the same hash value
    *so count the number of collision by adding the number of elements that have the same has value
    */
   for (int k=0; k<=(int)Math.pow(2,d); k++) {
     if (array[k]>1) {
       num_collision=num_collision + (array[k]-1);
     }
   }
     return num_collision;
 }
    
 /**
  * multiplicationMethod is the main method for multiplication method in hashing problem. It creates specific array ys, specifies A under with some validations, iterates over the set of n (defined by ns field) and for every n adds in ys array particular number of collisions
  * 
  * It requires arguments:
  * m - number of slots, d and w - are such integers, that w > d
  * 
  * It returns an array of number of collisions (for all n in range from 10% to 200% of m) - later in plotting phase - it is Y values for multiplicationMethod
  * @param d
  * @param m
  * @param w
  * @return ys for multiplication method {double[]}
  */
 public double[] multiplicationMethod (int d, int m, int w) {
  double[] ys = new double[ns.length];
  double y;
  double A = 0; // This line should be updated to assign a valid value to A
  for (int it = 0, len = ns.length; it < len; it++) {
   int n = (int)(ns[it]*m);
   y = multiplicationMethodImpl (d, n, w, A);
   if (y < 0) return null;
   ys[it] = y;
  }
  return ys;
 }
 
 
 /**
  * multiplicationMethodImpl is the particular implementation of the multiplication method.
  * 
  * It requires arguments:
  * n - number of key to insert, d and w - are such integers, that w > d, A is a factor
  * @param d
  * @param n
  * @param w
  * @param A
  * @return number of collisions for particular configuration {double}
  */
 public double multiplicationMethodImpl (int d, int n, int w, double A) {
  //initialize number of collision as 0
  double num_collision=0;
  //creating a hash table for division method
   int array[] = new int[(int)Math.pow(2,d)];
   //setting the all hash value equal to 0
   for (int j=0; j<=(int)Math.pow(2,d); j++) {
     array[j] = 0;
   }
   for (int i=0; i<n; i++) {
     long key = (long)generateRandomNumberiInRange(0, Math.pow(2,w));
     int function = (int)(((long)(A * key) % ((long)Math.pow(2,w))) >> (w-d));
     array[function]++;
   }
    for (int k=0; k<=(int)Math.pow(2,d); k++) {
     if (array[k]>1) {
       num_collision=num_collision + (array[k]-1);
     }
   }
     return num_collision;
 }
   
  
  /*TO STUDENT: WRITE YOUR CODE HERE*/
  
 
 
 
 /**
  * TO STUDENT: MAIN method - WRITE/CHANGE code here (it should be compiled anyway!)
  * TO STUDENT: NUMBERS ARE RANDOM! 
  * @param args
  */
 public static void main(String[] args) {
  int w = 0, d = 0, m = 0;
  if (args!= null && args.length>2) {
   w = Integer.parseInt(args[0]);
   d = Integer.parseInt(args[1]);
   m = Integer.parseInt(args[2]);
  } else {
   System.err.println("Input should be w d m (integers). Exit(-1).");
   System.exit(-1);
  }
  
  if (w<=d) {
   System.err.println("Input should contain w d (integers) such that w>d. Exit(-1).");
   System.exit(-1);
  }
  
  COMP251HW1 hf = new COMP251HW1();
  double[] yTemp;
  
  HashMap<String, double[]> ys = new HashMap<String, double[]>();
  System.out.println("===Division=Method==========");
  yTemp = hf.divisionMethod(d, m, w);
  if (yTemp == null) {
   System.out.println("Something wrong with division method. Check your implementation, formula and all its parameters.");
   System.exit(-1);
  }
  ys.put("divisionMethod", yTemp);
  for (double y: ys.get("divisionMethod")) {
   System.out.println(y);
  }
  
  System.out.println("============================");
  System.out.println("===Multiplication=Method====");
  yTemp = hf.multiplicationMethod(d, m, w);
  if (yTemp == null) {
   System.out.println("Something wrong with division method. Check your implementation, formula and all its parameters.");
   System.exit(-1);
  }
  ys.put("multiplicationMethod", yTemp);
  
  for (double y: ys.get("multiplicationMethod")) {
   System.out.println(y);
  }
  
  double[] x = new double[hf.ns.length];
  for (int it = 0, len = hf.ns.length; it < len; it++) {
   x[it] = (int)(hf.ns[it]*m);
  }
  
  hf.generateCSVOutputFile("hashFunctionProblem.csv", x, ys);
 }
}