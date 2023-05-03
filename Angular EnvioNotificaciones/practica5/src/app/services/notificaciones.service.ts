import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http'
import { Observable } from 'rxjs';
import { Notificacion } from '../models/notificacion';
import { NotificacionNueva } from '../models/notificacionNueva';

@Injectable({
  providedIn: 'root'
})
export class NotificacionesService {

  private baseURL = 'http://localhost:8080';

  private headers: HttpHeaders = new HttpHeaders({'Content-Type' : 'application/json'});

  constructor(private httpClient : HttpClient) { }

  getAllNotificaciones() : Observable<Notificacion[]>{
    return this.httpClient.get<Notificacion[]>(`${this.baseURL}/notificaciones`);
  }

  getNotificacionId(idNotificacion: number) : Observable<Notificacion>{
    return this.httpClient.get<Notificacion>(`${this.baseURL}/notificaciones/${idNotificacion}`);
  }

  guardarNotificacion(notificacionNueva: NotificacionNueva) : Observable<Notificacion> {
    return this.httpClient.post<Notificacion>(`${this.baseURL}/notificaciones`, notificacionNueva, {headers: this.headers});
  }

  actualizarNotificacion(notificacion: Notificacion) : Observable<Notificacion> {
    const idNotificacion = notificacion.id;
    return this.httpClient.put<Notificacion>(`${this.baseURL}/notificaciones/${idNotificacion}`, notificacion, {headers: this.headers});
  }

  eliminarNotificacion(idNotificacion: number) : Observable<void> {
    return this.httpClient.delete<void>(`${this.baseURL}/notificaciones/${idNotificacion}`);
  }

}
