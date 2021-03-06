/**
 * Created by Theo Linnemann on 3/15/16 as part of sorting.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Random;

public class sorting {

    private static int[] arr;
    private static int[] arrCopy;
    private static int[] arrCopy2;
    private static BufferedReader read;
    private static Random randomGenerator;

    private static int size;
    private static int random;
    private static long trials;

    private static long mergecomparisons = 0;
    private static long mergesort0comparisons = 0;
    private static long bottomupcomparisons = 0;

    private static void printArray(String msg) {
        System.out.print(msg + " [" + arr[0]);
        for(int i=1; i<size; i++) {
            System.out.print(", " + arr[i]);
        }
        System.out.println("]");
    }

    public static void insertSort(int left, int right) {
        // insertSort the subarray arr[left, right]
        int i, j;

        for(i=left+1; i<=right; i++) {
            int temp = arr[i];           // store a[i] in temp
            j = i;                       // start shifts at i
            // until one is smaller,
            while(j>left && arr[j-1] >= temp) {
                arr[j] = arr[j-1];        // shift item to right
                --j;                      // go left one position
            }
            arr[j] = temp;              // insert stored item
        }  // end for
    }  // end insertSort()

    public static void insertionSort() {
        insertSort(0, size-1);
    } // end insertionSort()


    public static void maxheapify(int i, int n) {
        // Pre: the left and right subtrees of node i are max heaps.
        // Post: make the tree rooted at node i as max heap of n nodes.
        int max = i;
        int left=2*i+1;
        int right=2*i+2;

        if(left < n && arr[left] > arr[max]) max = left;
        if(right < n && arr[right] > arr[max]) max = right;

        if (max != i) {  // node i is not maximal
            exchange(i, max);
            maxheapify(max, n);
        }
    }

    public static void exchange(int i, int j){
        int t=arr[i];
        arr[i]=arr[j];
        arr[j]=t;
    }

    public static void heapsort(){
        // Build an in-place bottom up max heap
        for (int i=size/2; i>=0; i--) maxheapify(i, size);

        for(int i=size-1;i>0;i--) {
            exchange(0, i);       // move max from heap to position i.
            maxheapify(0, i);     // adjust heap
        }
    }

    private static boolean isSorted(int low, int high) {
        //Check if a sub array is already sorted.
        for (int i = low; i < high; i++) {
            if (arr[i] > arr[i + 1]) {
                return false; // It is proven that the array is not sorted.
            }
        }

        return true; // If this part has been reached, the array must be sorted.
    }

    private static void mergesort0(int low, int high) {
        // Check if low is smaller then high, if not then the array is sorted
        mergesort0comparisons++;
        if (low < high) {
            // Get the index of the element which is in the middle
            int middle = low + (high - low) / 2;
            // Sort the left side of the array
            mergesort0(low, middle);
            // Sort the right side of the array
            mergesort0(middle + 1, high);
            // Combine them both
            merge(low, middle, high);
        }
    }

    private static void mergesort1(int low, int high) {
        //Check if the sub array is already sorted.
        if (isSorted(low, high)) return;
        else // Check if low is smaller then high, if not then the array is sorted
        {
            if (low < high) {
                // Get the index of the element which is in the middle
                int middle = low + (high - low) / 2;
                // Sort the left side of the array
                mergesort1(low, middle);
                // Sort the right side of the array
                mergesort1(middle + 1, high);
                // Combine them both
                merge(low, middle, high);
            }
        }
    }

    private static void mergesort2(int low, int high) {
        if (high - low < 100) {
            insertSort(low, high);
        } else {
            // Check if low is smaller then high, if not then the array is sorted
            if (low < high) {
                // Get the index of the element which is in the middle
                int middle = low + (high - low) / 2;
                // Sort the left side of the array
                mergesort2(low, middle);
                // Sort the right side of the array
                mergesort2(middle + 1, high);
                // Combine them both
                merge(low, middle, high);
            }
        }
    }

    private static void mergesort3(int low, int high) {
        //Check if the sub array is already sorted.
        if (isSorted(low, high)) return;
        else if (high - low < 100) {
            insertSort(low, high);
        } else // Check if low is smaller then high, if not then the array is sorted
        {
            if (low < high) {
                // Get the index of the element which is in the middle
                int middle = low + (high - low) / 2;
                // Sort the left side of the array
                mergesort3(low, middle);
                // Sort the right side of the array
                mergesort3(middle + 1, high);
                // Combine them both
                merge(low, middle, high);
            }
        }
    }

    private static void merge(int low, int middle, int high) {

        // Copy first part into the arrCopy array
        for (int i = low; i <= middle; i++) {
            arrCopy2[i] = arr[i];
        }

        int i = low;
        int j = middle + 1;
        int k = low;

        // Copy the smallest values from either the left or the right side back
        // to the original array
        while (i <= middle && j <= high) {
            mergecomparisons++;
            if (arrCopy2[i] <= arr[j]) {
                arr[k] = arrCopy2[i];
                i++;
            } else {
                arr[k] = arr[j];
                j++;
            }
            k++;
        }

        // Copy the rest of the left part of the array into the target array
        while (i <= middle) {
            arr[k] = arrCopy2[i];
            k++;
            i++;
        }

    }

    private static void bottomupmergesort0(int[] a)
    {
        int width;

        for ( width = 1; width < a.length; width = 2*width )
        {
            // Combine pairs of array a of width "width"
            int i;

            for ( i = 0; i < a.length - width; i = i + 2*width )
            {
                int left, middle, right;

                left = i;
                middle = i + width - 1;
                bottomupcomparisons++;
                right = (i + width * 2 -1) < (a.length-1) ? (i + width * 2 -1) : (a.length-1);

                merge( left, middle, right );

            }
        }
    }

    private static void quicksort0(int low, int high) {
        int i = low, j = high;

        // Get the pivot element from the middle of the list
        int pivot = arr[(high+low)/2];

        // Divide into two lists
        while (i <= j) {
            // If the current value from the left list is smaller then the pivot
            // element then get the next element from the left list
            while (arr[i] < pivot) i++;

            // If the current value from the right list is larger then the pivot
            // element then get the next element from the right list
            while (arr[j] > pivot) j--;

            // If we have found a value in the left list which is larger than
            // the pivot element and if we have found a value in the right list
            // which is smaller then the pivot element then we exchange the
            // values.
            // As we are done we can increase i and j
            if (i < j) {
                exchange(i, j);
                i++;
                j--;
            } else if (i == j) { i++; j--; }
        }

        // Recursion
        if (low < j)
            quicksort0(low, j);
        if (i < high)
            quicksort0(i, high);
    }

    private static void quicksort1(int low, int high) {
        if (isSorted(low, high)) return;
        else // Check if low is smaller then high, if not then the array is sorted
        {

            int i = low, j = high;

            // Get the pivot element from the middle of the list
            int pivot = arr[(high + low) / 2];

            // Divide into two lists
            while (i <= j) {
                // If the current value from the left list is smaller then the pivot
                // element then get the next element from the left list
                while (arr[i] < pivot) i++;

                // If the current value from the right list is larger then the pivot
                // element then get the next element from the right list
                while (arr[j] > pivot) j--;

                // If we have found a value in the left list which is larger than
                // the pivot element and if we have found a value in the right list
                // which is smaller then the pivot element then we exchange the
                // values.
                // As we are done we can increase i and j
                if (i < j) {
                    exchange(i, j);
                    i++;
                    j--;
                } else if (i == j) {
                    i++;
                    j--;
                }
            }

            // Recursion
            if (low < j)
                quicksort1(low, j);
            if (i < high)
                quicksort1(i, high);
        }
    }

    private static void quicksort2(int low, int high) {
        if (high - low < 100) {
            insertSort(low, high);
        } else {
            int i = low, j = high;

            // Get the pivot element from the middle of the list
            int pivot = arr[(high + low) / 2];

            // Divide into two lists
            while (i <= j) {
                // If the current value from the left list is smaller then the pivot
                // element then get the next element from the left list
                while (arr[i] < pivot) i++;

                // If the current value from the right list is larger then the pivot
                // element then get the next element from the right list
                while (arr[j] > pivot) j--;

                // If we have found a value in the left list which is larger than
                // the pivot element and if we have found a value in the right list
                // which is smaller then the pivot element then we exchange the
                // values.
                // As we are done we can increase i and j
                if (i < j) {
                    exchange(i, j);
                    i++;
                    j--;
                } else if (i == j) {
                    i++;
                    j--;
                }
            }

            // Recursion
            if (low < j)
                quicksort2(low, j);
            if (i < high)
                quicksort2(i, high);
        }
    }

    private static void quicksort3(int low, int high) {
        if (isSorted(low, high)) return;
        else if (high - low < 100) {
            insertSort(low, high);
        } else {
            int i = low, j = high;

            // Get the pivot element from the middle of the list
            int pivot = arr[(high + low) / 2];

            // Divide into two lists
            while (i <= j) {
                // If the current value from the left list is smaller then the pivot
                // element then get the next element from the left list
                while (arr[i] < pivot) i++;

                // If the current value from the right list is larger then the pivot
                // element then get the next element from the right list
                while (arr[j] > pivot) j--;

                // If we have found a value in the left list which is larger than
                // the pivot element and if we have found a value in the right list
                // which is smaller then the pivot element then we exchange the
                // values.
                // As we are done we can increase i and j
                if (i < j) {
                    exchange(i, j);
                    i++;
                    j--;
                } else if (i == j) {
                    i++;
                    j--;
                }
            }

            // Recursion
            if (low < j)
                quicksort3(low, j);
            if (i < high)
                quicksort3(i, high);
        }
    }

    private static void quicksort4(int left, int right) {
        int size = right - left + 1;
        if (size <= 3)                  // manual sort if small
            manualSort(left, right);
        else                           // quicksort if large
        {
            long median = medianpivot3(left, right);
            int partition = partioned(left, right, median);
            quicksort4(left, partition - 1);
            quicksort4(partition + 1, right);
        }
    }

    private static long medianpivot3(int left, int right) {
        int center = (left + right) / 2;
        if (arr[left] > arr[center])
            swap(left, center);
        if (arr[left] > arr[right])
            swap(left, right);
        if (arr[center] > arr[right])
            swap(center, right);

        swap(center, right - 1);
        return arr[right - 1];
    }

    private static void manualSort(int left, int right) {
        int size = right - left + 1;
        if (size <= 1)
            return;
        if (size == 2) {
            if (arr[left] > arr[right])
                swap(left, right);
            return;
        } else {
            if (arr[left] > arr[right - 1])
                swap(left, right - 1);
            if (arr[left] > arr[right])
                swap(left, right);
            if (arr[right - 1] > arr[right])
                swap(right - 1, right);
        }
    }

    private static int partioned(int left, int right, long pivot) {
        int leftPtr = left;
        int rightPtr = right - 1;

        while (true) {
            while (arr[++leftPtr] < pivot)
                ;
            while (arr[--rightPtr] > pivot)
                ;
            if (leftPtr >= rightPtr)
                break;
            else
                swap(leftPtr, rightPtr);
        }
        swap(leftPtr, right - 1);
        return leftPtr;
    }

    private static void swap(int dex1, int dex2)  // swap two elements
    {
        int temp = arr[dex1];
        arr[dex1] = arr[dex2];
        arr[dex2] = temp;
    }

    public static void main(String[] args) {

        read = new BufferedReader(new InputStreamReader(System.in));

        randomGenerator = new Random();

        long builtInSortAveTime = 0;
        long heapSortAvetime = 0;

        long mergeSort0AveTime = 0;
        long mergeSort1AveTime = 0;
        long mergeSort2AveTime = 0;
        long mergeSort3AveTime = 0;

        long bottomUpSortAveTime = 0;

        long quickSort0AveTime = 0;
        long quickSort1AveTime = 0;
        long quickSort2AveTime = 0;
        long quickSort3AveTime = 0;
        long quickSort4AveTime = 0;


        long quickSortNearlySortedAveTime = 0;
        long insertSortNearlySortedAveTime = 0;

        System.out.println();

        try {
            System.out.print("Please enter array size : ");
            size = Integer.parseInt(read.readLine());

            System.out.print("Please enter the random range : ");
            random = Integer.parseInt(read.readLine());

            System.out.print("Please enter the number of trials you would like to run all tests : ");
            trials = Integer.parseInt(read.readLine());

            System.out.println();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        long startTime = System.currentTimeMillis();

        for( int t = 0; t < trials; t++) {

            // create array
            arr = new int[size];
            arrCopy = new int[size];
            arrCopy2 = new int[size];

            // fill array
            for (int i = 0; i < size; i++)
                arr[i] = arrCopy[i] = randomGenerator.nextInt(random);

/*            //Sort, and reverse the array order. Used only for question 5.
            Arrays.sort(arrCopy);
            for(int i=0; i < arrCopy.length / 2; i++) {
                // swap the elements
                int temp = arrCopy[i];
                arrCopy[i] = arrCopy[arrCopy.length - (i+1)];
                arrCopy[arrCopy.length - (i + 1)] = temp;
            }*/

            // built-in sort
            long start = System.currentTimeMillis();
            if (size < 101) printArray("Initial array:");
            Arrays.sort(arr);
            if (size < 101) printArray("out");
            long finish = System.currentTimeMillis();
            System.out.println("Arrays.sort: " + (finish - start) + " milliseconds.");
            builtInSortAveTime += finish - start;
            System.out.println();


            // Heap sort
            for (int i = 0; i < size; i++) arr[i] = arrCopy[i];
            start = finish;
            if (size < 101) printArray("in");
            heapsort();
            if (size < 101) printArray("out");
            finish = System.currentTimeMillis();
            System.out.println("heapsort: " + (finish - start) + " milliseconds.");
            heapSortAvetime += finish - start;
            System.out.println();

            // Merge sort 0
            for (int i = 0; i < size; i++) arr[i] = arrCopy[i];
            start = finish;
            if (size < 101) printArray("in");
            mergesort0(0, size - 1);
            if (size < 101) printArray("out");
            finish = System.currentTimeMillis();
            System.out.println("mergesort0: " + (finish - start) + " milliseconds.");
            mergeSort0AveTime += finish - start;
            System.out.println();

            // Merge sort 1
            for (int i = 0; i < size; i++) arr[i] = arrCopy[i];
            start = finish;
            if (size < 101) printArray("in");
            mergesort1(0, size - 1);
            if (size < 101) printArray("out");
            finish = System.currentTimeMillis();
            System.out.println("mergesort1: " + (finish - start) + " milliseconds.");
            mergeSort1AveTime += finish - start;
            System.out.println();

            // Merge sort 2
            for (int i = 0; i < size; i++) arr[i] = arrCopy[i];
            start = finish;
            if (size < 101) printArray("in");
            mergesort2(0, size - 1);
            if (size < 101) printArray("out");
            finish = System.currentTimeMillis();
            System.out.println("mergesort2: " + (finish - start) + " milliseconds.");
            mergeSort2AveTime += finish - start;
            System.out.println();

            // Merge sort 3
            for (int i = 0; i < size; i++) arr[i] = arrCopy[i];
            start = finish;
            if (size < 101) printArray("in");
            mergesort3(0, size - 1);
            if (size < 101) printArray("out");
            finish = System.currentTimeMillis();
            System.out.println("mergesort3: " + (finish - start) + " milliseconds.");
            mergeSort3AveTime += finish - start;
            System.out.println();

            // Bottom Up Merge Sort
            for(int i=0; i<size; i++) arr[i] = arrCopy[i];
            start = finish;
            if (size < 101) printArray("in");
            bottomupmergesort0(arr);
            if (size < 101) printArray("out");
            finish = System.currentTimeMillis();
            System.out.println("Bottom Up Merge Sort: " + (finish - start) + " milliseconds");
            bottomUpSortAveTime += finish - start;
            System.out.println();

            // Quick sort 0
            for (int i = 0; i < size; i++) arr[i] = arrCopy[i];
            start = finish;
            if (size < 101) printArray("in");
            quicksort0(0, size - 1);
            if (size < 101) printArray("out");
            finish = System.currentTimeMillis();
            System.out.println("quicksort0: " + (finish - start) + " milliseconds.");
            quickSort0AveTime += finish - start;
            System.out.println();

            // Quick sort 1
            for (int i = 0; i < size; i++) arr[i] = arrCopy[i];
            start = finish;
            if (size < 101) printArray("in");
            quicksort1(0, size - 1);
            if (size < 101) printArray("out");
            finish = System.currentTimeMillis();
            System.out.println("quicksort1: " + (finish - start) + " milliseconds.");
            quickSort1AveTime += finish - start;
            System.out.println();

            // Quick sort 2
            for (int i = 0; i < size; i++) arr[i] = arrCopy[i];
            start = finish;
            if (size < 101) printArray("in");
            quicksort2(0, size - 1);
            if (size < 101) printArray("out");
            finish = System.currentTimeMillis();
            System.out.println("quicksort2: " + (finish - start) + " milliseconds.");
            quickSort2AveTime += finish - start;
            System.out.println();

            // Quick sort 3
            for (int i = 0; i < size; i++) arr[i] = arrCopy[i];
            start = finish;
            if (size < 101) printArray("in");
            quicksort3(0, size - 1);
            if (size < 101) printArray("out");
            finish = System.currentTimeMillis();
            System.out.println("quicksort3: " + (finish - start) + " milliseconds.");
            quickSort3AveTime += finish - start;
            System.out.println();

            // Quick sort 4
            for (int i = 0; i < size; i++) arr[i] = arrCopy[i];
            start = finish;
            if (size < 101) printArray("in");
            quicksort4(0, size - 1);
            if (size < 101) printArray("out");
            finish = System.currentTimeMillis();
            System.out.println("quicksort4: " + (finish - start) + " milliseconds.");
            quickSort4AveTime += finish - start;

            System.out.println();

            // arr[0..size-1] is already sorted. We randomly swap 100 pairs to make it nearly-sorted.
            for (int i = 0; i < 100; i++) {
                int j = randomGenerator.nextInt(size);
                int k = randomGenerator.nextInt(size);
                exchange(j, k);
            }
            for (int i = 0; i < size; i++) arrCopy2[i] = arr[i];

            // Quick sort on nearly-sorted array
            start = finish;
            if (size < 101) printArray("in");
            quicksort2(0, size - 1);
            if (size < 101) printArray("out");
            finish = System.currentTimeMillis();
            System.out.println("quicksort2 on nearly-sorted: " + (finish - start) + " milliseconds.");
            quickSortNearlySortedAveTime += finish - start;

            System.out.println();

            // Insert sort on nearly-sorted array
            for (int i = 0; i < size; i++) arr[i] = arrCopy2[i];
            start = finish;
            if (size < 101) printArray("in");
            insertionSort();
            if (size < 101) printArray("out");
            finish = System.currentTimeMillis();
            System.out.println("insertsort on nearly-sorted: " + (finish - start) + " milliseconds.");
            insertSortNearlySortedAveTime += finish - start;

            System.out.println();
            System.out.println("---------------------------- END OF TRIAL NUMBER: " + (t + 1) +" ----------------------------");
            System.out.println();

        }

        System.out.println("---------------------------- OUTPUT SUMMARY ----------------------------");

        System.out.println();

        System.out.println("Initial Run Time Conditions:");
        System.out.println("Array Size: " + size + " elements");
        System.out.println("Array Element Value Range: 1 - " + random);
        System.out.println("Number of Trial Runs: " + trials);

        System.out.println();

        //Sorting Algo's Performance Data
        System.out.println("Built In Sort Average Execution Time: " + builtInSortAveTime/trials + " milliseconds.");
        System.out.println();
        System.out.println("Heap Sort Average Execution Time: " + heapSortAvetime/trials + " milliseconds.");

        System.out.println();

        //Merge Sort Variations Performance Data:
        System.out.println("Merge Sort 0 Average Execution Time: " + mergeSort0AveTime / trials + " milliseconds.");
        System.out.println();
        System.out.println("Merge Sort 0 Average Comparisons: " + ((mergesort0comparisons + mergecomparisons) / trials));
        System.out.println();
        System.out.println("Merge Sort 1 Average Execution Time: " + mergeSort1AveTime / trials + " milliseconds.");
        System.out.println();
        System.out.println("Merge Sort 2 Average Execution Time: " + mergeSort2AveTime / trials + " milliseconds.");
        System.out.println();
        System.out.println("Merge Sort 3 Average Execution Time: " + mergeSort3AveTime / trials + " milliseconds.");
        System.out.println();


        System.out.println("Bottom Up Merge Sort Average Execution Time: " + bottomUpSortAveTime/trials + " milliseconds.");
        System.out.println();
        System.out.println("Bottom Up Merge Sort Average Comparisons: " + ((bottomupcomparisons + mergecomparisons) / trials));


        System.out.println();


        System.out.println("Quick Sort 0 Average Execution Time: " + quickSort0AveTime / trials + " milliseconds.");
        System.out.println();
        System.out.println("Quick Sort 1 Average Execution Time: " + quickSort1AveTime / trials + " milliseconds.");
        System.out.println();
        System.out.println("Quick Sort 2 Average Execution Time: " + quickSort2AveTime / trials + " milliseconds.");
        System.out.println();
        System.out.println("Quick Sort 3 Average Execution Time: " + quickSort3AveTime / trials + " milliseconds.");
        System.out.println();
        System.out.println("Quick Sort 4 Average Execution Time: " + quickSort4AveTime / trials + " milliseconds.");
        System.out.println();

        //Nearly Sorted Array Performance Data
        System.out.println("Nearly Sorted Quick Sort Average Execution Time: " + quickSortNearlySortedAveTime/trials + " milliseconds.");
        System.out.println();
        System.out.println("Nearly Sorted Insertion Sort Average Execution Time: " + insertSortNearlySortedAveTime/trials + " milliseconds.");

        System.out.println();

        System.out.println("Total Run Time: " + (System.currentTimeMillis() - startTime) + " milliseconds.");




    }
}
