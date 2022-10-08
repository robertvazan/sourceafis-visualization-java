// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
import com.machinezoo.stagean.*;

@NoDocs("Add javadoc and website.")
@DraftTests("Reach 100% coverage.")
@CodeIssue("Use proper SVG library instead of pushmode.")
module com.machinezoo.sourceafis.visualization {
	exports com.machinezoo.sourceafis.visualization;
	exports com.machinezoo.sourceafis.visualization.formats;
	exports com.machinezoo.sourceafis.visualization.keys;
	requires java.desktop;
	requires com.machinezoo.stagean;
	requires com.machinezoo.noexception;
	/*
	 * Transitive, because we process objects deserialized by the transparency lib.
	 */
	requires transitive com.machinezoo.sourceafis.transparency;
	requires one.util.streamex;
	requires it.unimi.dsi.fastutil;
	requires org.apache.commons.lang3;
	/*
	 * Transitive for now, but we shouldn't expose any APIs that are tied to pushmode.
	 * This dependency actually shouldn't be here at all. We should use proper SVG builder.
	 */
	requires transitive com.machinezoo.pushmode;
	requires com.google.common;
	requires org.apache.commons.io;
}
