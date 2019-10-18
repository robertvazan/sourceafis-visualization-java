package com.machinezoo.sourceafis.visualization.utils;

import com.machinezoo.pushmode.dom.*;

public class EmbeddedImg {
	private double width;
	public EmbeddedImg width(double width) {
		this.width = width;
		return this;
	}
	private double height;
	public EmbeddedImg height(double height) {
		this.height = height;
		return this;
	}
	private byte[] image;
	public EmbeddedImg image(byte[] image) {
		this.image = image;
		return this;
	}
	private String mime;
	public EmbeddedImg mime(String mime) {
		this.mime = mime;
		return this;
	}
	public EmbeddedImg size(int width, int height) {
		return this
			.width(width)
			.height(height);
	}
	public DomElement html() {
		if (image == null)
			return null;
		DomElement img = Html.img();
		if (width > 0)
			img.width(width);
		if (height > 0)
			img.width(height);
		img.src(EmbeddedImage.uri(mime, image));
		return img;
	}
	public static DomElement of(byte[] image) {
		return new EmbeddedImg()
			.image(image)
			.html();
	}
}
