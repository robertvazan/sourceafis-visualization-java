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
	public EmbeddedImage size(double width, double height) {
		return this
			.width(width)
			.height(height);
	}
	public DomElement svg() {
		if (image == null)
			return null;
		return Svg.image()
			.width(width)
			.height(height)
			.href(uri(mime, image));
	}
	public static String uri(String mime, byte[] image) {
		if (mime == null)
			mime = mime(image);
		return "data:" + mime + ";base64," + Base64.getEncoder().encodeToString(image);
	}
	public static String uri(byte[] image) {
		return "data:" + mime(image) + ";base64," + Base64.getEncoder().encodeToString(image);
	}
	public static String mime(byte[] image) {
		if (image[1] == 'P' && image[2] == 'N' && image[3] == 'G')
			return "image/png";
		if (image[0] == (byte)0xff && image[1] == (byte)0xd8)
			return "image/jpeg";
		throw new IllegalArgumentException();
	}
}
