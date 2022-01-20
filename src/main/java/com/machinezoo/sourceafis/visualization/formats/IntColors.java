// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.formats;

/*
 * Some methods on colors are exposed in order to help with interpretation of IntColor annotation.
 * We don't want to expose too much in public API though. Separate utility class can cover everything else.
 */
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
	public static @ByteColor int brightness(@IntColor int c) {
		return (red(c) + green(c) + blue(c)) / 3;
	}
	public static @IntColor int argb(@ByteColor int a, @ByteColor int r, @ByteColor int g, @ByteColor int b) {
		return ((a & 0xFF) << 24) | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | (b & 0xFF);
	}
	public static @IntColor int rgb(@ByteColor int r, @ByteColor int g, @ByteColor int b) {
		return argb(0xFF, r, g, b);
	}
	public static @IntColor int gray(@ByteColor int g) {
		return rgb(g, g, g);
	}
}
