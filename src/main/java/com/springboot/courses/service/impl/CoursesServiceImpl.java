package com.springboot.courses.service.impl;

import com.springboot.courses.entity.Category;
import com.springboot.courses.entity.Courses;
import com.springboot.courses.exception.BlogApiException;
import com.springboot.courses.exception.ResourceNotFoundException;
import com.springboot.courses.payload.CategoryDto;
import com.springboot.courses.payload.ClassResponse;
import com.springboot.courses.payload.course.CourseResponse;
import com.springboot.courses.payload.course.CoursesRequest;
import com.springboot.courses.repository.CategoryRepository;
import com.springboot.courses.repository.CoursesRepository;
import com.springboot.courses.service.CoursesService;
import com.springboot.courses.utils.UploadImage;
import com.springboot.courses.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CoursesServiceImpl implements CoursesService {

    @Autowired private CoursesRepository coursesRepository;
    @Autowired private ModelMapper modelMapper;
    @Autowired private UploadImage uploadImage;
    @Autowired private CategoryRepository categoryRepository;

    @Override
    public CourseResponse createCourse(CoursesRequest coursesRequest, MultipartFile image) {

        if(coursesRepository.existsCoursesByTitle(coursesRequest.getTitle())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Course name have existed!");
        }

        if(coursesRepository.existsCoursesBySlug(coursesRequest.getSlug())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Course slug have existed!");
        }

        Category category = categoryRepository.findById(coursesRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", coursesRequest.getCategoryId()));

        Courses courses = modelMapper.map(coursesRequest, Courses.class);

        String slug = Utils.removeVietnameseAccents(coursesRequest.getTitle());
        courses.setSlug(slug);

        String thumbnail = uploadImage.uploadImageOnCloudinary(image);
        courses.setThumbnail(thumbnail);

        courses.setCategory(category);

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

        List<Courses> listCourses = courses.getContent();

        List<CourseResponse> content = listCourses.stream().map(course -> modelMapper.map(course, CourseResponse.class)).collect(Collectors.toList());

        return ClassResponse.convertToClassResponse(courses, content);
    }

    @Override
    public CourseResponse get(Integer courseId) {
        Courses course = coursesRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", courseId));

        return modelMapper.map(course, CourseResponse.class);
    }

    @Override
    public CourseResponse update(Integer courseId, CoursesRequest coursesRequest, MultipartFile img) {
        Courses courseInDB = coursesRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", courseId));

        Category categoryInDB = categoryRepository.findById(coursesRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", coursesRequest.getCategoryId()));

        Courses courses = coursesRepository.findByTitleOrSlug(coursesRequest.getTitle(), coursesRequest.getSlug());
        if(!Objects.equals(courses.getId(), courseInDB.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Title/Slug have been duplicated!");
        }

        if(img != null){
            uploadImage.deleteImageInCloudinary(courseInDB.getThumbnail());
            String url = uploadImage.uploadImageOnCloudinary(img);
            courseInDB.setThumbnail(url);
        }

        courseInDB.setTitle(coursesRequest.getTitle());
        String slug = Utils.removeVietnameseAccents(coursesRequest.getTitle());
        courseInDB.setSlug(slug);
        courseInDB.setCategory(categoryInDB);
        courseInDB.setDescription(coursesRequest.getDescription());
        courseInDB.setPrice(coursesRequest.getPrice());
        courseInDB.setDiscount(coursesRequest.getDiscount());
        courseInDB.setStudentCount(coursesRequest.getStudentCount());
        courseInDB.setComingSoon(coursesRequest.isComingSoon());
        courseInDB.setPublished(coursesRequest.isPublished());
        if(coursesRequest.isPublished()){
            courseInDB.setPublishedAt(coursesRequest.getPublishedAt());
        }

        Courses savedCourse = coursesRepository.save(courseInDB);

        return modelMapper.map(savedCourse, CourseResponse.class);
    }

    @Override
    public String delete(Integer courseId) {
        Courses courseInDB = coursesRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", courseId));

        uploadImage.deleteImageInCloudinary(courseInDB.getThumbnail());

        coursesRepository.delete(courseInDB);

        return "Delete course successfully!";
    }
}
