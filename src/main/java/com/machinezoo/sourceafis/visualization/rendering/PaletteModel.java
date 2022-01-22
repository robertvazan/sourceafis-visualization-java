// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.rendering;

import com.machinezoo.sourceafis.visualization.formats.*;

public interface PaletteModel<T extends Enum<T> & PaletteSymbol> extends RasterModel {
	PaletteImage<T> render();
}
