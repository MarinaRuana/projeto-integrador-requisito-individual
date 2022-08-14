package com.desafiofinal.praticafinal.repository;

import com.desafiofinal.praticafinal.dto.queryDto.BatchStockSectorQuantityDTO;
import com.desafiofinal.praticafinal.dto.queryDto.DataBaseQuery;
import com.desafiofinal.praticafinal.dto.queryDto.DataBaseStockQuery;
import com.desafiofinal.praticafinal.dto.queryDto.DataBaseTotalQuantityQuery;
import com.desafiofinal.praticafinal.model.BatchStock;
import com.desafiofinal.praticafinal.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBatchStockRepo extends JpaRepository<BatchStock, Long> {

    @Query(value = "SELECT batch_id, current_quantity,s.sector_id, due_date, id_product, s.category FROM batch_stock \n" +
            "join in_bound_order on id_inboundorder = in_bound_order.order_id\n" +
            "join sector as s on in_bound_order.id_sector = s.sector_id\n" +
            "where id_product = ?1", nativeQuery = true)
    List<DataBaseQuery> getListBatchSector(Long id_product);

    @Query(value = "SELECT batch_id, current_quantity,s.sector_id, due_date, id_product, s.category FROM batch_stock \n" +
            "join in_bound_order on id_inboundorder = in_bound_order.order_id\n" +
            "join sector as s on in_bound_order.id_sector = s.sector_id\n" +
            "where id_product = ?1\n" +
            "order by batch_stock.batch_id;", nativeQuery = true)
    List<DataBaseQuery> getListOrderedById(long id);

    @Query(value = "SELECT batch_id, current_quantity,s.sector_id, due_date, id_product, s.category FROM batch_stock \n" +
            "join in_bound_order on id_inboundorder = in_bound_order.order_id\n" +
            "join sector as s on in_bound_order.id_sector = s.sector_id\n" +
            "where id_product = ?1\n" +
            "order by batch_stock.current_quantity;", nativeQuery = true)
    List<DataBaseQuery> getListOrderedByQuantity(long id);

    @Query(value = "SELECT batch_id, current_quantity,s.sector_id, due_date, id_product, s.category FROM batch_stock \n" +
            "join in_bound_order on id_inboundorder = in_bound_order.order_id\n" +
            "join sector as s on in_bound_order.id_sector = s.sector_id\n" +
            "where id_product = ?1\n" +
            "order by batch_stock.due_date;", nativeQuery = true)
    List<DataBaseQuery> getListOrderedByDueDate(long id);

    @Query(value = "\n" +
            "SELECT sum(batch.current_quantity) as total_quantity, s.sector_id, batch.id_product, s.category FROM batch_stock as batch\n" +
            "join in_bound_order on batch.id_inboundorder = in_bound_order.order_id\n" +
            "join sector as s on in_bound_order.id_sector = s.sector_id\n" +
            "where id_product = ?1\n" +
            "group by s.sector_id;", nativeQuery = true)
    List<DataBaseTotalQuantityQuery> getListQuantity(long id);

    @Query(value = "\n" +
            "SELECT batch.batch_id, batch.id_product, p.product_type, batch.due_date, batch.current_quantity FROM batch_stock as batch\n" +
            "join in_bound_order on batch.id_inboundorder = in_bound_order.order_id\n" +
            "join product as p on p.id = batch.id_product\n" +
            "join sector as s on in_bound_order.id_sector = s.sector_id\n" +
            "where s.sector_id =?1\n" +
            "order by batch.due_date;", nativeQuery = true)
    List<DataBaseStockQuery> getListDueDate(Long sectorId);

    @Query(value = "\n" +
            "SELECT batch.batch_id, batch.id_product, p.product_type, batch.due_date, batch.current_quantity FROM batch_stock as batch\n" +
            "join in_bound_order on batch.id_inboundorder = in_bound_order.order_id\n" +
            "join product as p on p.id = batch.id_product\n" +
            "join sector as s on in_bound_order.id_sector = s.sector_id\n" +
            "where s.category = ?1\n" +
            "order by batch.due_date;", nativeQuery = true)
    List<DataBaseStockQuery> getListCategory(String category);
}
