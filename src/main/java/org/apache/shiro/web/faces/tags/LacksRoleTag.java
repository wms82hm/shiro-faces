package org.apache.shiro.web.faces.tags;

import javax.faces.view.facelets.TagConfig;

/**
 * Tag that renders the tag body only if the current user has not the string role specified in <tt>name</tt>
 * attribute.
 *
 * @author Deluan Quintao
 */
public class LacksRoleTag extends PermissionTagHandler
{
	/**
	 * Creates the TagHandler for the tag that renders the tag body only if the current user has not the string
	 * role specified in <tt>name</tt> attribute.
	 *
	 * @param config
	 *            The tag configuration containing document definition for the tag handler.
	 */
	public LacksRoleTag(TagConfig config)
	{
		super(config);
	}

	@Override
	protected boolean showTagBody(String roleName)
	{
		boolean hasRole = getSubject() != null && getSubject().hasRole(roleName);
		return !hasRole;
	}
}