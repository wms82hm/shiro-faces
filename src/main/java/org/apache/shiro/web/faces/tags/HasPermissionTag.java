package org.apache.shiro.web.faces.tags;

import javax.faces.view.facelets.TagConfig;

/**
 * Tag that renders the tag body only if the current user has the string permissions
 * specified in <tt>name</tt> attribute.
 *
 * @author Deluan Quintao
 */
public class HasPermissionTag extends PermissionTagHandler
{
	/**
	 * Creates the TagHandler for the tag that renders the tag body only if the current user has the string permissions
	 * specified in <tt>name</tt> attribute.
	 *
	 * @param config
	 *            The tag configuration containing document definition for the tag handler.
	 */
	public HasPermissionTag(TagConfig config)
	{
		super(config);
	}

	@Override
	protected boolean showTagBody(String p)
	{
		return isPermitted(p);
	}
}
