package com.journey_book_now.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.journey_book_now.entity.Bus;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;



public interface BusRepo extends JpaRepository<Bus, Long> {
	
	@Query(value = "select * from bus s where s.bus_num = :bus_num",nativeQuery = true)
	Bus findByBus_numWithBusIDBusNumBusStatus(@Param("bus_num") String bus_num);
	
	@Query(value = "select * from bus s where s.status = 'Y'",nativeQuery = true)
	List<Bus> findBybus_status();

	@Query(value = "select b.* from schedule s INNER JOIN bus b where b.bus_id = :bus_id", nativeQuery = true)
	Bus findByBusId(@Param("bus_id") Long bus_id);
	
	@Query( value = "select * from bus s where s.bus_num = :bus_num", nativeQuery = true)
	Bus findByBus_num(@Param("bus_num") String bus_num);
	
	@Modifying
    @Transactional
	@Query( value = "UPDATE bus s set s.status = 'N' where s.bus_id = :bus_id", nativeQuery = true )
	void updateBusStatusWithBusID(@Param( value = "bus_id") Long bus_id);
	

}
