package com.springboot.courses.payload.dashboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReportItem {

    private String identifier;

    @JsonProperty("total_income")
    private int totalIncome;

    @JsonProperty("order_count")
    private int orderCount;

    public ReportItem(String identifier) {
        this.identifier = identifier;
    }

    public void addIncome(int income){
        this.totalIncome += income;
    }

    public void addOrder(){
        ++this.orderCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReportItem that)) return false;
        return Objects.equals(getIdentifier(), that.getIdentifier());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdentifier());
    }
}
