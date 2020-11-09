import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ChartsModule } from 'ng2-charts';
import { environment } from '../environments/environment';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { Routes, RouterModule } from '@angular/router';
import { ChartService } from './services/chart.service';


// const routes: Routes = [
//   {path: '**', component: HomeComponent }
// ];


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    // RouterModule.forRoot(routes),
    ChartsModule
  ],

  providers: [ChartService],
  bootstrap: [AppComponent]
})
export class AppModule {
}

