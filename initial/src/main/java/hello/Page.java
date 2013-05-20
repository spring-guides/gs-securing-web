package hello;

import java.util.*;

public enum Page {
    WELCOME, // a reception page. Also later mapped to '/'
    HOME(true), //
    LOGOUT,
    LOGIN,
    SLASH_ROOT("/", WELCOME.getView());
    private String url;
    private String view;
    private boolean secured;

    Page() {
        String name = name().toLowerCase();
        setup(name, name, false);
    }

    Page(boolean secured) {
        String name = name().toLowerCase();
        setup(name, name, secured);
    }

    Page(String url, String view) {
        setup(url, view, false);
    }

    public static Set<Page> getSecurePages() {
        Set<Page> pagesSet = new HashSet<Page>();
        for (Page p : Page.values())
            if (p.isSecured()) pagesSet.add(p);
        return pagesSet;
    }

    public static Collection<Page> getInsecurePages() {
        Set<Page> pagesSet = new HashSet<Page>();
        for (Page p : Page.values())
            if (!p.isSecured()) pagesSet.add(p);
        return pagesSet;
    }

    public boolean isSecured() {
        return this.secured;
    }

    private void setup(String u, String view, boolean secured) {
        if (!u.startsWith("/")) {
            u = '/' + u;
        }
        this.url = u;
        this.secured = secured;
        this.view = view;
    }

    public String getView() {
        return this.view;
    }

    public String getUrl() {
        return this.url;
    }
}
