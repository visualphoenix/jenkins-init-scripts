// Create user credentials
def user_credential = { instance, username, password ->
  // Retrieve the Global credential store
  def domain = com.cloudbees.plugins.credentials.domains.Domain.global()
  def store = instance.getExtensionList('com.cloudbees.plugins.credentials.SystemCredentialsProvider')[0].getStore()

  // Set up the local user
  def swarm_cred = new com.cloudbees.plugins.credentials.impl.UsernamePasswordCredentialsImpl(
    com.cloudbees.plugins.credentials.CredentialsScope.GLOBAL,
    "${username}_credential",
    "${username}_credential",
    username,
    password
  )
  store.addCredentials(domain, swarm_cred)
  println "Creating ${username} user credentials"
}
