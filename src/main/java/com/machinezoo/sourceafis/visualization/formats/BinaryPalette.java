// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.formats;

public enum BinaryPalette implements PaletteSymbol {
	BLACK,
	WHITE;
	@Override
	public @IntColor int color() {
		return switch (this) {
			case BLACK -> 0xFF_00_00_00;
			case WHITE -> 0xFF_FF_FF_FF;
		};
	}
}
