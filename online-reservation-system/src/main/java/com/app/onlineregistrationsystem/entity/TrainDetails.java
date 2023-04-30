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
@Table(name="train_details")
public class TrainDetails {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long route_id;

    @Column(nullable=false)
    private String route_name;
    
    @Column(nullable=false)
    private String train_name;
    
    @Column(nullable=false)
    private Long train_number;
    
    @Column(nullable=false)
    private Long ticket_price;
    
    @Column(nullable=false)
    private String departure_time;
}
