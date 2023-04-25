package com.example.demo;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.entity.Notificacion;
import com.example.demo.repository.NotificacionRepository;

@DataJpaTest
public class NotificacionRepositoryMockTest {

	@Autowired
	private NotificacionRepository notificacionRepository;
	
	@Test
	public void whenFindByAll_thenReturnListNotificacion() {
		Notificacion notficacion01 = Notificacion.builder()
				.asunto("pepe")
				.cuerpo("PEPE")
				.emailDestino("pepe@gmail.com")
				.estado(Notificacion.Estado.ENVIADO).build();
		notificacionRepository.save(notficacion01);

		Notificacion notficacion02 = Notificacion.builder()
				.asunto("albert")
				.cuerpo("ALBERT")
				.emailDestino("albert@gmail.com")
				.estado(Notificacion.Estado.ENVIADO).build();
		notificacionRepository.save(notficacion02);
		
		List<Notificacion> encontradas = notificacionRepository.findAll();
		Assertions.assertThat(encontradas.size()).isEqualTo(2);
	}
}
