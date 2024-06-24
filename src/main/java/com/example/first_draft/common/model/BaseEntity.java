package com.example.first_draft.common.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Temporal(TemporalType.TIMESTAMP)
//    private LocalDateTime createDate;
//    @CreatedBy
//    private Long createdById;
//    @Temporal(TemporalType.TIMESTAMP)
//    @LastModifiedBy
//    private LocalDateTime updateDates;
//    private Long updatedById;

}
