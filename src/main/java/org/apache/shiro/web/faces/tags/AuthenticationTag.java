package org.apache.shiro.web.faces.tags;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.TagConfig;

import javax.el.ELException;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import java.io.IOException;

/**
 * Base TagHandler for Tags that check for authentication. 
 *
 * @author Deluan
 */
public abstract class AuthenticationTag extends SecureTag {

    public AuthenticationTag(TagConfig config) {
        super(config);
    }

    public void apply(FaceletContext ctx, UIComponent parent) throws IOException, FacesException, ELException {
        if (showTagBody()) {
            this.nextHandler.apply(ctx, parent);
        }
    }

    protected abstract boolean checkAuthentication();

    protected boolean showTagBody() {
        if (checkAuthentication()) {
            if (log.isTraceEnabled()) {
                log.trace("Subject does not exist or does not have a known identity (aka \'principal\').  " + "Tag body will be evaluated.");
            }
            return true;
        } else {
            if (log.isTraceEnabled()) {
                log.trace("Subject exists or has a known identity (aka \'principal\').  " + "Tag body will not be evaluated.");
            }
            return false;
        }
    }

}