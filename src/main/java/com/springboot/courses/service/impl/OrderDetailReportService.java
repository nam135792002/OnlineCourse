package com.springboot.courses.service.impl;

import com.springboot.courses.entity.Order;
import com.springboot.courses.entity.Record;
import com.springboot.courses.payload.dashboard.ReportItem;
import com.springboot.courses.payload.dashboard.ReportType;
import com.springboot.courses.repository.OrderRepository;
import com.springboot.courses.repository.RecordRepository;
import com.springboot.courses.service.AbstractReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderDetailReportService extends AbstractReportService {

    @Autowired private OrderRepository orderRepository;

    @Override
    protected List<ReportItem> getReportDateByDateRangeInternal(Date startTime, Date endTime, ReportType reportType) {
        List<Order> listOrders = null;

        if(reportType.equals(ReportType.CATEGORY)){
            listOrders = orderRepository.findWithCategoryAndTimeBetween(startTime, endTime);
        } else if (reportType.equals(ReportType.COURSE)) {
            listOrders = orderRepository.findWithCourseAndTimeBetween(startTime, endTime);
        }

        List<ReportItem> listReportItems = new ArrayList<>();

        assert listOrders != null;
        for (Order order : listOrders){
            String identifier = "";
            if(reportType.equals(ReportType.CATEGORY)){
                identifier = order.getCourses().getCategory().getName();
            } else {
                identifier = order.getCourses().getTitle();
            }

            ReportItem reportItem = new ReportItem(identifier);
            int totalIncome = order.getTotalPrice();

            int itemIndex = listReportItems.indexOf(reportItem);
            if(itemIndex >= 0){
                reportItem = listReportItems.get(itemIndex);
                reportItem.addIncome(totalIncome);
                reportItem.addOrder();
            }else{
                listReportItems.add(new ReportItem(identifier, totalIncome, 1));
            }

        }

        return listReportItems;
    }
}
