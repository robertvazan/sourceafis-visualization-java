// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.markers;

import com.machinezoo.sourceafis.transparency.types.*;

public enum MinutiaMarkerType {
	ENDING,
	BIFURCATION,
	REMOVED;
	public String color() {
		return switch (this) {
			case ENDING -> "blue";
			case BIFURCATION -> "green";
			case REMOVED -> "red";
		};
	}
	public static MinutiaMarkerType from(MinutiaType type) {
		return switch (type) {
			case ENDING -> ENDING;
			case BIFURCATION -> BIFURCATION;
		};
	}
}
