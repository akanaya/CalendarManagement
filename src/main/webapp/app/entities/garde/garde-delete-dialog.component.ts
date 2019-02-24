import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGarde } from 'app/shared/model/garde.model';
import { GardeService } from './garde.service';

@Component({
    selector: 'jhi-garde-delete-dialog',
    templateUrl: './garde-delete-dialog.component.html'
})
export class GardeDeleteDialogComponent {
    garde: IGarde;

    constructor(private gardeService: GardeService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.gardeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'gardeListModification',
                content: 'Deleted an garde'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-garde-delete-popup',
    template: ''
})
export class GardeDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ garde }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(GardeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.garde = garde;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
