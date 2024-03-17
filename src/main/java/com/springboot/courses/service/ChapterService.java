package com.springboot.courses.service;

import com.springboot.courses.payload.ChapterDto;

public interface ChapterService {
    ChapterDto createChapter(Integer courseId, ChapterDto chapterDto);
    ChapterDto updateChapter(Integer courseId, Integer chapterId, ChapterDto chapterDto);
    String deleteChapter(Integer courseId, Integer chapterId);
}
