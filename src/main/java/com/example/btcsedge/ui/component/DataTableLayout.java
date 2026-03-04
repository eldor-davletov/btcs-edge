package com.example.btcsedge.ui.component;

import com.example.btcsedge.dto.UserDto;
import com.example.btcsedge.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import org.springframework.data.domain.PageRequest;

public class DataTableLayout extends VerticalLayout {

    private static final int PAGE_SIZE = 10;

    private final Grid<UserDto> grid = new Grid<>(UserDto.class, false);
    private final UserService userService;

    public DataTableLayout(UserService userService) {
        this.userService = userService;
        setSizeFull();
        configureGrid();
        add(grid);
    }

    private void configureGrid() {
        grid.addColumn(UserDto::getId).setHeader("ID").setWidth("80px").setFlexGrow(0);
        grid.addColumn(UserDto::getUsername).setHeader("Username").setSortable(true);
        grid.addColumn(UserDto::getEmail).setHeader("Email").setSortable(true);
        grid.addColumn(dto -> String.join(", ", dto.getRoles())).setHeader("Roles");
        grid.setSizeFull();

        grid.setDataProvider(DataProvider.fromCallbacks(
                query -> {
                    int page = query.getOffset() / query.getLimit();
                    return userService.getPage(PageRequest.of(page, query.getLimit())).stream();
                },
                query -> (int) userService.countUsers()
        ));
    }

    public void refresh() {
        grid.getDataProvider().refreshAll();
    }
}
