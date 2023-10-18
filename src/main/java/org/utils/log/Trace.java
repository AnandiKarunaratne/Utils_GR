package org.utils.log;

import java.util.ArrayList;
import java.util.Collection;

public class Trace extends ArrayList<String> {

    public Trace() {
        super();
    }

    public Trace(Collection<? extends String> c) {
        super(c);
    }

    private static final long serialVersionUID = 8117097524302408331L;

}
