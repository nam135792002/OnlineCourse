package vn.edu.ut.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.ut.entity.Category;
import vn.edu.ut.entity.CourseInfo;
import vn.edu.ut.entity.Courses;
import vn.edu.ut.entity.Lesson;
import vn.edu.ut.entity.Review;
import vn.edu.ut.entity.Video;
import vn.edu.ut.enums.ErrorCode;
import vn.edu.ut.enums.InformationType;
import vn.edu.ut.enums.LessonType;
import vn.edu.ut.exception.AppApiException;
import vn.edu.ut.exception.AppException;
import vn.edu.ut.exception.ResourceNotFoundException;
import vn.edu.ut.payload.ClassResponse;
import vn.edu.ut.payload.chapter.ChapterDto;
import vn.edu.ut.payload.chapter.ChapterReturnDetailResponse;
import vn.edu.ut.payload.course.CourseInfoRequest;
import vn.edu.ut.payload.course.CourseResponse;
import vn.edu.ut.payload.course.CourseReturnDetailPageResponse;
import vn.edu.ut.payload.course.CourseReturnHomePageResponse;
import vn.edu.ut.payload.course.CourseReturnSearch;
import vn.edu.ut.payload.course.CoursesRequest;
import vn.edu.ut.payload.lesson.LessonResponse;
import vn.edu.ut.payload.lesson.LessonReturnDetailResponse;
import vn.edu.ut.repository.CategoryRepository;
import vn.edu.ut.repository.CoursesRepository;
import vn.edu.ut.repository.LessonRepository;
import vn.edu.ut.repository.VideoRepository;
import vn.edu.ut.service.ICoursesService;
import vn.edu.ut.utils.UploadFile;
import vn.edu.ut.utils.Utils;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
        if (coursesRepository.existsCoursesByTitle(coursesRequest.getTitle())) {
            throw new AppApiException(ErrorCode.COURSE_NAME_EXISTED);
        }

        if (coursesRepository.existsCoursesBySlug(coursesRequest.getSlug())) {
            throw new AppApiException(ErrorCode.COURSE_SLUG_EXISTED);
        }

        Category category = categoryRepository.findById(coursesRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", coursesRequest.getCategoryId()));

        Courses courses = new Courses();

        convertSomeAttributeToEntity(courses, coursesRequest);

        String thumbnail = uploadFile.uploadFileOnCloudinary(image);
        courses.setThumbnail(thumbnail);

        courses.setCategory(category);

        for (CourseInfoRequest request : coursesRequest.getInfoList()) {
            courses.addInfoList(request.getValue(), InformationType.valueOf(request.getType()));
        }

        Courses savedCourse = coursesRepository.save(courses);

        return modelMapper.map(savedCourse, CourseResponse.class);
    }

    @Override
    public ClassResponse getAll(int pageNo, int pageSize, String sortBy, String sortDir, String keyword, Integer categoryId) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Courses> courses = null;
        if (keyword != null && !keyword.isEmpty()) {
            if (categoryId != null && categoryId > 0) {
                courses = coursesRepository.searchInCategory(keyword, categoryId, pageable);
            } else {
                courses = coursesRepository.search(keyword, pageable);
            }
        } else {
            courses = coursesRepository.findAllInCategory(categoryId, pageable);
        }

        if (keyword == null && categoryId == null) {
            courses = coursesRepository.findAll(pageable);
        }
        List<Courses> listCourses = courses.getContent();

        List<CourseResponse> content = listCourses.stream()
                .map(course -> {
                            CourseResponse courseResponse = modelMapper.map(course, CourseResponse.class);
                            int totalReview = course.getListReviews().size();
                            int totalRating = course.getListReviews().stream().mapToInt(Review::getRating).sum();
                            double averageRating = (double) totalRating / totalReview;
                            averageRating = Math.round(averageRating * 10.0) / 10.0;
                            courseResponse.setTotalReview(totalReview);
                            courseResponse.setAverageReview(averageRating);
                            courseResponse.setChapterList(null);
                            courseResponse.setInfoList(null);
                            return courseResponse;
                        }
                )
                .collect(Collectors.toList());

        return ClassResponse.convertToClassResponse(courses, content);
    }

    @Override
    public CourseResponse get(Integer courseId) {
        Courses course = coursesRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", courseId));

        CourseResponse response = modelMapper.map(course, CourseResponse.class);
        sortChapterAndLesson(response);
        return response;
    }

    @Override
    public CourseResponse update(Integer courseId, CoursesRequest coursesRequest, MultipartFile img) {
        Courses courseInDB = coursesRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", courseId));
        Category categoryInDB = categoryRepository.findById(coursesRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", coursesRequest.getCategoryId()));

        Courses courses = coursesRepository.findByTitleOrSlug(coursesRequest.getTitle(), coursesRequest.getSlug())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "name/slug", coursesRequest.getTitle()
                        .concat("/").concat(coursesRequest.getSlug())));

        if (!Objects.equals(courses.getId(), courseInDB.getId())) {
            throw new AppApiException(ErrorCode.COURSE_SLUG_OR_NAME_EXISTED);
        }

        if (img != null) {
            uploadFile.deleteImageInCloudinary(courseInDB.getThumbnail());
            String url = uploadFile.uploadFileOnCloudinary(img);
            courseInDB.setThumbnail(url);
        }

        convertSomeAttributeToEntity(courseInDB, coursesRequest);

        courseInDB.setCategory(categoryInDB);

        List<CourseInfo> infoList = new ArrayList<>();

        for (CourseInfoRequest request : coursesRequest.getInfoList()) {
            CourseInfo info = null;
            if (request.getId() != null) {
                info = new CourseInfo(request.getId(), request.getValue(), InformationType.valueOf(request.getType()), courseInDB);
            } else {
                info = new CourseInfo(request.getValue(), InformationType.valueOf(request.getType()), courseInDB);
            }
            infoList.add(info);
        }

        courseInDB.setInfoList(infoList);

        Courses savedCourse = coursesRepository.save(courseInDB);

        return modelMapper.map(savedCourse, CourseResponse.class);
    }

    @Override
    public void delete(Integer courseId) {
        Courses courseInDB = coursesRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", courseId));
        uploadFile.deleteImageInCloudinary(courseInDB.getThumbnail());
        coursesRepository.delete(courseInDB);
    }

    @Override
    public List<CourseReturnHomePageResponse> getCourseIntoHomePage(Integer categoryId) {
        List<Courses> listCourses;

        if (categoryId == null) {
            listCourses = coursesRepository.findAll();
        } else {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

            listCourses = coursesRepository.findAllByCategoryId(categoryId);
        }

        return listCourses.stream()
                .map(courses -> {
                    CourseReturnHomePageResponse response = modelMapper.map(courses, CourseReturnHomePageResponse.class);
                    int totalReview = courses.getListReviews().size();
                    int totalRating = courses.getListReviews().stream().mapToInt(Review::getRating).sum();
                    double averageRating = (double) totalRating / totalReview;
                    averageRating = Math.round(averageRating * 10.0) / 10.0;
                    response.setTotalReview(totalReview);
                    response.setAverageReview(averageRating);
                    return response;
                }).toList();
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
    public String updateIsEnabled(Integer courseId, boolean isEnabled) {
        Courses courses = coursesRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Courses", "id", courseId));

        coursesRepository.switchEnabled(courseId, isEnabled);

        return "SUCCESS";
    }

    @Override
    public String updateIsPublished(Integer courseId, boolean isPublished) {
        Courses courses = coursesRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Courses", "id", courseId));

        coursesRepository.switchPublished(courseId, isPublished);
        return "SUCCESS";
    }

    @Override
    public String updateIsFinished(Integer courseId, boolean isFinished) {
        Courses courses = coursesRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Courses", "id", courseId));

        coursesRepository.switchFinished(courseId, isFinished);
        return "SUCCESS";
    }

    @Override
    public List<CourseReturnSearch> listAllCourseByKeyword(String keyword) {
        List<Courses> listCourses = coursesRepository.search(keyword);

        return listCourses.stream().map(
                courses -> {
                    CourseReturnSearch response = modelMapper.map(courses, CourseReturnSearch.class);
                    int totalReview = courses.getListReviews().size();
                    int totalRating = courses.getListReviews().stream().mapToInt(Review::getRating).sum();
                    double averageRating = (double) totalRating / totalReview;
                    averageRating = Math.round(averageRating * 10.0) / 10.0;
                    response.setAverageReview(averageRating);
                    return response;
                }
        ).toList();
    }

    private void convertSomeAttributeToEntity(Courses courses, CoursesRequest request) {
        courses.setTitle(request.getTitle());
        String slug = Utils.removeVietnameseAccents(request.getTitle());
        courses.setSlug(slug);
        courses.setDescription(request.getDescription());
        courses.setPrice(request.getPrice());
        courses.setDiscount(request.getDiscount());
        courses.setEnabled(request.isEnabled());
        courses.setPublished(request.isPublished());
        courses.setFinished(request.isFinished());
        if (request.isPublished()) {
            courses.setPublishedAt(new Date());
        }
    }

    private void sortChapterAndLesson(CourseResponse response) {
        int totalLessonInCourse = 0;
        List<ChapterDto> chapterList = response.getChapterList();
        response.setTotalChapter(chapterList.size());
        chapterList.sort(Comparator.comparingInt(ChapterDto::getOrders));
        for (ChapterDto dto : chapterList) {
            List<LessonResponse> listLesson = dto.getLessonList();
            listLesson.sort(Comparator.comparingInt(LessonResponse::getOrders));
            totalLessonInCourse += listLesson.size();
            dto.setTotalLesson(listLesson.size());
        }
        response.setTotalLesson(totalLessonInCourse);
    }

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
