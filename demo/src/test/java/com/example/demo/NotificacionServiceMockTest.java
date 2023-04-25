package com.example.demo;

import com.example.demo.entity.Notificacion;
import com.example.demo.repository.NotificacionRepository;
import com.example.demo.service.NotificacionService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.Optional;

@SpringBootTest
public class NotificacionServiceMockTest {

    @Mock
    private NotificacionRepository notificacionRepository;

    private NotificacionService notificacionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        notificacionService = new NotificacionService(notificacionRepository);
        Notificacion notificacion01 = Notificacion.builder()
                .id(1L)
                .emailDestino("pepelombarde@gmail.com")
                .asunto("Nota entrega 2 SII")
                .cuerpo("Suspenso")
                .momentoRealEnvio(new Date())
                .estado(Notificacion.Estado.ENVIADO).build();

        Mockito.when(notificacionRepository.findById(1L))
                .thenReturn(Optional.of(notificacion01));

        Mockito.when(notificacionRepository.save(notificacion01))
                .thenReturn(notificacion01);
    }

    @Test
    public void whenValidGetID_ThenReturnNotificacion(){
        Notificacion found = notificacionService.getNotificacion(1L);
        Assertions.assertThat(found.getEmailDestino()).isEqualTo("pepelombarde@gmail.com");
    }

    @Test
    public void whenUpdateNotificacion_ThenReturnNewNotificacion(){
        Notificacion notificacionActualizada = Notificacion.builder()
                .id(1L)
                .emailDestino("pepelombarde@gmail.com")
                .asunto("Nota entrega 2 SII")
                .cuerpo("Aprobado")
                .momentoRealEnvio(new Date())
                .estado(Notificacion.Estado.ENVIADO).build();
        Notificacion newNotificacion = notificacionService.updateNotificacion(notificacionActualizada);

        Assertions.assertThat(newNotificacion.getCuerpo()).isEqualTo("Aprobado");
    }
}
