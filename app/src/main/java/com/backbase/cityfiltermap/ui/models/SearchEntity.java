package com.backbase.cityfiltermap.ui.models;

public class SearchEntity {

    private String country;
    private String name;
    private Long _id;
    private SearchCoordinates coord;

    public SearchEntity() {
    }

    public SearchEntity(String country, String name) {
        this.country = country;
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public SearchCoordinates getCoord() {
        return coord;
    }

    public void setCoord(SearchCoordinates coord) {
        this.coord = coord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SearchEntity entity = (SearchEntity) o;

        if (country != null ? !country.equals(entity.country) : entity.country != null) {
            return false;
        }
        if (name != null ? !name.equals(entity.name) : entity.name != null) {
            return false;
        }

        return _id != null ? _id.equals(entity._id) : entity._id == null;
    }

    @Override
    public int hashCode() {
        int result = country != null ? country.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (_id != null ? _id.hashCode() : 0);

        return result;
    }
}
