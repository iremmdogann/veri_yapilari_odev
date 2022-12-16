/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 
*/
//02205076070
//İrem Hatice DOĞAN

package min_heap;
import java.util.Arrays;
import java.util.Random;
public class Min_heap {
      
	private int currentSize;
	private int[] arr;
	private int maxIdx = 0;
	private int minIdx = 1;
	
	public Min_heap(int capacity){
		arr = new int[capacity + 1];
		currentSize = 0;
	}
	
	public boolean isFull(){
		return currentSize == arr.length - 1;
	}
	public boolean isEmpty(){
		return currentSize == 0;
	}
	
	public void insert(int x){
		if (isEmpty()){
			arr[1] = x;
			currentSize++;
		}
		else {
			arr[++currentSize] = x;
			percolateUp(currentSize);
		}
	}
	public int min() {return arr[1];} 
	
	public int max(){
		if(currentSize==1){
			maxIdx = 1;
			return arr[1];
		}
		else if(currentSize==2){	
			maxIdx=2;
			return arr[2];
		}
		else{
			if (arr[2] < arr[3]) maxIdx = 3;
			else if (arr[3] < arr[2]) maxIdx = 2;
			
			return Math.max(arr[2], arr[3]); 
		}
	}
	public int deleteMin(){
		int tmp = min();
		if(currentSize==1){	
			currentSize--;
			return tmp;
		}
		
		arr[minIdx] = arr[currentSize--];
		percolateDown(minIdx);
		return tmp;
	}
	public int deleteMax(){
		int tmp = max();
		if(currentSize==1){
			currentSize--;
			return tmp;
		}
		else if(currentSize==2){
			currentSize--;
			return tmp;
		}
		
		arr[maxIdx] = arr[currentSize--];
		percolateDown(maxIdx);
		return tmp;
		
	}
	
	private void switchData(int x, int y){	
		int tmp = arr[x];
		arr[x]=arr[y];
		arr[y]=tmp;
	}
	
	private boolean evenLevel(int i){	
		if (log2(i)%2==0){
			return true;
		}
		else return false;
	}
	private int log2(int num){	
		return (int) (Math.log(num)/Math.log(2));
	}
	
	private int leftChild(int i){
		return 2*i;
	}
	private int rightChild(int i){
		return ((2*i)+1);
	}
	private int parent(int i){	
		return i/2;
	}
	private int grandparent(int i){	
		return i/4;
	}
	
	private int minChild(int i){
		int minChildIdx=0;
		if (arr[leftChild(i)]<arr[rightChild(i)]) minChildIdx=leftChild(i);
		else minChildIdx=rightChild(i);
		return minChildIdx;
	}
	
	
	private int minChildIdx(int i){
		int minChildIdx=leftChild(i);	
		int minAmt=arr[minChildIdx];
		
		if(rightChild(i)<=currentSize){ 
			if(arr[rightChild(i)]<minAmt){
				minAmt=arr[rightChild(i)];
				minChildIdx = rightChild(i);
			}
		}
		else return minChildIdx;
		
		if(leftChild(leftChild(i))<=currentSize){  
			if(arr[leftChild(leftChild(i))]<minAmt){
				minAmt=arr[leftChild(leftChild(i))];
				minChildIdx=leftChild(leftChild(i));
			}
		}
		else return minChildIdx;	
		
		if(rightChild(leftChild(i))<=currentSize) {
			if(arr[rightChild(leftChild(i))]<minAmt){
				minAmt=arr[rightChild(leftChild(i))];
				minChildIdx=rightChild(leftChild(i));
			}
		}
		else return minChildIdx;
		
		if(leftChild(rightChild(i))<=currentSize){ 
			if(arr[leftChild(rightChild(i))]<minAmt){
				minAmt=arr[leftChild(rightChild(i))];
				minChildIdx=leftChild(rightChild(i));
			}
		}
		else return minChildIdx; 
		
		if(rightChild(rightChild(i))<=currentSize){
			if(arr[rightChild(rightChild(i))]<minAmt){
				minAmt=arr[rightChild(rightChild(i))];
				minChildIdx=rightChild(rightChild(i));
			}
		}
		else return minChildIdx; 
		
		return minChildIdx;
	}
	
	private void percolateDown(int node){
		if(leftChild(node)>currentSize) return;	
		if (evenLevel(node)) percolateDownEven(node);
		else percolateDownOdd(node);
	}

	private void percolateDownEven(int node){
		if (leftChild(node)<=currentSize){ 
			
			int minChildIdx=minChildIdx(node);
			boolean smallestChild = false;
			if(minChildIdx==minChild(node)) smallestChild=true;
		
			if(!smallestChild){ 
				if (arr[minChildIdx]<arr[node]){
					switchData(minChildIdx,node);
					if(arr[minChildIdx]>arr[parent(minChildIdx)]){
						switchData(minChildIdx,parent(minChildIdx));
					}
					percolateDownEven(minChildIdx);
				}
			}
			else{	
				if(arr[minChildIdx]<arr[node]){
					switchData(minChildIdx,node);
				}
				return;
			}
		}
	}
	
	
	private void percolateUpEven(int node){
		if(node<=1) return;
		if((parent(node)<=1)) return;
		if(arr[node]<arr[grandparent(node)]){
			switchData(node,grandparent(node));
			percolateUpEven(grandparent(node));
		}
	}
	private void percolateUpOdd(int node){
		if(node<=1) return;
		if((parent(node)<=1)) return;
		if(arr[node]>arr[grandparent(node)]){
			switchData(node,grandparent(node));
			percolateUpOdd(grandparent(node));
		}
	}
	private void percolateUp(int node){
	if (node==1) return;
	if(evenLevel(node)){
		if(arr[node] < arr[parent(node)]){
			percolateUpEven(node);
		}
		else{
			switchData(node,parent(node));
			node = parent(node);	
			percolateUpOdd(node);
		}
	}
	else{
		if(arr[node] > arr[parent(node)]){
			percolateUpOdd(node);
		}
		else {
			switchData(node,parent(node));
			node = parent(node);
			percolateUpEven(node);
		}
	}
}

	
	public static void main(String[] args){
		Random rand = new Random();
		int randSize=rand.nextInt(100);
		Min_heap h = new Min_heap(randSize);
		
		int max1=0;
		int max2=0;
		int avgVar=0;
		int count=0, totalcount=0;
		String ostring="";
		
		while(totalcount<1000){
			randSize=rand.nextInt(100000);
			h = new Min_heap(randSize);
			
			
			for(int i = 1; i<=randSize;i++) {
				h.insert(rand.nextInt());
			}
			ostring = Arrays.toString(h.arr);
			for(int i = 1; i<=randSize/2;i++) {
				max1=h.deleteMax();
				max2=h.deleteMax();
				
				if((max1<max2)){
					avgVar+=max2-max1;
					count++;
					i=randSize+1;
				}
			}
			totalcount++;
		}
		System.out.println("count:" + count);
	}

    private void percolateDownOdd(int node) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    }
    

