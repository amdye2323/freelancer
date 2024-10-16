package org.easy.subject.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDto<T> {
    private Long startId;
    private Long pageSize;
    private Long totalSize;
    private List<T> data;
}
