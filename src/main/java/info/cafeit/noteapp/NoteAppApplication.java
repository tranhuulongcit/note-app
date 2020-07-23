package info.cafeit.noteapp;

import info.cafeit.noteapp.entity.NoteEntity;
import info.cafeit.noteapp.repository.NoteRepository;
import info.cafeit.noteapp.service.NoteService;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.thymeleaf.expression.Lists;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

@SpringBootApplication
public class NoteAppApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(NoteAppApplication.class, args);
    }

    @Autowired
    private NoteRepository noteRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        NoteEntity note1 = new NoteEntity();
        note1.setTitle("Do something");
        note1.setContent("Do something");
        note1.setCreatedDate(new Date());
        note1.setUpdatedDate(new Date());
        NoteEntity note2 = new NoteEntity();
        note2.setTitle("Do walker");
        note2.setContent("5h - 6h PM");
        note2.setCreatedDate(new Date());
        note2.setUpdatedDate(new Date());
        NoteEntity note3 = new NoteEntity();
        note3.setTitle("Happy birth day mother");
        note3.setContent("20/20/2020");
        note3.setCreatedDate(new Date());
        note3.setUpdatedDate(new Date());
        NoteEntity note4 = new NoteEntity();
        note4.setTitle("Event bus");
        note4.setContent("Event bus start at 5h10 PM");
        note4.setCreatedDate(new Date());
        note4.setUpdatedDate(new Date());
        noteRepository.saveAll(Arrays.asList(note1, note2, note3, note4));
    }
}
