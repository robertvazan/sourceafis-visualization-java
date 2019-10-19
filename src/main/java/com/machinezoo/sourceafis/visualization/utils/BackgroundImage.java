// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.utils;

import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;

public class BackgroundImage {
	private double width;
	private double height;
	public BackgroundImage size(double width, double height) {
		this.width = width;
		this.height = height;
		return this;
	}
	private byte[] background;
	public BackgroundImage background(byte[] background) {
		this.background = background;
		return this;
	}
	private DomContent content;
	public BackgroundImage content(DomContent content) {
		this.content = content;
		return this;
	}
	public BackgroundImage size(IntPoint size) {
		return this.size(size.x, size.y);
	}
	public BackgroundImage size(BlockMap blocks) {
		return size(blocks.pixels);
	}
	public DomContent svg() {
		if (background == null)
			return content;
		return new DomFragment()
			.add(new EmbeddedImage()
				.size(width, height)
				.image(background)
				.svg())
			.add(content);
	}
	public DomElement document() {
		return SvgImage.of(width, height, svg());
	}
}
