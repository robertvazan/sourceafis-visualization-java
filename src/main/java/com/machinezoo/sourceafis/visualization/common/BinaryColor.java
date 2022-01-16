// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.common;

public enum BinaryColor implements ColorCode {
	BACKGROUND,
	FOREGROUND;
	@Override
	public @IntColor int color() {
		return switch (this) {
			case BACKGROUND -> 0xFF_FF_FF_FF;
			case FOREGROUND -> 0xFF_00_00_00;
		};
	}
}
