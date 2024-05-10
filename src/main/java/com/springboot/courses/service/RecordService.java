package com.springboot.courses.service;

import com.springboot.courses.payload.record.RecordRequest;
import com.springboot.courses.payload.record.RecordResponse;

import java.util.List;

public interface RecordService{
    RecordResponse saveRecord(RecordRequest recordRequest);
    List<RecordResponse> listAllRecord(Integer userId);
}
