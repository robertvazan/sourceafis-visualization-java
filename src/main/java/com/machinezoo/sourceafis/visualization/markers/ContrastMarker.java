// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.markers;

import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.types.*;
import com.machinezoo.sourceafis.visualization.graphics.*;
import com.machinezoo.sourceafis.visualization.types.*;
import com.machinezoo.stagean.*;

public record ContrastMarker(IntRect rect, double contrast) implements LayerModel {
	@Override
	@DraftCode("Centralize styling with CSS.")
	public ImageLayer render() {
		double radius = Math.sqrt(contrast) * IntRects.radius(rect);
		var center = IntRects.center(rect);
		return new LayerFrame(Svg.circle()
			.cx(center.x())
			.cy(center.y())
			.r(radius)
			.stroke("#080")
			.strokeWidth(0.3)
			.fill("#0f0")
			.fillOpacity(0.2));
	}
}
