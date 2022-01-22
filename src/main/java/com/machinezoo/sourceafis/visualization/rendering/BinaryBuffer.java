// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.rendering;

import java.util.*;
import com.machinezoo.sourceafis.transparency.types.*;
import com.machinezoo.sourceafis.visualization.formats.*;

public class BinaryBuffer<T extends Enum<T> & PaletteSymbol> implements BinaryModel<T> {
	public final Class<T> palette;
	public final int width;
	public final int height;
	public final BitSet bits;
	public BinaryBuffer(Class<T> palette, int width, int height) {
		this.palette = palette;
		this.width = width;
		this.height = height;
		bits = new BitSet(width * height);
	}
	public BinaryBuffer(Class<T> palette, IntPoint size) {
		this(palette, size.x(), size.y());
	}
	public BinaryBuffer(Class<T> palette, BooleanMatrix matrix) {
		this(palette, matrix.size());
		for (int i = 0; i < matrix.cells().length; ++i)
			bits.set(i, matrix.cells()[i]);
	}
	public void fill(boolean v) {
		bits.set(0, width * height, v);
	}
	public void fill(T symbol) {
		fill(symbol.ordinal() > 0);
	}
	public void invert() {
		bits.flip(0, width * height);
	}
	public void set(int x, int y, boolean v) {
		bits.set(y * width + x, v);
	}
	public void set(int x, int y, T symbol) {
		bits.set(y * width + x, symbol.ordinal() > 0);
	}
	public void set(IntPoint at, boolean v) {
		set(at.x(), at.y(), v);
	}
	public void set(IntPoint at, T symbol) {
		set(at.x(), at.y(), symbol);
	}
	@Override
	public BinaryImage<T> render() {
		return new BinaryFrame<>(palette, width, height, bits);
	}
}
