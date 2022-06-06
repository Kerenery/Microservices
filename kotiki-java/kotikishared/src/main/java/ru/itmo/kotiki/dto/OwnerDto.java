package ru.itmo.kotiki.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;

public class OwnerDto implements Serializable {

	private static final SimpleDateFormat dateFormat
			= new SimpleDateFormat("yyyy-MM-dd HH:mm");

	private long id;
	private String firstName;
	private String secondName;
	private String lastName;

	private LocalDate dateOfBirth;
	private int age;

	private UserDto userDto;

	public OwnerDto(long id,
					String firstName,
					String secondName,
					String lastName,
					LocalDate dateOfBirth,
					UserDto userDto) {
		this.id = id;
		this.firstName = firstName;
		this.secondName = secondName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.userDto = userDto;
	}

	@JsonCreator
	public OwnerDto(@JsonProperty("firstName")
							String firstName,
					@JsonProperty("secondName")
							String secondName,
					@JsonProperty("lastName")
							String lastName,
					@JsonProperty("dateOfBirth")
							LocalDate dateOfBirth,
					@JsonProperty("userDto")
							UserDto userDto) {
		this.firstName = firstName;
		this.secondName = secondName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.userDto = userDto;
	}

	public long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public String getLastName() {
		return lastName;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public int getAge() {
		if(dateOfBirth != null) {
			return Period.between(dateOfBirth, LocalDate.now()).getYears();
		}

		return age;
	}

	@Override
	public String toString() {
		return "OwnerDto{" +
				"id=" + id +
				", name='" + firstName + '\'' +
				", secondName='" + secondName + '\'' +
				", lastName='" + lastName + '\'' +
				", dateOfBirth='" + dateOfBirth + '\'' +
				", age=" + age +
				'}';
	}

	public UserDto getUserDto() {
		return userDto;
	}
}
