package vn.edu.ut.service.impl;

import vn.edu.ut.entity.TextLesson;
import vn.edu.ut.exception.ResourceNotFoundException;
import vn.edu.ut.payload.TextLessonDto;
import vn.edu.ut.repository.TextLessonRepository;
import vn.edu.ut.service.TextLessonService;
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

    @Override
    public TextLesson updateTextLesson(TextLessonDto textLessonDto) {
        TextLesson textLessonInDB = textLessonRepository.findById(textLessonDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Text lesson", "id", textLessonDto.getId()));

        textLessonInDB.setContent(textLessonDto.getContent());
        return textLessonRepository.save(textLessonInDB);
    }

}
