package com.app.onlineregistrationsystem.service;

import java.util.List;

import com.app.onlineregistrationsystem.entity.Reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface ReservationService {

	@AllArgsConstructor
	@Getter
	class TrainDetail {
		Long train_no;
		String train_name;
	}

	boolean cancelReservation (String pnrNumber, boolean deleteEntry);

	void saveReservationDeatails (Reservation reservation);
	
	public List<Reservation> findAllReservationsById(Long id);
	
	public List<Reservation> findAllReservationsByPnrId(String pnrId);
}
