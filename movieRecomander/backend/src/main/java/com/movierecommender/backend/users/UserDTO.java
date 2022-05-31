package com.movierecommender.backend.users;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Objects;

public abstract class UserDTO
{
	protected Long id;

	@NotBlank(message="Email is mandatory")
	@Pattern(regexp="^[\\p{javaLetterOrDigit}+_.-]+@(.+)$", message = "cd")
	protected String email;

	@NotBlank(message="Name is mandatory")
	protected String name;

	@Pattern(regexp="(.)*.{8,20}$",message="length must be 8")
	@NotBlank(message="Password is mandatory")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	protected String password; //password that will be stored as hash

	@NotBlank(message="Role is mandatory")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String role;

	protected UserDTO(String role)
	{
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() { return role; }

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UserDTO userDTO = (UserDTO) o;
		return Objects.equals(id, userDTO.id) && Objects.equals(email, userDTO.email) && Objects.equals(name, userDTO.name) && Objects.equals(password, userDTO.password) && Objects.equals(role, userDTO.role);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(id, email, name, password, role);
	}
}
