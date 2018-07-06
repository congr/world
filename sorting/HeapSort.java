public class HeapSort
{
    void swap(int[] A, int i, int j) {
        int temp = A[i];
        A[i] = A[j];
        A[j] = temp;
    }

    public void sort(int arr[])
    {
        int n = arr.length;

        // {1 12 9 5 6 10} - i is 2 and element is 9,
        // rearrange - right subtree, then rearrange left subtree
        for (int i = n/2-1; i>=0; i--) {
            heapify(arr, n, i);
        }

        // now max heap was built, swap max 0 to the last position
        for (int i = n-1; i>=0; i--) {
            swap(arr, 0, i); // 0 is max, so swap to the last index
            heapify(arr, i, 0); // i is the number of node to heapify, 0 is largest
        }
    }

    void heapify(int arr[], int n, int i)
    {
        int largest = i;
        int left = 2*i+1, right = 2*i+2;
        if (left < n && arr[largest] < arr[left]) largest = left;
        if (right < n && arr[largest] < arr[right]) largest = right;

        if (largest != i) { // changed
            swap(arr, i, largest);
            heapify(arr, n, largest);
        }
    }

    static void printArray(int arr[])
    {
        int n = arr.length;
        for (int i=0; i < n; ++i)
            System.out.print(arr[i]+" ");
        System.out.println();
    }

    public static void main(String args[])
    {
        int arr[] = {1,12,9,5,6,10};

        HeapSort hs = new HeapSort();
        hs.sort(arr);

        System.out.println("Sorted array is");
        printArray(arr);
    }
}
