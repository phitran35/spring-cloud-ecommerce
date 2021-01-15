package com.phitran.services.auth.domain;

import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Size;

import lombok.Data;
@Data
@Entity
@Table(name = "auth_user")
public class User {

	@Id
	@Column(updatable = false, nullable = false)
	@Size(min = 0, max = 50)
	private String username;

	@Size(min = 0, max = 500)
	private String password;

	@Size(min = 0, max = 50)
	private String email;

	private boolean activated;

	@Size(min = 0, max = 100)
	@Column(name = "activationkey")
	private String activationKey;

	@Size(min = 0, max = 100)
	@Column(name = "resetpasswordkey")
	private String resetPasswordKey;

	@ManyToMany
	@JoinTable(name = "user_authority", joinColumns = @JoinColumn(name = "username"), inverseJoinColumns = @JoinColumn(name = "authority"))
	private Set<Authority> authorities;

}
