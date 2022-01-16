// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.common;

public class IntColors {
	public static @ByteColor int alpha(@IntColor int c) {
		return (c >>> 24) & 0xFF;
	}
	public static @ByteColor int red(@IntColor int c) {
		return (c >>> 16) & 0xFF;
	}
	public static @ByteColor int green(@IntColor int c) {
		return (c >>> 8) & 0xFF;
	}
	public static @ByteColor int blue(@IntColor int c) {
		return c & 0xFF;
	}
	public static @IntColor int argb(@ByteColor int a, @ByteColor int r, @ByteColor int g, @ByteColor int b) {
		return ((a & 0xFF) << 24) | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | (b & 0xFF);
	}
	public static @IntColor int gray(@ByteColor int g) {
		g &= 0xFF;
		return 0xff_00_00_00 | (g << 16) | (g << 8) | g;
	}
}
