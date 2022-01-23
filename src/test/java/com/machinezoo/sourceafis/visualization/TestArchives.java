// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization;

import java.util.concurrent.*;
import com.machinezoo.sourceafis.*;
import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.transparency.keys.*;

public class TestArchives {
	public static TransparencyArchive extractor() {
		var buffer = new TransparencyBuffer()
			.write(new InputImageKey(), TestResources.probe());
		try (var scope = buffer.open()) {
			var template = new FingerprintTemplate(new FingerprintImage(TestResources.probe()));
			buffer.write(new OutputTemplateKey(), template.toByteArray());
		}
		return buffer.toArchive();
	}
	public static TransparencyArchive probe() {
		var template = new FingerprintTemplate(new FingerprintImage(TestResources.probe()));
		return new TransparencyBuffer()
			.write(new InputImageKey(), TestResources.probe())
			.write(new InputTemplateKey(), template.toByteArray())
			.capture(() -> new FingerprintMatcher(template))
			.toArchive();
	}
	public static TransparencyArchive comparison() {
		var probe = new FingerprintTemplate(new FingerprintImage(TestResources.probe()));
		var candidate = new FingerprintTemplate(new FingerprintImage(TestResources.candidate()));
		var matcher = new FingerprintMatcher(probe);
		var buffer = new TransparencyBuffer()
			.write(new ProbeImageKey(), TestResources.probe())
			.write(new ProbeTemplateKey(), probe.toByteArray())
			.write(new CandidateImageKey(), TestResources.candidate())
			.write(new CandidateTemplateKey(), candidate.toByteArray());
		try (var scope = buffer.open()) {
			var score = matcher.match(candidate);
			buffer.serialize(new OutputScoreKey(), score);
		}
		return buffer.toArchive();
	}
	public static TransparencyArchive deserialization() {
		var template = new FingerprintTemplate(new FingerprintImage(TestResources.probe()));
		var serialized = template.toByteArray();
		return new TransparencyBuffer()
			.write(new InputImageKey(), TestResources.probe())
			.write(new InputTemplateKey(), serialized)
			.capture(() -> new FingerprintTemplate(serialized))
			.toArchive();
	}
	private static final ConcurrentMap<TransparentOperation, TransparencyArchive> archives = new ConcurrentHashMap<>();
	public static TransparencyArchive full(TransparentOperation operation) {
		return archives.computeIfAbsent(operation, op -> switch (op) {
			case EXTRACT_FEATURES -> extractor();
			case PREPARE_PROBE -> probe();
			case COMPARE_CANDIDATE -> comparison();
			case DESERIALIZE_TEMPLATE -> deserialization();
		});
	}
	public static TransparencyArchive dependencies(TransparencyVisualizer visualizer, TransparentOperation operation) {
		var dependencies = visualizer.dependencies(operation);
		return new TransparencyBuffer()
			.accept(TransparencyFilter.only(k -> dependencies.contains(k) || k instanceof ContextKey))
			.append(full(operation))
			.toArchive();
	}
}
