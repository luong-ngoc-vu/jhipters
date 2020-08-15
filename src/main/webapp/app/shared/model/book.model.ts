import { Moment } from 'moment';
import { ISponsor } from 'app/shared/model/sponsor.model';

export interface IBook {
  id?: number;
  title?: string;
  description?: string;
  content?: string;
  releaseDate?: Moment;
  price?: number;
  sponsors?: ISponsor[];
  authorId?: number;
}

export class Book implements IBook {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string,
    public content?: string,
    public releaseDate?: Moment,
    public price?: number,
    public sponsors?: ISponsor[],
    public authorId?: number
  ) {}
}
