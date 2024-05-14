package com.springboot.courses.controller;

import com.springboot.courses.payload.dashboard.ReportType;
import com.springboot.courses.service.ReportService;
import com.springboot.courses.service.impl.MasterOrderReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    @Autowired private ReportService reportService;
    @Autowired private MasterOrderReportService masterOrderReportService;

    @GetMapping("/count")
    public ResponseEntity<?> count(){
        return ResponseEntity.ok(reportService.count());
    }

    @GetMapping("/sales-income/{period}")
    public ResponseEntity<?> getReportDateByDatePeriod(@PathVariable(value = "period") String period){
        return switch (period) {
            case "last_28_days" -> ResponseEntity.ok(masterOrderReportService.getReportDateLast28Days(ReportType.DAY));
            case "last_6_months" ->
                    ResponseEntity.ok(masterOrderReportService.getReportDateLast6Months(ReportType.MONTH));
            case "last_year" -> ResponseEntity.ok(masterOrderReportService.getReportDateLastYear(ReportType.MONTH));
            default -> ResponseEntity.ok(masterOrderReportService.getReportDateLast7Days(ReportType.DAY));
        };
    }
}
