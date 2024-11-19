package vn.edu.ut.service;

import vn.edu.ut.entity.TextLesson;
import vn.edu.ut.payload.TextLessonDto;

public interface TextLessonService {
    TextLesson createTextLesson(TextLessonDto textLessonDto);
    TextLesson updateTextLesson(TextLessonDto textLessonDto);
}
