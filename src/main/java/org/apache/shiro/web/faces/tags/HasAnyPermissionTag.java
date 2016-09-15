package org.apache.shiro.web.faces.tags;

import javax.faces.view.facelets.TagConfig;

import org.apache.shiro.subject.Subject;

/**
 * Tag that renders the tag body only if the current user has <em>at least one</em> of the comma-delimited
 * string permissions specified in <tt>name</tt> attribute.
 *
 * @author Deluan Quintao
 */
public class HasAnyPermissionTag extends PermissionTagHandler
{
	private static final String PERMISSIONS_DELIMETER = ",";

	/**
	 * Creates the TagHandler for the tag that renders the tag body only if the current user has <em>at least one</em>
	 * of the comma-delimited string permissions specified in <tt>name</tt> attribute.
	 *
	 * @param config
	 *            The tag configuration containing document definition for the tag handler.
	 */
	public HasAnyPermissionTag(TagConfig config)
	{
		super(config);
	}

	@Override
	protected boolean showTagBody(String permissions)
	{
		boolean hasAnyPermission = false;

		Subject subject = getSubject();

		if (subject != null)
		{
			// Iterate through permissions and check to see if the user has one of the permission
			for (String permission : permissions.split(PERMISSIONS_DELIMETER))
			{
				if (subject.isPermitted(permission.trim()))
				{
					hasAnyPermission = true;
					break;
				}
			}
		}

		return hasAnyPermission;
	}
}
