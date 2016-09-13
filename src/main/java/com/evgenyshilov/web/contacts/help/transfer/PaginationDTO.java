package com.evgenyshilov.web.contacts.help.transfer;

import java.io.Serializable;

/**
 * Created by Evgeny Shilov on 13.09.2016.
 */
public class PaginationDTO implements Serializable {

    private int startPage;
    private int activePage;
    private int endPage;

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getActivePage() {
        return activePage;
    }

    public void setActivePage(int activePage) {
        this.activePage = activePage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }
}
