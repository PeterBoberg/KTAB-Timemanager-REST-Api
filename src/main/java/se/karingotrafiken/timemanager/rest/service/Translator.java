package se.karingotrafiken.timemanager.rest.service;

import se.karingotrafiken.timemanager.rest.dto.DtoObject;
import se.karingotrafiken.timemanager.rest.entitys.DbEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Translator<DTO extends DtoObject, Entity extends DbEntity> {
    public abstract DTO translateFromEntity(Entity entity);

    public abstract Entity translateFromDTO(DTO dto);

    public Entity translateFromNestedDTO(DTO dto, Object parent) {
        throw new RuntimeException("translateFromNestedDTO() not implemented in abstract class Translator");
    }

    public Set<Entity> translateFromSetOfNestedDTOs(Set<DTO> nestedDtos, Object parent) {
        Set<Entity> entities = new HashSet<>();
        nestedDtos.forEach(dto -> entities.add(translateFromNestedDTO(dto, parent)));
        return entities;
    }

    public List<DTO> translateFromEntityList(List<Entity> entities) {
        List<DTO> dtos = new ArrayList<>();
        entities.forEach(entity -> dtos.add(translateFromEntity(entity)));
        return dtos;
    }

    public Set<DTO> translateFromEntitySet(Set<Entity> entities) {
        Set dtos = new HashSet();
        entities.forEach(entity -> dtos.add(translateFromEntity(entity)));
        return dtos;
    }

    public List<Entity> translateFromDTOList(List<DTO> dtos) {
        List<Entity> entities = new ArrayList<>();
        dtos.forEach(dto -> entities.add(translateFromDTO(dto)));
        return entities;
    }

    public Set<Entity> translateFromDTOSet(Set<DTO> dtos) {
        Set<Entity> entities = new HashSet<>();
        dtos.forEach(dto -> entities.add(translateFromDTO(dto)));
        return entities;
    }
}
