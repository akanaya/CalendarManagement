import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TwentyOnePointsSharedModule } from 'app/shared';
import {
    GardeComponent,
    GardeDetailComponent,
    GardeUpdateComponent,
    GardeDeletePopupComponent,
    GardeDeleteDialogComponent,
    gardeRoute,
    gardePopupRoute
} from './';

const ENTITY_STATES = [...gardeRoute, ...gardePopupRoute];

@NgModule({
    imports: [TwentyOnePointsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [GardeComponent, GardeDetailComponent, GardeUpdateComponent, GardeDeleteDialogComponent, GardeDeletePopupComponent],
    entryComponents: [GardeComponent, GardeUpdateComponent, GardeDeleteDialogComponent, GardeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TwentyOnePointsGardeModule {}
