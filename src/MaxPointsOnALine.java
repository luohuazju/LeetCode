import java.util.HashMap;
import java.util.Map;

/*
 * Given n points on a 2D plane, find the maximum number of points that lie on the same straight line.
 */

/**
 * Definition for a point.
 * class Point {
 *     int x;
 *     int y;
 *     Point() { x = 0; y = 0; }
 *     Point(int a, int b) { x = a; y = b; }
 * }
 */

/*
 * A line is determined by two factors,say y=ax+b
 * 
 * If two points(x1,y1) (x2,y2) are on the same line(Of course).
 * 
 * Consider the gap between two points.
 * 
 * We have (y2-y1)=a(x2-x1),a=(y2-y1)/(x2-x1) a is a rational, b is canceled
 * since b is a constant
 * 
 * If a third point (x3,y3) are on the same line. So we must have y3=ax3+b
 * 
 * Thus,(y3-y1)/(x3-x1)=(y2-y1)/(x2-x1)=a
 * 
 * Since a is a rational, there exists y0 and x0,
 * y0/x0=(y3-y1)/(x3-x1)=(y2-y1)/(x2-x1)=a
 * 
 * So we can use y0&x0 to track a line;
 */

public class MaxPointsOnALine {
	public int maxPoints(Point[] points) {
		if (points == null)
			return 0;
		if (points.length <= 2)
			return points.length;

		Map<Integer, Map<Integer, Integer>> map = new HashMap<>();
		int result = 0;
		for (int i = 0; i < points.length; i++) {
			map.clear();
			int overlap = 0, max = 0;
			for (int j = i + 1; j < points.length; j++) {
				int x = points[j].x - points[i].x;
				int y = points[j].y - points[i].y;
				if (x == 0 && y == 0) {
					overlap++;
					continue;
				}
				// try to get minimum x0, y0;
				int gcd = generateGCD(x, y);
				if (gcd != 0) {
					x /= gcd;
					y /= gcd;
				}
				if (map.containsKey(x)) {
					if (map.get(x).containsKey(y)) {
						map.get(x).put(y, map.get(x).get(y) + 1);
					} else {
						map.get(x).put(y, 1);
					}
				} else {
					Map<Integer, Integer> m = new HashMap<>();
					m.put(y, 1);
					map.put(x, m);
				}
				// local max of each i.
				max = Math.max(max, map.get(x).get(y));
			}
			// global max
			result = Math.max(result, max + overlap + 1);
		}
		return result;
	}

	private int generateGCD(int a, int b) {
		if (b == 0)
			return a;
		return generateGCD(b, a % b);
	}
}
