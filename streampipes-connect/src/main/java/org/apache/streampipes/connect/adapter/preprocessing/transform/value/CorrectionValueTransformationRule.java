/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.apache.streampipes.connect.adapter.preprocessing.transform.value;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class CorrectionValueTransformationRule implements ValueTransformationRule {

    private static Logger logger = LoggerFactory.getLogger(CorrectionValueTransformationRule.class);

    private final List<String> eventKey;
    private final double correctionValue;
    private final String operator;

    public CorrectionValueTransformationRule(List<String> keys,
                                             double correctionValue, String operator) {
        this.correctionValue = correctionValue;
        this.operator = operator;
        this.eventKey = keys;
    }

    @Override
    public Map<String, Object> transform(Map<String, Object> event) {
        return transform(event, eventKey);
    }

    private Map<String, Object> transform(Map<String, Object> event, List<String> eventKey) {

        if (eventKey.size() == 1) {
            try {
                double oldValue = (Double) event.get(eventKey.get(0));
                double newValue;

                switch (operator) {
                    case "MULTIPLY":
                        newValue = oldValue * correctionValue;
                        break;
                    case "ADD":
                        newValue = oldValue + correctionValue;
                        break;
                    case "SUBSTRACT":
                        newValue = oldValue - correctionValue;
                        break;
                    default:
                        newValue = oldValue;
                        break;
                }

                event.put(eventKey.get(0), newValue);
            } catch (ClassCastException e) {
                logger.error(e.toString());
            }
            return event;

        } else {
            String key = eventKey.get(0);
            List<String> newKeysTmpList = eventKey.subList(1, eventKey.size());

            Map<String, Object> newSubEvent =
                    transform((Map<String, Object>) event.get(eventKey.get(0)), newKeysTmpList);

            event.remove(key);
            event.put(key, newSubEvent);

            return event;
        }

    }
}
