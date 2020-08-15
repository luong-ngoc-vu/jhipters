import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISponsor } from 'app/shared/model/sponsor.model';
import { SponsorService } from './sponsor.service';
import { SponsorDeleteDialogComponent } from './sponsor-delete-dialog.component';

@Component({
  selector: 'jhi-sponsor',
  templateUrl: './sponsor.component.html',
})
export class SponsorComponent implements OnInit, OnDestroy {
  sponsors?: ISponsor[];
  eventSubscriber?: Subscription;

  constructor(protected sponsorService: SponsorService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.sponsorService.query().subscribe((res: HttpResponse<ISponsor[]>) => (this.sponsors = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSponsors();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISponsor): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSponsors(): void {
    this.eventSubscriber = this.eventManager.subscribe('sponsorListModification', () => this.loadAll());
  }

  delete(sponsor: ISponsor): void {
    const modalRef = this.modalService.open(SponsorDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.sponsor = sponsor;
  }
}
