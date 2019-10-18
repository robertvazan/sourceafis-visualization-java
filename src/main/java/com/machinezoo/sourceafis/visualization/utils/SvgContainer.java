package com.machinezoo.sourceafis.visualization.utils;

import java.nio.charset.*;
import java.security.*;
import java.util.*;
import com.machinezoo.noexception.*;
import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;

public class SvgContainer {
	private double width;
	public SvgContainer width(double width) {
		this.width = width;
		return this;
	}
	private double height;
	public SvgContainer height(double height) {
		this.height = height;
		return this;
	}
	private double padding;
	public SvgContainer padding(double padding) {
		this.padding = padding;
		return this;
	}
	private DomContent content;
	public SvgContainer content(DomContent content) {
		this.content = content;
		return this;
	}
	public SvgContainer size(double width, double height) {
		return this
			.width(width)
			.height(height);
	}
	public SvgContainer size(IntPoint size) {
		return this.size(size.x, size.y);
	}
	public SvgContainer size(BlockMap blocks) {
		return size(blocks.pixels);
	}
	public DomElement document() {
		DomElement root = Svg.svg()
			.version("1.1")
			.viewBox((-padding) + " " + (-padding) + " " + (width + 2 * padding) + " " + (height + 2 * padding))
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
	public static DomElement of(double width, double height, DomContent content) {
		return new SvgContainer()
			.size(width, height)
			.content(content)
			.document();
	}
	public static DomElement of(IntPoint size, DomContent content) {
		return new SvgContainer()
			.size(size)
			.content(content)
			.document();
	}
}
