// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.layers;

import java.util.*;
import org.apache.commons.lang3.*;
import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.transparency.keys.*;
import com.machinezoo.sourceafis.transparency.types.*;
import com.machinezoo.sourceafis.visualization.rendering.*;
import com.machinezoo.sourceafis.visualization.utils.*;

/*
 * Like EmbeddedImageLayer, but it enforces image/jpeg MIME and scaled image dimensions.
 */
public record BackgroundImageLayer(int width, int height, byte[] image) implements LayerModel {
	public BackgroundImageLayer {
		Validate.isTrue(width > 0 && height > 0);
		Objects.requireNonNull(image);
		if (!"image/jpeg".equals(ImageMime.detect(image)))
			image = new RasterBuffer(image).render().jpeg();
	}
	public BackgroundImageLayer(IntPoint size, byte[] image) {
		this(size.x(), size.y(), image);
	}
	public static Optional<BackgroundImageLayer> deserialize(int width, int height, TransparencyArchive archive, MatchSide side) {
		return archive.read(SideImageKey.of(side))
			.map(img -> new BackgroundImageLayer(width, height, img))
			.or(() -> archive.deserialize(SideGrayscaleKey.of(side))
				.map(g -> new BackgroundImageLayer(width, height, new GrayscaleFrame(g.width(), g.height(), g.array()).jpeg())));
	}
	public static Optional<BackgroundImageLayer> deserialize(IntPoint size, TransparencyArchive archive, MatchSide side) {
		return deserialize(size.x(), size.y(), archive, side);
	}
	@Override
	public ImageLayer render() {
		return new LayerFrame(Svg.image()
			.width(width)
			.height(height)
			.href("data:image/jpeg;base64," + Base64.getEncoder().encodeToString(image)));
	}
}
