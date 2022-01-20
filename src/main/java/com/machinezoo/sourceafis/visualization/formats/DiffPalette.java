// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.formats;

public enum DiffPalette implements PaletteSymbol {
	BACKGROUND,
	FOREGROUND,
	REMOVED,
	ADDED;
	@Override
	public @IntColor int color() {
		return switch (this) {
			case BACKGROUND -> 0xFF_FF_FF_FF;
			case FOREGROUND -> 0xFF_00_00_00;
			case REMOVED -> 0xFF_FF_00_00;
			case ADDED -> 0xFF_00_FF_00;
		};
	}
}
