// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import org.junit.jupiter.api.*;
import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.transparency.keys.*;

public class TransparencyVisualizerTest {
	@Test
	public void keyInDependencies() {
		for (var visualizer : TransparencyVisualizer.all())
			assertThat(visualizer.dependencies(), hasItem(visualizer.key()));
	}
	private static final List<Class<?>> NONVISUAL_KEYS = List.of(
		ContextKey.class,
		VersionKey.class,
		BestMatchKey.class,
		ScoreKey.class,
		BestScoreKey.class,
		/*
		 * Temporary.
		 */
		PairingKey.class,
		BestPairingKey.class);
	@Test
	public void archiveCoverage() {
		for (var key : TransparencyKey.all())
			if (!NONVISUAL_KEYS.stream().anyMatch(c -> c.isInstance(key)))
				assertTrue(TransparencyVisualizer.all().stream().anyMatch(v -> v.key().equals(key)));
	}
	@Test
	public void hasExtension() {
		for (var visualizer : TransparencyVisualizer.all())
			assertThat(visualizer.extension(), is(not(emptyOrNullString())));
	}
	@Test
	public void materializable() {
		for (var visualizer : TransparencyVisualizer.all())
			for (var operation : visualizer.key().operations())
				visualizer.visualize(TestArchives.dependencies(visualizer, operation)).materialize();
	}
}
