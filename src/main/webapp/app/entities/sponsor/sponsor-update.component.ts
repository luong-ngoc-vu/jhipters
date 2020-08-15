import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISponsor, Sponsor } from 'app/shared/model/sponsor.model';
import { SponsorService } from './sponsor.service';

@Component({
  selector: 'jhi-sponsor-update',
  templateUrl: './sponsor-update.component.html',
})
export class SponsorUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    phone: [],
  });

  constructor(protected sponsorService: SponsorService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sponsor }) => {
      this.updateForm(sponsor);
    });
  }

  updateForm(sponsor: ISponsor): void {
    this.editForm.patchValue({
      id: sponsor.id,
      name: sponsor.name,
      phone: sponsor.phone,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sponsor = this.createFromForm();
    if (sponsor.id !== undefined) {
      this.subscribeToSaveResponse(this.sponsorService.update(sponsor));
    } else {
      this.subscribeToSaveResponse(this.sponsorService.create(sponsor));
    }
  }

  private createFromForm(): ISponsor {
    return {
      ...new Sponsor(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      phone: this.editForm.get(['phone'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISponsor>>): void {
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
}
