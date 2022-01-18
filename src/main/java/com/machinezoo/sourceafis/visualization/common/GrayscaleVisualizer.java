// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.common;

import com.machinezoo.sourceafis.transparency.*;

public interface GrayscaleVisualizer extends RasterVisualizer {
	GrayscaleVisualization render(TransparencyArchive archive);
}