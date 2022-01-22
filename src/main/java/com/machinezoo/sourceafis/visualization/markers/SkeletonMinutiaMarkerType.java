// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.markers;

import com.machinezoo.sourceafis.transparency.types.*;
import it.unimi.dsi.fastutil.objects.*;

public enum SkeletonMinutiaMarkerType {
	ENDPOINT,
	SPLITPOINT,
	DOT,
	KNOT,
	ADDED,
	REMOVED;
	public String color() {
		return switch (this) {
			case ENDPOINT -> "blue";
			case SPLITPOINT -> "cyan";
			case DOT -> "plum";
			case KNOT -> "plum";
			case ADDED -> "green";
			case REMOVED -> "red";
		};
	}
	public static SkeletonMinutiaMarkerType classify(int degree) {
		return switch (degree) {
			case 0 -> DOT;
			case 1 -> ENDPOINT;
			case 2 -> KNOT;
			default -> SPLITPOINT;
		};
	}
	public static SkeletonMinutiaMarkerType classify(IntPoint minutia, Object2IntMap<IntPoint> degrees) {
		return classify(degrees.getOrDefault(minutia, 0));
	}
}
