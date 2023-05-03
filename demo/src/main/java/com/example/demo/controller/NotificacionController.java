package com.example.demo.controller;

import com.example.demo.entity.Notificacion;
import com.example.demo.entity.Notificacion.*;
import com.example.demo.entity.NotificacionNueva;
import com.example.demo.service.NotificacionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
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
    public ResponseEntity<Notificacion> postNotificacion(@RequestBody NotificacionNueva notificacionNueva){
        Notificacion notificacionCreate = notificacionService.createNotificacion(notificacionNueva);
        return ResponseEntity.status(HttpStatus.CREATED).body(notificacionCreate);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Notificacion> putNotificacion(@PathVariable("id") Long id, @RequestBody Notificacion notificacion){
        notificacion.setId(id);
        Notificacion notificacionDB = notificacionService.updateNotificacion(notificacion);
        if(notificacionDB == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(notificacionDB);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Notificacion> deleteNotificacion(@PathVariable("id") Long id){
        Notificacion notificacionDB = notificacionService.getNotificacion(id);
        if (notificacionDB == null) {
            return ResponseEntity.notFound().build();
        }else {
            notificacionService.deleteNotificacion(id);
            return ResponseEntity.ok(notificacionDB);
        }
    }

    @PostMapping("/pendientes/abortar")
    public ResponseEntity<List<Notificacion>> abortarPendientes(@RequestParam(name="tipo", required = false) Tipo tipo){
        List<Notificacion> listaPendientes = notificacionService.findByEstado(Estado.PENDIENTE);
        if(listaPendientes.isEmpty()){
            return ResponseEntity.notFound().build();
        }else {
            if (tipo != null) {
                List<Notificacion> notificacionesTipo = notificacionService.findByTipo(tipo);
                listaPendientes.retainAll(notificacionesTipo);
                if(listaPendientes.isEmpty()){
                    return ResponseEntity.notFound().build();
                }
            }
            for (Notificacion n : listaPendientes){
                notificacionService.abortarNotificacion(n);
            }
            return ResponseEntity.ok(listaPendientes);
        }
    }
}
