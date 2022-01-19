// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.layers;

import java.util.*;
import org.apache.commons.lang3.*;
import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.transparency.keys.*;
import com.machinezoo.sourceafis.transparency.types.*;
import com.machinezoo.sourceafis.visualization.graphics.*;

public record EmbeddedImageLayer(int width, int height, String mime, byte[] image) implements LayerModel {
	public EmbeddedImageLayer {
		Validate.isTrue(width > 0 && height > 0);
		Validate.notBlank(mime);
		Objects.requireNonNull(image);
	}
	public EmbeddedImageLayer(IntPoint size, String mime, byte[] image) {
		this(size.x(), size.y(), mime, image);
	}
	public static EmbeddedImageLayer jpeg(int width, int height, byte[] image) {
		if (!"image/jpeg".equals(ImageMime.detect(image)))
			image = new RasterBuffer(image).render().jpeg();
		return new EmbeddedImageLayer(width, height, "image/jpeg", image);
	}
	public static EmbeddedImageLayer jpeg(IntPoint size, byte[] image) {
		return jpeg(size.x(), size.y(), image);
	}
	public static Optional<EmbeddedImageLayer> input(int width, int height, TransparencyArchive archive, MatchSide side) {
		return archive.read(SideImageKey.of(side))
			.map(img -> EmbeddedImageLayer.jpeg(width, height, img))
			.or(() -> archive.deserialize(SideGrayscaleKey.of(side))
				.map(g -> new EmbeddedImageLayer(width, height, "image/jpeg", new GrayscaleFrame(g.width(), g.height(), g.array()).jpeg())));
	}
	@Override
	public ImageLayer render() {
		return new LayerFrame(Svg.image()
			.width(width)
			.height(height)
			.href("data:" + mime + ";base64," + Base64.getEncoder().encodeToString(image)));
	}
}
