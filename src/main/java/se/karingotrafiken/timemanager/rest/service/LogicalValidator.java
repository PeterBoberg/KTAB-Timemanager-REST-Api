package se.karingotrafiken.timemanager.rest.service;

public interface LogicalValidator<DTO> {

    void validate(DTO dto) throws RuntimeException;
}
