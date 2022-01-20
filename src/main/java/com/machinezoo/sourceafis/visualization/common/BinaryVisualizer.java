// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.common;

import com.machinezoo.sourceafis.transparency.*;

public interface BinaryVisualizer<T extends Enum<T> & PaletteSymbol> extends PaletteVisualizer<T> {
	BinaryImage<T> visualize(TransparencyArchive archive);
}
