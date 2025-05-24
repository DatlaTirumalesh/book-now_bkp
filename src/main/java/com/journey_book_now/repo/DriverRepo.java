package com.journey_book_now.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.journey_book_now.entity.Driver;
import java.util.List;



public interface DriverRepo extends JpaRepository<Driver, Long> {
	
	@Query(value = "select * from driver d where d.status = 'Y'", nativeQuery = true)
	List<Driver> findByDriver_status();

	@Modifying
    @Transactional
	@Query( value = "UPDATE driver s set s.status = 'N' where s.driver_id = :driver_id", nativeQuery = true )
	void updateDriverStatusWithDriverID(@Param( value = "driver_id") Long driver_id);
}
