package info.cafeit.noteapp.dto;

import info.cafeit.noteapp.entity.NoteEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.Optional;

@Getter @Setter
public class NoteDto {

    private Integer id;

    @NotEmpty(message = "{msg.invalid.blank}")
    @Length(max = 200, message = "{msg.invalid.max-length}")
    private String title;

    @NotEmpty(message = "{msg.invalid.blank}")
    @Length(max = 1500, message = "{msg.invalid.max-length}")
    private String content;

    public void update(Optional<NoteEntity> note) {
        note.ifPresent(noteEntity -> {
            noteEntity.setTitle(this.title);
            noteEntity.setContent(this.content);
            noteEntity.setUpdatedDate(new Date());
        });
    }

    public NoteEntity toNote() {
        NoteEntity entity = new NoteEntity();
        entity.setTitle(this.title);
        entity.setContent(this.content);
        entity.setCreatedDate(new Date());
        entity.setUpdatedDate(new Date());
        return entity;
    }

    public static NoteDto toNoteDto(NoteEntity entity) {
        NoteDto noteDto = new NoteDto();
        noteDto.setId(entity.getId());
        noteDto.setTitle(entity.getTitle());
        noteDto.setContent(entity.getContent());
        return noteDto;
    }
}
