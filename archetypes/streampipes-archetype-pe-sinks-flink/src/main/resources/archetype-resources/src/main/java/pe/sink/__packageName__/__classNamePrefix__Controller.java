#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.pe.sink.${packageName};

import ${package}.config.Config;

import org.streampipes.model.DataSinkType;
import org.streampipes.model.graph.DataSinkDescription;
import org.streampipes.model.graph.DataSinkInvocation;
import org.streampipes.sdk.builder.DataSinkBuilder;
import org.streampipes.sdk.builder.StreamRequirementsBuilder;
import org.streampipes.sdk.extractor.DataSinkParameterExtractor;
import org.streampipes.sdk.helpers.EpRequirements;
import org.streampipes.sdk.helpers.Labels;
import org.streampipes.sdk.helpers.SupportedFormats;
import org.streampipes.sdk.helpers.SupportedProtocols;
import org.streampipes.wrapper.flink.FlinkDataSinkDeclarer;
import org.streampipes.wrapper.flink.FlinkDataSinkRuntime;
import org.streampipes.sdk.helpers.*;
import org.streampipes.sdk.utils.Assets;

public class ${classNamePrefix}Controller extends FlinkDataSinkDeclarer<${classNamePrefix}Parameters> {

	private static final String EXAMPLE_KEY = "example-key";

	@Override
	public DataSinkDescription declareModel() {
		return DataSinkBuilder.create("${package}.pe.sink.${packageName}")
						.category(DataSinkType.NOTIFICATION)
						.withAssets(Assets.DOCUMENTATION, Assets.ICON)
						.withLocales(Locales.EN)
						.requiredStream(StreamRequirementsBuilder
							.create()
							.requiredProperty(EpRequirements.anyProperty())
							.build())
						.supportedFormats(SupportedFormats.jsonFormat())
						.supportedProtocols(SupportedProtocols.kafka())
						.requiredTextParameter(Labels.withId(EXAMPLE_KEY))
						.build();
	}

	@Override
	public FlinkDataSinkRuntime<${classNamePrefix}Parameters> getRuntime(DataSinkInvocation graph, DataSinkParameterExtractor extractor) {

		String exampleString = extractor.singleValueParameter(EXAMPLE_KEY, String.class);

		${classNamePrefix}Parameters params = new ${classNamePrefix}Parameters(graph, exampleString);

		return new ${classNamePrefix}Program(params, Config.INSTANCE.getDebug());
	}

}
