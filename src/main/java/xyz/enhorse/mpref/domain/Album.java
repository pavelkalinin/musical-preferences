package xyz.enhorse.mpref.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.enhorse.mpref.json.JsonCompatible;
import xyz.enhorse.mpref.json.JsonString;

import javax.validation.constraints.NotNull;

/**
 * Entity class represents Album with {@code Artist}, {@code ReleaseDate} and {@code Cover}
 *
 * @author <a href="mailto:go2amd@gmail.com">Pavel Kalinin</a>
 *         17.12.2015
 */
public class Album implements JsonCompatible {
    private static final Logger LOGGER = LoggerFactory.getLogger(Album.class);

    @NotNull
    private Artist artist;
    @NotNull
    private String name;
    @NotNull
    private ReleaseDate releaseDate;
    private Cover cover;

    public Album(Artist artist,
                 String name,
                 ReleaseDate releaseDate,
                 Cover cover) throws IllegalArgumentException {
        setArtist(artist);
        setName(name);
        setReleaseDate(releaseDate);
        setCover(cover);
    }


    public Album(JsonString json) throws IllegalArgumentException {
        setFromJsonString(json);
    }


    private void setFromJsonString(JsonString json) throws IllegalArgumentException {
        if (json == null) throw new IllegalArgumentException("JsonString of Album cannot be null!");

        Album value = json.convertTo(getClass());
        if (value == null) throw new IllegalArgumentException("JsonString \"" + json.value()
                + "\" cannot be applicable to " + getClass().getSimpleName());

        setArtist(value.getArtist());
        setName(value.getName());
        setReleaseDate(value.getReleaseDate());
        setCover(value.getCover());
    }


    public Artist getArtist() {
        return artist;
    }


    public void setArtist(Artist value) throws IllegalArgumentException {
        if (value == null) {
            throw new IllegalArgumentException("Artist cannot be null!");
        }
        artist = value;
    }


    public String getName() {
        return name;
    }


    public void setName(String value) throws IllegalArgumentException {
        if (value == null) throw new IllegalArgumentException("Album name cannot be null!");
        if (value.trim().length() == 0) throw new IllegalArgumentException("Album name cannot be empty!");

        name = value;
    }


    public ReleaseDate getReleaseDate() {
        return releaseDate;
    }


    public void setReleaseDate(ReleaseDate value) throws IllegalArgumentException {
        if (value == null) throw new IllegalArgumentException("Album release date cannot be null!");

        releaseDate = value;
    }


    public Cover getCover() {
        return cover;
    }


    public void setCover(Cover value) {
        cover = value;
    }


    public String description() {
        return String.format("%s-%s(%d)",
                getArtist().getName(),
                getName(),
                getReleaseDate().getValue().getYear());
    }


    @Override
    public String toString() {
        return String.format("%s \"%s\" (%s) [%s]", getArtist(), getName(), getReleaseDate(), getCover());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Album album = (Album) o;

        return getArtist().equals(album.getArtist())
                && getName().equalsIgnoreCase(album.getName())
                && getReleaseDate().equals(album.getReleaseDate());
    }


    @Override
    public int hashCode() {
        int result = getArtist().hashCode();
        result = 31 * result + getName().toLowerCase().hashCode();
        result = 31 * result + getReleaseDate().hashCode();
        return result;
    }


    @Override
    public JsonString toJsonString() {
        return JsonString.generateFor(this);
    }

    private Album() {} //for Jackson JSON engine
}
