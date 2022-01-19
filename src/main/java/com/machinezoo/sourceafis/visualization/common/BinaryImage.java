// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.common;

import java.util.*;
import com.machinezoo.stagean.*;

/*
 * False is 0 or black. True is 1 or white.
 */
@DraftCode("Support native binary and grayscale output images.")
public interface BinaryImage extends PaletteImage<BinaryColor>, GrayscaleImage {
	BitSet bits();
	@Override
	default Class<BinaryColor> type() {
		return BinaryColor.class;
	}
	@Override
	default byte[] codes() {
		var bits = bits();
		var codes = new byte[width() * height()];
		for (int i = 0; i < codes.length; ++i)
			codes[i] = bits.get(i) ? (byte)1 : (byte)0;
		return codes;
	}
	@Override
	default @ByteColor byte[] shades() {
		var bits = bits();
		var shades = new byte[width() * height()];
		for (int i = 0; i < shades.length; ++i)
			shades[i] = bits.get(i) ? (byte)0xFF : (byte)0;
		return shades;
	}
	@Override
	@DraftCode("Provide direct conversion to raster.")
	default @IntColor int[] pixels() {
		return PaletteImage.super.pixels();
	}
}
