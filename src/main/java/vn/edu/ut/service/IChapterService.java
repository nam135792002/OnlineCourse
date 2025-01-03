package vn.edu.ut.service;

import vn.edu.ut.payload.chapter.ChapterDto;

public interface IChapterService {
    ChapterDto createChapter(Integer courseId, ChapterDto chapterDto);

    ChapterDto updateChapter(Integer courseId, Integer chapterId, ChapterDto chapterDto);

    void deleteChapter(Integer courseId, Integer chapterId);
}
