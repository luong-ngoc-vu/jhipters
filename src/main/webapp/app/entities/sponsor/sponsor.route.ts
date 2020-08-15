import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISponsor, Sponsor } from 'app/shared/model/sponsor.model';
import { SponsorService } from './sponsor.service';
import { SponsorComponent } from './sponsor.component';
import { SponsorDetailComponent } from './sponsor-detail.component';
import { SponsorUpdateComponent } from './sponsor-update.component';

@Injectable({ providedIn: 'root' })
export class SponsorResolve implements Resolve<ISponsor> {
  constructor(private service: SponsorService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISponsor> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((sponsor: HttpResponse<Sponsor>) => {
          if (sponsor.body) {
            return of(sponsor.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Sponsor());
  }
}

export const sponsorRoute: Routes = [
  {
    path: '',
    component: SponsorComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'myappApp.sponsor.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SponsorDetailComponent,
    resolve: {
      sponsor: SponsorResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'myappApp.sponsor.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SponsorUpdateComponent,
    resolve: {
      sponsor: SponsorResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'myappApp.sponsor.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SponsorUpdateComponent,
    resolve: {
      sponsor: SponsorResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'myappApp.sponsor.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
