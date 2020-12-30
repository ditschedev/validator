package dev.ditsche.validator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Tobias Dittmann
 */
@Data
@AllArgsConstructor
public class NestedEntity {

    public String name;

    public LocalDateTime created;

}
