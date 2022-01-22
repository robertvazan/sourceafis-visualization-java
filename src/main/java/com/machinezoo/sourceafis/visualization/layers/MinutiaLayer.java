// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.layers;

import java.util.*;
import com.machinezoo.sourceafis.transparency.types.*;
import com.machinezoo.sourceafis.visualization.markers.*;
import com.machinezoo.sourceafis.visualization.rendering.*;

public record MinutiaLayer(Template template) implements LayerModel {
	public MinutiaLayer {
		Objects.requireNonNull(template);
	}
	@Override
	public ImageLayer render() {
		var buffer = new LayerBuffer();
		for (var minutia : template.minutiae())
			buffer.add(new MinutiaMarker(minutia));
		return buffer.render();
	}
}
