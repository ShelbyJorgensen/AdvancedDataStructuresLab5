package LabAssignment5;

import java.io.*;
import java.util.*;
public class Lab5
{
    /**
     *  Problem 1: Find the largest integer that appears at least m times.
     */
    private static int problem1(int[] arr, int m) {
       HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
       int max = Integer.MIN_VALUE;
       
       // Iterate through the provided array
       for(int i = 0; i < arr.length; i++) {
    	   // If the current value in the array isn't in the hashmap
    	   if(!map.containsKey(arr[i])) {
    		   // Use the array value as a key, and start the count of occurences at 1
    		   map.put(arr[i], 1);
    	   } 
    	   // If the current array value is already a key in the hashmap
    	   else {
    		   // Update the occurence count by 1
    		   int value = map.get(arr[i]) + 1;
    		   map.put(arr[i], value);
    	   }
    	   // If the current hashmap key's count is larger than m
    	   if(map.get(arr[i]) >= m) {
    		   // Check if the current key is larger than the stored max key, if not update value
			   if(arr[i] >= max) {
				   max = arr[i];
			   }
		   }
       }
       
       return max;
    }
    /**
     *  Problem 2: Find two distinct indices i and j such that arr[i] = arr[j] and 
|i - j| <= m.
     */
    private static int[] problem2(int[] arr, int m) {
        int i = -1;
        int j = -1;
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        
        // Iterate through the provided array
        for(int k = 0; k < arr.length; k++) {
        	// If the current array value isn't, store the value as the key and current index as value
        	if(!map.containsKey(arr[k])) {
        		map.put(arr[k], k);
        	} else {
        		// Check if the previous index of arr[k], and the current index subtracted from eachother is less than m
        		int temp = map.get(arr[k]);
        		if(Math.abs(temp - k) <= m) {
        			i = temp;
        			j = k;
        		} 
        		// If not less than/equal to m, than update the value of k incase of another instance of arr[k] in arr
        		else {
        			map.put(arr[k], k);
        		}
        	}
        }
        
        return new int[] { i, j };
    }
    // ---------------------------------------------------------------------
    // Do not change any of the code below!
    private static final int LabNo = 5;
    private static final Random rng = new Random(123456);
    private static boolean testProblem1(int[][] testCase)
    {
        int[] arr = testCase[0];
        int m = testCase[1][0];
        int answer = problem1(arr.clone(), m);       
        
        Arrays.sort(arr);
        
        for (int i = arr.length-1, j = arr.length-1; i >= 0; i = j)
        {
            for (; j >=0 && arr[i] == arr[j]; j--) { }
             if (i - j >= m){
                return answer == arr[i];
             }
        }
        
        
        return false; // Will never happen.
    }
    private static boolean testProblem2(int[][] testCase)
    {
        int[] arr = testCase[0];
        int m = testCase[1][0];
        int[] answer = problem2(arr.clone(), m);
        if (answer == null || answer.length != 2)
        {
            return false;
        }
        Arrays.sort(answer);
        // Check answer
        int i = answer[0];
        int j = answer[1];
        return i != j
            && j - i <= m
            && i >= 0
            && j < arr.length
            && arr[i] == arr[j];
    }
    public static void main(String args[])
    {
        System.out.println("CS 302 -- Lab " + LabNo);
        testProblems(1);
        testProblems(2);
    }
    private static void testProblems(int prob)
    {
        int noOfLines = prob == 1 ? 100000 : 500000;
        System.out.println("-- -- -- -- --");
        System.out.println(noOfLines + " test cases for problem " + prob + ".");
        boolean passedAll = true;
        for (int i = 1; i <= noOfLines; i++)
        {
            int[][] testCase = null;
            boolean passed = false;
            boolean exce = false;
            try
            {
                switch (prob)
                {
                    case 1:
                        testCase = createProblem1(i);
                        passed = testProblem1(testCase);
                        break;
                    case 2:
                        testCase = createProblem2(i);
                        passed = testProblem2(testCase);
                        break;
                }
            }
            catch (Exception ex)
            {
                passed = false;
                exce = true;
            }
            if (!passed)
            {
                System.out.println("Test " + i + " failed!" + (exce ? " (Exception)" : ""));
                passedAll = false;
                break;
            }
        }
        if (passedAll)
        {
            System.out.println("All test passed.");
        }
    }
    private static int[][] createProblem1(int testNo)
    {
        int size = rng.nextInt(Math.min(1000, testNo)) + 5;
        int[] numbers = getRandomNumbers(size, size);
        Arrays.sort(numbers);
        int maxM = 0;
        for (int i = 0, j = 0; i < size; i = j)
        {
            for (; j < size && numbers[i] == numbers[j]; j++) { }
            maxM = Math.max(maxM, j - i);
        }
        int m = rng.nextInt(maxM) + 1;
        shuffle(numbers);
        return new int[][] { numbers, new int[] { m } };
    }
    private static int[][] createProblem2(int testNo)
    {
        int size = rng.nextInt(Math.min(1000, testNo)) + 5;
        int[] numbers = getRandomNumbers(size, size);
        int i = rng.nextInt(size);
        int j = rng.nextInt(size - 1);
        if (i <= j) j++;
        numbers[i] = numbers[j];
        return new int[][] { numbers, new int[] { Math.abs(i - j) } };
    }
    private static void shuffle(int[] arr)
    {
        for (int i = 0; i < arr.length - 1; i++)
        {
            int rndInd = rng.nextInt(arr.length - i) + i;
            int tmp = arr[i];
            arr[i] = arr[rndInd];
            arr[rndInd] = tmp;
        }
    }
    private static int[] getRandomNumbers(int range, int size)
    {
        int numbers[] = new int[size];
        for (int i = 0; i < size; i++)
        {
            numbers[i] = rng.nextInt(2 * range) - range;
        }
        return numbers;
    }
}