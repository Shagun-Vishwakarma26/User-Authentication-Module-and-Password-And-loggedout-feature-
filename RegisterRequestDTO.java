package com.TaskMange.DTO;

import com.TaskMange.Enum.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RegisterRequestDTO {
	
	public String userName;
	public String userOfficialEmail;
	public String password;
	public String role;
	
	

}

	