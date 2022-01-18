// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.utils;

public class Doubles {
	public static double sq(double x) {
		return x * x;
	}
	public static String svg(double x) {
		/*
		 * Limit number of digits produced into SVG. Screens cannot display smaller differences anyway.
		 * Some SVG viewers don't support more than 2 decimal places anyway.
		 */
		return String.format("%.2f", x);
	}
}
