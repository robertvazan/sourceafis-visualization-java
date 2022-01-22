// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.layers;

import java.util.*;
import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.types.*;
import com.machinezoo.sourceafis.visualization.rendering.*;
import com.machinezoo.sourceafis.visualization.types.*;

/*
 * Mask is optional.
 */
public record BlockOrientationLayer(BlockMap blocks, BooleanMatrix mask, DoublePointMatrix orientations) implements LayerModel {
	public BlockOrientationLayer {
		Objects.requireNonNull(blocks);
		Objects.requireNonNull(orientations);
	}
	private static DomContent marker(DoublePoint orientation, IntRect rect) {
		var center = IntRects.center(rect);
		var direction = DoubleAngles.toVector(DoubleAngles.fromOrientation(orientation.angle()));
		var arm = DoublePoints.multiply(0.5 * Math.min(rect.width(), rect.height()), direction);
		var from = DoublePoints.sum(center, arm);
		var to = DoublePoints.difference(center, arm);
		return Svg.line()
			.x1(from.x())
			.y1(from.y())
			.x2(to.x())
			.y2(to.y())
			.stroke("red");
	}
	@Override
	public ImageLayer render() {
		var buffer = new LayerBuffer();
		for (var at : IntPoints.stream(blocks.primary().blocks()))
			if (mask == null || mask.get(at))
				buffer.add(marker(orientations.get(at), blocks.primary().block(at)));
		return buffer.render();
	}
}
