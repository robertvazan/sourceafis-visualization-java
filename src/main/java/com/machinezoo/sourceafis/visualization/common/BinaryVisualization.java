// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.common;

public interface BinaryVisualization extends PaletteVisualization<BinaryColor> {
	boolean[] bits();
	@Override
	default Class<BinaryColor> type() {
		return BinaryColor.class;
	}
	@Override
	default byte[] codes() {
		var bits = bits();
		var codes = new byte[bits.length];
		for (int i = 0; i < codes.length; ++i)
			codes[i] = bits[i] ? (byte)1 : (byte)0;
		return codes;
	}
}
