package be.cloudway.easy.reflection.model;

public class SourceConfig {
    private String path;
    private boolean ignoreAnnotations = false;
    private boolean queryDsl = false;

    public SourceConfig() {
    }

    public SourceConfig(String path, boolean ignoreAnnotations, boolean queryDsl) {
        this.path = path;
        this.ignoreAnnotations = ignoreAnnotations;
        this.queryDsl = queryDsl;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isIgnoreAnnotations() {
        return ignoreAnnotations;
    }

    public void setIgnoreAnnotations(boolean ignoreAnnotations) {
        this.ignoreAnnotations = ignoreAnnotations;
    }

    public boolean isQueryDsl() {
        return queryDsl;
    }

    public void setQueryDsl(boolean queryDsl) {
        this.queryDsl = queryDsl;
    }
}
