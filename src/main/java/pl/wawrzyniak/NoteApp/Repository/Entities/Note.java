package pl.wawrzyniak.NoteApp.Repository.Entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@Entity
public class Note implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Integer version;
    private String text;
    @UpdateTimestamp
    private LocalDateTime updateTime;
}


