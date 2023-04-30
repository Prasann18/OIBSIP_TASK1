package com.app.onlineregistrationsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.onlineregistrationsystem.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	Reservation findById(String id);
	
	List<Reservation> deleteByPnr(String pnr_number);

	@Query(value = "select * from reservation where user_id=?1", nativeQuery = true)
	List<Reservation> findAllByUserId(Long id);

	@Query(value = "select * from reservation where pnr=?1", nativeQuery = true)
	List<Reservation> findAllByPnrId(String pnrId);
	
	@Query(value = "select id from reservation where pnr=?1", nativeQuery = true)
	Long findIdByPnr(String pnr);
}