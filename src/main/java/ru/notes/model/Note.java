package ru.notes.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notes", schema = "public")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private Long id;

    @Column(name = "note_text")
    private String text;

    @Column(name = "is_italics")
    private boolean isItalics;

    @Column(name = "is_bold")
    private boolean isBold;

    @Column(name = "text_size")
    private double textSize;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group groupId;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public boolean isDeleted() {
        return isDeleted;
    }
}
