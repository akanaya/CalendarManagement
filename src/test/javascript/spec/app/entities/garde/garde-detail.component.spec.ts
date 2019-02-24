/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TwentyOnePointsTestModule } from '../../../test.module';
import { GardeDetailComponent } from 'app/entities/garde/garde-detail.component';
import { Garde } from 'app/shared/model/garde.model';

describe('Component Tests', () => {
    describe('Garde Management Detail Component', () => {
        let comp: GardeDetailComponent;
        let fixture: ComponentFixture<GardeDetailComponent>;
        const route = ({ data: of({ garde: new Garde(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TwentyOnePointsTestModule],
                declarations: [GardeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(GardeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GardeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.garde).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
