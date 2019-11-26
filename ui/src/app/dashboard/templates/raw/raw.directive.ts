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

import { WidgetInstances } from '../../widget-instances.service'
'use strict';
declare const require: any;
rawWidget.$inject = ['WidgetInstances'];

export default function rawWidget(WidgetInstances) {
    return {
        restrict: 'A',
        replace: true,
        template: require('./raw.html'),
        scope: {
            data: '=',
            widgetId: '@'
        },
        controller: function ($scope) {
            WidgetInstances.get($scope.widgetId).then(function(data) {
            });
        },
        link: function postLink(scope) {
            scope.$watch('data', function (data) {
                if (data) {
                    scope.items = data;
                }
            });
        }
    };
};