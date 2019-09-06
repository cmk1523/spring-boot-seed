package com.techdevsolutions.shared.beans;

import java.util.*;

public class CSV {
    public static String CSV_SPLIT_REGEX = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";

    private Map<String, Integer> columnsToPositionMap = new HashMap<>();
    private String data;

    public CSV() {
    }

    public static List<String> splitLines(String i) {
        return new ArrayList<>(Arrays.asList(i.split("\\r?\\n")));
    }

    public static List<String> splitRow(String i) {
        return new ArrayList<>(Arrays.asList(i.split(CSV.CSV_SPLIT_REGEX, -1)));
    }

    @Override
    public String toString() {
        return "CSV{" +
                "columnsToPositionMap=" + columnsToPositionMap +
                ", data='" + data + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CSV)) return false;
        CSV csv = (CSV) o;
        return Objects.equals(columnsToPositionMap, csv.columnsToPositionMap) &&
                Objects.equals(data, csv.data);
    }

    @Override
    public int hashCode() {

        return Objects.hash(columnsToPositionMap, data);
    }

    public Map<String, Integer> getColumnsToPositionMap() {
        return columnsToPositionMap;
    }

    public CSV setColumnsToPositionMap(Map<String, Integer> columnsToPositionMap) {
        this.columnsToPositionMap = columnsToPositionMap;
        return this;
    }

    public String getData() {
        return data;
    }

    public CSV setData(String data) {
        this.data = data;
        return this;
    }
}
