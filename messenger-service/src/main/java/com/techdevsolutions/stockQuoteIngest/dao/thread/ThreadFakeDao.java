package com.techdevsolutions.files.dao.thread;

import com.techdevsolutions.files.beans.auditable.Thread;
import com.techdevsolutions.shared.beans.Filter;
import com.techdevsolutions.shared.dao.DaoCrudInterface;
import com.techdevsolutions.shared.service.HashUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ThreadFakeDao implements DaoCrudInterface<Thread> {
    List<Thread> items = new ArrayList<>();

    @Override
    public List<Thread> search(Filter filter) throws Exception {
        List<Thread> list = this.items.stream().filter((i)->!i.getRemoved()).collect(Collectors.toList());

        if (StringUtils.isNotEmpty(filter.getTerm())) {
            list = list.stream().filter((i) -> i.toString().contains(filter.getTerm()))
                    .collect(Collectors.toList());
        }

        if (StringUtils.isNotEmpty(filter.getSort()) || StringUtils.isNotEmpty(filter.getOrder())) {
            list = list.stream().sorted(new Comparator<Thread>() {
                @Override
                public int compare(Thread o1, Thread o2) {
                    int i = 0;

                    if (filter.getSort().equalsIgnoreCase("id")) {
                        i = o1.getId().compareTo(o2.getId());
                    } else if (filter.getSort().equalsIgnoreCase("name")) {
                        i = o1.getName().compareTo(o2.getName());
                    } else if (filter.getSort().equalsIgnoreCase("createdBy")) {
                        i = o1.getCreatedBy().compareTo(o2.getCreatedBy());
                    } else if (filter.getSort().equalsIgnoreCase("updatedBy")) {
                        i = o1.getUpdatedBy().compareTo(o2.getUpdatedBy());
                    } else if (filter.getSort().equalsIgnoreCase("createdDate")) {
                        i = o1.getCreatedDate().compareTo(o2.getCreatedDate());
                    } else if (filter.getSort().equalsIgnoreCase("updatedDate")) {
                        i = o1.getUpdatedDate().compareTo(o2.getUpdatedDate());
                    } else if (filter.getSort().equalsIgnoreCase("removed")) {
                        i = o1.getRemoved().compareTo(o2.getRemoved());
                    } else if (filter.getSort().equalsIgnoreCase("key")) {
                        i = o1.getKey().compareTo(o2.getKey());
                    }

                    return filter.getOrder().equalsIgnoreCase(Filter.SORT_ASC) ? i : -1;
                }
            }).collect(Collectors.toList());
        }

        if (filter.getSize() != null) {
            return list.subList(0, filter.getSize() <= list.size() ? filter.getSize() : list.size());
        }

        return list;
    }

    @Override
    public Thread get(String id) throws Exception {
        Optional<Thread> item = this.items.stream().filter((i)->i.getId().equalsIgnoreCase(id)).findFirst();

        if (item.isPresent()) {
            return item.get();
        } else {
            throw new NoSuchElementException("Unable to get. Thread not found by ID: " + id);
        }
    }

    @Override
    public Thread create(Thread item) throws Exception {
        item.setId(HashUtils.md5(item.toString()));

        try {
            this.get(item.getId());
            throw new IllegalArgumentException("Item already exists with ID: " + item.getId());
        } catch (NoSuchElementException e) {
            this.items.add(item);
            return this.get(item.getId());
        }
    }

    @Override
    public void remove(String id) throws Exception {
        Thread item = this.get(id);
        item.setRemoved(true);
        this.update(item);
    }

    @Override
    public void delete(String id) throws Exception {
        this.items = this.items.stream().filter((i)->!i.getId().equalsIgnoreCase(id)).collect(Collectors.toList());
    }

    @Override
    public Thread update(Thread item) throws Exception {
        this.items = this.items.stream().map((i)->{
            if (item.getId().equalsIgnoreCase(i.getId())) {
                return item;
            } else {
                return i;
            }
        }).collect(Collectors.toList());
        return item;
    }
}
