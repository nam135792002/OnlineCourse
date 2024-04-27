package com.springboot.courses.service.impl;

import com.springboot.courses.entity.TextLesson;
import com.springboot.courses.payload.TextLessonDto;
import com.springboot.courses.repository.TextLessonRepository;
import com.springboot.courses.service.TextLessonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TextLessonServiceImpl implements TextLessonService {

    @Autowired private TextLessonRepository textLessonRepository;
    @Autowired private ModelMapper modelMapper;

    @Override
    public TextLesson createTextLesson(TextLessonDto textLessonDto) {
        TextLesson lesson = modelMapper.map(textLessonDto, TextLesson.class);
        return textLessonRepository.save(lesson);
    }
}
