package org.apache.shiro.web.faces.tags;

import javax.faces.view.facelets.TagConfig;

/**
 * Tag that renders the tag body only if the current user has <em>not</em> executed a successful authentication
 * attempt <em>during their current session</em>.
 * <p>
 * The logically opposite tag of this one is the {@link AuthenticatedTag}.
 * </p>
 *
 * @author Deluan Quintao
 * @author Jeremy Haile
 */
public class NotAuthenticatedTag extends AuthenticatedTag
{
	/**
	 * Creates the TagHandler for the tag that renders the tag body only if the current user has <em>not</em> executed a
	 * successful authentication attempt <em>during their current session</em>.
	 * <p>
	 * The logically opposite tag of this one is the {@link AuthenticatedTag}.
	 * </p>
	 *
	 * @param config
	 *            The tag configuration containing document definition for the tag handler.
	 */
	public NotAuthenticatedTag(TagConfig config)
	{
		super(config);
	}

	@Override
	protected boolean checkAuthentication()
	{
		return !super.checkAuthentication();
	}
}
