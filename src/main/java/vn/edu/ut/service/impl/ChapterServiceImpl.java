package vn.edu.ut.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import vn.edu.ut.entity.Chapter;
import vn.edu.ut.entity.Courses;
import vn.edu.ut.enums.ErrorCode;
import vn.edu.ut.exception.AppApiException;
import vn.edu.ut.exception.ResourceNotFoundException;
import vn.edu.ut.payload.chapter.ChapterDto;
import vn.edu.ut.repository.ChapterRepository;
import vn.edu.ut.repository.CoursesRepository;
import vn.edu.ut.service.ChapterService;

@Service
@Transactional
@RequiredArgsConstructor
public class ChapterServiceImpl implements ChapterService {
    private final ChapterRepository chapterRepository;
    private final CoursesRepository coursesRepository;
    private final ModelMapper modelMapper;

    @Override
    public ChapterDto createChapter(Integer courseId, ChapterDto chapterDto) {
        Courses course = coursesRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", courseId));

        if (chapterRepository.existsChapterByNameAndCourse(chapterDto.getName(), course))
            throw new AppApiException(ErrorCode.DUPLICATE_NAME_CHAPTER_WITHIN_COURSE);

        Chapter chapter = new Chapter(chapterDto.getName(), chapterDto.getOrders(), course);
        Integer maxOrderChapter = chapterRepository.findMaxOrderByCourse(courseId);
        if (chapterDto.getOrders() <= maxOrderChapter)
            chapterRepository.updateChapterOrderByCourseIdAfterCreateNewChapter(courseId, chapterDto.getOrders());
        chapterRepository.save(chapter);
        return modelMapper.map(chapter, ChapterDto.class);
    }

    @Override
    public ChapterDto updateChapter(Integer courseId, Integer chapterId, ChapterDto chapterDto) {
        if (!coursesRepository.existsById(courseId))
            throw new ResourceNotFoundException("Course", "id", courseId);

        Chapter chapterInDB = chapterRepository.findById(chapterId)
                .orElseThrow(() -> new ResourceNotFoundException("Chapter", "id", chapterId));

        if (!chapterRepository.existsNameInChapterAndCourse(chapterDto.getName(), chapterId, courseId))
            throw new AppApiException(ErrorCode.DUPLICATE_NAME_CHAPTER_WITHIN_COURSE);

        chapterInDB.setName(chapterDto.getName());

        if (chapterDto.getOrders() > chapterInDB.getOrders())
            chapterRepository.updateChapterOrderByCourseIdAfterUpdateChapterPushBottom(courseId,
                    chapterInDB.getOrders(), chapterDto.getOrders());
        else if (chapterDto.getOrders() < chapterInDB.getOrders())
            chapterRepository.updateChapterOrderByCourseIdAfterUpdateChapterPushUp(courseId,
                    chapterInDB.getOrders(), chapterDto.getOrders());
        chapterInDB.setOrders(chapterDto.getOrders());

        chapterRepository.save(chapterInDB);
        return modelMapper.map(chapterInDB, ChapterDto.class);
    }

    @Override
    public void deleteChapter(Integer courseId, Integer chapterId) {
        if (!coursesRepository.existsById(courseId))
            throw new ResourceNotFoundException("Course", "id", courseId);

        Chapter chapter = chapterRepository.findById(chapterId)
                .orElseThrow(() -> new ResourceNotFoundException("Chapter", "id", chapterId));
        chapterRepository.updateChapterOrderByCourseIdAfterDeleteChapter(courseId, chapter.getOrders());
        chapterRepository.delete(chapter);
    }
}
