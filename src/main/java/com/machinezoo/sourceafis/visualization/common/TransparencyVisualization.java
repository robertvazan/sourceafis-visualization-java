// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.common;

public interface TransparencyVisualization {
	int width();
	int height();
	String mime();
	String extension();
	byte[] bytes();
}
