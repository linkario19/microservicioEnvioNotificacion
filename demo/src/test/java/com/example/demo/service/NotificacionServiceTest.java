package com.example.demo.service;

import com.example.demo.entity.Notificacion;
import com.example.demo.entity.NotificacionNueva;
import com.example.demo.repository.NotificacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class NotificacionServiceTest {

    @Mock
    private NotificacionRepository notificacionRepository;

    @InjectMocks
    private NotificacionService notificacionService;
    private Notificacion notificacion;
    private NotificacionNueva notificacionNueva;
    private Notificacion notificacion2;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

         notificacion = new Notificacion();
        notificacion.setId(1L);
        notificacion.setAsunto("Nota");
        notificacion.setCuerpo("Aprobado");
        notificacion.setEmailDestino("pepelombarde@gmail.com");
        notificacion.setProgramacionEnvio(new Date());
        notificacion.setMedios(null);
        notificacion.setTipo(Notificacion.Tipo.ANUNCIO_NOTA_ESTUDIANTE);
        notificacion.setEstado(Notificacion.Estado.PENDIENTE);

        notificacion2 = new Notificacion();
        notificacion2.setId(2L);
        notificacion2.setAsunto("Nota");
        notificacion2.setCuerpo("Aprobado");
        notificacion2.setEmailDestino("pepelombarde@gmail.com");
        notificacion2.setProgramacionEnvio(new Date());
        notificacion2.setMedios(null);
        notificacion2.setTipo(Notificacion.Tipo.ANUNCIO_NOTA_ESTUDIANTE);
        notificacion2.setEstado(Notificacion.Estado.PENDIENTE);

        notificacionNueva = new NotificacionNueva();
        notificacionNueva.setAsunto("Nota");
        notificacionNueva.setCuerpo("Aprobado");
        notificacionNueva.setEmailDestino("pepelombarde@gmail.com");
        notificacionNueva.setProgramacionEnvio(new Date());
        notificacionNueva.setMedios(null);
    }

    @Test
    void getAllNotificaciones() {
        when(notificacionRepository.findAll()).thenReturn(Arrays.asList(notificacion));
        assertNotNull(notificacionService.getAllNotificaciones());
    }

    @Test
    void getNotificacion() {
        when(notificacionRepository.findById(1L)).thenReturn(Optional.ofNullable(notificacion));
        assertEquals(notificacionService.getNotificacion(1L), notificacion);

    }

    @Test
    void createNotificacion() {
        when(notificacionRepository.save(any(Notificacion.class))).thenReturn(notificacion);
        Notificacion result = notificacionService.createNotificacion(notificacionNueva);

        assertEquals(notificacion.getAsunto(), result.getAsunto());

        when(notificacionRepository.findAll()).thenReturn(new ArrayList<Notificacion>());

        Notificacion result2 = notificacionService.createNotificacion(notificacionNueva);

        assertNotNull(result);
        assertEquals(1L, result2.getId());
        assertEquals(notificacionNueva.getAsunto(), result.getAsunto());
        assertEquals(Notificacion.Estado.PENDIENTE, result.getEstado());
    }

    @Test
    void updateNotificacion() {
        Notificacion notificacion = new Notificacion();
        notificacion.setId(1L);
        notificacion.setAsunto("Notificación actualizada");
        notificacion.setEstado(Notificacion.Estado.ENVIADO);

        when(notificacionRepository.findById(1L)).thenReturn(Optional.of(notificacion));

        when(notificacionRepository.save(any(Notificacion.class))).thenAnswer(invocation -> {
            Notificacion savedNotificacion = invocation.getArgument(0);
            return savedNotificacion;
        });

        Notificacion result = notificacionService.updateNotificacion(notificacion);

        assertEquals(notificacion.getAsunto(), result.getAsunto());
        assertEquals(notificacion.getEstado(), result.getEstado());

        when(notificacionRepository.findById(1L)).thenReturn(Optional.empty());

        Notificacion result2 = notificacionService.updateNotificacion(notificacion);

        assertNull(result2);
    }

    @Test
    void deleteNotificacion() {
        Notificacion result = notificacionService.deleteNotificacion(notificacion2.getId());
        assertNull(result);
    }

    @Test
    void abortarNotificacion() {
    }

    @Test
    void findByEstado() {
    }

    @Test
    void findByTipo() {
    }
}