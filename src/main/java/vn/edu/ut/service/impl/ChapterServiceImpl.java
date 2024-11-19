package vn.edu.ut.service.impl;

import vn.edu.ut.entity.Chapter;
import vn.edu.ut.entity.Courses;
import vn.edu.ut.exception.AppException;
import vn.edu.ut.exception.ResourceNotFoundException;
import vn.edu.ut.payload.chapter.ChapterDto;
import vn.edu.ut.repository.ChapterRepository;
import vn.edu.ut.repository.CoursesRepository;
import vn.edu.ut.service.ChapterService;
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
            throw new AppException(HttpStatus.BAD_REQUEST, "Tên chương đã từng tồn tại trong khóa học " + course.getTitle());
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
                throw new AppException(HttpStatus.BAD_REQUEST, "Tên chương này đã từng tồn tại trong khóa học " + course.getTitle());
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
        return "Xóa chương thành công!";
    }
}
