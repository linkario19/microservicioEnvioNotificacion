package com.example.demo.controller;

import com.example.demo.entity.Notificacion;
import com.example.demo.entity.Notificacion.*;
import com.example.demo.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/notificaciones")
public class NotificacionController {
    @Autowired
    private NotificacionService notificacionService;

    @GetMapping("")
    public ResponseEntity<List<Notificacion>> getNotificaciones
            (@RequestParam(name = "tipo", required = false) Tipo tipo, @RequestParam(name = "estado", required = false) Estado estado){
        List<Notificacion> notificaciones = new ArrayList<>();
        if(tipo==null && estado==null) {
            notificaciones = notificacionService.getAllNotificaciones();
        }else {
            if(tipo!=null && estado==null){
                notificaciones = notificacionService.findByTipo(tipo);
            }else if(estado!=null && tipo==null){
                notificaciones = notificacionService.findByEstado(estado);
            }else if(estado!=null && tipo!=null) {
                List<Notificacion> notificacionesTipo = notificacionService.findByTipo(tipo);
                List<Notificacion> notificacionesMedio = notificacionService.findByEstado(estado);
                List<Notificacion> notificacionesComunes = new ArrayList<>(notificacionesMedio);
                notificacionesComunes.retainAll(notificacionesTipo);
                notificaciones = notificacionesComunes;
            }
        }
        if (notificaciones.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(notificaciones);
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<Notificacion> getNotificacionById(@PathVariable("id") Long id){
        Notificacion notificacion = notificacionService.getNotificacion(id);
        if(notificacion==null){
            return ResponseEntity.notFound().build();
        }else {
            return ResponseEntity.ok(notificacion);
        }
    }

    @PostMapping
    public ResponseEntity<Notificacion> postNotificacion(@RequestBody Notificacion notificacion){
        Notificacion notificacionCreate = notificacionService.createNotificacion(notificacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(notificacionCreate);
    }
}
