package com.example.demo.service;

import com.example.demo.entity.Notificacion;
import com.example.demo.entity.Notificacion.*;
import com.example.demo.repository.NotificacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificacionService {
    @Autowired
    private final NotificacionRepository notificacionRepository;

    //Obtiene una lista con todas las notificaciones del repositorio
    public List<Notificacion> getAllNotificaciones(){
        return notificacionRepository.findAll();
    }

    //Obtiene una notificacion con id
    public Notificacion getNotificacion(Long id){
        return notificacionRepository.findById(id).orElse(null);
    }

    //Crea una notificacion
    public Notificacion createNotificacion(Notificacion notificacion){
        notificacion.setEstado(Estado.ENVIADO);
        notificacion.setMomentoRealEnvio(new Date());
        return notificacionRepository.save(notificacion);
    }

    //Actualizar una notificacion
    public Notificacion updateNotificacion(Notificacion notificacion){
        Notificacion notificacionDB = getNotificacion(notificacion.getId());
        if(notificacionDB==null){
            return null;
        }
        notificacionDB.setAsunto(notificacion.getAsunto());
        notificacionDB.setCuerpo(notificacion.getCuerpo());
        notificacionDB.setEstado(notificacion.getEstado());
        notificacionDB.setEmailDestino(notificacion.getEmailDestino());
        return notificacionRepository.save(notificacionDB);
    }

    //Borra una notificacion

    public void deleteNotificacion(Long id){
        Notificacion notificacionDB = getNotificacion(id);
        if(notificacionDB!=null) {
            notificacionRepository.deleteById(id);
        }
    }

    //Encuentra notificaciones filtradas por estado
    public List<Notificacion> findByEstado(Estado estado){
        return notificacionRepository.findByEstado(estado);
    }

    //Encuentra notificaciones filtradas por tipo
    public List<Notificacion> findByTipo(Tipo tipo){
        return notificacionRepository.findByTipo(tipo);
    }
}
