// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.utils;

import java.util.*;
import com.machinezoo.pushmode.dom.*;

public class SvgPath {
	private List<String> steps = new ArrayList<>();
	private SvgPath add(String step) {
		steps.add(step);
		return this;
	}
	public SvgPath move(double x, double y) {
		return add(String.format("M %f %f", x, y));
	}
	public SvgPath line(double x, double y) {
		return add(String.format("L %f %f", x, y));
	}
	public SvgPath close() {
		return add("Z");
	}
	public SvgPath arc(double rx, double ry, double rotation, boolean large, boolean sweep, double x, double y) {
		return add(String.format("A %f %f %f %d %d %f %f", rx, ry, rotation, large ? 1 : 0, sweep ? 1 : 0, x, y));
	}
	public DomElement svg() {
		return Svg.path().d(String.join(" ", steps));
	}
}
