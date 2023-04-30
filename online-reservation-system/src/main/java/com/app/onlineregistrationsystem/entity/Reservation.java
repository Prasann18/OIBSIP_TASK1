package com.app.onlineregistrationsystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="reservation")
public class Reservation {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private String pnr;
    
    @Column(nullable=false)
    private String departure_from;
    
    @Column(nullable=false)
    private String departure_to;
    
    @Column(nullable=false)
    private String class_type;

    @Column(nullable=false)
    private String departure_datetime;

    @Column(nullable=false)
    private String departure_time;

    @Column(nullable=false)
    private Long passenger_count;
    
    @Column(nullable=false)
    private String route_id;

    @Column(nullable=false)
    private Long user_id;

    @Column(nullable = false)
    private String train_name;

    @Column(nullable = false)
    private Long train_no;    
}
