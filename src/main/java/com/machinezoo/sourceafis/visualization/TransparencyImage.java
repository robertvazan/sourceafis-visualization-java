// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization;

import com.machinezoo.sourceafis.visualization.formats.*;
import com.machinezoo.sourceafis.visualization.utils.*;
import com.machinezoo.stagean.*;

@DraftApi("This could be in a separate library.")
public interface TransparencyImage {
	int width();
	int height();
	String mime();
	default String extension() {
		return ImageMime.extension(mime());
	}
	byte[] bytes();
	default MaterializedImage materialize() {
		return new MaterializedImage(width(), height(), mime(), bytes());
	}
}
