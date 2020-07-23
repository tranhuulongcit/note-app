package info.cafeit.noteapp.controller;

import info.cafeit.noteapp.dto.NoteDto;
import info.cafeit.noteapp.entity.NoteEntity;
import info.cafeit.noteapp.service.NoteService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/notes")
@PropertySource("classpath:messages.properties")
public class NoteController {
    private final NoteService noteService;
    private final Environment env;

    @GetMapping("")
    public String home(Model model) {
        model.addAttribute("notes", noteService.getNotes());
        return "note/home";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Integer id, Model model) {
        Optional<NoteEntity> note = noteService.getNote(id);
        model.addAttribute("note", note.get());
        return "note/detail";
    }

    @GetMapping("/create")
    public String createNote(Model model) {
        model.addAttribute("note", new NoteDto());
        return "note/create";
    }

    @GetMapping("/update/{id}")
    public String updateNote(@PathVariable("id") Integer id, Model model) {
        Optional<NoteEntity> noteEntity = noteService.getNote(id);
        noteEntity.ifPresent(note -> model.addAttribute("note", NoteDto.toNoteDto(note)));
        return "note/update";
    }

    @PostMapping("")
    public String createOrUpdateNote(@Valid @ModelAttribute("note") NoteDto noteDto, BindingResult binding, RedirectAttributes ra) {
        if (noteDto != null && noteDto.getId() != null) {
            //do update
            if (true) {
                return "note/update";
            }
            Optional<NoteEntity> note = noteService.updateNote(noteDto);
            if (note.isPresent()) {
                ra.addFlashAttribute("messageSuccess", env.getProperty("msg.update.success"));
            } else {
                ra.addFlashAttribute("messageError", env.getProperty("msg.update.error"));
            }
            return "redirect:/notes/" + noteDto.getId();
        } else {
            // do insert
            if (binding.hasErrors()) {
                return "note/create";
            }
            Optional<NoteEntity> note = noteService.saveNote(noteDto);
            if (note.isPresent()) {
                ra.addFlashAttribute("messageSuccess", env.getProperty("msg.save.success"));
            } else {
                ra.addFlashAttribute("messageError", env.getProperty("msg.save.error"));
            }
            return "redirect:/notes";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes ra) {
        boolean hasSuccess = noteService.deleteNote(id);
        if (hasSuccess) {
            ra.addFlashAttribute("messageSuccess", env.getProperty("msg.delete.success"));
        } else {
            ra.addFlashAttribute("messageError", env.getProperty("msg.delete.error"));
        }
        return "redirect:/notes";
    }

    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword, Model model) {
        model.addAttribute("notes", noteService.search(keyword));
        model.addAttribute("keyword", keyword);
        return "note/search";
    }
}
