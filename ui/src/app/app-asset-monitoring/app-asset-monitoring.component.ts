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

import {Component, EventEmitter, Output} from '@angular/core';
import {DashboardConfiguration} from "./model/dashboard-configuration.model";

@Component({
    selector: 'app-asset-monitoring',
    templateUrl: './app-asset-monitoring.component.html',
    styleUrls: ['./app-asset-monitoring.component.css']
})
export class AppAssetMonitoringComponent {


    selectedIndex: number = 0;
    dashboardSelected: boolean = false;
    selectedDashboard: DashboardConfiguration;
    @Output() appOpened = new EventEmitter<boolean>();

    constructor() {

    }

    ngOnInit() {
        this.appOpened.emit(true);
    }

    selectedIndexChange(index: number) {
        this.selectedIndex = index;
    }

    openDashboard(dashboardConfig: DashboardConfiguration) {
        this.selectedDashboard = dashboardConfig;
        this.dashboardSelected = true;
    }

    closeDashboard(dashboardClosed: boolean) {
        this.dashboardSelected = false;
    }

}