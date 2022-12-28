package pl.wawrzyniak.NoteApp.Repository.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;


import java.time.LocalDateTime;


@Data
@Entity
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    @UpdateTimestamp
    private LocalDateTime updateTime;
}


