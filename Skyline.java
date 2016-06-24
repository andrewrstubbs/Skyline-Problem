import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import CS232.Building;

/**
 * 
 * @author Andrew Stubbs
 * @version 3/12/14 Uses a mergesort-like algorithm on a list of
 *          buildings(entered as a left x-axis side, right x-axis side, and
 *          y-axis height) to calculate a list of points which represents the
 *          "skyline" of these buildings if viewed in three dimensions
 * 
 */
public class Skyline {

	public Skyline() {
	}

	/**
	 * Uses a mergesort like algorithm to divide a list of buildings into
	 * individual lists of two skyline coordinates, then merging each into an
	 * overall skyline
	 * 
	 * @param buildingList
	 *            The list of buildings to be evaluated
	 * @return The overall skyline for the input list of buildings
	 */
	public static ArrayList<Point> makeSkyline(ArrayList<Building> buildingList) {

		// If the base case (one or zero building) is reached, returns the two
		// skyline coordinates
		// of that building
		ArrayList<Point> mergedSkyline = new ArrayList<Point>();
		if (buildingList.size() < 2) {
			for (Building h : buildingList) {
				Point point1 = new Point(h.getLeft(), h.getTop());
				Point point2 = new Point(h.getRight(), 0);
				mergedSkyline.add(point1);
				mergedSkyline.add(point2);
			}
			return mergedSkyline;
		}

		// If the base case has not been reached, divides the list of buildings
		// in half and recursively
		// calls the makeSkylive method. Upon returning from the base case,
		// merges the two lists of
		// transformed skyline coordinates into an overall skyline
		else {
			ArrayList<Point> a = new ArrayList<Point>();
			ArrayList<Point> b = new ArrayList<Point>();
			// Creates the first half of the buildings
			ArrayList<Building> c = new ArrayList<Building>();
			for (int f = 0; f < (buildingList.size() / 2)
					+ (buildingList.size() % 2); f++) {
				c.add(buildingList.get(f));
			}
			// Creates the second half of the buildings
			ArrayList<Building> d = new ArrayList<Building>();
			for (int g = (buildingList.size() / 2) + buildingList.size() % 2; g < buildingList
					.size(); g++) {
				d.add(buildingList.get(g));
			}
			// Recursively calls makeSkyline
			a = makeSkyline(c);
			b = makeSkyline(d);
			// Merges the two skylines upon return from the base case
			ArrayList<Point> testSky = mergeSkyline(a, b);
			// Returns the overall skyline
			return mergeSkyline(a, b);

		}

	}

	/**
	 * Merges a building's skyline with an existing skyline to produce one
	 * accurate skyline of both sets of coordinates
	 * 
	 * @param greenList
	 *            The building's skyline
	 * @param redList
	 *            The existing skyline
	 * @return One overall skyline comprising both input coordinates
	 */
	public static ArrayList<Point> mergeSkyline(ArrayList<Point> greenList,
			ArrayList<Point> redList) {

		// If the existing skyline is empty, returns the building's skyline as
		// the overall skyline
		if (redList.size() < 2) {
			return greenList;
		}

		// Initializes variables to be used in merging the two sets of
		// coordinates
		ArrayList<Point> mergedSkyline = new ArrayList<Point>();
		int redHeight = 0;
		int greenHeight = 0;
		int skyHeight = 0;
		int redx = 0;
		int greenx = 0;
		int skyx = 0;
		int redIndex = 0;
		int greenIndex = 0;

		// Continues to iterate while neither skyline's potential coordinates
		// have been exhausted
		while (greenIndex < greenList.size() && redIndex < redList.size()) {

			// Sets the initial x-values for each skyline
			redx = (int) redList.get(redIndex).getX();
			greenx = (int) greenList.get(greenIndex).getX();
			// Determines the minimum of these x-values
			skyx = Math.min(redx, greenx);

			// If the two skylines' current coordinates are identical, creates a
			// new point when
			// this point's height differs from the previous height of the
			// skyline.
			if (redList.get(redIndex).equals(greenList.get(greenIndex))) {
				if (redList.get(redIndex).getY() != skyHeight) {
					skyHeight = (int) redList.get(redIndex).getY();
					Point newPoint = new Point(redList.get(redIndex));
					mergedSkyline.add(newPoint);
					// Iterates the list of coordinates
					redIndex++;
					greenIndex++;
				} else {
					// Iterates the list of coordinates
					redIndex++;
					greenIndex++;
				}

			}
			// If the x values of the two skylines' current coordinates are
			// identical, creates
			// a new point at the greater of these two points' heights if it
			// differs
			// from the previous height of the skyline
			else if (redx == greenx) {
				redHeight = (int) redList.get(redIndex).getY();
				greenHeight = (int) greenList.get(greenIndex).getY();
				if (Math.max(redHeight, greenHeight) != skyHeight) {
					skyHeight = Math.max(redHeight, greenHeight);
					Point newPoint = new Point(redx, Math.max(redHeight,
							greenHeight));
					mergedSkyline.add(newPoint);
					// Iterates the list of coordinates
					redIndex++;
					greenIndex++;
				} else {
					// Iterates the list of coordinates
					redIndex++;
					greenIndex++;
				}
			}
			// If the x value of the existing skyline's current coordinate is
			// smaller than that of
			// the new building, creates a new point at the greater of the two
			// points' y heights if
			// it differs from the previous height of the skyline
			else if (redx == skyx) {
				redHeight = (int) redList.get(redIndex).getY();
				if (Math.max(redHeight, greenHeight) != skyHeight) {
					skyHeight = Math.max(redHeight, greenHeight);
					Point newPoint = new Point(redx, skyHeight);
					mergedSkyline.add(newPoint);
					// Iterates the list of coordinates
					redIndex++;
				} else {
					// Iterates the list of coordinates
					redIndex++;
				}
			}
			// If the x value of the new building's current coordinate is
			// smaller than that of
			// the existing skyline, creates a new point at the greater of the
			// two points' y heights if
			// it differs from the previous height of the skyline
			else if (greenx == skyx) {
				greenHeight = (int) greenList.get(greenIndex).getY();
				if (Math.max(redHeight, greenHeight) != skyHeight) {
					skyHeight = Math.max(redHeight, greenHeight);
					Point newPoint = new Point(greenx, skyHeight);
					mergedSkyline.add(newPoint);
					// Iterates the list of coordinates
					greenIndex++;
				} else {
					// Iterates the list of coordinates
					greenIndex++;
				}
			}
		}
		// Adds any remaining coordinates to the overall skyline which were were
		// not compared
		// in the while loop
		for (int d = redIndex; d < redList.size(); d++) {
			System.out.println("RED");
			mergedSkyline.add(redList.get(redIndex));
			redIndex++;
		}
		for (int d = greenIndex; d < greenList.size(); d++) {
			System.out.println("GREEN");
			mergedSkyline.add(greenList.get(greenIndex));
			greenIndex++;
		}
		// Returns the overall skyline
		return mergedSkyline;

	}
}
