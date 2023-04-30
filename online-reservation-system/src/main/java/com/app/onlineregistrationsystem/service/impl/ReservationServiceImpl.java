package com.app.onlineregistrationsystem.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.app.onlineregistrationsystem.entity.Reservation;
import com.app.onlineregistrationsystem.repository.ReservationRepository;
import com.app.onlineregistrationsystem.service.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService {

	// Declaring the static map
    static Map<String, TrainDetail> trainDetailsMap;
  
    // Instantiating the static map
    static
    {
    	trainDetailsMap = new HashMap<>();
    	trainDetailsMap.put("DEWAS - UJJAIN", new TrainDetail(12465L, "SF Express"));
    	trainDetailsMap.put("DEWAS - INDORE", new TrainDetail(19304L, "BPL-IDR Express"));
    	trainDetailsMap.put("DEWAS - BHOPAL", new TrainDetail(19323L, "Intercity Express"));
    	
    	trainDetailsMap.put("UJJAIN - DEWAS", new TrainDetail(19304L, "BPL-IDR Express"));
    	trainDetailsMap.put("UJJAIN - INDORE", new TrainDetail(12961L, "Avantika SF Express"));
    	trainDetailsMap.put("UJJAIN - BHOPAL", new TrainDetail(19339L, "InterCity Express"));
    	
    	trainDetailsMap.put("INDORE - DEWAS", new TrainDetail(19334L, "Penchvalley Express"));
    	trainDetailsMap.put("INDORE - UJJAIN", new TrainDetail(12961L, "Avantika SF Express"));
    	trainDetailsMap.put("INDORE - BHOPAL", new TrainDetail(19765L, "IDR-BPL Express"));
    	
    	trainDetailsMap.put("BHOPAL - DEWAS", new TrainDetail(19323L, "Intercity Express"));
    	trainDetailsMap.put("BHOPAL - UJJAIN", new TrainDetail(19304L, "BPL-IDR Express"));
    	trainDetailsMap.put("BHOPAL - INDORE", new TrainDetail(19304L, "BPL-IDR Express"));
    }

	private ReservationRepository reservationRepository;	

	public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

	@Override
	public void saveReservationDeatails(Reservation reservation) {
		reservation.setPnr(String.valueOf(generatePNR()));
		String route = reservation.getDeparture_from() +" - "+ reservation.getDeparture_to();
		TrainDetail tranDetail =  trainDetailsMap.get(route);
		reservation.setTrain_name(tranDetail.getTrain_name());
		reservation.setTrain_no(tranDetail.getTrain_no());
		reservation.setRoute_id(route);
		reservationRepository.save(reservation);
	}

	@Override
	public List<Reservation> findAllReservationsById(Long id) {
        List<Reservation> reservations = reservationRepository.findAllByUserId(id);
        return reservations;
    }
	
	@Override
	public List<Reservation> findAllReservationsByPnrId(String pnrId) {
        List<Reservation> reservations = reservationRepository.findAllByPnrId(pnrId);
        return reservations;
    }

	@Override
	public boolean cancelReservation (String pnrNumber, boolean deleteEntry) {
		Long reservationId = reservationRepository.findIdByPnr(pnrNumber);
		if(reservationId == null) {
			return false;
		}
		if (deleteEntry) {
			reservationRepository.deleteById(reservationId);
		}
		return true;
	}

	public int generatePNR() {
		Random r = new Random(System.currentTimeMillis());
		return ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
	}

}
