// Configure global slave agent port
def agent_port = { instance, port ->
  assert instance
  assert port
  instance.setSlaveAgentPort(port)
  println "Setting slave agent port for jnlp to ${port}"
}
