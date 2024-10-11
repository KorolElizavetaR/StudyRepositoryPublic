package com.mockito.programmer.model;

import jakarta.validation.constraints.Pattern;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table (name = "programmers")
@AllArgsConstructor (access = AccessLevel.PACKAGE)
@NoArgsConstructor 
@Getter
@Setter
@Builder
public class Programmer {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name = "id", unique = true, nullable = false)
	private Integer id;
	
	@Column (name = "name", nullable = false)
	@Pattern (regexp = "[А-ЯЁ][-А-яЁё]+ [А-ЯЁ][-А-яЁё]+ [А-ЯЁ][-А-яЁё]+", message = "Некорректный ввод ФИО")
	private String name;
	
	@Column (name = "location")
	private String location;
	
	@Column(name = "qualification")
	private String qualification;

	public Programmer(String name, String qualification) {
		this.name = name;
		this.qualification = qualification;
	}
	
	
}
