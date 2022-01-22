// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.images;

import com.machinezoo.sourceafis.visualization.formats.*;

public enum BinaryLayerPalette implements PaletteSymbol {
	BACKGROUND,
	FOREGROUND;
	@Override
	public @IntColor int color() {
		return switch (this) {
			case BACKGROUND -> 0x00_FF_FF_FF;
			case FOREGROUND -> 0x90_00_FF_FF;
		};
	}
}
