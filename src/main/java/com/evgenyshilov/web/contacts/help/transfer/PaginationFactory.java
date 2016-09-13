package com.evgenyshilov.web.contacts.help.transfer;

/**
 * Created by Evgeny Shilov on 13.09.2016.
 */
public class PaginationFactory {

    public static PaginationDTO createPagination(int totalItems, int itemsPerPage, int activePage) {
        PaginationDTO paginationDTO = new PaginationDTO();

        int totalPages = (totalItems != 0)
                ? (totalItems / itemsPerPage + ((totalItems % itemsPerPage == 0) ? 0 : 1))
                : 1;
        activePage = ((activePage > 0) && (activePage <= totalPages)) ? activePage : 1;

        int startPage = ((activePage - 5) >= 1) ? (activePage - 5) : 1;
        int endPage = ((activePage + 5) <= totalPages) ? (activePage + 5) : totalPages;

        paginationDTO.setActivePage(activePage);
        paginationDTO.setStartPage(startPage);
        paginationDTO.setEndPage(endPage);

        return paginationDTO;
    }
}
