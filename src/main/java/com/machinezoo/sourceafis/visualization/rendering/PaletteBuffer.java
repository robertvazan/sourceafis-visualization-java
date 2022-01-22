// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.rendering;

import com.machinezoo.sourceafis.transparency.types.*;
import com.machinezoo.sourceafis.visualization.formats.*;

public class PaletteBuffer<T extends Enum<T> & PaletteSymbol> implements PaletteModel<T> {
	public final Class<T> palette;
	public final int width;
	public final int height;
	public final @PaletteColor byte[] codes;
	public PaletteBuffer(Class<T> palette, int width, int height) {
		this.palette = palette;
		this.width = width;
		this.height = height;
		codes = new byte[width * height];
	}
	public PaletteBuffer(Class<T> palette, IntPoint size) {
		this(palette, size.x(), size.y());
	}
	public void fill(T symbol) {
		for (int i = 0; i < codes.length; ++i)
			codes[i] = (byte)symbol.ordinal();
	}
	public void set(int x, int y, T symbol) {
		codes[y * width + x] = (byte)symbol.ordinal();
	}
	public void set(IntPoint at, T symbol) {
		set(at.x(), at.y(), symbol);
	}
	@Override
	public PaletteImage<T> render() {
		return new PaletteFrame<>(palette, width, height, codes);
	}
}
