package application;

import java.io.Serializable;

/**
*
* The class {@code Message} defines a simple message model.
*
**/

public final class Message implements Serializable
{
  private static final long serialVersionUID = 1L;

  private User user;
  private String content;

  /**
   * Class constructor.
   *
   * @param u  the user.
   * @param c  the content.
   *
  **/
  public Message(final User u, final String c)
  {
    this.user    = u;
    this.content = c;
  }

  /**
   * Gets the user.
   *
   * @return the user.
   *
  **/
  public User getUser()
  {
    return this.user;
  }

  /**
   * Gets the content.
   *
   * @return the content.
   *
  **/
  public String getContent()
  {
    return this.content;
  }

  /**
   * Sets the content.
   *
   * @param c  the content.
   *
  **/
  public void setContent(final String c)
  {
    this.content = c;
  }
}