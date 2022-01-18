// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.layers;

import com.machinezoo.sourceafis.transparency.types.*;
import com.machinezoo.sourceafis.visualization.utils.*;

public record DoubleBlockGridLayer(IntPoint size, BlockGrid foreground, BlockGrid background, String color) implements FragmentRenderer {
	@Override
	public FragmentVisualization render() {
		return new FragmentBuffer()
			.add(new BlockGridLayer(size, background, "#888", 0.1))
			.add(new BlockGridLayer(size, foreground, color, 0.25))
			.render();
	}
}