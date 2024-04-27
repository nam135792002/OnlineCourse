package com.springboot.courses.service;

import com.springboot.courses.entity.TextLesson;
import com.springboot.courses.payload.TextLessonDto;

public interface TextLessonService {
    TextLesson createTextLesson(TextLessonDto textLessonDto);
}
