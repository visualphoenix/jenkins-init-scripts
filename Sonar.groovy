// Configure global sonar
def sonar = { instance, sonar_host, sonar_addr, sonar_db, sonar_user, sonar_password ->
  assert instance : "Must pass a valid jenkins instance. E.g: Jenkins.getInstance()"
  assert sonar_host : "Must pass a valid sonar host. E.g: localhost"
  assert sonar_addr : "Must pass a valid sonar address. E.g: http://${sonar_host}:9000"
  assert sonar_db : "Must pass a valid sonar db connection. E.g: jdbc:mysql://${sonar_host}:3306/sonar?useUnicode=true&characterEncoding=utf8&rewriteBatchedStatements=true&useConfigs=maxPerformance&autoReconnect=true"
  assert sonar_user : "Must pass a valid sonar user"
  assert sonar_password : "Must pass a valid sonar password"

  def descriptor = instance.getDescriptor("hudson.plugins.sonar.SonarGlobalConfiguration")
  def sonar = new hudson.plugins.sonar.SonarInstallation(
    sonar_host,
    sonar_addr,
    "5.1",
    "",
    sonar_db,
    sonar_user,
    sonar_password,
    "",
    "-Dsonar.sourceEncoding=\"UTF-8\"",
    new hudson.plugins.sonar.model.TriggersConfig(),
    sonar_user,
    sonar_password,
    ""
  )
  descriptor.setInstallations(sonar)
  descriptor.save()
  println "Setting up global Sonar installation"
}
