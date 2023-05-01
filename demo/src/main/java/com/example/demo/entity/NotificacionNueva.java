package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.demo.entity.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificacionNueva implements Serializable {

    private static final long serialVersionUID = 1L;

    private String asunto;
    private String cuerpo;
    private String emailDestino;
    private String telefonoDestino;
    private Date programacionEnvio;
    private Notificacion.Medio[] medios;
    private Notificacion.Tipo tipoNotificacion;

        public Notificacion toNotificacion() {
            Notificacion notificacion = new Notificacion();
            notificacion.setAsunto(asunto);
            notificacion.setCuerpo(cuerpo);
            notificacion.setEmailDestino(emailDestino);
            notificacion.setTelefonoDestino((telefonoDestino));
            notificacion.setTipo(tipoNotificacion);
            notificacion.setMedios(medios);
            notificacion.setProgramacionEnvio(programacionEnvio);
            return notificacion;
        }
}
