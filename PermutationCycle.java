
import java.util.HashMap;
import java.util.Scanner;

/**
 * PermutationCycle.java
 * @author Sharon Li
 * sharonli@jhu.edu
 *
 */
public class PermutationCycle {
	
	//size of the list
	private static int list_size  = 0;
	
	//whether to list all permutations
	private static boolean disp_permutations = false;
	
	//whether to include reverse permutation
	private static boolean rev_permutation = false;
	
	//number of possible permutations
	private static int numPerm;
	
	//map to hold all the generated permutations
	private static HashMap<String, Integer> map = new HashMap<String, Integer>();
	private static HashMap<String, Integer> newMap = new HashMap<String, Integer>();
	public static void main(String[] args) {
		
		System.out.println("Please input size of permutation");
		Scanner scan = new Scanner (System.in);
		list_size = scan.nextInt();
		
		System.out.println("Include reverse permutation? (y/n)");
		String resp = scan.next();
		if(resp.equalsIgnoreCase("y")){
			rev_permutation = true;
		}
		
		System.out.println("Do you wish to see all permutations that are generated? (y/n)");
		resp = scan.next();
		if(resp.equalsIgnoreCase("y")){
			disp_permutations = true;
		}
		

		
		//find number of permutations needed
		numPerm = factorial(list_size);
		
		//output some information about size of current list
		System.out.println("Using lists of length: " + list_size);
		System.out.println("Using long cycle and short cycle");
		System.out.println("Looking for "+ numPerm + " permutations.\n");
		
		//initialize first array to {1,2,3 ...}
		Integer[] array = new Integer[list_size];
		for(int i = 0; i < list_size; i ++){
			array[i] = i+1;
		}
		
		//insert first array into map with groupNum = 0
		int groupNum = 0;
		map.put(arrayToString(array), groupNum);
		
		while(true){
			//permute each array in the map
			for(String key: map.keySet()){
				
				//only permute if it is in current group level
				if(map.get(key)!= groupNum){
					continue;
				}
				//convert the string back to an array
				array = stringToArray(key);
				
				//make a copy of current array
				Integer[] permArray = new Integer[list_size];
				
				//do long cycle
				permArray[0] = array[list_size-1];
				for(int i = 1; i< list_size; i++){
					permArray[i] = array[i-1];
				}
				String permArrayString = arrayToString(permArray);
				if(map.get(permArrayString)==null){
					newMap.put(arrayToString(permArray), groupNum +1);
				}
				
				//do short cycle
				permArray = new Integer[list_size];
				System.arraycopy(array, 0, permArray, 0, list_size);
				permArray[0]=array[1];
				permArray[1]=array[0];
				permArrayString = arrayToString(permArray);
				if(map.get(permArrayString)==null){
					newMap.put(arrayToString(permArray), groupNum +1);
				}
				
				//do reverse cycle
				if(rev_permutation){
					permArray[list_size-1] = array[0];
					for(int i = 0; i< list_size-1; i++){
						permArray[i] = array[i+1];
					}
					permArrayString = arrayToString(permArray);
					if(map.get(permArrayString)==null){
						newMap.put(arrayToString(permArray), groupNum +1);
					}
				}
			}
			
			map.putAll(newMap);
			
			groupNum ++;
			System.out.println("Group number: "+ groupNum);
			System.out.println("Total perm: " + map.size() + "\n");
//			System.out.print(map.size() + ",");
			
			if(disp_permutations){
				printMap(newMap);
			}
			
			if(map.size()==numPerm){
				if(!disp_permutations){
					printMap(newMap);
				}
//				System.out.println("Found all permutations!");
				System.out.println();
				System.out.println("Total steps taken: " +groupNum);
				break;
			}

			newMap.clear();

		}

	}
	
	/**
	 * prints the map
	 */
	private static void printMap(HashMap<String, Integer> maptoprint){
		System.out.println("**BEGIN LIST**");
		for(String key: maptoprint.keySet()){
			System.out.println(key);
		}
		System.out.println("**END LIST**");
	}
	/**
	 * Given n, a positive integer, returns n!
	 * @param n
	 * @return n!
	 */
	private static int factorial(int n){
		int factorial = 1;
		for(;n>1;n--){
			factorial = factorial*n;
		}
		return factorial;
	}
	
	/**
	 * Given an array of integers, converts into a string representation
	 * separated by ","
	 * Example:
	 * Given the array {1,2,3}
	 * returns the string "1,2,3,"
	 * @param array
	 * @return string representation of the array
	 */
	private static String arrayToString(Integer[] array){
		String stringRep = "";
		for (Integer value: array){
			stringRep = stringRep + value.toString() + ",";
		}
		return stringRep;
	}
	
	/**
	 * Given a string with integers separated by ",", converts into an array of integers
	 * Example:
	 * Given the string "1,2,3,"
	 * returns the array{1,2,3}
	 */
	private static Integer[] stringToArray(String string){
		Integer[] arrayRep = new Integer[list_size];
		int index = 0;
		for(String number: string.split(",")){
			arrayRep[index] = Integer.parseInt(number);
			index++;
		}
		return arrayRep;
	}
}
