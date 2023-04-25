package com.example.demo.repository;

import com.example.demo.entity.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Notificacion.*;

import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    public List<Notificacion> findByTipo (Tipo tipo);
    public List<Notificacion> findByEstado(Estado estado);
}
