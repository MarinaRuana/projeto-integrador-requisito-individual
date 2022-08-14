package com.desafiofinal.praticafinal.repository;

import com.desafiofinal.praticafinal.model.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISectorRepo extends JpaRepository<Sector, Long> {
}
