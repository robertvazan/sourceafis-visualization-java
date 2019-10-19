// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.utils;

import java.util.*;
import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;

public class EmbeddedImage {
	private double width;
	public EmbeddedImage width(double width) {
		this.width = width;
		return this;
	}
	private double height;
	public EmbeddedImage height(double height) {
		this.height = height;
		return this;
	}
	public EmbeddedImage size(IntPoint size) {
		width = size.x;
		height = size.y;
		return this;
	}
	public EmbeddedImage size(BlockMap blocks) {
		return size(blocks.pixels);
	}
	private byte[] image;
	public EmbeddedImage image(byte[] image) {
		this.image = image;
		return this;
	}
	public String uri() {
		return "data:" + mime() + ";base64," + Base64.getEncoder().encodeToString(image);
	}
	private String mime() {
		if (image[1] == 'P' && image[2] == 'N' && image[3] == 'G')
			return "image/png";
		if (image[0] == (byte)0xff && image[1] == (byte)0xd8)
			return "image/jpeg";
		throw new IllegalArgumentException();
	}
	public DomElement svg() {
		if (image == null)
			return null;
		if (width <= 0 || height <= 0)
			throw new IllegalStateException("Width and height is required for SVG image element.");
		return Svg.image()
			.width(width)
			.height(height)
			.href(uri());
	}
	public DomElement html() {
		if (image == null)
			return null;
		DomElement img = Html.img();
		if (width > 0)
			img.width(width);
		if (height > 0)
			img.width(height);
		img.src(uri());
		return img;
	}
}
