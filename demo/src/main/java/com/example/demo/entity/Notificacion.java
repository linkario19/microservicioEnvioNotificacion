package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "tbl_notificaciones")
@Data
@AllArgsConstructor @NoArgsConstructor @Builder
public class Notificacion {

	public enum Estado {
		PENDIENTE, ENVIADO, ABORTADA, ERROR
	}

	public enum Medio {
		SMS, EMAIL
	}

	public enum Tipo {
		ANUNCIO_NOTA_ESTUDIANTE, ANUNCIO_AULA_ESTUDIANTE, ANUNCIO_AULA_VIGILANTE, PASSWORD_RESET
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String asunto;

	private String cuerpo;

	private String emailDestino;

	@Temporal(TemporalType.TIMESTAMP)
	private Date programacionEnvio;

	@Enumerated(EnumType.STRING)
	private Medio[] medios;

	@Enumerated(EnumType.STRING)
	private Tipo tipo;

	@Enumerated(EnumType.STRING)
	private Estado estado;

	private String mensajeError;

	@Temporal(TemporalType.TIMESTAMP)
	private Date momentoRealEnvio;

}
