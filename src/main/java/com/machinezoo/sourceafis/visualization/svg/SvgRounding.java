// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.svg;

/*
 * Limit number of digits produced into SVG. Screens cannot display smaller differences anyway.
 * Some SVG viewers don't support more than 2 decimal places anyway.
 */
public class SvgRounding {
	public static String position(double x) {
		return String.format("%.2f", x);
	}
	public static String angle(double x) {
		/*
		 * 1% of one degree is accurate enough for small markers.
		 */
		return String.format("%.2f", x);
	}
}
