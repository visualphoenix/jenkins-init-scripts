// Configure the global git tool
def git = { instance, name, email ->
  assert instance
  assert name
  assert email
  def descriptor = instance.getDescriptor("hudson.plugins.git.GitSCM")
  descriptor.setGlobalConfigName(name)
  descriptor.setGlobalConfigEmail(email)
  descriptor.save()
  println "Setting git name to ${name} <${email}>"
}
