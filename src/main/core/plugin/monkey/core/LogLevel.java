package core.plugin.monkey.core;

/**
 * @author DrkCore
 * @since 2017-01-29
 */
public enum LogLevel {
    
    SIMPLE("-v"), NORMAL("-v -v"), ALL("-v -v -v");
    
    private final String params;
    
    public String getParams() {
        return params;
    }
    
    private LogLevel(String params) {
        this.params = params;
    }
}
