import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';

import { IGarde } from 'app/shared/model/garde.model';
import { GardeService } from './garde.service';

@Component({
    selector: 'jhi-garde-update',
    templateUrl: './garde-update.component.html'
})
export class GardeUpdateComponent implements OnInit {
    garde: IGarde;
    isSaving: boolean;
    dateDp: any;

    constructor(private gardeService: GardeService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ garde }) => {
            this.garde = garde;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.garde.id !== undefined) {
            this.subscribeToSaveResponse(this.gardeService.update(this.garde));
        } else {
            this.subscribeToSaveResponse(this.gardeService.create(this.garde));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IGarde>>) {
        result.subscribe((res: HttpResponse<IGarde>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
