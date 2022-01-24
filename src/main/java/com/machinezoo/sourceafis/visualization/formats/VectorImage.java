// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.formats;

import java.nio.charset.*;
import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.visualization.*;
import com.machinezoo.stagean.*;

public interface VectorImage extends TransparencyImage {
	@DraftApi("Replace pushmode with proper SVG library.")
	DomElement tree();
	@Override
	default String mime() {
		return "image/svg+xml";
	}
	default String source() {
		return DomFormatter.svg()
			.format(tree())
			.toString();
	}
	@Override
	default byte[] bytes() {
		return source().getBytes(StandardCharsets.UTF_8);
	}
}
