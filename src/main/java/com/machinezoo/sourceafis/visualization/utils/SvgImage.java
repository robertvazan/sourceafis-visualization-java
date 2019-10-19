// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.utils;

import java.nio.charset.*;
import java.security.*;
import java.util.*;
import com.machinezoo.noexception.*;
import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;

public class SvgImage {
	private double width;
	public SvgImage width(double width) {
		this.width = width;
		return this;
	}
	private double height;
	public SvgImage height(double height) {
		this.height = height;
		return this;
	}
	public SvgImage size(IntPoint size) {
		width = size.x;
		height = size.y;
		return this;
	}
	public SvgImage size(BlockMap blocks) {
		return size(blocks.pixels);
	}
	private double padding;
	public SvgImage padding(double padding) {
		this.padding = padding;
		return this;
	}
	private DomContent content;
	public SvgImage content(DomContent content) {
		this.content = content;
		return this;
	}
	private byte[] background;
	public SvgImage background(byte[] background) {
		this.background = background;
		return this;
	}
	public DomElement document() {
		DomElement root = Svg.svg()
			.version("1.1")
			.viewBox((-padding) + " " + (-padding) + " " + (width + 2 * padding) + " " + (height + 2 * padding))
			.add(new EmbeddedImage()
				.width(width)
				.height(height)
				.image(background)
				.svg())
			.add(content);
		/*
		 * Force removing and re-adding of the whole SVG document whenever anything in it changes
		 * in order to workaround browser bugs that break changing SVG.
		 */
		byte[] serialized = DomFormatter.fragment()
			.format(root)
			.toString()
			.getBytes(StandardCharsets.UTF_8);
		byte[] hash = Exceptions.sneak().get(() -> MessageDigest.getInstance("SHA-256")).digest(serialized);
		root.id(Base64.getUrlEncoder().encodeToString(hash).replace("_", "").replace("-", "").replace("=", ""));
		return root;
	}
}
