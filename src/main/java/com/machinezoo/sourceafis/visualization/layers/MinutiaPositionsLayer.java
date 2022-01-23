// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.layers;

import com.machinezoo.sourceafis.transparency.types.*;
import com.machinezoo.sourceafis.visualization.markers.*;
import com.machinezoo.sourceafis.visualization.rendering.*;

public record MinutiaPositionsLayer(Template template) implements LayerModel {
	@Override
	public ImageLayer render() {
		var buffer = new LayerBuffer();
		for (var minutia : template.minutiae())
			buffer.add(new MinutiaPositionMarker(minutia));
		return buffer.render();
	}
}
