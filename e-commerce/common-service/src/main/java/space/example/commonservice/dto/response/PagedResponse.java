package space.example.commonservice.dto.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class PagedResponse<T> {
    private List<T> elements;
    private int totalPages;
    private long totalElements;
    private boolean hasNext;
    private boolean hasPrevious;

}