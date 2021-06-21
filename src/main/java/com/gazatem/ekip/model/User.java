package com.gazatem.ekip.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.*;

import lombok.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Transient;

@Entity
@Table(name = "users")
@Setter
@Getter
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private Integer id;
	@Column(name = "email")
	@Email(message = "*Please provide a valid Email")
	@NotEmpty(message = "*Please provide an email")
	private String email;
	@Column(name = "password")
	@Length(min = 5, message = "*Your password must have at least 5 characters")
	@NotEmpty(message = "*Please provide your password")
	@Transient
	private String password;
	@Column(name = "name")
	@NotEmpty(message = "*Please provide your name")
	private String name;
	@Column(name = "last_name")
	@NotEmpty(message = "*Please provide your last name")
	private String lastName;
	@Column(name = "active")
	private boolean active;
	@Column(name = "createDate")
	private Date createDate;
	@Column(name = "modifiedDate")
	private Date modifiedDate;
	@Column(name = "image")
	private String  image;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

	@MapsId
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "id", referencedColumnName = "id")
	private FileInfo fileInfo;

	public User() {}
	public User(String name, String lastName, String email, String password, boolean active, Date createDate, Date modifiedDate, String image) {
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.active = active;
		this.createDate = createDate;
		this.modifiedDate = modifiedDate;
		this.image = image;
	}

	public User(String name, String lastName, String email, String password, boolean active, Date createDate, Date modifiedDate, String image, Set <Role> roles) {
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.roles = roles;
		this.active = active;
		this.createDate = createDate;
		this.modifiedDate = modifiedDate;
		this.image = image;
	}
}
