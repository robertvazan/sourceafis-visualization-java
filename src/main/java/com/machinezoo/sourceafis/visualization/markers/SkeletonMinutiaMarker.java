// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.markers;

import java.util.*;
import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.types.*;
import com.machinezoo.sourceafis.visualization.rendering.*;
import com.machinezoo.sourceafis.visualization.types.*;
import com.machinezoo.stagean.*;

public record SkeletonMinutiaMarker(IntPoint position, SkeletonMinutiaMarkerType type) implements LayerModel {
	public SkeletonMinutiaMarker {
		Objects.requireNonNull(position);
		Objects.requireNonNull(type);
	}
	@Override
	@DraftCode("Use SVG defs.")
	public ImageLayer render() {
		var at = IntPoints.center(position);
		return new LayerFrame(Svg.circle()
			.cx(at.x())
			.cy(at.y())
			.r(4)
			.fill("none")
			.stroke(type.color())
			.strokeWidth(0.7));
	}
}
