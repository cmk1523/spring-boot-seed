package com.techdevsolutions.shared.beans;

import java.util.Objects;

public class CSVRow extends CSV {
    private Integer rowNumber;

    public CSVRow() {
    }

    @Override
    public String toString() {
        return "CSVRow{" +
                "rowNumber=" + rowNumber +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CSVRow)) return false;
        if (!super.equals(o)) return false;
        CSVRow csvRow = (CSVRow) o;
        return Objects.equals(rowNumber, csvRow.rowNumber);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), rowNumber);
    }

    public Integer getRowNumber() {
        return rowNumber;
    }

    public CSVRow setRowNumber(Integer rowNumber) {
        this.rowNumber = rowNumber;
        return this;
    }
}
