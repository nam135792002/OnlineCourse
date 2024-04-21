package com.springboot.courses.service.impl;

import com.springboot.courses.entity.*;
import com.springboot.courses.exception.BlogApiException;
import com.springboot.courses.exception.ResourceNotFoundException;
import com.springboot.courses.payload.chapter.ChapterDto;
import com.springboot.courses.payload.ClassResponse;
import com.springboot.courses.payload.chapter.ChapterReturnDetailResponse;
import com.springboot.courses.payload.course.*;
import com.springboot.courses.payload.lesson.LessonResponse;
import com.springboot.courses.payload.lesson.LessonReturnDetailResponse;
import com.springboot.courses.repository.CategoryRepository;
import com.springboot.courses.repository.CoursesRepository;
import com.springboot.courses.repository.LessonRepository;
import com.springboot.courses.repository.VideoRepository;
import com.springboot.courses.service.CoursesService;
import com.springboot.courses.utils.UploadFile;
import com.springboot.courses.utils.Utils;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class CoursesServiceImpl implements CoursesService {

    @Autowired private CoursesRepository coursesRepository;
    @Autowired private ModelMapper modelMapper;
    @Autowired private UploadFile uploadFile;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private LessonRepository lessonRepository;
    @Autowired private VideoRepository videoRepository;

    @Override
    public CourseResponse createCourse(CoursesRequest coursesRequest, MultipartFile image) {

        if(coursesRepository.existsCoursesByTitle(coursesRequest.getTitle())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Tên khóa học đã được tạo trước đó!");
        }

        if(coursesRepository.existsCoursesBySlug(coursesRequest.getSlug())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Slug khóa học đã được tạo trước đó!");
        }

        Category category = categoryRepository.findById(coursesRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", coursesRequest.getCategoryId()));

        Courses courses = new Courses();
        
        convertSomeAttributeToEntity(courses, coursesRequest);

        String thumbnail = uploadFile.uploadFileOnCloudinary(image);
        courses.setThumbnail(thumbnail);

        courses.setCategory(category);

        for (CourseInfoRequest request : coursesRequest.getInfoList()){
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
        if(keyword != null && !keyword.isEmpty()){
            if(categoryId != null && categoryId > 0){
                courses = coursesRepository.searchInCategory(keyword, categoryId, pageable);
            }else{
                courses = coursesRepository.search(keyword, pageable);
            }
        }else{
            courses = coursesRepository.findAllInCategory(categoryId, pageable);
        }

        if(keyword == null && categoryId == null){
            courses = coursesRepository.findAll(pageable);
        }
        List<Courses> listCourses = courses.getContent();

        List<CourseResponse> content = listCourses.stream().map(course -> modelMapper.map(course, CourseResponse.class)).collect(Collectors.toList());

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

        Courses courses = coursesRepository.findByTitleOrSlug(coursesRequest.getTitle(), coursesRequest.getSlug());

        if(courses != null){
            if(!Objects.equals(courses.getId(), courseInDB.getId())){
                throw new BlogApiException(HttpStatus.BAD_REQUEST, "Tên/Slug khóa học đã tồn tại trước đó");
            }
        }

        if(img != null){
            uploadFile.deleteImageInCloudinary(courseInDB.getThumbnail());
            String url = uploadFile.uploadFileOnCloudinary(img);
            courseInDB.setThumbnail(url);
        }

        convertSomeAttributeToEntity(courseInDB, coursesRequest);
        
        courseInDB.setCategory(categoryInDB);

        List<CourseInfo> infoList = new ArrayList<>();

        for (CourseInfoRequest request : coursesRequest.getInfoList()){
            CourseInfo info = null;
            if(request.getId() != null){
                info = new CourseInfo(request.getId(), request.getValue(), InformationType.valueOf(request.getType()), courseInDB);
            }else{
                info = new CourseInfo(request.getValue(), InformationType.valueOf(request.getType()), courseInDB);
            }
            infoList.add(info);
        }

        courseInDB.setInfoList(infoList);

        Courses savedCourse = coursesRepository.save(courseInDB);

        return modelMapper.map(savedCourse, CourseResponse.class);
    }

    @Override
    public String delete(Integer courseId) {
        Courses courseInDB = coursesRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", courseId));

        uploadFile.deleteImageInCloudinary(courseInDB.getThumbnail());

        coursesRepository.delete(courseInDB);

        return "Xóa khóa học thành công";
    }

    @Override
    public List<CourseReturnHomePageResponse> getCourseIntoHomePage(Integer categoryId) {
        List<Courses> listCourses = null;

        if(categoryId == null){
            listCourses = coursesRepository.findAll();
        }else{
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

    private void convertSomeAttributeToEntity(Courses courses, CoursesRequest request){
        courses.setTitle(request.getTitle());
        String slug = Utils.removeVietnameseAccents(request.getTitle());
        courses.setSlug(slug);
        courses.setDescription(request.getDescription());
        courses.setPrice(request.getPrice());
        courses.setDiscount(request.getDiscount());
        courses.setEnabled(request.isEnabled());
        courses.setPublished(request.isPublished());
        if(request.isPublished()){
            courses.setPublishedAt(new Date());
        }
    }

    private void sortChapterAndLesson(CourseResponse response){
        int totalLessonInCourse = 0;
        List<ChapterDto> chapterList = response.getChapterList();
        response.setTotalChapter(chapterList.size());
        chapterList.sort(Comparator.comparingInt(ChapterDto::getOrders));
        for(ChapterDto dto : chapterList){
            List<LessonResponse> listLesson = dto.getLessonList();
            listLesson.sort(Comparator.comparingInt(LessonResponse::getOrders));
            totalLessonInCourse += listLesson.size();
            dto.setTotalLesson(listLesson.size());
        }
        response.setTotalLesson(totalLessonInCourse);
    }

    private void sortChapterAndLesson(CourseReturnDetailPageResponse response){
        int totalLessonInCourse = 0;
        Duration duration = Duration.ZERO;

        List<ChapterReturnDetailResponse> chapterList = response.getChapterList().stream().map(chapter ->
                        modelMapper.map(chapter, ChapterReturnDetailResponse.class)).collect(Collectors.toList());
        response.setTotalChapter(chapterList.size());
        chapterList.sort(Comparator.comparingInt(ChapterReturnDetailResponse::getOrders));
        int i = 1;
        for (ChapterReturnDetailResponse chapter : chapterList){
            List<LessonReturnDetailResponse> listLesson = chapter.getLessonList();
            listLesson.sort(Comparator.comparingInt(LessonReturnDetailResponse::getOrders));
            for (LessonReturnDetailResponse lesson : listLesson){
                if (lesson.getLessonType().equals(LessonType.VIDEO)){
                    Lesson lessonInDB = lessonRepository.findById(lesson.getId()).get();
                    Video video = videoRepository.findById(lessonInDB.getVideo().getId()).get();
                    lesson.setDuration(video.getDuration());

                    duration = duration.plus(
                            Duration.ofMinutes(video.getDuration().getMinute())
                                    .plusSeconds(video.getDuration().getSecond())
                    );
                }else{
                    LocalTime time = LocalTime.of(0,1,0);
                    lesson.setDuration(time);
                    duration= duration.plus(
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
