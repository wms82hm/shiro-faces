package org.apache.shiro.web.faces.tags;

import javax.faces.view.facelets.TagConfig;

import org.apache.shiro.subject.Subject;

/**
 * Displays body content if the current user has any of the roles specified.
 *
 * @author Deluan Quintao
 * @author Jeremy Haile
 */
public class HasAnyRolesTag extends PermissionTagHandler
{
	// Delimeter that separates role names in tag attribute
	private static final String ROLE_NAMES_DELIMETER = ",";

	/**
	 * Creates the TagHandler for the tag that renders the tag body only if the current user has any of the roles
	 * specified.
	 *
	 * @param config
	 *            The tag configuration containing document definition for the tag handler.
	 */
	public HasAnyRolesTag(TagConfig config)
	{
		super(config);
	}

	@Override
	protected boolean showTagBody(String roleNames)
	{
		boolean hasAnyRole = false;

		Subject subject = getSubject();

		if (subject != null)
		{
			// Iterate through roles and check to see if the user has one of the roles
			for (String role : roleNames.split(ROLE_NAMES_DELIMETER))
			{
				if (subject.hasRole(role.trim()))
				{
					hasAnyRole = true;
					break;
				}
			}
		}

		return hasAnyRole;
	}
}
