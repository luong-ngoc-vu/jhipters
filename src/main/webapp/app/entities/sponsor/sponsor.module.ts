import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyappSharedModule } from 'app/shared/shared.module';
import { SponsorComponent } from './sponsor.component';
import { SponsorDetailComponent } from './sponsor-detail.component';
import { SponsorUpdateComponent } from './sponsor-update.component';
import { SponsorDeleteDialogComponent } from './sponsor-delete-dialog.component';
import { sponsorRoute } from './sponsor.route';

@NgModule({
  imports: [MyappSharedModule, RouterModule.forChild(sponsorRoute)],
  declarations: [SponsorComponent, SponsorDetailComponent, SponsorUpdateComponent, SponsorDeleteDialogComponent],
  entryComponents: [SponsorDeleteDialogComponent],
})
export class MyappSponsorModule {}
