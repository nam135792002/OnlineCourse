package vn.edu.ut.service.impl;

import vn.edu.ut.entity.Order;
import vn.edu.ut.payload.dashboard.ReportItem;
import vn.edu.ut.payload.dashboard.ReportType;
import vn.edu.ut.repository.OrderRepository;
import vn.edu.ut.service.AbstractReportService;
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
