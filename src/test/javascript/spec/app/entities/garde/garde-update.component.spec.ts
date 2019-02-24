/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { TwentyOnePointsTestModule } from '../../../test.module';
import { GardeUpdateComponent } from 'app/entities/garde/garde-update.component';
import { GardeService } from 'app/entities/garde/garde.service';
import { Garde } from 'app/shared/model/garde.model';

describe('Component Tests', () => {
    describe('Garde Management Update Component', () => {
        let comp: GardeUpdateComponent;
        let fixture: ComponentFixture<GardeUpdateComponent>;
        let service: GardeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TwentyOnePointsTestModule],
                declarations: [GardeUpdateComponent]
            })
                .overrideTemplate(GardeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(GardeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GardeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Garde(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.garde = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Garde();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.garde = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
