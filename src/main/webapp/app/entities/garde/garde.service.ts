import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IGarde } from 'app/shared/model/garde.model';

type EntityResponseType = HttpResponse<IGarde>;
type EntityArrayResponseType = HttpResponse<IGarde[]>;

@Injectable({ providedIn: 'root' })
export class GardeService {
    private resourceUrl = SERVER_API_URL + 'api/gardes';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/gardes';

    constructor(private http: HttpClient) {}

    create(garde: IGarde): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(garde);
        return this.http
            .post<IGarde>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(garde: IGarde): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(garde);
        return this.http
            .put<IGarde>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IGarde>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IGarde[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IGarde[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    private convertDateFromClient(garde: IGarde): IGarde {
        const copy: IGarde = Object.assign({}, garde, {
            date: garde.date != null && garde.date.isValid() ? garde.date.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.date = res.body.date != null ? moment(res.body.date) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((garde: IGarde) => {
            garde.date = garde.date != null ? moment(garde.date) : null;
        });
        return res;
    }
}
