package com.springboot.courses.controller;

import com.springboot.courses.payload.dashboard.ReportType;
import com.springboot.courses.service.AbstractReportService;
import com.springboot.courses.service.ReportService;
import com.springboot.courses.service.impl.ContestDetailReportService;
import com.springboot.courses.service.impl.MasterOrderReportService;
import com.springboot.courses.service.impl.OrderDetailReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @Autowired private OrderDetailReportService orderDetailReportService;
    @Autowired private ContestDetailReportService contestDetailReportService;

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

    @GetMapping("/sales/{groupBy}/{period}")
    public ResponseEntity<?> getReportDataByCategoryOrCourseOrContest(@PathVariable(value = "groupBy") String groupBy,
                                                                      @PathVariable(value = "period") String period) {
        try {
            ReportType reportType = ReportType.valueOf(groupBy.toUpperCase());

            if (reportType.equals(ReportType.CATEGORY) || reportType.equals(ReportType.COURSE)) {
                return getResponseEntity(orderDetailReportService, reportType, period);
            } else if (reportType.equals(ReportType.CONTEST)) {
                return getResponseEntity(contestDetailReportService, reportType, period);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sai kiểu báo cáo.");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sai kiểu báo cáo");
        }
    }

    private ResponseEntity<?> getResponseEntity(AbstractReportService reportService, ReportType reportType, String period) {
        return switch (period) {
            case "last_28_days" -> ResponseEntity.ok(reportService.getReportDateLast28Days(reportType));
            case "last_6_months" -> ResponseEntity.ok(reportService.getReportDateLast6Months(reportType));
            case "last_year" -> ResponseEntity.ok(reportService.getReportDateLastYear(reportType));
            default -> ResponseEntity.ok(reportService.getReportDateLast7Days(reportType));
        };
    }
}
