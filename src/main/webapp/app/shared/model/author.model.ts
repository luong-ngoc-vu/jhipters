import { Moment } from 'moment';
import { IBook } from 'app/shared/model/book.model';

export interface IAuthor {
  id?: number;
  firstName?: string;
  lastName?: string;
  jobTitle?: string;
  dateOfBirth?: Moment;
  books?: IBook[];
}

export class Author implements IAuthor {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public jobTitle?: string,
    public dateOfBirth?: Moment,
    public books?: IBook[]
  ) {}
}
