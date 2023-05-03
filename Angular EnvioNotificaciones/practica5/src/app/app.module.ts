import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NotificacionesComponent } from './components/notificaciones/notificaciones.component';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { BsDatepickerModule, BsDatepickerConfig } from 'ngx-bootstrap/datepicker';
@NgModule({
  declarations: [
    AppComponent,
    NotificacionesComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    FormsModule,
    HttpClientModule,
    BsDatepickerModule.forRoot()
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
