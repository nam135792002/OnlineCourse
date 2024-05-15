package com.springboot.courses.payload.dashboard;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class ReportByContest {

    private String identifier;

    @JsonProperty("average_grade")
    private float averageGrade;

    @JsonProperty("number_of_joined")
    private int numberOfJoined;

    @JsonIgnore
    private float totalGrade;

    public ReportByContest(String identifier) {
        this.identifier = identifier;
    }

    public ReportByContest(String identifier, int numberOfJoined, float totalGrade) {
        this.identifier = identifier;
        this.numberOfJoined = numberOfJoined;
        this.totalGrade = totalGrade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReportByContest that)) return false;
        return Objects.equals(getIdentifier(), that.getIdentifier());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdentifier());
    }

    public void addNumberOfJoined(){
        ++this.numberOfJoined;
    }

    public void addToTalGrade(float grade){
        this.totalGrade += grade;
    }

    public void calculateAverageGrade(){
        float averageTmp = this.totalGrade / this.numberOfJoined;
        this.averageGrade = (float) (Math.round(averageTmp * 100.0) / 100.0);
    }
}
