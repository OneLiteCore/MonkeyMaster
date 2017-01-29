package core.plugin.monkey.core;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author DrkCore
 * @since 2017-01-28
 */
public class Config implements Serializable {
    
    private ArrayList<Builder> builders;
    private File logDir;
    private Builder curBuilder;
    
    public Builder getCurBuilder() {
        return curBuilder;
    }
    
    public Config setCurBuilder(Builder curBuilder) {
        this.curBuilder = curBuilder;
        return this;
    }
    
    public ArrayList<Builder> getBuilders() {
        return builders;
    }
    
    public Config setBuilders(ArrayList<Builder> builders) {
        this.builders = builders;
        return this;
    }
    
    public File getLogDir() {
        return logDir;
    }
    
    public Config setLogDir(File logDir) {
        this.logDir = logDir;
        return this;
    }
}
