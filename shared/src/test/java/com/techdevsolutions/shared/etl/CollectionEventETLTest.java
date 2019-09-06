package com.techdevsolutions.shared.etl;

import com.techdevsolutions.shared.beans.CSV;
import com.techdevsolutions.shared.beans.CollectionEvent;
import com.techdevsolutions.shared.beans.ValidationResponse;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CollectionEventETLTest {

    @Test
    public void test() {

        String csvData = "";
        csvData += "1,123,type 1,12345678901";
        csvData += "2,456,type 2";
        csvData += "3,789,type 1,12345678903";

        CSV csv = new CSV();
        csv.setData(csvData);

        csv.getColumnsToPositionMap().put("id", 0);
        csv.getColumnsToPositionMap().put("selector", 1);
        csv.getColumnsToPositionMap().put("selectorType", 2);
        csv.getColumnsToPositionMap().put("date", 3);

        CollectionEventETL i = new CollectionEventETL();
        List<CollectionEvent> acquired = i.convert(csv);
        List<CollectionEvent> normalized = i.normalize(acquired);
        List<ValidationResponse> validated = i.validate(normalized);
        List<CollectionEvent> valid = new ArrayList<>();

        for (int j = 0; j < normalized.size(); j++) {
            CollectionEvent a = normalized.get(j);
            ValidationResponse b = validated.get(j);

            if (b.getValid()) {
                valid.add(a);
            }
        }

        i.store(valid);
    }
}
