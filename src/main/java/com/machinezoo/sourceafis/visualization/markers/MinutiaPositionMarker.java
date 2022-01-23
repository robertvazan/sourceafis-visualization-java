// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.markers;

import java.util.*;
import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.types.*;
import com.machinezoo.sourceafis.visualization.rendering.*;
import com.machinezoo.sourceafis.visualization.types.*;
import com.machinezoo.stagean.*;

public record MinutiaPositionMarker(IntPoint position) implements LayerModel {
	public MinutiaPositionMarker {
		Objects.requireNonNull(position);
	}
	public MinutiaPositionMarker(MinutiaPoint minutia) {
		this(minutia.position());
	}
	@Override
	@DraftCode("Use SVG defs.")
	public ImageLayer render() {
		var at = IntPoints.center(position);
		return new LayerFrame(Svg.circle()
			.cx(at.x())
			.cy(at.y())
			.r(2.5)
			.fill("red"));
	}
}
