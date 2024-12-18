package com.example.AccountService.Model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatisticDTO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String message;

	private Date createdDate;

	private boolean status;


}
