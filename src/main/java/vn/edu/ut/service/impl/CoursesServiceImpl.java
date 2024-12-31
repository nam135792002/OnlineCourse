package vn.edu.ut.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.ut.entity.Category;
import vn.edu.ut.entity.CourseInfo;
import vn.edu.ut.entity.Courses;
import vn.edu.ut.entity.Lesson;
import vn.edu.ut.entity.Video;
import vn.edu.ut.enums.ErrorCode;
import vn.edu.ut.enums.InformationType;
import vn.edu.ut.enums.LessonType;
import vn.edu.ut.exception.AppApiException;
import vn.edu.ut.exception.ResourceNotFoundException;
import vn.edu.ut.payload.ClassResponse;
import vn.edu.ut.payload.chapter.ChapterReturnDetailResponse;
import vn.edu.ut.payload.course.CourseInfoRequest;
import vn.edu.ut.payload.course.CourseResponse;
import vn.edu.ut.payload.course.CourseReturnDetailPageResponse;
import vn.edu.ut.payload.course.CourseReturnHomePageResponse;
import vn.edu.ut.payload.course.CourseReturnSearch;
import vn.edu.ut.payload.course.CoursesRequest;
import vn.edu.ut.payload.lesson.LessonReturnDetailResponse;
import vn.edu.ut.repository.CategoryRepository;
import vn.edu.ut.repository.CoursesRepository;
import vn.edu.ut.repository.LessonRepository;
import vn.edu.ut.repository.VideoRepository;
import vn.edu.ut.service.ICoursesService;
import vn.edu.ut.utils.UploadFile;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CoursesServiceImpl implements ICoursesService {
    private final CoursesRepository coursesRepository;
    private final ModelMapper modelMapper;
    private final UploadFile uploadFile;
    private final CategoryRepository categoryRepository;
    private final LessonRepository lessonRepository;
    private final VideoRepository videoRepository;

    @Override
    public CourseResponse createCourse(CoursesRequest coursesRequest, MultipartFile image) {
        Category category = categoryRepository.findById(coursesRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", coursesRequest.getCategoryId()));

        if (coursesRepository.existsCoursesByTitle(coursesRequest.getTitle())) {
            throw new AppApiException(ErrorCode.COURSE_NAME_EXISTED);
        }

        if (coursesRepository.existsCoursesBySlug(coursesRequest.getSlug())) {
            throw new AppApiException(ErrorCode.COURSE_SLUG_EXISTED);
        }

        Courses courses = new Courses(coursesRequest);
        String thumbnail = uploadFile.uploadFileOnCloudinary(image);
        courses.setThumbnail(thumbnail);
        courses.setCategory(category);

        for (CourseInfoRequest request : coursesRequest.getInfoList()) {
            courses.addInfoList(request.getValue(), InformationType.valueOf(request.getType()));
        }
        coursesRepository.save(courses);

        return modelMapper.map(courses, CourseResponse.class);
    }

    /*TODO: calculate total review and average review*/
    @Override
    public ClassResponse getAll(int pageNo, int pageSize, String sortBy, String sortDir, String keyword, Integer categoryId) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Courses> coursesResult;
        if (StringUtils.isNotEmpty(keyword)) {
            if (categoryId != null) {
                coursesResult = coursesRepository.searchInCategory(keyword, categoryId, pageable);
            } else {
                coursesResult = coursesRepository.search(keyword, pageable);
            }
        } else {
            if (categoryId != null) {
                coursesResult = coursesRepository.findAllInCategory(categoryId, pageable);
            } else {
                coursesResult = coursesRepository.findAll(pageable);
            }
        }
        List<Courses> listCourses = coursesResult.getContent();

        List<CourseReturnHomePageResponse> content = listCourses.stream()
                .map(courses -> modelMapper.map(courses, CourseReturnHomePageResponse.class))
                .collect(Collectors.toList());

        return ClassResponse.convertToClassResponse(coursesResult, content);
    }

    @Override
    public CourseResponse get(Integer courseId) {
        Courses course = coursesRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", courseId));

        CourseResponse response = modelMapper.map(course, CourseResponse.class);
//        sortChapterAndLesson(response);
        return response;
    }

    @Override
    public CourseResponse update(Integer courseId, CoursesRequest coursesRequest, MultipartFile img) {
        Courses courseInDB = coursesRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", courseId));

        Category categoryInDB = categoryRepository.findById(coursesRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", coursesRequest.getCategoryId()));

        if (!coursesRepository.existsCoursesByTitleOrSlugAndId(coursesRequest.getTitle(),
                coursesRequest.getSlug(), courseId)) {
            throw new AppApiException(ErrorCode.COURSE_SLUG_NAME_EXISTED);
        }
        courseInDB.setTitle(coursesRequest.getTitle());
        courseInDB.setSlug(coursesRequest.getSlug());
        courseInDB.setDescription(coursesRequest.getDescription());
        courseInDB.setCategory(categoryInDB);
        courseInDB.setPrice(coursesRequest.getPrice());
        courseInDB.setDiscount(coursesRequest.getDiscount());
        courseInDB.setEnabled(coursesRequest.isEnabled());
        courseInDB.setPublished(coursesRequest.isPublished());
        courseInDB.setFinished(coursesRequest.isFinished());

        if (img != null) {
            uploadFile.deleteImageInCloudinary(courseInDB.getThumbnail());
            String url = uploadFile.uploadFileOnCloudinary(img);
            courseInDB.setThumbnail(url);
        }

        List<CourseInfo> infoList = new ArrayList<>();
        for (CourseInfoRequest request : coursesRequest.getInfoList()) {
            CourseInfo info;
            if (request.getId() != null) {
                info = new CourseInfo(request.getId(), request.getValue(), InformationType.valueOf(request.getType()), courseInDB);
            } else {
                info = new CourseInfo(request.getValue(), InformationType.valueOf(request.getType()), courseInDB);
            }
            infoList.add(info);
        }
        courseInDB.setInfoList(infoList);
        coursesRepository.save(courseInDB);

        return modelMapper.map(courseInDB, CourseResponse.class);
    }

    @Override
    public void delete(Integer courseId) {
        Courses courseInDB = coursesRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", courseId));

        uploadFile.deleteImageInCloudinary(courseInDB.getThumbnail());

        coursesRepository.delete(courseInDB);
    }

    /*TODO: calculate total review and average review*/
    @Override
    public List<CourseReturnHomePageResponse> getCourseIntoHomePage(Integer categoryId) {
        List<Courses> listCourses;

        if (categoryId == null) {
            listCourses = coursesRepository.findAll();
        } else {
            listCourses = coursesRepository.findAllByCategoryId(categoryId);
        }
        return listCourses.stream()
                .map(courses -> modelMapper.map(courses, CourseReturnHomePageResponse.class))
                .toList();
    }

    @Override
    public CourseReturnDetailPageResponse getCourseDetail(String slug) {
        Courses course = coursesRepository.findCoursesBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "slug", slug));

        CourseReturnDetailPageResponse response = modelMapper.map(course, CourseReturnDetailPageResponse.class);
        sortChapterAndLesson(response);
        return response;
    }

    @Override
    public void updateIsEnabled(Integer courseId, boolean isEnabled) {
        if (coursesRepository.existsById(courseId))
            coursesRepository.switchEnabled(courseId, isEnabled);
        else
            throw new ResourceNotFoundException("Course", "id", courseId);
    }

    @Override
    public void updateIsPublished(Integer courseId, boolean isPublished) {
        if (coursesRepository.existsById(courseId))
            coursesRepository.switchPublished(courseId, isPublished);
        else
            throw new ResourceNotFoundException("Course", "id", courseId);
    }

    @Override
    public void updateIsFinished(Integer courseId, boolean isFinished) {
        if (coursesRepository.existsById(courseId))
            coursesRepository.switchFinished(courseId, isFinished);
        else
            throw new ResourceNotFoundException("Course", "id", courseId);
    }

    /*TODO: calculate average review*/
    @Override
    public List<CourseReturnSearch> listAllCourseByKeyword(String keyword) {
        List<Courses> listCourses = coursesRepository.search(keyword);

        return listCourses.stream()
                .map(courses -> modelMapper.map(courses, CourseReturnSearch.class))
                .toList();
    }

//    private void sortChapterAndLesson(CourseResponse response) {
//        int totalLessonInCourse = 0;
//        List<ChapterDto> chapterList = response.getChapterList();
//        response.setTotalChapter(chapterList.size());
//        chapterList.sort(Comparator.comparingInt(ChapterDto::getOrders));
//        for (ChapterDto dto : chapterList) {
//            List<LessonResponse> listLesson = dto.getLessonList();
//            listLesson.sort(Comparator.comparingInt(LessonResponse::getOrders));
//            totalLessonInCourse += listLesson.size();
//            dto.setTotalLesson(listLesson.size());
//        }
//        response.setTotalLesson(totalLessonInCourse);
//    }

    private void sortChapterAndLesson(CourseReturnDetailPageResponse response) {
        int totalLessonInCourse = 0;
        Duration duration = Duration.ZERO;

        List<ChapterReturnDetailResponse> chapterList = response.getChapterList().stream().map(chapter ->
                modelMapper.map(chapter, ChapterReturnDetailResponse.class)).collect(Collectors.toList());
        response.setTotalChapter(chapterList.size());
        chapterList.sort(Comparator.comparingInt(ChapterReturnDetailResponse::getOrders));
        int i = 1;
        for (ChapterReturnDetailResponse chapter : chapterList) {
            List<LessonReturnDetailResponse> listLesson = chapter.getLessonList();
            listLesson.sort(Comparator.comparingInt(LessonReturnDetailResponse::getOrders));
            for (LessonReturnDetailResponse lesson : listLesson) {
                if (lesson.getLessonType().equals(LessonType.VIDEO)) {
                    Lesson lessonInDB = lessonRepository.findById(lesson.getId()).get();
                    Video video = videoRepository.findById(lessonInDB.getVideo().getId()).get();
                    lesson.setDuration(video.getDuration());

                    duration = duration.plus(
                            Duration.ofMinutes(video.getDuration().getMinute())
                                    .plusSeconds(video.getDuration().getSecond())
                    );
                } else {
                    LocalTime time = LocalTime.of(0, 1, 0);
                    lesson.setDuration(time);
                    duration = duration.plus(
                            Duration.ofMinutes(1)
                    );
                }
                lesson.setOrders(i);
                ++i;
            }
            totalLessonInCourse += listLesson.size();
            chapter.setTotalLesson(listLesson.size());
        }

        long hours = duration.toHours();
        long minutes = duration.toMinutes(); // Lấy tổng số phút
        long seconds = duration.minusMinutes(minutes).getSeconds(); // Lấy số giây còn lại sau khi lấy tổng số phút

        // Tạo LocalTime từ số phút và số giây
        LocalTime localTime = LocalTime.of((int) hours, (int) minutes, (int) seconds);
        response.setTotalTime(localTime);
        response.setTotalLesson(totalLessonInCourse);
    }
}
