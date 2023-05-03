import { Component, OnInit } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { BsDatepickerConfig } from 'ngx-bootstrap/datepicker';
import { Estado, Medio, Notificacion, Tipo } from 'src/app/models/notificacion';
import { NotificacionNueva } from 'src/app/models/notificacionNueva';
import { NotificacionesService } from 'src/app/services/notificaciones.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-notificaciones',
  templateUrl: './notificaciones.component.html',
  styleUrls: ['./notificaciones.component.css']
})
export class NotificacionesComponent implements OnInit {
  notificacionElegida?: Notificacion;
  notificacion: Notificacion = new Notificacion();
  modalReference?: NgbModalRef;
  public notificaciones : Notificacion[] = [];
  public notificacionNueva : NotificacionNueva = new NotificacionNueva();
  public notificacionActual : Notificacion = new Notificacion();
  page = 1;
  pageSize = 10;
  collectionSize = 0;
  tipoSeleccionado: Tipo | undefined;
  estadoSeleccionado: Estado | undefined;
  tiposNotificacion = Object.values(Tipo);
  mediosDispo = Object.values(Medio);
  estados = Object.values(Estado);
  idBusqueda: string = '';
  mostrarTodos: boolean = false;




  constructor(private notificacionesService: NotificacionesService, 
              private modalService: NgbModal) {
              }

  ngOnInit(): void {
      this.getAllNotificaciones();
      this.notificacion = new Notificacion();
  }

  getAllNotificaciones() {
    console.log('Consultando notificaciones');
    this.notificacionesService.getAllNotificaciones().subscribe(response => {
      console.log(response);
      this.notificaciones = response;
  
      const notificacionesFiltradas = this.notificaciones.filter(notificacion => {
        // filtro por tipo de notificación
        if (this.tipoSeleccionado && this.tipoSeleccionado !== undefined) {
          return notificacion.tipo === this.tipoSeleccionado;
        } else {
          return true;
        }
      }).filter(notificacion => {
        // filtro por estado de notificación
        if (this.estadoSeleccionado && this.estadoSeleccionado !== undefined) {
          return notificacion.estado === this.estadoSeleccionado;
        } else {
          return true;
        }
      }).filter(notificacion => {
        // filtro por id de notificación
        if (this.idBusqueda) {
          return notificacion.id.toString().includes(this.idBusqueda);
        } else {
          return true;
        }
      });
  
      // si se ha seleccionado mostrar todas las notificaciones, no aplicar ningún filtro
      if (this.mostrarTodos) {
        this.collectionSize = this.notificaciones.length;
        this.notificaciones = this.notificaciones
          .slice((this.page - 1) * this.pageSize, (this.page - 1) * this.pageSize + this.pageSize);
      } else {
        this.collectionSize = notificacionesFiltradas.length;
        this.notificaciones = notificacionesFiltradas
          .slice((this.page - 1) * this.pageSize, (this.page - 1) * this.pageSize + this.pageSize);
      }
    });
  }


  fechaFormateada(fechaISO: string): string {
    const fecha = new Date(fechaISO);
    const opcionesDeFormato: Intl.DateTimeFormatOptions = { day: '2-digit', month: '2-digit', year: 'numeric', hour: '2-digit', minute: '2-digit', second: '2-digit', timeZone: 'Europe/Madrid', hourCycle: 'h23', };
    return new Intl.DateTimeFormat('es-ES', opcionesDeFormato).format(fecha);
  }

  open(content: any) {
		this.modalReference = this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' });
    this.modalReference.result.then((result) => {
				//this.closeResult = `Closed with: ${result}`;
			},
			(reason) => {
				//this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
			},
		);
	}

  guardarNotificacion(data: any){

    if(this.notificacion.id){
      this.notificacionesService.actualizarNotificacion(this.notificacion).subscribe(response => {
        this.modalReference?.close();

        this.getAllNotificaciones();
      })
    }else {
      this.notificacionNueva = new NotificacionNueva();
      this.notificacionNueva.asunto = data.asunto;
      this.notificacionNueva.cuerpo = data.cuerpo;
      this.notificacionNueva.emailDestino = data.emailDestino;
      this.notificacionNueva.telefonoDestino = data.telefonoDestino;
      this.notificacionNueva.tipo = data.tipo;
      this.notificacionNueva.medios = data.medios;
      this.notificacionNueva.programacionEnvio = data.programacionEnvio;


      this.notificacionesService.guardarNotificacion(this.notificacionNueva).subscribe(response => {

        this.modalReference?.close();
        this.getAllNotificaciones();
        this.notificacionNueva = new NotificacionNueva();
      })
    }
  }

  cargarNotificacion(notificacionSeleccionada: Notificacion, content : any){
    this.notificacionesService.getNotificacionId(notificacionSeleccionada.id).subscribe(
      (res: Notificacion) => {
        this.notificacion = res;
        console.log(this.notificacion);
        this.open(content);
      },
      (error: any) => {
        console.log(error);
      }
    );
  }

  mostrarVentanaEliminar(notificacion: Notificacion) {
    Swal.fire({
      title: "Confirmacion",
      text: `¿Estas seguro de eliminar la notificacion con ID ${notificacion.id}?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Confirmar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if(result.isConfirmed){
        
        this.notificacionesService.eliminarNotificacion(notificacion.id).subscribe(response => {
          this.getAllNotificaciones();
          Swal.fire(
            'Eliminado',
            `La notificación con ID ${notificacion.id} ha sido eliminada.`,
            'success'
          )
        })
      }
    })
  }

  verNotificacion(notificacion : Notificacion) {
    this.notificacionElegida = notificacion;
  }
}
