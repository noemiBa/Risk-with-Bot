import java.util.ArrayList;
import java.util.Arrays;

public class Test {
	 public ArrayList<int[]> combinations = new ArrayList<int[]>(); 
	
	 public static void main(String[] args) {
		 	Test t = new Test(); 
	        int[] arr = {0, 0, 1, 2, 0};
	        int r = 3;
	        Arrays.sort(arr);
	        t.combine(arr, r);
	        
	        for (int i = 0; i<t.getCombinations().size(); i++) {
	        	t.printArray(t.getCombinations().get(i)); 
	        }
	    }

	    public ArrayList<int[]> getCombinations() {
		return combinations;
	}

		private  void combine(int[] arr, int r) {
	        int[] res = new int[r];
	        doCombine(arr, res, 0, 0, r);
	    }



	    private  void doCombine(int[] arr, int[] res, int currIndex, int level, int r) {
	        if(level == r){
	        	combinations.add(res);
	            //printArray(res);
	            return;
	        }
	        for (int i = currIndex; i < arr.length; i++) {
	            res[level] = arr[i];
	            doCombine(arr, res, i+1, level+1, r);
	            //way to avoid printing duplicates
	            if(i < arr.length-1 && arr[i] == arr[i+1]){
	                i++;
	            }
	        }
	    }
	    
	    private  void printArray(int[] res) {
	        for (int i = 0; i < res.length; i++) {
	            System.out.print(res[i] + " ");
	        }
	        System.out.println();
	    }
}
