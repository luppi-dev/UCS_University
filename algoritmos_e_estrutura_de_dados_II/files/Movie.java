package files;

import java.util.List;

public class Movie {

    private Integer id;
    private String name;
    private Integer year;
    private List<Director> directors;

    public Movie(int id, String name, Integer year, List<Director> directors) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.directors = directors;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public List<Director> getDirectors() {
        return directors;
    }

    public void setDirectors(List<Director> directorId) {
        this.directors = directorId;
    }


    @Override
    public String toString() {
        return "ID: " + this.id + " | " + "Nome: " + this.name + " | " + "Ano: " + this.year;
    }

}
