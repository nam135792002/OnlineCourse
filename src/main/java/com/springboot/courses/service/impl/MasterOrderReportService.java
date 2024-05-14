package com.springboot.courses.service.impl;

import com.springboot.courses.entity.Order;
import com.springboot.courses.payload.dashboard.ReportItem;
import com.springboot.courses.payload.dashboard.ReportType;
import com.springboot.courses.repository.OrderRepository;
import com.springboot.courses.service.AbstractReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class MasterOrderReportService extends AbstractReportService {

    @Autowired private OrderRepository orderRepository;

    @Override
    protected List<ReportItem> getReportDateByDateRangeInternal(Date startTime, Date endTime, ReportType reportType) {
        List<Order> listOrders = orderRepository.findByOrderTimeBetween(startTime, endTime);
        List<ReportItem> listReportItems = createReportDate(startTime, endTime, reportType);
        calculateSalesForReportDate(listOrders, listReportItems);
        
        return listReportItems;
    }

    private void calculateSalesForReportDate(List<Order> listOrders, List<ReportItem> listReportItems) {
        for(Order order : listOrders){
            String orderDateString = dateFormat.format(order.getCreatedTime());
            ReportItem reportItem = new ReportItem(orderDateString);
            int itemIndex = listReportItems.indexOf(reportItem);
            if (itemIndex >= 0){
                reportItem = listReportItems.get(itemIndex);
                reportItem.addIncome(order.getTotalPrice());
            }
        }
    }

    private List<ReportItem> createReportDate(Date startTime, Date endTime, ReportType reportType) {
        List<ReportItem> listReportItems = new ArrayList<>();

        Calendar startDate = Calendar.getInstance();
        startDate.setTime(startTime);

        Calendar endDate = Calendar.getInstance();
        endDate.setTime(endTime);

        Date currentDate = startDate.getTime();
        String dateString = dateFormat.format(currentDate);
        listReportItems.add(new ReportItem(dateString));

        do{
            if (reportType.equals(ReportType.DAY)){
                startDate.add(Calendar.DAY_OF_MONTH, 1);
            } else if (reportType.equals(ReportType.MONTH)) {
                startDate.add(Calendar.MONTH, 1);
            }

            currentDate = startDate.getTime();
            dateString = dateFormat.format(currentDate);
            listReportItems.add(new ReportItem(dateString));
        }while (startDate.before(endDate));

        return listReportItems;
    }
    
    
}
