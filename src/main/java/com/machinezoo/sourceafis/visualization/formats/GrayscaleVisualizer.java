// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.formats;

import com.machinezoo.sourceafis.transparency.*;

public interface GrayscaleVisualizer extends RasterVisualizer {
	GrayscaleImage visualize(TransparencyArchive archive);
}
