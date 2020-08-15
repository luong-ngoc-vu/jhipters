import { IBook } from 'app/shared/model/book.model';

export interface ISponsor {
  id?: number;
  name?: string;
  phone?: string;
  books?: IBook[];
}

export class Sponsor implements ISponsor {
  constructor(public id?: number, public name?: string, public phone?: string, public books?: IBook[]) {}
}
