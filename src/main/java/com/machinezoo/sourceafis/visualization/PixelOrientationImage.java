package com.machinezoo.sourceafis.visualization;

import org.apache.sanselan.color.*;
import com.google.common.collect.*;
import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.visualization.utils.*;

public class PixelOrientationImage {
	private final DoublePointMatrix orientations;
	private final byte[] background;
	public PixelOrientationImage(DoublePointMatrix orientations, byte[] background) {
		this.orientations = orientations;
		this.background = background;
	}
	public PixelOrientationImage(DoublePointMatrix orientations) {
		this(orientations, null);
	}
	public WritableImage image() {
		WritableImage image = new WritableImage(orientations.size());
		double max = Math.log1p(Streams.stream(orientations.size()).map(orientations::get).mapToDouble(DoublePoint::length).max().orElse(1));
		int opacity = background != null ? 0x60000000 : 0xff000000;
		for (IntPoint at : orientations.size()) {
			DoublePoint vector = orientations.get(at);
			if (vector.x != 0 || vector.y != 0) {
				double angle = DoubleAngle.atan(vector);
				double strength = Math.log1p(vector.length()) / max;
				image.set(at, ColorConversions.convertHSLtoRGB(angle / DoubleAngle.PI2, 0.2 + 0.8 * strength, 0.5) & 0xffffff | opacity);
			}
		}
		return image;
	}
	public DomElement html() {
		if (background == null)
			return image().html();
		else {
			return new SvgImage()
				.size(orientations.size())
				.background(background)
				.content(image().svg())
				.document();
		}
	}
}
