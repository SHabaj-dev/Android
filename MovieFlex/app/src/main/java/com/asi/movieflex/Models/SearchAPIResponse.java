package com.asi.movieflex.Models;

import java.util.List;

public class SearchAPIResponse {

    List<SearchArrayObjects> titles = null;
    List<SearchArrayObjects> names = null;
    List<SearchArrayObjects> companies = null;


    public List<SearchArrayObjects> getTitles() {
        return titles;
    }

    public void setTitles(List<SearchArrayObjects> titles) {
        this.titles = titles;
    }

    public List<SearchArrayObjects> getNames() {
        return names;
    }

    public void setNames(List<SearchArrayObjects> names) {
        this.names = names;
    }

    public List<SearchArrayObjects> getCompanies() {
        return companies;
    }

    public void setCompanies(List<SearchArrayObjects> companies) {
        this.companies = companies;
    }
}
