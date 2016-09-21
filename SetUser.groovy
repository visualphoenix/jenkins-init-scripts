// Set up local users or modify existing ones
def set_user = { username, fullname, email ->
  def user = hudson.model.User.get(username)
  user.setFullName(fullname)
  user.addProperty(new hudson.tasks.Mailer.UserProperty(email))
  user
}
