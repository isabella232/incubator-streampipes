/*
 * Copyright 2019 FZI Forschungszentrum Informatik
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

import {StreamQualityDefinitionsController} from "./stream-quality-definitions.controller";
declare const require: any;

export let StreamQualityDefinitionsComponent = {
    template: require('./stream-quality-definitions.tmpl.html'),
    bindings: {
        disabled : "<",
        property : "=element",
        runtimeType: "="
    },
    controller: StreamQualityDefinitionsController,
    controllerAs: 'ctrl'
};