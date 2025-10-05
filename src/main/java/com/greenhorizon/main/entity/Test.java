package com.greenhorizon.main.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Test {

	@Id
	@Column
	private int id;
	
	@Column
	private String test;
	
	
	
}


