package main.project.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class PageInfo {
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

    public static PageInfo of(Page page) {

        return PageInfo.builder()
                .page(page.getNumber() + 1)
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }
}