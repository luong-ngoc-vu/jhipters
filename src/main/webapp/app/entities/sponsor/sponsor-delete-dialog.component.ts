import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISponsor } from 'app/shared/model/sponsor.model';
import { SponsorService } from './sponsor.service';

@Component({
  templateUrl: './sponsor-delete-dialog.component.html',
})
export class SponsorDeleteDialogComponent {
  sponsor?: ISponsor;

  constructor(protected sponsorService: SponsorService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sponsorService.delete(id).subscribe(() => {
      this.eventManager.broadcast('sponsorListModification');
      this.activeModal.close();
    });
  }
}
