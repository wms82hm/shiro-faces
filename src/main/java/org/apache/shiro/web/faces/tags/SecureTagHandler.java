package org.apache.shiro.web.faces.tags;

import com.sun.facelets.tag.TagConfig;
import com.sun.facelets.tag.TagHandler;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for all TagHandlers
 * 
 * @author Deluan Quintao
 */
public abstract class SecureTagHandler extends TagHandler {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    public SecureTagHandler(TagConfig config) {
        super(config);
    }

    protected Subject getSubject() {
        return SecurityUtils.getSubject();
    }

}