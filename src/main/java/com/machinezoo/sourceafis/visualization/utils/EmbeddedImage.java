// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.utils;

import java.util.*;
import com.machinezoo.pushmode.dom.*;

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
	private byte[] image;
	public EmbeddedImage image(byte[] image) {
		this.image = image;
		return this;
	}
	private String mime;
	public EmbeddedImage mime(String mime) {
		this.mime = mime;
		return this;
	}
	public String uri() {
		return "data:" + Optional.ofNullable(mime).orElseGet(this::guess) + ";base64," + Base64.getEncoder().encodeToString(image);
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
	private String guess() {
		if (image[1] == 'P' && image[2] == 'N' && image[3] == 'G')
			return "image/png";
		if (image[0] == (byte)0xff && image[1] == (byte)0xd8)
			return "image/jpeg";
		throw new IllegalArgumentException();
	}
}
