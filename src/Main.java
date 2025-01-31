import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        Random rnd = new Random();
        Scanner sc = new Scanner(System.in);


        // Asking the user for array size
        System.out.println("please enter the arrays size");
        int size = sc.nextInt();
        int[] arr = new int[size];

        // Timing the sorting process

        long startTime = System.nanoTime();
        Sort.sort(arr , false ,2);
        Long estimatedTime = System.nanoTime() - startTime;




        menu(arr);   // Sorting the array
        System.out.println(estimatedTime + "  nano seconds");
        //printArray(arr);


    }

    public static void printArray(int[] array) {
        for (int num : array) {
            System.out.print(num + " ");
        }
        System.out.println();

    }

    public static void menu(int[] arr) {    // Menu method to provide sorting options
        boolean valid = false;
        do {
            System.out.println("- - The Sorting System - -\n\n- - MAIN MENU - -\n1 - Shall Sort\n2 - Shall Sort Reverse\n3 - Dual Pivot Quick Sort\n4 - Dual Pivot Quick Reverse Sortn\nQ - Quit\nPick :");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.next();
            input = input.toLowerCase();

            if (input.equals("1")) {
                order(arr);
                Sort.sort(arr,false  , 1);
            } else if (input.equals("2")) {
                order(arr);
                Sort.sort(arr,true  , 1);
            } else if (input.equals("3")) {
                order(arr);
                Sort.sort(arr,false  , 2);
            } else if (input.equals("4")) {
                order(arr);
                Sort.sort(arr,true  , 2);
            } else if (input.equals("q")) {
                valid = false;
            } else {
                System.out.println("ERROR! Input must be a number between 1 and 4, or the character Q to exit the program!");
            }
        } while (valid == true);

    }


    public static void order(int[] arr){
        boolean flag = true;
        do {
            System.out.println("- - The ordering System - -\n\n- - MAIN MENU - -\n1 - Equal\n2 - Random\n3 - Increasing\n4 - Decreasing\nPick :");
            Scanner scanner = new Scanner(System.in);
            Random rnd = new Random();
            String input = scanner.next();
            input = input.toLowerCase();

            if (input.equals("1")) {
                flag = false;
                for (int i = 0; i < arr.length; i++) {
                    arr[i] = 100;
                }
            } else if (input.equals("2")) {
                flag = false;
                for (int i = 0; i < arr.length; i++) {
                    arr[i] = rnd.nextInt(1000);
                }
            } else if (input.equals("3")) {
                flag = false;
                for (int i = 0; i < arr.length; i++) {
                    arr[i] = i;
                }
            } else if (input.equals("4")) {
                flag = false;
                for (int i = 0; i < arr.length; i++) {
                    arr[i] = arr.length - i;
                }
            } else {
                flag = true;
                System.out.println("ERROR! Input must be a number between 1 and 4, or the character Q to exit the program!");
            }
        } while (flag == true);
    }


    // Method to write array to a file
    public static void write (String filename, int[]x) throws IOException {
        BufferedWriter outputWriter = null;
        outputWriter = new BufferedWriter(new FileWriter(filename));
        for (int i = 0; i < x.length; i++) {
            // Maybe:
            outputWriter.write(x[i]+" ");

        }
        outputWriter.flush();
        outputWriter.close();
    }

    // Static inner class Sort containing sorting algorithms
    public class Sort {

        // Gap sequence for Shell Sort
        private final static int[] gap =
                {1, 4, 10, 23, 57, 132, 301, 701, 1750, 3937,
                        8858, 19930, 44842, 100894, 227011, 510774,
                        1149241, 2585792, 5818032, 13090572, 29453787,
                        66271020, 149109795, 335497038, 754868335, 1698453753};


        // Method to determine the gap size for Shell Sort
        private static int getGap(int length) {
            int index = 0;
            int len = (int) (length / 2.25);
            while (gap[index] < len) index++;
            return index;
        }


        // Shell Sort algorithm
        private static void ShellSort(int[] arr, int size) {
            int gapIndex = getGap(size);

            while (gapIndex >= 0) {
                int step = gap[gapIndex--];


                for (int i = step; i < size; i++) {

                    int target = arr[i];
                    int j = i;

                    for (; j >= step && target < arr[j - step]; j -= step) {
                        arr[j] = arr[j - step];
                    }
                    arr[j] = target;
                }
            }
        }

        // Reverse Shell Sort algorithm
        private static void ShellSortReverse(int[] arr, int size) {
            int gapIndex = getGap(size);

            while (gapIndex >= 0) {
                int step = gap[gapIndex--];

                for (int i = size - step - 1; i >= 0; i--) {
                    int target = arr[i];
                    int j = i;

                    for (; j + step < size && target < arr[j + step]; j += step) {
                        arr[j] = arr[j + step];
                    }
                    arr[j] = target;
                }
            }
        }


        // Dual Pivot Quick Sort algorithm
        private static int[] DualPivotQuickSort(int[] array, int start, int end) {
            if (start < end) {
                int leftPivotIndex = partition(array, start, end, start);    // left pivot
                int rightPivotIndex = partition(array, start, end, leftPivotIndex);  // right pivot

                DualPivotQuickSort(array, start, leftPivotIndex - 1);       // recursively sorts the left,
                DualPivotQuickSort(array, leftPivotIndex + 1, rightPivotIndex - 1);    // middle, and right sub-arrays
                DualPivotQuickSort(array, rightPivotIndex + 1, end);        // created for algorithm
            }
            return array;
        }

        // Partitioning method for Dual Pivot Quick Sort
        private static int partition(int[] array, int start, int end, int leftPivotIndex) {
            if (array[start] > array[end]) {
                int temp = array[start];
                array[start] = array[end];
                array[end] = temp;
            }

            int left = start + 1;
            int right = end - 1;
            int current = start + 1;
            int leftPivot = array[start];    // left pivot
            int rightPivot = array[end];     // right pivot

            while (current <= right) {
                // checks if element at current is less than the left pivot
                if (array[current] < leftPivot) {
                    int temp = array[current];
                    array[current] = array[left];
                    array[left] = temp;
                    left++;
                }
                // checks if element at current is greater than or equal to the right pivot
                else if (array[current] >= rightPivot) {
                    while (array[right] > rightPivot && current < right)
                        right--;
                    int temp = array[current];
                    array[current] = array[right];
                    array[right] = temp;
                    right--;
                    if (array[current] < leftPivot) {
                        temp = array[current];
                        array[current] = array[left];
                        array[left] = temp;
                        left++;
                    }
                }
                current++;
            }
            left--;
            right++;

            // puts pivots into their position in the array

            // left pivot
            int temp = array[start];
            array[start] = array[left];
            array[left] = temp;
            // right pivot
            temp = array[end];
            array[end] = array[right];
            array[right] = temp;

            // return indices of pivots
            leftPivotIndex = left; // resets left pivot (cannot return two elements for this method)

            return right;
        }


        //Reverse Dual Pivot Quick Sort algorithm
        private static int[] DualPivotQuickReverseSort(int[] array, int start, int end) {
            if (start < end) {
                int leftPivotIndex = reversePartition(array, start, end, start);    // left pivot
                int rightPivotIndex = reversePartition(array, start, end, leftPivotIndex);  // right pivot

                DualPivotQuickReverseSort(array, start, leftPivotIndex - 1);       // recursively sorts the left,
                DualPivotQuickReverseSort(array, leftPivotIndex + 1, rightPivotIndex - 1);    // middle, and right sub-arrays
                DualPivotQuickReverseSort(array, rightPivotIndex + 1, end);        // created for algorithm
            }
            return array;
        }

        // Partitioning method for reverse Dual Pivot Quick Sort
        private static int reversePartition(int[] array, int start, int end, int leftPivotIndex) {
            if (array[start] < array[end]) {
                int temp = array[start];
                array[start] = array[end];
                array[end] = temp;
            }

            int left = start + 1;
            int right = end - 1;
            int current = start + 1;
            int leftPivot = array[start];    // left pivot
            int rightPivot = array[end];     // right pivot

            while (current <= right) {
                // checks if element at current is greater than the left pivot
                if (array[current] > leftPivot) {
                    int temp = array[current];
                    array[current] = array[left];
                    array[left] = temp;
                    left++;
                }
                // checks if element at current is less than or equal to the right pivot
                else if (array[current] <= rightPivot) {
                    while (array[right] < rightPivot && current < right)
                        right--;
                    int temp = array[current];
                    array[current] = array[right];
                    array[right] = temp;
                    right--;
                    if (array[current] > leftPivot) {
                        temp = array[current];
                        array[current] = array[left];
                        array[left] = temp;
                        left++;
                    }
                }
                current++;
            }
            left--;
            right++;

            // puts pivots into their position in the array

            // left pivot
            int temp = array[start];
            array[start] = array[left];
            array[left] = temp;
            // right pivot
            temp = array[end];
            array[end] = array[right];
            array[right] = temp;

            // return indices of pivots
            leftPivotIndex = left; // resets left pivot (cannot return two elements for this method)

            return right;
        }


        public static void sort(int[] arr, boolean isReverse, int select) {

            switch (select) {
                case (1):
                    // reverse order
                    if (isReverse == true) {
                        ShellSortReverse(arr, arr.length);
                    } else {
                        ShellSort(arr, arr.length);
                    }
                    break;
                case (2):
                    if (isReverse == true) {
                        DualPivotQuickReverseSort(arr, 0, arr.length - 1);

                    } else {
                        DualPivotQuickSort(arr, 0, arr.length - 1);

                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported sorting algorithm selection: " + select);
            }
        }
    }

}