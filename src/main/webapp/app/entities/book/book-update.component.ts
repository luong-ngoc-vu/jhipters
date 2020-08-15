import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBook, Book } from 'app/shared/model/book.model';
import { BookService } from './book.service';
import { ISponsor } from 'app/shared/model/sponsor.model';
import { SponsorService } from 'app/entities/sponsor/sponsor.service';
import { IAuthor } from 'app/shared/model/author.model';
import { AuthorService } from 'app/entities/author/author.service';

type SelectableEntity = ISponsor | IAuthor;

@Component({
  selector: 'jhi-book-update',
  templateUrl: './book-update.component.html',
})
export class BookUpdateComponent implements OnInit {
  isSaving = false;
  sponsors: ISponsor[] = [];
  authors: IAuthor[] = [];
  releaseDateDp: any;

  editForm = this.fb.group({
    id: [],
    title: [],
    description: [],
    content: [],
    releaseDate: [],
    price: [],
    sponsors: [],
    authorId: [],
  });

  constructor(
    protected bookService: BookService,
    protected sponsorService: SponsorService,
    protected authorService: AuthorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ book }) => {
      this.updateForm(book);

      this.sponsorService.query().subscribe((res: HttpResponse<ISponsor[]>) => (this.sponsors = res.body || []));

      this.authorService.query().subscribe((res: HttpResponse<IAuthor[]>) => (this.authors = res.body || []));
    });
  }

  updateForm(book: IBook): void {
    this.editForm.patchValue({
      id: book.id,
      title: book.title,
      description: book.description,
      content: book.content,
      releaseDate: book.releaseDate,
      price: book.price,
      sponsors: book.sponsors,
      authorId: book.authorId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const book = this.createFromForm();
    if (book.id !== undefined) {
      this.subscribeToSaveResponse(this.bookService.update(book));
    } else {
      this.subscribeToSaveResponse(this.bookService.create(book));
    }
  }

  private createFromForm(): IBook {
    return {
      ...new Book(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      description: this.editForm.get(['description'])!.value,
      content: this.editForm.get(['content'])!.value,
      releaseDate: this.editForm.get(['releaseDate'])!.value,
      price: this.editForm.get(['price'])!.value,
      sponsors: this.editForm.get(['sponsors'])!.value,
      authorId: this.editForm.get(['authorId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBook>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  getSelected(selectedVals: ISponsor[], option: ISponsor): ISponsor {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
