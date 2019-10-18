package com.machinezoo.sourceafis.visualization.utils;

import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;

public class BackgroundImage {
	private double width;
	public BackgroundImage width(double width) {
		this.width = width;
		return this;
	}
	private double height;
	public BackgroundImage height(double height) {
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
	public BackgroundImage size(double width, double height) {
		return this
			.width(width)
			.height(height);
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
		return SvgContainer.of(width, height, svg());
	}
}
