// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.layers;

import java.util.*;
import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.visualization.common.*;
import com.machinezoo.sourceafis.visualization.graphics.*;

public record EmbeddedImageLayer(TransparencyImage image) implements LayerModel {
	public EmbeddedImageLayer {
		Objects.requireNonNull(image);
	}
	@Override
	public ImageLayer render() {
		return new LayerFrame(Svg.image()
			.width(image.width())
			.height(image.height())
			.href("data:" + image.mime() + ";base64," + Base64.getEncoder().encodeToString(image.bytes())));
	}
}
