import { Moment } from 'moment';

export interface IGarde {
    id?: number;
    date?: Moment;
    amount?: number;
    seller_residant?: string;
    buyer_resident?: string;
}

export class Garde implements IGarde {
    constructor(
        public id?: number,
        public date?: Moment,
        public amount?: number,
        public seller_residant?: string,
        public buyer_resident?: string
    ) {}
}
