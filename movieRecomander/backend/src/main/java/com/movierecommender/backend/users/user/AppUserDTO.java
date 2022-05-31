package com.movierecommender.backend.users.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.movierecommender.backend.advice.BusinessException;
import com.movierecommender.backend.security.config.UserRoles;
import com.movierecommender.backend.users.UserDTO;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class AppUserDTO extends UserDTO
{
	private String gender;
	private String birthdate;
	private String country;
	private String phoneNumber;
	private String profileImageLink;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Integer age;

	public AppUserDTO() {
		super(String.valueOf(UserRoles.USER));
	}

	public AppUserDTO(String gender, String birthdate, String country, String phoneNumber, String profileImageLink) {
		super(String.valueOf(UserRoles.USER));
		this.gender = gender;
		this.birthdate = birthdate;
		this.country = country;
		this.phoneNumber = phoneNumber;
		this.profileImageLink = profileImageLink;
	}

	public AppUserDTO(String email, String name, String password, String gender, String birthdate, String country, String phoneNumber, String profileImageLink) {
		super(email, name, password, String.valueOf(UserRoles.USER));
		this.gender = gender;
		this.birthdate = birthdate;
		this.country = country;
		this.phoneNumber = phoneNumber;
		this.profileImageLink = profileImageLink;
	}

	public AppUserDTO(Long id, String email, String name, String password, String gender, String birthdate, String country, String phoneNumber, String profileImageLink) {
		super(id, email, name, password, String.valueOf(UserRoles.USER));
		this.gender = gender;
		this.birthdate = birthdate;
		this.country = country;
		this.phoneNumber = phoneNumber;
		this.profileImageLink = profileImageLink;
	}

	public String getGender()
	{
		return gender;
	}

	public void setGender(String gender)
	{
		this.gender = gender;
	}

	public String getBirthdate()
	{
		return birthdate;
	}

	public void setBirthdate(String birthdate)
	{
		this.birthdate = birthdate;
	}

	public String getCountry()
	{
		return country;
	}

	public void setCountry(String country)
	{
		this.country = country;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public String getProfileImageLink()
	{
		return profileImageLink;
	}

	public void setProfileImageLink(String profileImageLink)
	{
		this.profileImageLink = profileImageLink;
	}

	public Integer getAge()
	{
		return age;
	}

	public void setAge(Integer age)
	{
		this.age = age;
	}

	public void isValid(){
		try {
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate.parse(this.birthdate, dateFormatter);
		} catch (DateTimeParseException e) {
			throw new BusinessException("Invalid date or invalid format.", "Json Format", HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		AppUserDTO that = (AppUserDTO) o;
		return Objects.equals(gender, that.gender) && Objects.equals(birthdate, that.birthdate) && Objects.equals(country, that.country) && Objects.equals(phoneNumber, that.phoneNumber) && Objects.equals(profileImageLink, that.profileImageLink) && Objects.equals(age, that.age);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(super.hashCode(), gender, birthdate, country, phoneNumber, profileImageLink, age);
	}
}
