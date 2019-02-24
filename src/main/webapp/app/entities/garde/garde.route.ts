import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Garde } from 'app/shared/model/garde.model';
import { GardeService } from './garde.service';
import { GardeComponent } from './garde.component';
import { GardeDetailComponent } from './garde-detail.component';
import { GardeUpdateComponent } from './garde-update.component';
import { GardeDeletePopupComponent } from './garde-delete-dialog.component';
import { IGarde } from 'app/shared/model/garde.model';

@Injectable({ providedIn: 'root' })
export class GardeResolve implements Resolve<IGarde> {
    constructor(private service: GardeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((garde: HttpResponse<Garde>) => garde.body));
        }
        return of(new Garde());
    }
}

export const gardeRoute: Routes = [
    {
        path: 'garde',
        component: GardeComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'twentyOnePointsApp.garde.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'garde/:id/view',
        component: GardeDetailComponent,
        resolve: {
            garde: GardeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'twentyOnePointsApp.garde.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'garde/new',
        component: GardeUpdateComponent,
        resolve: {
            garde: GardeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'twentyOnePointsApp.garde.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'garde/:id/edit',
        component: GardeUpdateComponent,
        resolve: {
            garde: GardeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'twentyOnePointsApp.garde.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const gardePopupRoute: Routes = [
    {
        path: 'garde/:id/delete',
        component: GardeDeletePopupComponent,
        resolve: {
            garde: GardeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'twentyOnePointsApp.garde.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
