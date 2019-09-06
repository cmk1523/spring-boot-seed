package com.techdevsolutions.shared.etl;

import com.techdevsolutions.shared.beans.CSV;
import com.techdevsolutions.shared.beans.CSVRow;
import com.techdevsolutions.shared.beans.CollectionEvent;
import com.techdevsolutions.shared.beans.ValidationResponse;
import com.techdevsolutions.shared.beans.etl.ETL;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectionEventETL implements ETL<CSV, CollectionEvent> {
    @Override
    public CSV acquire() {
        return null;
    }

    @Override
    public List<CollectionEvent> convert(CSV csv) {
        List<String> lines = CSV.splitLines(csv.getData());
        Map<String, Integer> map = csv.getColumnsToPositionMap();

        return lines.stream()
                .filter(StringUtils::isNotEmpty)
                .map((i)->{
                    List<String> columns = CSV.splitRow(i);

                    CollectionEvent j = new CollectionEvent();
                    j.setSelector(columns.get(map.get("selector")));
                    j.setSelectorType(columns.get(map.get("selectorType")));
                    String dateAsStr = columns.get(map.get("date"));

                    if (StringUtils.isNotEmpty(dateAsStr)) {
                        j.setDate(new Date(Long.valueOf(dateAsStr)));
                    }

                    return j;
                }).collect(Collectors.toList());
    }

    @Override
    public List<CollectionEvent> normalize(List<CollectionEvent> items) {
        return items.stream().map((i)->{
            if (StringUtils.isNotEmpty(i.getSelector())) {
                i.setSelector(i.getSelector().trim().replaceAll("[.]|[-]|[:]", ""));
            }

            if (StringUtils.isNotEmpty(i.getSelectorType())) {
                i.setSelectorType(i.getSelectorType().trim().replaceAll("[.]|[-]|[:]", ""));
            }

            return i;
        }).collect(Collectors.toList());
    }

    @Override
    public List<ValidationResponse> validate(List<CollectionEvent> items) {
        return items.stream().map(CollectionEvent::Validate).collect(Collectors.toList());
    }

    @Override
    public void store(List<CollectionEvent> items) {
        items.stream().forEach((i->{
            System.out.println(i);
        }));
    }

}
