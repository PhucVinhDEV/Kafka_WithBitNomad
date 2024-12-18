package com.example.AccountService.Model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MailInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "to_email")
	private String to;
	
	private String toName;
	private String subject;
	private String content;

	private boolean status;


}
