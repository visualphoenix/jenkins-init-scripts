// Configure global maven options
def maven = { instance, maven_home, maven_opts = '' ->
  assert instance : "Must provide a valid jenkins instance"
  assert maven_home : "Must provide a valid MAVEN_HOME. E.g: '\$MAVEN_HOME'"
  // maven_opts, E.g: -Xmx1024m

  def mavenTask = instance.getExtensionList(
    hudson.tasks.Maven.DescriptorImpl.class
  )[0]
  mavenTask.setInstallations(
    new hudson.tasks.Maven.MavenInstallation(
      "Maven", maven_home, []
    )
  )
  mavenTask.save()

  def maven = instance.getExtensionList(
    hudson.maven.MavenModuleSet.DescriptorImpl.class
  )[0]
  maven.setGlobalMavenOpts(maven_opts)
  maven.save()
  println "Setting default maven tool"
}
