package com.machinezoo.sourceafis.visualization;

import static com.machinezoo.sourceafis.visualization.TransparencyImages.*;
import com.machinezoo.sourceafis.transparency.*;

public class SkeletonImages {
	private final ExtractorImages extractor;
	private TransparencyArchiveSkeleton archive;
	SkeletonImages(ExtractorImages extractor, TransparencyArchiveSkeleton archive) {
		this.extractor = extractor;
		this.archive = archive;
	}
	public VisualizationImage binarized() {
		return visualizeBinarizedSkeleton(archive.binarized(), extractor.input());
	}
	public VisualizationImage thinned() {
		return visualizeThinnedSkeleton(archive.thinned(), extractor.input());
	}
	public VisualizationImage traced() {
		return visualizeTracedSkeleton(archive.traced(), extractor.input());
	}
	public VisualizationImage removedDots() {
		return visualizeRemovedDots(archive.removedDots(), extractor.input());
	}
	public VisualizationImage removedDotsDiff() {
		return visualizeRemovedDotsDiff(archive.removedDots(), archive.traced());
	}
	public VisualizationImage removedPores() {
		return visualizeRemovedPores(archive.removedPores(), extractor.input());
	}
	public VisualizationImage removedPoresDiff() {
		return visualizeRemovedPoresDiff(archive.removedPores(), archive.removedDots());
	}
	public VisualizationImage removedGaps() {
		return visualizeRemovedGaps(archive.removedGaps(), extractor.input());
	}
	public VisualizationImage removedGapsDiff() {
		return visualizeRemovedGapsDiff(archive.removedGaps(), archive.removedPores());
	}
	public VisualizationImage removedTails() {
		return visualizeRemovedTails(archive.removedTails(), extractor.input());
	}
	public VisualizationImage removedTailsDiff() {
		return visualizeRemovedTailsDiff(archive.removedTails(), archive.removedGaps());
	}
	public VisualizationImage removedFragments() {
		return visualizeRemovedFragments(archive.removedFragments(), extractor.input());
	}
	public VisualizationImage removedFragmentsDiff() {
		return visualizeRemovedFragmentsDiff(archive.removedFragments(), archive.removedTails());
	}
}
