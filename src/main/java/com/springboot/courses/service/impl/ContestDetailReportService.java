package com.springboot.courses.service.impl;

import com.springboot.courses.entity.Contest;
import com.springboot.courses.entity.Record;
import com.springboot.courses.payload.dashboard.ReportByContest;
import com.springboot.courses.payload.dashboard.ReportItem;
import com.springboot.courses.payload.dashboard.ReportType;
import com.springboot.courses.repository.RecordRepository;
import com.springboot.courses.service.AbstractReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ContestDetailReportService extends AbstractReportService {

    @Autowired private RecordRepository recordRepository;

    @Override
    protected List<ReportByContest> getReportDateByDateRangeInternal(Date startTime, Date endTime, ReportType reportType) {
        List<Record> listRecord = recordRepository.findWithRecordOfContestAndTimeBetween(startTime, endTime);
        List<ReportByContest> listReportByContests = new ArrayList<>();

        for (Record record : listRecord){
            String identifier = record.getContest().getTitle();

            ReportByContest reportItem = new ReportByContest(identifier);
            int itemIndex = listReportByContests.indexOf(reportItem);
            if(itemIndex >= 0){
                reportItem = listReportByContests.get(itemIndex);
                reportItem.addNumberOfJoined();
                reportItem.addToTalGrade(record.getGrade());
            }else {
                listReportByContests.add(new ReportByContest(identifier, 1, record.getGrade()));
            }
        }

        listReportByContests.forEach(ReportByContest::calculateAverageGrade);

        return listReportByContests;
    }
}
