package com.example.demo.domain;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

@Entity
@Table(name = "\"user\"")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "username")
	@NotBlank(message = "Please provide your username")
	private String username;

	@Column(name = "consumer_id")
	private String consumerId;

	public Long getId() {
		return id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {

		return username;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}

	public String getConsumerId() {
		return consumerId;
	}

}