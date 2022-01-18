// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.layers;

import java.util.*;
import org.apache.commons.lang3.*;
import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.types.*;
import com.machinezoo.sourceafis.visualization.utils.*;

public record EmbeddedImageLayer(int width, int height, String mime, byte[] image) implements FragmentRenderer {
	public EmbeddedImageLayer {
		Validate.isTrue(width > 0 && height > 0);
		if (image != null)
			Validate.notBlank(mime);
	}
	public EmbeddedImageLayer(IntPoint size, String mime, byte[] image) {
		this(size.x(), size.y(), mime, image);
	}
	public static EmbeddedImageLayer jpeg(int width, int height, byte[] image) {
		if (image == null)
			return new EmbeddedImageLayer(width, height, null, null);
		if (!"image/jpeg".equals(ImageMime.detect(image)))
			image = new RasterBuffer(image).render().jpeg();
		return new EmbeddedImageLayer(width, height, "image/jpeg", image);
	}
	public static EmbeddedImageLayer jpeg(IntPoint size, byte[] image) {
		return jpeg(size.x(), size.y(), image);
	}
	@Override
	public FragmentVisualization render() {
		if (image == null)
			return FragmentData.EMPTY;
		return new FragmentData(Svg.image()
			.width(width)
			.height(height)
			.href("data:" + mime + ";base64," + Base64.getEncoder().encodeToString(image)));
	}
}
