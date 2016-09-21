// Configure global java tools
def java = { instance, java_name, java_home ->
  def descriptor = instance.getDescriptor("hudson.model.JDK")

  if (descriptor.getInstallations()) {
      println "Skipping ${java_name} tool"
  } else {
      def jdk = new hudson.model.JDK(java_name, java_home)
      descriptor.setInstallations(jdk)
      println "Adding ${java_name} tool"
  }
}
