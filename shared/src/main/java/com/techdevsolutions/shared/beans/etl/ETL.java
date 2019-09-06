package com.techdevsolutions.shared.beans.etl;

import com.techdevsolutions.shared.beans.ValidationResponse;

import java.util.List;

public interface ETL<CSV, G> {
    CSV acquire();
    List<G> convert(CSV item);
    List<G> normalize(List<G> item);
    List<ValidationResponse> validate(List<G> item);
    void store(List<G> items);
}
