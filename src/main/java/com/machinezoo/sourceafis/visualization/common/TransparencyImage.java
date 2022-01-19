// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.common;

public interface TransparencyImage {
	int width();
	int height();
	String mime();
	String extension();
	byte[] bytes();
	default MaterializedImage materialize() {
		return new MaterializedImage(width(), height(), mime(), bytes());
	}
}
