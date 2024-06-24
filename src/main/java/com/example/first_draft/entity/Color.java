package com.example.first_draft.entity;

import com.example.first_draft.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Color extends BaseEntity {
    private String name;
    private String code;
    private double discount;
}
