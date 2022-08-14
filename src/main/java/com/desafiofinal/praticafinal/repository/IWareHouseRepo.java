package com.desafiofinal.praticafinal.repository;

import com.desafiofinal.praticafinal.model.WareHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IWareHouseRepo extends JpaRepository<WareHouse, Long> {
}
