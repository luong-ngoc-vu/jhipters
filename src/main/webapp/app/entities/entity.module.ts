import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'book',
        loadChildren: () => import('./book/book.module').then(m => m.MyappBookModule),
      },
      {
        path: 'author',
        loadChildren: () => import('./author/author.module').then(m => m.MyappAuthorModule),
      },
      {
        path: 'sponsor',
        loadChildren: () => import('./sponsor/sponsor.module').then(m => m.MyappSponsorModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class MyappEntityModule {}
