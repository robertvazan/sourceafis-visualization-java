// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization;

public class TransparencyContext {
	byte[] input;
	public TransparencyContext input(byte[] image) {
		input = image;
		return this;
	}
	byte[] output;
	public TransparencyContext output(byte[] template) {
		output = template;
		return this;
	}
	byte[] probe;
	public TransparencyContext probe(byte[] template) {
		probe = template;
		return this;
	}
	byte[] candidate;
	public TransparencyContext candidate(byte[] template) {
		candidate = template;
		return this;
	}
	byte[] probeImage;
	public TransparencyContext probeImage(byte[] image) {
		probeImage = image;
		return this;
	}
	byte[] candidateImage;
	public TransparencyContext candidateImage(byte[] image) {
		candidateImage = image;
		return this;
	}
}
