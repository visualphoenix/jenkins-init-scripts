// Configure global admin email
def admin_email = { instance, admin_addr ->
  def jenkinsLocationConfiguration = jenkins.model.JenkinsLocationConfiguration.get()
  jenkinsLocationConfiguration.setAdminAddress(admin_addr)
  jenkinsLocationConfiguration.save()
  println "Setting admin email to ${admin_addr}"
}
