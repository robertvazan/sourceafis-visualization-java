// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization;

import static com.machinezoo.sourceafis.visualization.TransparencyMarkers.*;
import java.nio.charset.*;
import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.types.*;
import com.machinezoo.sourceafis.visualization.layers.*;

public class TransparencyImage {
	private final double width;
	public double width() {
		return width;
	}
	private final double height;
	public double height() {
		return height;
	}
	public TransparencyImage(double width, double height) {
		this.width = width;
		this.height = height;
	}
	public TransparencyImage(IntPoint size) {
		this(size.x(), size.y());
	}
	public TransparencyImage(BlockMap blocks) {
		this(blocks.pixels());
	}
	private double padding;
	public double padding() {
		return padding;
	}
	public TransparencyImage padding(double padding) {
		this.padding = padding;
		return this;
	}
	private DomFragment fragment = new DomFragment();
	public DomContent content() {
		return fragment;
	}
	public TransparencyImage add(DomContent content) {
		fragment.add(content);
		return this;
	}
	public TransparencyImage png(TransparencyPixmap pixmap) {
		return add(embedPng(pixmap));
	}
	public TransparencyImage jpeg(TransparencyPixmap pixmap) {
		return add(embedJpeg(pixmap));
	}
	public TransparencyImage image(byte[] image) {
		return add(image != null ? new BackgroundImageLayer((int)width, (int)height, image).render().content() : null);
	}
	public DomElement tree() {
		return Svg.svg()
			.version("1.1")
			.viewBox((-padding) + " " + (-padding) + " " + (width + 2 * padding) + " " + (height + 2 * padding))
			.add(fragment);
	}
	public byte[] bytes() {
		return DomFormatter.svg()
			.format(tree())
			.toString()
			.getBytes(StandardCharsets.UTF_8);
	}
}
