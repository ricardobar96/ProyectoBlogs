package net.atos.rest.proyecto.dto;

import lombok.Data;

@Data
public class ProjectRequestDto {

	private String description;
	private String language;
	private boolean open;
}
