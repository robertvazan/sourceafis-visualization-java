// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.common;

import com.machinezoo.sourceafis.transparency.*;

public interface BinaryVisualizer extends PaletteVisualizer<BinaryColor> {
	BinaryVisualization render(TransparencyArchive archive);
	@Override
	default Class<BinaryColor> type() {
		return BinaryColor.class;
	}
}
