// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.images;

import com.machinezoo.sourceafis.visualization.formats.*;

public enum MaskPalette implements PaletteSymbol {
	INTERIOR,
	EXTERIOR;
	@Override
	public @IntColor int color() {
		return switch (this) {
			case INTERIOR -> 0x20_FF_FF_00;
			case EXTERIOR -> 0x00_FF_FF_FF;
		};
	}
}
