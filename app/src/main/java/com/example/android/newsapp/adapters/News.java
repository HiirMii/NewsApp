package com.example.android.newsapp.adapters;

/**
 * Created by HiirMii on 2017-06-27.
 */

/**
 * An {@link News} object contains information related to a single article.
 */
public class News {

    /** Section of the article */
    private String section;

    /** Title of the article */
    private String title;

    /** Release date of the article */
    private String date;

    /** Url to the article's location */
    private String url;

    /**
     * Constructs a new {@link News} object.
     *
     * @param currentSection is the section of the article
     * @param currentTitle is the title of the article
     * @param currentDate is the release date of the article
     * @param currentUrl is the website URL to find more details about the article (full article)
     */
    public News(String currentSection, String currentTitle, String currentDate, String currentUrl) {
        section =currentSection;
        title = currentTitle;
        date = currentDate;
        url = currentUrl;
    }

    /**
     * Returns the section of the article.
     */
    public String getSection() {
        return section;
    }

    /**
     * Returns the title of the article.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the date of the article.
     */
    public String getDate() {
        return date;
    }

    /**
     * Returns the url to find more information about the article (full article).
     */
    public String getUrl() {
        return url;
    }
}
