package com.pioneers.jpa.models.dtos.responses;

public record GenericResponse<T>(String message, T body) {
}
