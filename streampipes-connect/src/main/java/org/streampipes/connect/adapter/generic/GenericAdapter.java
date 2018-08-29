/*
 * Copyright 2018 FZI Forschungszentrum Informatik
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.streampipes.connect.adapter.generic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.streampipes.connect.adapter.Adapter;
import org.streampipes.connect.adapter.AdapterRegistry;
import org.streampipes.connect.adapter.generic.format.Format;
import org.streampipes.connect.adapter.generic.format.Parser;
import org.streampipes.connect.adapter.generic.pipeline.AdapterPipeline;
import org.streampipes.connect.adapter.generic.pipeline.AdapterPipelineElement;
import org.streampipes.connect.adapter.generic.pipeline.elements.SendToKafkaAdapterSink;
import org.streampipes.connect.adapter.generic.pipeline.elements.TransformSchemaAdapterPipelineElement;
import org.streampipes.connect.adapter.generic.protocol.Protocol;
import org.streampipes.connect.exception.AdapterException;
import org.streampipes.model.connect.adapter.AdapterDescription;
import org.streampipes.model.connect.adapter.GenericAdapterDescription;
import org.streampipes.model.connect.guess.GuessSchema;

import java.util.ArrayList;
import java.util.List;

public abstract class GenericAdapter extends Adapter {

    public abstract GenericAdapterDescription getAdapterDescription();
    public abstract void setProtocol(Protocol protocol);

    private static final Logger logger = LoggerFactory.getLogger(Adapter.class);

    public GenericAdapter(boolean debug) {
        super(debug);
    }

    public GenericAdapter() {
        super();
    }


    @Override
    public void startAdapter() throws AdapterException {

        GenericAdapterDescription adapterDescription = getAdapterDescription();


        Parser parser = getParser(adapterDescription);
        Format format = getFormat(adapterDescription);

        Protocol protocol = getProtocol(adapterDescription, format, parser);
        setProtocol(protocol);

        logger.debug("Start adatper with format: " + format.getId() + " and " + protocol.getId());


        List<AdapterPipelineElement> pipelineElements = new ArrayList<>();
        pipelineElements.add(new TransformSchemaAdapterPipelineElement(adapterDescription.getRules()));
        pipelineElements.add(new SendToKafkaAdapterSink((AdapterDescription) adapterDescription));

        AdapterPipeline adapterPipeline = new AdapterPipeline(pipelineElements);

        protocol.run(adapterPipeline);
    }


    @Override
    public GuessSchema getSchema(AdapterDescription adapterDescription) {
        Parser parser = getParser((GenericAdapterDescription) adapterDescription);
        Format format = getFormat((GenericAdapterDescription) adapterDescription);

        Protocol protocol = getProtocol((GenericAdapterDescription) adapterDescription, format, parser);

        logger.debug("Extract schema with format: " + format.getId() + " and " + protocol.getId());

        return protocol.getGuessSchema();
    }

    private Parser getParser(GenericAdapterDescription adapterDescription) {
         return AdapterRegistry.getAllParsers().get(adapterDescription.getFormatDescription().getUri()).getInstance(adapterDescription.getFormatDescription());
    }

    private Format getFormat(GenericAdapterDescription adapterDescription) {
        return AdapterRegistry.getAllFormats().get(adapterDescription.getFormatDescription().getUri()).getInstance(adapterDescription.getFormatDescription());
    }

    private Protocol getProtocol(GenericAdapterDescription adapterDescription, Format format, Parser parser) {
        return AdapterRegistry.getAllProtocols().get(adapterDescription.getProtocolDescription().getUri()).getInstance(adapterDescription.getProtocolDescription(), parser, format);
    }
}