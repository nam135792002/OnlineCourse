package com.springboot.courses.service.impl;

import com.springboot.courses.entity.Chapter;
import com.springboot.courses.entity.Courses;
import com.springboot.courses.exception.BlogApiException;
import com.springboot.courses.exception.ResourceNotFoundException;
import com.springboot.courses.payload.chapter.ChapterDto;
import com.springboot.courses.repository.ChapterRepository;
import com.springboot.courses.repository.CoursesRepository;
import com.springboot.courses.service.ChapterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ChapterServiceImpl implements ChapterService {

    @Autowired private ChapterRepository chapterRepository;
    @Autowired private CoursesRepository coursesRepository;
    @Autowired private ModelMapper modelMapper;

    @Override
    public ChapterDto createChapter(Integer courseId, ChapterDto chapterDto) {
        Courses course = coursesRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", courseId));

        Chapter chapterDuplicate = chapterRepository.findChapterByNameAndCourse(chapterDto.getName(), course);

        if(chapterDuplicate != null){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Chapter name have existed in course " + course.getTitle());
        }

        Chapter chapter = new Chapter();
        chapter.setName(chapterDto.getName());
        chapter.setOrders(chapterDto.getOrders());
        chapter.setCourse(course);

        Chapter savedChapter = chapterRepository.save(chapter);

        return modelMapper.map(savedChapter, ChapterDto.class);
    }

    @Override
    public ChapterDto updateChapter(Integer courseId, Integer chapterId, ChapterDto chapterDto) {
        Courses course = coursesRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", courseId));

        Chapter chapter = chapterRepository.findById(chapterId)
                .orElseThrow(() -> new ResourceNotFoundException("Chapter", "id", chapterId));

        Chapter chapterDuplicate = chapterRepository.findChapterByNameAndCourse(chapterDto.getName(), course);

        if(chapterDuplicate != null){
            if(!Objects.equals(chapter.getId(), chapterDuplicate.getId()))
            {
                throw new BlogApiException(HttpStatus.BAD_REQUEST, "This chapter name have existed in course " + course.getTitle());
            }
        }

        chapter.setName(chapterDto.getName());

        Chapter savedChapter = chapterRepository.save(chapter);
        return modelMapper.map(savedChapter, ChapterDto.class);
    }

    @Override
    public String deleteChapter(Integer courseId, Integer chapterId) {
        Courses course = coursesRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", courseId));

        Chapter chapter = chapterRepository.findById(chapterId)
                .orElseThrow(() -> new ResourceNotFoundException("Chapter", "id", chapterId));

        chapterRepository.delete(chapter);
        return "Delete successfully chapter!";
    }
}
