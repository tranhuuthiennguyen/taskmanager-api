package com.thiennth.taskmanager.repository;

import java.util.List;
import java.util.Map;

import com.thiennth.taskmanager.model.Tag;

public interface TagRepository {
    void saveTagsByTaskId(Long taskId, List<Long> tagIds);
    Map<Long, List<Tag>> getListByTaskIds(List<Long> taskIds);
    List<Tag> getListByTaskId(Long taskId);
    List<Tag> getList(List<Long> tagIds);
}
