import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGarde } from 'app/shared/model/garde.model';

@Component({
    selector: 'jhi-garde-detail',
    templateUrl: './garde-detail.component.html'
})
export class GardeDetailComponent implements OnInit {
    garde: IGarde;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ garde }) => {
            this.garde = garde;
        });
    }

    previousState() {
        window.history.back();
    }
}
