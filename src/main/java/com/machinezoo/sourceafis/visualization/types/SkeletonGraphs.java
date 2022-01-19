// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.types;

import com.machinezoo.sourceafis.transparency.types.*;

public class SkeletonGraphs {
	public static BooleanMatrix shadow(SkeletonGraph skeleton) {
		var pixels = new boolean[skeleton.width() * skeleton.height()];
		for (var minutia : skeleton.minutiae())
			pixels[minutia.y() * skeleton.width() + minutia.x()] = true;
		for (var ridge : skeleton.ridges())
			if (ridge.start() <= ridge.end())
				for (var point : ridge.points())
					pixels[point.y() * skeleton.width() + point.x()] = true;
		return new BooleanMatrix(skeleton.width(), skeleton.height(), pixels);
	}
}
