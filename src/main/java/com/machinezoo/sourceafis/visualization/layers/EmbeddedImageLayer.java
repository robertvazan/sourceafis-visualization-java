// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.layers;

import java.util.*;
import org.apache.commons.lang3.*;
import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.types.*;
import com.machinezoo.sourceafis.visualization.common.*;
import com.machinezoo.sourceafis.visualization.utils.*;

public record EmbeddedImageLayer(int width, int height, String mime, byte[] image) implements FragmentRenderer {
	public EmbeddedImageLayer {
		Validate.isTrue(width > 0 && height > 0);
		Validate.notBlank(mime);
		Objects.requireNonNull(image);
	}
	public EmbeddedImageLayer(IntPoint size, String mime, byte[] image) {
		this(size.x(), size.y(), mime, image);
	}
	public EmbeddedImageLayer(RasterVisualization visualization) {
		this(visualization.width(), visualization.height(), visualization.mime(), visualization.bytes());
	}
	public static EmbeddedImageLayer jpeg(int width, int height, byte[] image) {
		if (!"image/jpeg".equals(ImageMime.detect(image)))
			image = new RasterBuffer(image).render().jpeg();
		return new EmbeddedImageLayer(width, height, "image/jpeg", image);
	}
	public static EmbeddedImageLayer jpeg(IntPoint size, byte[] image) {
		return jpeg(size.x(), size.y(), image);
	}
	public static EmbeddedImageLayer jpeg(RasterVisualization visualization) {
		return new EmbeddedImageLayer(visualization.width(), visualization.height(), "image/jpeg", visualization.jpeg());
	}
	@Override
	public FragmentVisualization render() {
		return new FragmentData(Svg.image()
			.width(width)
			.height(height)
			.href("data:" + mime + ";base64," + Base64.getEncoder().encodeToString(image)));
	}
}
