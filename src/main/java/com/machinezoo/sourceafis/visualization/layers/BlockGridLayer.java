// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.layers;

import java.util.*;
import org.apache.commons.lang3.*;
import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.types.*;
import com.machinezoo.sourceafis.visualization.rendering.*;
import com.machinezoo.stagean.*;

public record BlockGridLayer(IntPoint size, BlockGrid grid, String color, double thickness) implements LayerModel {
	public BlockGridLayer {
		Objects.requireNonNull(size);
		Validate.isTrue(size.x() > 0 && size.y() > 0);
		Objects.requireNonNull(grid);
		Validate.notBlank(color);
		Validate.isTrue(thickness > 0);
	}
	@Override
	@DraftCode("Use SVG definitions.")
	public ImageLayer render() {
		var buffer = new LayerBuffer();
		for (int x : grid.x()) {
			buffer.add(Svg.line()
				.x1(x)
				.y1(0)
				.x2(x)
				.y2(size.y())
				.stroke(color)
				.strokeWidth(thickness));
		}
		for (int y : grid.y()) {
			buffer.add(Svg.line()
				.x1(0)
				.y1(y)
				.x2(size.x())
				.y2(y)
				.stroke(color)
				.strokeWidth(thickness));
		}
		return buffer.render();
	}
}
