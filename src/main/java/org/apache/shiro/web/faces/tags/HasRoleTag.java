package org.apache.shiro.web.faces.tags;

import javax.faces.view.facelets.TagConfig;

/**
 * Displays body content if the current user has the specified roles.
 * 
 * @author Deluan Quintao
 */
public class HasRoleTag extends PermissionTagHandler
{
	/**
	 * Creates the TagHandler for the tag that renders the tag body only if the current user has the specified roles.
	 *
	 * @param config
	 *            The tag configuration containing document definition for the tag handler.
	 */
	public HasRoleTag(TagConfig config)
	{
		super(config);
	}

	@Override
	protected boolean showTagBody(String roleName)
	{
		return getSubject() != null && getSubject().hasRole(roleName);
	}
}