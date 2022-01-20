// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.formats;

import java.util.*;
import com.machinezoo.stagean.*;

/*
 * False is 0 or black. True is 1 or white.
 */
@DraftCode("Support native binary and grayscale output images.")
public interface BinaryImage<T extends Enum<T> & PaletteSymbol> extends PaletteImage<T>, GrayscaleImage {
	BitSet bits();
	@Override
	default @PaletteColor byte[] codes() {
		var bits = bits();
		var codes = new byte[width() * height()];
		for (int i = 0; i < codes.length; ++i)
			codes[i] = bits.get(i) ? (byte)1 : (byte)0;
		return codes;
	}
	@Override
	default @ByteColor byte[] shades() {
		var bits = bits();
		var symbols = palette().getEnumConstants();
		var black = (byte)IntColors.brightness(symbols[0].color());
		var white = (byte)IntColors.brightness(symbols[1].color());
		var shades = new byte[width() * height()];
		for (int i = 0; i < shades.length; ++i)
			shades[i] = bits.get(i) ? white : black;
		return shades;
	}
	@Override
	@DraftCode("Provide direct conversion to raster.")
	default @IntColor int[] pixels() {
		return PaletteImage.super.pixels();
	}
}
