package org.apache.shiro.web.faces.tags;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Modifier;

import javax.faces.context.FacesContext;

import org.apache.shiro.subject.PrincipalCollection;

/**
 * <p>
 * Tag used to print out the String value of a user's default principal,
 * or a specific principal as specified by the tag's attributes.
 * </p>
 * <p>
 * If no attributes are specified, the tag prints out the <tt>toString()</tt>
 * value of the user's default principal. If the <tt>type</tt> attribute
 * is specified, the tag looks for a principal with the given type. If the
 * <tt>property</tt> attribute is specified, the tag prints the string value of
 * the specified property of the principal. If no principal is found or the user
 * is not authenticated, the tag displays nothing unless a <tt>defaultValue</tt>
 * is specified.
 * </p>
 *
 * @author Jeremy Haile
 * @author Deluan Quintao
 */
public class PrincipalTag extends SecureComponent
{
	/**
	 * The type of principal to be retrieved, or null if the default principal should be used.
	 */
	private String type;
	/**
	 * The property name to retrieve of the principal, or null if the <tt>toString()</tt> value should be used.
	 */
	private String property;
	/**
	 * The default value that should be displayed if the user is not authenticated, or no principal is found.
	 */
	private String defaultValue;

	/*--------------------------------------------
	|  A C C E S S O R S / M O D I F I E R S    |
	============================================*/
	/**
	 * Return the type of the principal to retrieve.
	 *
	 * @return The type of the principal to retrieve.
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * Sets the type of the principal to be retrieved.
	 *
	 * @param type
	 *            The type of the principal to be retrieved.
	 */
	public void setType(String type)
	{
		this.type = type;
	}

	/**
	 * Returns the property name to retrieve the principal.
	 *
	 * @return The property name to retrieve the principal.
	 */
	public String getProperty()
	{
		return property;
	}

	/**
	 * Sets the property name to retrieve the principal.
	 *
	 * @param property
	 *            The property name to retrieve the principal.
	 */
	public void setProperty(String property)
	{
		this.property = property;
	}

	/**
	 * Returns the default value that should be displayed if the user is not authenticated, or no principal is found.
	 *
	 * @return The default value that should be displayed if the user is not authenticated, or no principal is found.
	 */
	public String getDefaultValue()
	{
		return defaultValue;
	}

	/**
	 * Sets she default value that should be displayed if the user is not authenticated, or no principal is found.
	 *
	 * @param defaultValue
	 *            The default value that should be displayed if the user is not authenticated, or no principal is found.
	 */
	public void setDefaultValue(String defaultValue)
	{
		this.defaultValue = defaultValue;
	}

	/*--------------------------------------------
	|               M E T H O D S               |
	============================================*/
	@Override
	@SuppressWarnings({ "unchecked" })
	protected void doEncodeAll(FacesContext ctx) throws IOException
	{
		String strValue = null;

		try
		{
			if (getSubject() != null)
			{
				// Get the principal to print out
				Object principal;

				if (type == null)
				{
					principal = getSubject().getPrincipal();
				}
				else
				{
					principal = getPrincipalFromClassName();
				}

				// Get the string value of the principal
				if (principal != null)
				{
					if (property == null)
					{
						strValue = principal.toString();
					}
					else
					{
						strValue = getPrincipalProperty(principal, property);
					}
				}
			}
		}
		catch (Exception e)
		{
			log.error("Error getting principal type [" + type + "], property [" + property + "]: " + e.getMessage(), e);
		}

		if (strValue == null)
		{
			strValue = defaultValue;
		}

		// Print out the principal value if not null
		if (strValue != null)
		{
			try
			{
				ctx.getResponseWriter().write(strValue);
			}
			catch (IOException e)
			{
				throw new IOException("Error writing [" + strValue + "] to output.");
			}
		}
	}

	@SuppressWarnings({ "unchecked" })
	private Object getPrincipalFromClassName()
	{
		Object principal = null;

		try
		{
			Class cls = Class.forName(type);
			PrincipalCollection principals = getSubject().getPrincipals();
			if (principals != null)
			{
				principal = principals.oneByType(cls);
			}
		}
		catch (ClassNotFoundException e)
		{
			if (log.isErrorEnabled())
			{
				log.error("Unable to find class for name [" + type + "]");
			}
		}
		catch (Exception e)
		{
			if (log.isErrorEnabled())
			{
				log.error("Unknown error while getting principal for type [" + type + "]: " + e.getMessage(), e);
			}
		}
		return principal;
	}

	private String getPrincipalProperty(Object principal, String property) throws IOException
	{
		String strValue = null;

		try
		{
			BeanInfo bi = Introspector.getBeanInfo(principal.getClass());

			// Loop through the properties to get the string value of the specified property
			boolean foundProperty = false;
			for (PropertyDescriptor pd : bi.getPropertyDescriptors())
			{
				if (pd.getName().equals(property) && Modifier.isPublic(pd.getReadMethod().getModifiers()))
				{
					Object value = null;
					try
					{
						pd.getReadMethod().setAccessible(true);
						value = pd.getReadMethod().invoke(principal, (Object[]) null);
					}
					finally
					{
						pd.getReadMethod().setAccessible(false);
					}
					strValue = String.valueOf(value);
					foundProperty = true;
					break;
				}
			}

			if (!foundProperty)
			{
				final String message = "Property [" + property + "] not found in principal of type ["
						+ principal.getClass().getName() + "]";
				if (log.isErrorEnabled())
				{
					log.error(message);
				}
				throw new IOException(message);
			}

		}
		catch (Exception e)
		{
			final String message = "Error reading property [" + property + "] from principal of type ["
					+ principal.getClass().getName() + "]";
			if (log.isErrorEnabled())
			{
				log.error(message, e);
			}
			throw new IOException(message);
		}

		return strValue;
	}

	// ----------------------------------------------------- StateHolder Methods
	private Object[] values;

	@Override
	public Object saveState(FacesContext context)
	{
		if (values == null)
		{
			values = new Object[4];
		}
		values[0] = super.saveState(context);
		values[1] = type;
		values[2] = property;
		values[3] = defaultValue;

		return values;
	}

	@Override
	public void restoreState(FacesContext context, Object state)
	{
		values = (Object[]) state;
		super.restoreState(context, values[0]);
		type = (String) values[1];
		property = (String) values[2];
		defaultValue = (String) values[3];
	}
}
