package org.apache.shiro.web.faces.tags;

import java.io.IOException;

import javax.el.ELException;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagConfig;

/**
 * Base TagHandler for Tags that check for authentication.
 *
 * @author Deluan Quintao
 */
public abstract class AuthenticationTagHandler extends SecureTagHandler
{
	/**
	 * Creates the base TagHandler for tags that checks for authentication.
	 *
	 * @param config
	 *            The tag configuration containing document definition for the tag handler.
	 */
	public AuthenticationTagHandler(TagConfig config)
	{
		super(config);
	}

	@Override
	public void apply(FaceletContext ctx, UIComponent parent) throws IOException, FacesException, ELException
	{
		if (showTagBody())
		{
			this.nextHandler.apply(ctx, parent);
		}
	}

	protected abstract boolean checkAuthentication();

	protected boolean showTagBody()
	{
		if (checkAuthentication())
		{
			if (log.isTraceEnabled())
			{
				log.trace("Authentication successfully verified.  " + "Tag body will be evaluated.");
			}
			return true;
		}
		else
		{
			if (log.isTraceEnabled())
			{
				log.trace("Authentication verification failed.  " + "Tag body will not be evaluated.");
			}
			return false;
		}
	}
}
