// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
/*
 * There are visualizers for all keys but no visualizers for types, because visualization is highly contextual.
 * For example, there is a large number of ways to visualize matrices. Applications rarely want to build their own visualizations.
 * Publishing reusable visualizers would add a lot of complexity with little benefit.
 * Applications that wish to visualize types can always populate transparency archive with required data and use key visualizer.
 */
package com.machinezoo.sourceafis.visualization.keys;
