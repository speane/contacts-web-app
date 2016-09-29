package com.evgenyshilov.web.contacts.help.transfer;

/**
 * Created by Evgeny Shilov on 13.09.2016.
 */
public class PaginationFactory {

    public PaginationDTO createPagination(int totalItems, int itemsPerPage, int activePage) {
        final int SIDE_PAGES_NUMBER = 5;
        PaginationDTO paginationDTO = new PaginationDTO();

        int totalPages = (totalItems != 0)
                ? (totalItems / itemsPerPage + ((totalItems % itemsPerPage == 0) ? 0 : 1))
                : 1;
        activePage = ((activePage > 0) && (activePage <= totalPages)) ? activePage : 1;

        int startPage = ((activePage - SIDE_PAGES_NUMBER) >= 1) ? (activePage - SIDE_PAGES_NUMBER) : 1;
        int endPage = ((activePage + SIDE_PAGES_NUMBER) <= totalPages) ? (activePage + SIDE_PAGES_NUMBER) : totalPages;

        paginationDTO.setActivePage(activePage);
        paginationDTO.setStartPage(startPage);
        paginationDTO.setEndPage(endPage);

        return paginationDTO;
    }
}
