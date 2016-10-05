// Configure global ssh credentials
def ssh_credential = { instance, id, username, description, privateKeySource=new com.cloudbees.jenkins.plugins.sshcredentials.impl.BasicSSHUserPrivateKey.UsersPrivateKeySource(), passphrase='' ->
  // Retrieve the Global credential store
  def domain = com.cloudbees.plugins.credentials.domains.Domain.global()
  def store = instance.getExtensionList('com.cloudbees.plugins.credentials.SystemCredentialsProvider')[0].getStore()

  // Create the SSH credential
  def jenkins_creds = new com.cloudbees.jenkins.plugins.sshcredentials.impl.BasicSSHUserPrivateKey(
    com.cloudbees.plugins.credentials.CredentialsScope.GLOBAL,
    id,
    username,
    privateKeySource,
    passphrase,
    description
  )
  store.addCredentials(domain, jenkins_creds)
  println "Adding ${id} ssh credentials"
}
