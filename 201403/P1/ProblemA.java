import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.Box.Filler;

/*

입력
2
1 2 4 3
3 1 5 4

출력
3 5

*/

class Point {
	int x;
	int y;
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
}

class Rect { 
	int left;
	int top;
	int right;
	int bottom;
	int area;
	
	ArrayList<Rect> removes = new ArrayList<Rect>();
	
	public Rect(int left, int top, int right, int bottom) {
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		
	}
	
	@Override
	public String toString() {
		return "(" + left + " " + top + " " + right + " " + bottom + " " + area + ")";
	}
}

public class ProblemA {
	
	static boolean map[][];
	static ArrayList<Integer> xLines;
	static ArrayList<Integer> yLines;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		
		ArrayList<Rect> rects = new ArrayList<Rect>();
		xLines = new ArrayList<Integer>();
		yLines = new ArrayList<Integer>();
		
		for (int i = 0; i < n; i++) {
			int left = sc.nextInt();
			int top = sc.nextInt();
			int right = sc.nextInt();
			int bottom = sc.nextInt();
			
			rects.add(new Rect(left, top, right, bottom));
			
			xLines.add(left);
			xLines.add(right);
			yLines.add(top);
			yLines.add(bottom);
		}
		
		Collections.sort(xLines);
		Collections.sort(yLines);
		
		map = new boolean[yLines.size() * 2][xLines.size() * 2];
		for (int i = 0; i < yLines.size() * 2; i++) {
			for (int j = 0; j < xLines.size() * 2; j++) {
				map[i][j] = true; 
			}
		}
		
		for (Rect rect : rects) {
			int leftIndex = xLines.indexOf(rect.left);
			int rightIndex = xLines.indexOf(rect.right);
			int topIndex = yLines.indexOf(rect.top);
			int bottomIndex = yLines.indexOf(rect.bottom);

			for (int y = topIndex * 2; y <= bottomIndex * 2; y++) {
				map[y][leftIndex * 2] = false;
			}
			for (int y = topIndex * 2; y <= bottomIndex * 2; y++) {
				map[y][rightIndex * 2] = false;
			}
			for (int x = leftIndex * 2; x <= rightIndex * 2; x++) {
				map[topIndex * 2][x] = false;
			}
			for (int x = leftIndex * 2; x <= rightIndex * 2; x++) {
				map[bottomIndex * 2][x] = false;
			}
		}
		
		/*for (int i = 0; i < yLines.size() * 2; i++) {
			for (int j = 0; j < xLines.size() * 2; j++) {
				if (map[i][j]) {
					System.out.print("_");
				} else {
					System.out.print("X");
				}
			}
			System.out.println();
		}*/
		
		int numOfRect = 0;
		int maxArea = 0;
		for (Rect rect : rects) {
			int leftIndex = xLines.indexOf(rect.left);
			int rightIndex = xLines.indexOf(rect.right);
			int topIndex = yLines.indexOf(rect.top);
			int bottomIndex = yLines.indexOf(rect.bottom);
			
			for (int i = topIndex * 2 + 1; i < bottomIndex * 2; i += 2) {
				for (int j = leftIndex * 2 + 1; j < rightIndex * 2; j += 2) {
					int area = getArea(j, i);
					if (area > 0) {
						numOfRect++;
						maxArea = Math.max(maxArea, area);
					}
				}
			}
		}
		System.out.println(numOfRect + " " + maxArea);
	}

	private static int getArea(int x, int y) {
		LinkedList<Point> next = new LinkedList<Point>();
		next.add(new Point(x, y));
		int area = 0;
		while (next.size() > 0) {
			Point point = next.removeFirst();
			y = point.y;
			x = point.x;
			
			if (map[y][x] == false) {
				continue;
			}
			
			map[y][x] = false;
			area += (xLines.get((x + 1) / 2) - (xLines.get((x - 1) / 2)))
					* (yLines.get((y + 1) / 2) - (yLines.get((y - 1) / 2)));

			if (map[y][x - 1] == true) {
				Point newPoint = new Point(x - 2, y);
				next.add(newPoint);
			}
			if (map[y][x + 1] == true) {
				Point newPoint = new Point(x + 2, y);
				next.add(newPoint);
			}
			if (map[y - 1][x] == true) {
				Point newPoint = new Point(x, y - 2);
				next.add(newPoint);
			}
			if (map[y + 1][x] == true) {
				Point newPoint = new Point(x, y + 2);
				next.add(newPoint);
			}
		}

		return area;
	}
}
